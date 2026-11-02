package com.medayes.erp.ErpApi.services.production;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medayes.erp.ErpApi.dto.request.production.*;
import com.medayes.erp.ErpApi.dto.response.production.*;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleResponseDto;
import com.medayes.erp.ErpApi.entity.IdempotencyEntity;
import com.medayes.erp.ErpApi.exeption.ConflictException;

import com.medayes.erp.ErpApi.repository.production.*;
import com.medayes.erp.ErpApi.utils.IdempotencyStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.stream.Collectors;



@Slf4j
@Service
@RequiredArgsConstructor
public class SeedSowingServicesImpl implements SeedSowingServices {

    private final SeedSowingRepository sowingRepository;
    private final SeedSowingUpdateRepository sowingUpdateRepository;
    private  final FarmersLotNoRepository farmersLotNoRepository;
    private  final OperationRepository operationRepository;
    private  final  FieldOperationDetailsRepository fieldOperationDetailsRepository;
    private final IdempotencyReservationService idempotencyReservationService;
    private final FieldOperationRepository fieldOperationRepository;
    private final ReportRepository repository;
    private final QcInputDataRepository qcInputDataRepository;
    private  final  FieldOperationDetSaveRepository fieldOperationDetSaveRepository;
    private  final FieldOperationSaveNumberRepository fieldOperationSaveNumberRepository;
    private  final  QualityCheckRepository qualityCheckRepository;
    private  final  ReasonRepository reasonRepository;
    private  final QualityCheckUpdateRepository updateRepository;
    private  final  OrganiserRepository  organiserRepository;

    private final ObjectMapper objectMapper;

    @Override
    public TaskMastResponsibleResponseDto<?> getSeedSowing(
            SeedSowingRequestSto request) {

        List<SeedSowingResponseDto> response =
                sowingRepository.seedSowingResponses(request);

        if (response == null || response.isEmpty()) {

            return successResponse(
                    null,
                    "Data not found",
                    "false"
            );
        }



        List<OrganizationSeedSowingDto> result =
                response.stream()
                        .collect(Collectors.groupingBy(
                                SeedSowingResponseDto::getOrgName,
                                LinkedHashMap::new,
                                Collectors.toList()
                        ))
                        .entrySet()
                        .stream()
                        .map(entry -> new OrganizationSeedSowingDto(
                                entry.getKey(),
                                entry.getValue()
                        ))
                        .toList();


        return successResponse(
                result,
                "Data fetch successful",
                "true"
        );
    }

    @Override
    @Transactional
    public TaskMastResponsibleResponseDto<?> updateSeedSowing(SeedSowingUpdateRequestDto seedSowingRequestSto, String idempotencyKey) {
        Optional<IdempotencyEntity> existing = idempotencyReservationService.reserve(idempotencyKey);

        if (existing.isPresent()) {
            IdempotencyEntity record = existing.get();
            IdempotencyStatus status = IdempotencyStatus.valueOf(record.getStatus());

            switch (status) {
                case COMPLETED:
                    return deserialize(record.getResponseBody());
                case PROCESSING:
                    throw new ConflictException("Request already in progress");
                case FAILED:
                    // fall through — retry below, reusing the same key
                    break;
            }
        }

        // Step 2: Do the actual work (in its own transaction, separate from reservation bookkeeping)
        try {
            TaskMastResponsibleResponseDto<?> response = doUpdate(seedSowingRequestSto);
            idempotencyReservationService.markCompleted(idempotencyKey, serialize(response));
            return response;
        } catch (Exception ex) {
            idempotencyReservationService.markFailed(idempotencyKey);
            throw ex;
        }
    }

    @Override
    public TaskMastResponsibleResponseDto<?> generateFarmerLotNo(SeedSowingUpdateRequestDto seedSowingRequestSto, String idempotencyKey) {
       /* Optional<IdempotencyEntity> existing = idempotencyReservationService.reserve(idempotencyKey);

        if (existing.isPresent()) {
            IdempotencyEntity record = existing.get();
            IdempotencyStatus status = IdempotencyStatus.valueOf(record.getStatus());

            switch (status) {
                case COMPLETED:
                    return deserialize(record.getResponseBody());
                case PROCESSING:
                    throw new ConflictException("Request already in progress");
                case FAILED:
                    // fall through — retry below, reusing the same key
                    break;
            }
        }*/
        try {
            TaskMastResponsibleResponseDto<?> response = generateFarmerLotNo(seedSowingRequestSto);
           // idempotencyReservationService.markCompleted(idempotencyKey, serialize(response));
            return response;
        } catch (Exception ex) {
           // idempotencyReservationService.markFailed(idempotencyKey);
            throw ex;
        }

        // Step 2: Do the actual work (in its own transaction, separate from reservation bookkeeping)

    }


