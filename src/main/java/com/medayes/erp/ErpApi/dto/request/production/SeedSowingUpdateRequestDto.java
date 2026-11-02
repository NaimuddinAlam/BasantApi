package com.medayes.erp.ErpApi.dto.request.production;

public record SeedSowingUpdateRequestDto(
        String compCode,
        String centCode,
        String branchCode,
        String distNo,

        String orgCode,
        String farmerCode,
        String itemCode,
        String sizeCode,
        String batchNo,

        String sowingDate1,
        String sowingDate2,

        String surveyNo,
        String farmerLotNo,
        String prevCropCode,

        String totalRows,
        String plantsInRow,

        String refAgrNo,
        String harvestDate,

        String varietyCode,
        String stageCode,
        String status,

        String ip,
        String userId,
         String moduleCode,
        String docType,
        String docNumType,
        String rcpDate,
        String agencyCode,
        String extra,
        String extra1,
        String extra2,
        String extra3,
        String extra4,
        String extra5
) {
}
