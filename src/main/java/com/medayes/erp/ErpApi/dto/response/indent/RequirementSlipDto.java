package com.medayes.erp.ErpApi.dto.response.indent;

import java.util.List;

public record RequirementSlipDto(  String rsNo,
                                   String rsDate,
                                   String remake,
                                   String status,

                                   List<ItemRequirementSlipDto> items

) {


}
