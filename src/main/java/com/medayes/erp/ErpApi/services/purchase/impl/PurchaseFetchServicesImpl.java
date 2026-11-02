package com.medayes.erp.ErpApi.services.purchase.impl;

import com.medayes.erp.ErpApi.dto.request.po.PurchaseOrderRequestDTO;
import com.medayes.erp.ErpApi.dto.request.po.PurchaseRequest;
import com.medayes.erp.ErpApi.dto.response.op.*;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleResponseDto;

import com.medayes.erp.ErpApi.exeption.BusinessException;
import com.medayes.erp.ErpApi.exeption.DatabaseException;
import com.medayes.erp.ErpApi.repository.POapproval.PurchaseOrderFetchRepositoryImpl;
import com.medayes.erp.ErpApi.services.purchase.PurchaseFetchServices;

import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PurchaseFetchServicesImpl implements PurchaseFetchServices {

    private final PurchaseOrderFetchRepositoryImpl repository;
    private final ModelMapper mapper;

    public PurchaseFetchServicesImpl(
            PurchaseOrderFetchRepositoryImpl repository,
            ModelMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    // ===================== PURCHASE HEADER =====================

    @Override
    public TaskMastResponsibleResponseDto<List<PurchaseOrderResponseDto>>
    getPurchaseFetch(PurchaseRequest request) {

        List<PurchaseOrderResponse> responses =
                executeRepositoryCall(() -> repository.purchaseFetch(request));

        List<PurchaseOrderResponseDto> dtoList =
                mapList(responses, PurchaseOrderResponseDto.class);

        return successResponse(dtoList);
    }

    // ===================== PURCHASE ITEMS =====================

    @Override
    public TaskMastResponsibleResponseDto<List<PurchaseOrderItemDto>>
    getPurchaseItemFetch(PurchaseRequest request) {

        List<PurchaseOrderItemResponse> responses =
                executeRepositoryCall(() -> repository.purchaseItemFetch(request));

        List<PurchaseOrderItemDto> dtoList =
                mapList(responses, PurchaseOrderItemDto.class);

        return successResponse(dtoList);
    }

    // ===================== QUOTATION =====================

    @Override
    public TaskMastResponsibleResponseDto<List<QuotationDto>>
    getQuotation(PurchaseRequest request) {

        List<QuotationResponse> responses =
                executeRepositoryCall(() -> repository.quotationFetch(request));

        List<QuotationDto> dtoList =
                mapList(responses, QuotationDto.class);

        return successResponse(dtoList);
    }

    @Override
    public TaskMastResponsibleResponseDto<List<PurchaseOrderApprovalDTO>> savePurchaseOrder(PurchaseOrderRequestDTO purchaseRequest) {
        List<PurchaseOrderApprovalDTO> responses =
                executeRepositoryCall(() -> repository.savePurchaseOrder(purchaseRequest));

        List<PurchaseOrderApprovalDTO> dtoList =
                mapList(responses, PurchaseOrderApprovalDTO.class);

        return successResponse(dtoList);
    }

    // ===================== COMMON UTILITY METHODS =====================

    private <T> List<T> executeRepositoryCall(RepositoryCall<T> call) {
        try {
            List<T> result = call.execute();

            if (result == null || result.isEmpty()) {
                throw new BusinessException("No data found");
            }
            return result;

        } catch (DataAccessException ex) {
            throw new DatabaseException(
                    "Database error occurred: " + ex.getMostSpecificCause().getMessage(),
                    ex
            );
        }
    }

    private <S, T> List<T> mapList(List<S> source, Class<T> target) {
        return source.stream()
                .map(item -> mapper.map(item, target))
                .toList();   // Java 16+
    }

    private <T> TaskMastResponsibleResponseDto<List<T>> successResponse(List<T> data) {
        return new TaskMastResponsibleResponseDto<>(
                "true",
                "Data fetch successful",
                HttpStatus.OK.value(),
                data
        );
    }

    // ===================== FUNCTIONAL INTERFACE =====================

    @FunctionalInterface
    private interface RepositoryCall<T> {
        List<T> execute();
    }
}
