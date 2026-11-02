package com.medayes.erp.ErpApi.dto.request.task;

public record RecipientDTO(
        String email,
        String personName,
        String token
) {}
