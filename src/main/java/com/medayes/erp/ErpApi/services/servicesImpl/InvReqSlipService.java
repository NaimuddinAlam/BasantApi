package com.medayes.erp.ErpApi.services.servicesImpl;


import com.medayes.erp.ErpApi.repository.InvReqSlipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InvReqSlipService {

    @Autowired
    private InvReqSlipRepository repository;

    public List<Map<String, Object>> getInvReqSlipData(
            String compCode, String centCode, String branch, String deptNo,
            String rsNo, String itemType, String item, String rsDateFrom, String rsDateTo,
            String dateFormat, String pextra, String pextra1, String pextra2,
            String pextra3, String pextra4, String pextra5) {




        return repository.fetchInvReqSlip( compCode,  centCode,  branch,  deptNo,
                 rsNo,  itemType,  item,  rsDateFrom,  rsDateTo,
                 dateFormat,  pextra,  pextra1,  pextra2,
                 pextra3,  pextra4,  pextra5);
    }
}