    @Override
    public TaskMastResponsibleResponseDto<?> fetchFiledOperation(
            OperationRequestDto request) {

        log.debug("Fetching field operation data");



        List<OperationResponseDto> response =
                operationRepository.responseData(request);

        if (response == null || response.isEmpty()) {
            return successResponse(
                    null,
                    "Data not found",
                    "false"
            );
        }

       /* int totalElements = response.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        long startLong = (long) page * size;

        List<OperationResponseDto> content;

        if (startLong >= totalElements) {
            content = java.util.Collections.emptyList();
        } else {
            int start = (int) startLong;
            int end = (int) Math.min(startLong + size, totalElements);

            content = response.subList(start, end);
        }

        Map<String, Object> pageResponse = new java.util.LinkedHashMap<>();
        pageResponse.put("content", content);
        pageResponse.put("page", page);
        pageResponse.put("size", size);
        pageResponse.put("totalElements", totalElements);
        pageResponse.put("totalPages", totalPages);
        pageResponse.put("first", page == 0);
        pageResponse.put("last", page >= totalPages - 1);

        log.debug("Page: {}, Size: {}, Total records: {}",
                page, size, totalElements);
*/
        return successResponse(
                response,
                "Data fetch successful",
                "true"
        );
    }

    @Override
    public ReportResponseDto<?> fetchIssueOrganiser(
            ReportRequestDto dto) {

        List<ReportReposeDto> response =
                repository.getIssuedOrganiserStockSummary(dto);

        if (response == null || response.isEmpty()) {
            return new  ReportResponseDto<>(
                    "false",
                    "Data not found",  HttpStatus.NOT_FOUND.value()
                    ,null,null,null
            );
        }
        Double total=  response.stream()
                .mapToDouble(item -> parseQuantity(item.getDistrQty()))
                .sum();
        Double totalKg=  response.stream()
                .mapToDouble(item -> parseQuantity(item.getDistrQtyKg()))
                .sum();
        // Group data by orgName
        List<IssueOrgResponseDto> result = response.stream()
                .filter(item -> item.getOrgName() != null)
                .collect(Collectors.groupingBy(ReportReposeDto::getOrgName))
                .entrySet()
                .stream()
                .map(entry -> {

                    List<ReportReposeDto> list = entry.getValue();


                    return IssueOrgResponseDto.builder()
                            .orgName(entry.getKey())
                            .dto(list)
                            .totalQuantity(
                                    list.stream()
                                            .mapToDouble(item -> parseQuantity(item.getDistrQty()))
                                            .sum()

                            )
                            .totalQuantityKg(
                                    list.stream()
                                            .mapToDouble(item -> parseQuantity(item.getDistrQtyKg()))
                                            .sum()

                            )

                            .build();
                })
                .collect(Collectors.toList());

        return new  ReportResponseDto<>(
                "false",
                "Data fetch successful",  HttpStatus.OK.value()
                ,result,total,totalKg
        );

    }

