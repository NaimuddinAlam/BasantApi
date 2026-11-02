package com.medayes.erp.ErpApi.dto.response.production;

import java.util.List;

public record OrganizationSeedSowingDto(
        String orgName,
        List<SeedSowingResponseDto> farmers
) {

}
