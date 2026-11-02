package com.medayes.erp.ErpApi.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.medayes.erp.ErpApi.utils.Utils.buildDebugSql;

@Repository
public class InvReqSlipRepository {

    private final JdbcTemplate jdbcTemplate;

    public InvReqSlipRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> fetchInvReqSlip(
            String compCode, String centCode, String branch, String deptNo,
            String rsNo, String itemType, String item, String rsDateFrom, String rsDateTo,
            String dateFormat, String pextra, String pextra1, String pextra2,
            String pextra3, String pextra4, String pextra5
    ) {
        // Initialize SimpleJdbcCall
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("INV_Req_Slip_App_FETCH");

        // Pass named parameters (keys)
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("P_COMP_CODE", compCode);
        inParams.put("P_CENT_CODE", centCode);
        inParams.put("P_BRANCH", branch);
        inParams.put("P_DEPTNO", deptNo);
        inParams.put("P_RSNO", rsNo);
        inParams.put("P_ITEMTYPE", itemType);
        inParams.put("P_ITEM", item);
        inParams.put("P_RS_Date_From", rsDateFrom);
        inParams.put("PRS_Date_To", rsDateTo);
        inParams.put("dateformat", dateFormat);
        inParams.put("PEXTRA", pextra);
        inParams.put("PEXTRA1", pextra1);
        inParams.put("PEXTRA2", pextra2);
        inParams.put("PEXTRA3", pextra3);
        inParams.put("PEXTRA4", pextra4);
        inParams.put("PEXTRA5", pextra5);


        // Execute procedure
        Map<String, Object> result = jdbcCall.execute(inParams);

        String logSql = buildDebugSql(inParams,"INV_Req_Slip_App_FETCH");
        System.out.println("Executing SQL (for log only): " + logSql);
        return (List<Map<String, Object>>) result.get("#result-set-1");
    }

}