    @Override
    public ReportOrganiserStockResponseDto<?> fetchOrganiserStockLedge(ReportRequestDto dto) {
        List<StockLedgerResponse> response =
                repository.getOrganiserStockLedger(dto);

        if (response == null || response.isEmpty()) {
            return new  ReportOrganiserStockResponseDto<>(
                    "false",
                    "Data not found",  HttpStatus.NOT_FOUND.value()
                    ,null,null,null,null,null,null,null
            );
        }
        Comparator<String> alphabeticalOrder =
                String.CASE_INSENSITIVE_ORDER.thenComparing(Comparator.naturalOrder());
        Double receiptQtyTotal=  response.stream()
                .mapToDouble(item -> parseQuantity(item.getReceiptQty()))
                .sum();
        Double receiptQtyKgTotal=  response.stream()
                .mapToDouble(item -> parseQuantity(item.getReceiptQtyKg()))
                .sum();
        Double issueQtyTotal=  response.stream()
                .mapToDouble(item -> parseQuantity(item.getIssueQty()))
                .sum();
        Double issueQtyKgTotal=  response.stream()
                .mapToDouble(item -> parseQuantity(item.getIssueQtyKg()))
                .sum();
        Double balanceQtTotal=  response.stream()
                .mapToDouble(item -> parseQuantity(item.getBalanceQty()))
                .sum();
        Double qtyInKgTotal=  response.stream()
                .mapToDouble(item -> parseQuantity(item.getQtyInKg()))
                .sum();
        Map<String, Map<String, List<StockLedgerResponse>>> groupedData =
                response.stream()
                        .filter(item -> item.getOrgName() != null)
                        .filter(item -> item.getItemName() != null)
                        .collect(Collectors.groupingBy(
                                StockLedgerResponse::getOrgName,
                                Collectors.groupingBy(
                                        StockLedgerResponse::getItemName
                                )
                        ));

        List<StockOrgResponseDto> result = groupedData.entrySet()
                .stream()
                .flatMap(orgEntry -> orgEntry.getValue().entrySet()
                        .stream()

                        .map(productEntry -> {

                            List<StockLedgerResponse> list = productEntry.getValue();

                            return StockOrgResponseDto.builder()
                                    .orgName(orgEntry.getKey())

                                    .productName(productEntry.getKey())
                                    .dto(list)

                                    .receiptQty(list.stream()
                                            .mapToDouble(item -> parseQuantity(item.getReceiptQty()))
                                            .sum())

                                    .receiptQtyKg(list.stream()
                                            .mapToDouble(item -> parseQuantity(item.getReceiptQtyKg()))
                                            .sum())

                                    .issueQty(list.stream()
                                            .mapToDouble(item -> parseQuantity(item.getIssueQty()))
                                            .sum())

                                    .issueQtyKg(list.stream()
                                            .mapToDouble(item -> parseQuantity(item.getIssueQtyKg()))
                                            .sum())

                                    .balanceQty(list.stream()
                                            .mapToDouble(item -> parseQuantity(item.getBalanceQty()))
                                            .sum())

                                    .qtyInKg(list.stream()
                                            .mapToDouble(item -> parseQuantity(item.getQtyInKg()))
                                            .sum())

                                    .build();
                        }))
                .collect(Collectors.toList());

        return new  ReportOrganiserStockResponseDto<>(

                "false",
                "Data fetch successful",  HttpStatus.OK.value()
                ,result,receiptQtyTotal,receiptQtyKgTotal,issueQtyTotal,issueQtyKgTotal,balanceQtTotal,qtyInKgTotal

        );
    }

    @Override
    public TaskMastResponsibleResponseDto<?> fetchOrganiserSummary(ReportRequestDto dto) {
        List<OrganiserSummaryResponseDto> response =
                repository.getOrganiserStockSummary(dto);

        if (response == null || response.isEmpty()) {

            return successResponse(
                    null,
                    "Data not found",
                    "false"
            );
        }
        return successResponse(
                response,
                "Data fetch successful",
                "true"
        );
    }

    @Override
    public TaskMastResponsibleResponseDto<?> fetchFiledOperationDetails(OperationRequestDto operationRequestDto) {
        List<OperationResponseDto> response =
                fieldOperationDetailsRepository.responseData(operationRequestDto);

        if (response == null || response.isEmpty()) {

            return successResponse(
                    null,
                    "Data not found",
                    "false"
            );
        }
        return successResponse(
                response,
                "Data fetch successful",
                "true"
        );
    }

    @Override
    public TaskMastResponsibleResponseDto<?> fetchFiledOperations(OperationRequestDto operationRequestDto) {
        List<OperationFiledResponseDto> response =
                fieldOperationRepository.responseData(operationRequestDto);

        if (response == null || response.isEmpty()) {

            return successResponse(
                    null,
                    "Data not found",
                    "false"
            );
        }
        return successResponse(
                response,
                "Data fetch successful",
                "true"
        );

    }

    @Override
    public TaskMastResponsibleResponseDto<?> fetchQCInputType(OperationRequestDto operationRequestDto) {
        List<OperationFiledResponseDto> response =
                qcInputDataRepository.responseData(operationRequestDto);

        if (response == null || response.isEmpty()) {

            return successResponse(
                    null,
                    "Data not found",
                    "false"
            );
        }
        return successResponse(
                response,
                "Data fetch successful",
                "true"
        );

    }

    @Override
    public TaskMastResponsibleResponseDto<?> operationDetSave(OperationRequestDto operationRequestDto) {
        List<OperationFiledResponseDto> response =
                fieldOperationDetSaveRepository.responseData(operationRequestDto);

        if (response == null || response.isEmpty()) {

            return successResponse(
                    null,
                    "Data not save",
                    "false"
            );
        }
        return successResponse(
                response,
                "Data save successful",
                "true"
        );
    }

