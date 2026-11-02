package com.medayes.erp.ErpApi.dto.request.sale;

public record SalePermissionRequestDto(String compcode,
                                        String userid,
                                        String formname, String pmodule) {
}
