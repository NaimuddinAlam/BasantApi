package com.medayes.erp.ErpApi.controller;


import com.medayes.erp.ErpApi.services.servicesImpl.InvReqSlipService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inv-req-slip")
public class InvReqSlipController {

    @Autowired
    private InvReqSlipService service;

    @GetMapping("/fetch")
    public List<Map<String, Object>> fetchInvReqSlip(
            @RequestParam String compCode,
            @RequestParam String centCode,
            @RequestParam String branch,
            @RequestParam String deptNo,
            @RequestParam(required = false) String rsNo,
            @RequestParam(required = false) String itemType,
            @RequestParam(required = false) String item,
            @RequestParam(required = false) String rsDateFrom,
            @RequestParam(required = false) String rsDateTo,
            @RequestParam(defaultValue = "dd/mm/yyyy") String dateFormat,
            @RequestParam String pextra,
            @RequestParam String pextra1,
            @RequestParam String pextra2,
            @RequestParam(required = false) String pextra3,
            @RequestParam(required = false) String pextra4,
            @RequestParam(required = false) String pextra5
    ) {
        return service.getInvReqSlipData(compCode, centCode, branch, deptNo, rsNo, itemType, item,
                rsDateFrom, rsDateTo, dateFormat, pextra, pextra1, pextra2, pextra3, pextra4, pextra5);
    }
}