    @Override
    public TaskMastResponsibleResponseDto<?> filedOperationGenerate(SeedSowingUpdateRequestDto seedSowingRequestSto) {
        try {
            TaskMastResponsibleResponseDto<?> response = generateFiledNo(seedSowingRequestSto);
            // idempotencyReservationService.markCompleted(idempotencyKey, serialize(response));
            return response;
        } catch (Exception ex) {
            // idempotencyReservationService.markFailed(idempotencyKey);
            throw ex;
        }

    }

    @Override
    public TaskMastResponsibleResponseDto<?> fetchQualityCheck(QualityCheckRequestDto dto) {

        List<QualityCheckResponseDto> response =
                qualityCheckRepository.response(dto);

        if (response == null || response.isEmpty()) {

            return successResponse(
                    null,
                    "Data not found",
                    "false"
            );
        }
        return successResponse(
                response,
                "Data fetch successful",
                "true"
        );

    }

    @Override
    public TaskMastResponsibleResponseDto<?> fetchReason(QualityCheckRequestDto dto) {

            List<ReasonResponseDto> response=   reasonRepository.response(dto);

        if (response == null || response.isEmpty()) {

            return successResponse(
                    null,
                    "Data not found",
                    "false"
            );
        }
        return successResponse(
                response,
                "Data fetch successful",
                "true"
        );



    }

    @Override
    public TaskMastResponsibleResponseDto<?> fetchQualityCheckUpdate(QualityCheckUpdateRequestDto dto) {
        List<QualityCheckUpdateResponseDto> response =
                updateRepository.response(dto);

        if (response == null || response.isEmpty()) {

            return successResponse(
                    null,
                    "Data not save",
                    "false"
            );
        }
        return successResponse(
                response,
                "Data save successful",
                "true"
        );

    }

    @Override
    public TaskMastResponsibleResponseDto<?> fetchReasonType(QualityCheckRequestDto dto) {
        List<ReasonResponseDto> response=   reasonRepository.responseType(dto);

        if (response == null || response.isEmpty()) {

            return successResponse(
                    null,
                    "Data not found",
                    "false"
            );
        }
        return successResponse(
                response,
                "Data fetch successful",
                "true"
        );
    }

    @Override
    public TaskMastResponsibleResponseDto<?> fetchOrganiser(QualityCheckRequestDto dto) {
        List<OrganiserResponseDto> response=   organiserRepository.response(dto);

        if (response == null || response.isEmpty()) {

            return successResponse(
                    null,
                    "Data not found",
                    "false"
            );
        }
        return successResponse(
                response,
                "Data fetch successful",
                "true"
        );
    }

    @Override
    public TaskMastResponsibleResponseDto<?> fetchFarmer(QualityCheckRequestDto dto) {
        List<OrganiserResponseDto> response=   organiserRepository.responseFarmer(dto);

        if (response == null || response.isEmpty()) {

            return successResponse(
                    null,
                    "Data not found",
                    "false"
            );
        }
        return successResponse(
                response,
                "Data fetch successful",
                "true"
        );
    }


    @Transactional
    protected TaskMastResponsibleResponseDto<?> doUpdate(SeedSowingUpdateRequestDto requestDto) {
        SeedSowingUpdateDto seedSowingUpdateDto = sowingUpdateRepository.updateSeedSowing(requestDto);
        return successResponse(
                seedSowingUpdateDto,
                "Data update successful",
                "true"
        );
    }
    @Transactional
    protected TaskMastResponsibleResponseDto<?> generateFarmerLotNo(SeedSowingUpdateRequestDto requestDto) {
        SeedSowingUpdateDto seedSowingUpdateDto = farmersLotNoRepository.generateFarmersLotNo(requestDto);
        return successResponse(
                seedSowingUpdateDto,
                "Data update successful",
                "true"
        );
    }
    @Transactional
    protected TaskMastResponsibleResponseDto<?> generateFiledNo(SeedSowingUpdateRequestDto requestDto) {
        SeedSowingUpdateDto seedSowingUpdateDto = fieldOperationSaveNumberRepository.generateFieldOperationNumber(requestDto);
        return successResponse(
                seedSowingUpdateDto,
                "Data update successful",
                "true"
        );
    }

    private String serialize(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize idempotency response", e);
        }
    }

    private TaskMastResponsibleResponseDto<?> deserialize(String json) {
        try {
            return objectMapper.readValue(json, TaskMastResponsibleResponseDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize idempotency response", e);
        }
    }

    private <T> TaskMastResponsibleResponseDto<T> successResponse(
            T data,
            String message,
            String status) {

        return new TaskMastResponsibleResponseDto<>(
                status,
                message,
                HttpStatus.OK.value(),
                data
        );
    }
    private double parseQuantity(String value) {

        if (value == null || value.trim().isEmpty()) {
            return 0.0;
        }

        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}