package com.medayes.erp.ErpApi.services.indent.impl;

import com.medayes.erp.ErpApi.config.ModelMapperConfig;
import com.medayes.erp.ErpApi.dto.request.indent.PendingPurchaseRequisitionRequestDTO;
import com.medayes.erp.ErpApi.dto.request.indent.PendingPurchaseRequisitionRequestStatusDTO;
import com.medayes.erp.ErpApi.dto.request.indent.RequirementSlipApprovalRequestDto;
import com.medayes.erp.ErpApi.dto.request.indent.RequirementSlipRequestDto;
import com.medayes.erp.ErpApi.dto.response.indent.*;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleResponseDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleTempResponseDto;
import com.medayes.erp.ErpApi.exeption.BusinessException;
import com.medayes.erp.ErpApi.exeption.DatabaseException;
import com.medayes.erp.ErpApi.repository.indent.*;
import com.medayes.erp.ErpApi.services.indent.IndentServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;



import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IndentServicesImpl  implements IndentServices {

    private final RequirementSlipRepository repository;
    private final RequirementSlipPermissionRepository repositorys;
    private final RequirementSlipApprovalListRepository requirementSlipApprovalRepository;
    private final RequirementSlipApprovalRepository requirementSlipApprovalRepositorys;
    private final PendingPurchaseRequisitionRepository pendingPurchaseRequisitionRepository;
    @Autowired
    private ModelMapperConfig modelMapperConfig;

    @Override
    public TaskMastResponsibleResponseDto<List<RequirementSlipDto>> getRequirementSlip(
            RequirementSlipRequestDto requestDto) {

        List<RequirementSlipReposeDto> result =
                repository.fetchRequirementSlip(requestDto);

        if (result == null || result.isEmpty()) {
            throw new BusinessException("No data found");
        }

        Map<String, RequirementSlipDto> map = new LinkedHashMap<>();

        for (RequirementSlipReposeDto r : result) {

            ItemRequirementSlipDto item = new ItemRequirementSlipDto(
                    r.itemType(),
                    r.itemCode(),
                    r.itemTypeName(),
                    r.itemName(),
                    r.size(),
                    r.color(),
                    r.baseUom(),
                    r.rsQty(),
                    r.rsRaisedQty(),
                    r.remarks(),
                    r.status()

            );

//            if (!map.containsKey(key)) {
//                map.put(key, new DTO());
//            }
//            DTO dto = map.get(key);
            map.computeIfAbsent(r.rsNo(), k ->
                    new RequirementSlipDto(
                            r.rsNo(),
                            r.rsDate(),
                            r.remarks(),
                            r.status(),
                            new ArrayList<>()
                    )
            ).items().add(item);
        }

        List<RequirementSlipDto> finalList =
                new ArrayList<>(map.values());

        return new TaskMastResponsibleResponseDto<>(
                "true",
                "Data fetch successful",
                HttpStatus.OK.value(),
                finalList
        );
    }

    @Override
    public TaskMastResponsibleResponseDto<List<RequirementSlipReposeDto>> getRequirementApprovalSlip(RequirementSlipRequestDto requestDto) {
        try {
            List<RequirementSlipReposeDto> result = requirementSlipApprovalRepository.fetchRequirementSlip(requestDto);

            if (result == null || result.isEmpty()) {
                throw new BusinessException(" No data found");

            }

            return new TaskMastResponsibleResponseDto<>("true",
                    "Data fetch successful",
                    HttpStatus.OK.value(),
                    result);
        } catch (DataAccessException ex) {
            throw new DatabaseException(
                    "Database error occurred: " + ex.getMostSpecificCause().getMessage(), ex
            );


        }
    }

    @Override
    public TaskMastResponsibleResponseDto<List<PermissionsResponseDto>> getRequirementPermission(RequirementSlipRequestDto requestDto) {
        try {
            List<PermissionResponseDto> responseDtos = repositorys.getPermission(requestDto);
            if (responseDtos.isEmpty() || responseDtos == null) {
                throw new BusinessException("No data found");
            }
            List<PermissionsResponseDto> permissionsResponseDtos = responseDtos.stream().flatMap(
                            (data -> {
                                List<PermissionsResponseDto> list = new ArrayList<>();
                                if ("Y".equalsIgnoreCase(data.REPORTING())) {
                                    list.add(new PermissionsResponseDto(
                                            "Reporting Person", "0", data.REPORTING()));
                                }

                                if ("Y".equalsIgnoreCase(data.SEQ1())) {
                                    list.add(new PermissionsResponseDto(
                                            "Project Head", "1", data.SEQ1()));
                                }

                                if ("Y".equalsIgnoreCase(data.SEQ2())) {
                                    list.add(new PermissionsResponseDto(
                                            "Asset Manager", "2", data.SEQ2()));
                                }
                                return list.stream();
                            }))

                    .collect(Collectors.toList());
            return new TaskMastResponsibleResponseDto<>("true",
                    "Data fetch successful",
                    HttpStatus.OK.value(),
                    permissionsResponseDtos);

        } catch (DataAccessException ex) {
            throw new DatabaseException("Database error occurred: " + ex.getMostSpecificCause().getMessage(), ex);
        }

    }

    @Override
    public TaskMastResponsibleTempResponseDto requirementApprovalSlip(RequirementSlipApprovalRequestDto requestDto) {
        try {
            String save = requirementSlipApprovalRepositorys
                    .saveRequirementSlipApproval(requestDto);
            if (save == null || save.isEmpty() || "N".equalsIgnoreCase(save)) {

                throw new BusinessException("Not RequirementSlipApproval");

            } else {

                return new TaskMastResponsibleTempResponseDto(
                        "true",
                        "Request processed successfully",
                        HttpStatus.OK.value()
                );
            }

        } catch (DataAccessException ex) {
            throw new DatabaseException("Database error occurred: " + ex.getMostSpecificCause().getMessage(), ex);
        }
    }

    @Override
    public TaskMastResponsibleResponseDto<List<PendingPurchaseRequisitionResponseDTO>> getPendingPurchaseRequisition(PendingPurchaseRequisitionRequestDTO requestDto) {
        try {
            List<PendingPurchaseRequisitionDTO> result = pendingPurchaseRequisitionRepository.response(requestDto);


            if (result == null || result.isEmpty()) {
                throw new BusinessException(" No data found");

            }

            Map<String, PendingPurchaseRequisitionResponseDTO> map = new LinkedHashMap<>();
            for (PendingPurchaseRequisitionDTO requisitionDTO : result) {
                ItemPendingPurchaseRequisitionDTO dto = new ItemPendingPurchaseRequisitionDTO(
                        requisitionDTO.getItemType(),
                        requisitionDTO.getProductCode(),
                        requisitionDTO.getItemTypeName(),
                        requisitionDTO.getItemName(),
                        requisitionDTO.getSize(),
                        requisitionDTO.getColor(),
                        requisitionDTO.getPurUom(),
                        requisitionDTO.getRsBalanceQty(),
                        requisitionDTO.getPrRaisedQty(),
                        requisitionDTO.getClosingStk(),
                        requisitionDTO.getRsQty(),
                        requisitionDTO.getBranchName(),
                        requisitionDTO.getBranchCode(),
                        requisitionDTO.getRemarks(),
                        requisitionDTO.getConvFactor(),
                        requisitionDTO.getPurUom(),
                        requisitionDTO.getBaseUom(),
                        requisitionDTO.getRefFinItemCode()


                );
                map.computeIfAbsent(requisitionDTO.getRsNo(), k ->
                        new PendingPurchaseRequisitionResponseDTO(
                                requisitionDTO.getRsNo(),
                                requisitionDTO.getRsDate(),

                                new ArrayList<>()
                        )
                ).items().add(dto);
            }
            List<PendingPurchaseRequisitionResponseDTO> finalList =
                    new ArrayList<>(map.values());
            return new TaskMastResponsibleResponseDto<>("true",
                    "Data fetch successful",
                    HttpStatus.OK.value(),
                    finalList);
        } catch (DataAccessException ex) {
            throw new DatabaseException(
                    "Database error occurred: " + ex.getMostSpecificCause().getMessage(), ex
            );


        }
    }

    @Override
    public TaskMastResponsibleTempResponseDto saveInDet(IssueRequestDTO dto) {
        try {
            String save = pendingPurchaseRequisitionRepository
                    .savePRDETINSUPDDEL(dto);
            if (save == null || save.isEmpty() || "N".equalsIgnoreCase(save)) {

                throw new BusinessException("Not RequirementSlipApproval");

            } else {

                return new TaskMastResponsibleTempResponseDto(
                        "true",
                        "Request processed successfully",
                        HttpStatus.OK.value()
                );
            }

        } catch (DataAccessException ex) {
            throw new DatabaseException("Database error occurred: " + ex.getMostSpecificCause().getMessage(), ex);
        }
    }

    @Override
    public TaskMastResponsibleTempResponseDto saveInHrd(IssueHeaderRequestDTO dto) {
        try {
            String save = pendingPurchaseRequisitionRepository
                    .savePRHDRINSUPDDEL(dto);
            if (save == null || save.isEmpty() || "N".equalsIgnoreCase(save)) {

                throw new BusinessException("Not RequirementSlipApproval");

            } else {

                return new TaskMastResponsibleTempResponseDto(
                        "true",
                        "Request processed successfully",
                        HttpStatus.OK.value()
                );
            }

        } catch (DataAccessException ex) {
            throw new DatabaseException("Database error occurred: " + ex.getMostSpecificCause().getMessage(), ex);
        }
    }

    @Override
    public TaskMastResponsibleResponseDto<List<PurchaseRequisitionResponseDTO>> getPendingPurchaseRequisitionStatus(PendingPurchaseRequisitionRequestStatusDTO requestDto) {
        try {
            List<PurchaseRequisitionResponseDTO> result = pendingPurchaseRequisitionRepository.purchaseRequisitionStatus(requestDto);

            if (result == null || result.isEmpty()) {
                throw new BusinessException(" No data found");

            }

            return new TaskMastResponsibleResponseDto<>("true",
                    "Data fetch successful",
                    HttpStatus.OK.value(),
                    result);
        } catch (DataAccessException ex) {
            throw new DatabaseException(
                    "Database error occurred: " + ex.getMostSpecificCause().getMessage(), ex
            );


        }
    }

    @Override
    public TaskMastResponsibleResponseDto<List<PurchaseRequisitionApprovalDTO>> getPurchaseRequisitionApproval(PendingPurchaseRequisitionRequestStatusDTO requestDto) {
        try {
            List<PurchaseRequisitionResponseDTO> result = pendingPurchaseRequisitionRepository.purchaseRequisitionStatus(requestDto);

            if (result == null || result.isEmpty()) {
                throw new BusinessException(" No data found");

            }
            Map<String, PurchaseRequisitionApprovalDTO> map = new LinkedHashMap<>();
            for (PurchaseRequisitionResponseDTO responseDTO : result) {
                PurchaseRequisitionResponseDataDTO dto = new PurchaseRequisitionResponseDataDTO(
                        responseDTO.getRefJobNo(),
                        responseDTO.getRefJobDate(),
                        responseDTO.getRefFinItemCode(),
                        responseDTO.getPrDate(),
                        responseDTO.getRefQuotNo(),
                        responseDTO.getCompCode(),
                        responseDTO.getCentCode(),
                        responseDTO.getBranchCode(),
                        responseDTO.getPrNo(),
                        responseDTO.getItemType(),
                        responseDTO.getItemCode(),
                        responseDTO.getMiscMastName(),
                        responseDTO.getItemName(),
                        responseDTO.getSize(),
                        responseDTO.getSizeName(),
                        responseDTO.getColor(),
                        responseDTO.getBaseUom(),
                        responseDTO.getBaseName(),
                        responseDTO.getConvFactor(),
                        responseDTO.getPurUom(),
                        responseDTO.getPurUomName(),
                        responseDTO.getPrQty(),
                        responseDTO.getPoRaisedQty(),
                        responseDTO.getPrBalanceQty(),
                        responseDTO.getPrApprovalFlag(),
                        responseDTO.getPrApprovalUser(),
                        responseDTO.getPrApprovalDate(),
                        responseDTO.getPrCancelFlag(),
                        responseDTO.getPrCancelUser(),
                        responseDTO.getPrCancelDate(),
                        responseDTO.getPrCloseFlag(),
                        responseDTO.getPrCloseUser(),
                        responseDTO.getPrCloseDate(),
                        responseDTO.getRemarks(),
                        responseDTO.getStatus(),
                        responseDTO.getComments(),
                        responseDTO.getRefRsNo(),
                        responseDTO.getQuotCnt(),
                        responseDTO.getItemQuotCnt(),
                        responseDTO.getProductCode(),
                        responseDTO.getMode(),
                        responseDTO.getConvOperator()
                );
                map.computeIfAbsent(responseDTO.getPrNo(), k ->
                        new PurchaseRequisitionApprovalDTO(
                                responseDTO.getPrNo(),
                                responseDTO.getPrDate(),
                                responseDTO.getStatus(),
                                responseDTO.getItemName(),
                                responseDTO.getItemCode(),
                                responseDTO.getPrQty(),
                                new ArrayList<>()
                        )
                ).getItems().add(dto);


            }
            List<PurchaseRequisitionApprovalDTO> finalList =
                    new ArrayList<>(map.values());
            return new TaskMastResponsibleResponseDto<>("true",
                    "Data fetch successful",
                    HttpStatus.OK.value(),
                    finalList);
        } catch (DataAccessException ex) {
            throw new DatabaseException(
                    "Database error occurred: " + ex.getMostSpecificCause().getMessage(), ex
            );


        }
    }


    @Override
    public TaskMastResponsibleTempResponseDto prApproval(PendingPurchaseRequisitionRequestStatusDTO dto) {
        try {
            String save = pendingPurchaseRequisitionRepository
                    .savePurchaseRequisitionApproval(dto);
            if (save == null || save.isEmpty() || "N".equalsIgnoreCase(save)) {

                throw new BusinessException("Not RequirementSlipApproval");

            } else {

                return new TaskMastResponsibleTempResponseDto(
                        "true",
                        "Data Saved successfully",
                        HttpStatus.OK.value()
                );
            }

        }catch (DataAccessException ex )
        {
            throw  new DatabaseException("Database error occurred: " + ex.getMostSpecificCause().getMessage(), ex);
        }

    }
}






