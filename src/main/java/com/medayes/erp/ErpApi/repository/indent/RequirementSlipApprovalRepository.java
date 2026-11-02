package com.medayes.erp.ErpApi.repository.indent;


import com.medayes.erp.ErpApi.dto.request.indent.RequirementSlipApprovalRequestDto;
import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RequirementSlipApprovalRepository {

    final  private JdbcTemplate jdbcTemplate;
      private SimpleJdbcCall call;

    private static final String PARAM_COMP_CODE = "pcompcode";
    private static final String PARAM_CENT_CODE = "pcenter";
    private static final String PARAM_BRANCH = "pbranch";
    private static final String PARAM_DEPTNO = "pdocno";
    private static final String PARAM_ITEMTYPE = "pitemtype";
    private static final String PARAM_ITEMTYPECode = "pitemcode";
    private static final String PARAM_UserId = "puserid";
    private static final String PARAM_Approval_Flag = "papporvalflag";
    private static final String PARAM_Cancel_Flag = "pappcencelfalg";
    private static final String PARAM_Approval_Type = "papprovaltype";
    private static final String PARAM_Status = "pstatus";
    private static final String PARAM_EXTRA1 = "pextra1";
    private static final String PARAM_EXTRA2 = "pextra2";
    private static final String PARAM_EXTRA3 = "pextra3";
    private static final String PARAM_EXTRA4 = "pextra4";
    private static final String PARAM_EXTRA5 = "pextra5";



    public RequirementSlipApprovalRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }
    @PostConstruct
    public  void  init()
    {
         call= new SimpleJdbcCall(jdbcTemplate)
                 .withCatalogName("dbo")
                 .withProcedureName("INV_Req_Slip_App_SAVE_NEW_APP")
                 .withoutProcedureColumnMetaDataAccess()
                 .declareParameters(
                         new SqlParameter(PARAM_COMP_CODE, Types.VARCHAR),
                         new SqlParameter(PARAM_CENT_CODE, Types.VARCHAR),
                         new SqlParameter(PARAM_BRANCH, Types.VARCHAR),
                         new SqlParameter(PARAM_DEPTNO, Types.VARCHAR),
                         new SqlParameter(PARAM_ITEMTYPE, Types.VARCHAR),
                         new SqlParameter(PARAM_ITEMTYPECode, Types.VARCHAR),
                         new SqlParameter(PARAM_UserId, Types.VARCHAR),
                         new SqlParameter(PARAM_Approval_Flag, Types.VARCHAR),
                         new SqlParameter(PARAM_Cancel_Flag, Types.VARCHAR),
                         new SqlParameter(PARAM_Approval_Type, Types.VARCHAR),
                         new SqlParameter(PARAM_Status, Types.VARCHAR),
                         new SqlParameter(PARAM_EXTRA1, Types.VARCHAR),
                         new SqlParameter(PARAM_EXTRA2, Types.VARCHAR),
                         new SqlParameter(PARAM_EXTRA3, Types.VARCHAR),
                         new SqlParameter(PARAM_EXTRA4, Types.VARCHAR),
                         new SqlParameter(PARAM_EXTRA5, Types.VARCHAR)

                 ) .returningResultSet("Data", (rs, rowNum) -> rs.getString("RES"));
    }



    public  String saveRequirementSlipApproval(RequirementSlipApprovalRequestDto requestDto)
    {
        Map<String,Object>map= buildParams(requestDto);
        Map<String,Object>result= call.execute(map);
        @SuppressWarnings("unchecked")
        List<String> data = (List<String>) result.get("Data");

        return (data != null && !data.isEmpty()) ? data.get(0) : null;


    }
    public Map<String, Object> buildParams(RequirementSlipApprovalRequestDto requestDto) {

        Map<String, Object> hashMap = new HashMap<>();

        hashMap.put(PARAM_COMP_CODE, requestDto.pcompcode());
        hashMap.put(PARAM_CENT_CODE, requestDto.pcenter());
        hashMap.put(PARAM_BRANCH, requestDto.pbranch());
        hashMap.put(PARAM_DEPTNO, requestDto.pdocno());
        hashMap.put(PARAM_ITEMTYPE, requestDto.pitemtype());
        hashMap.put(PARAM_ITEMTYPECode, requestDto.pitemcode());
        hashMap.put(PARAM_UserId, requestDto.puserid());
        hashMap.put(PARAM_Approval_Flag, requestDto.papporvalflag());
        hashMap.put(PARAM_Cancel_Flag, requestDto.pappcencelfalg());
        hashMap.put(PARAM_Approval_Type, requestDto.papprovaltype());
        hashMap.put(PARAM_Status, requestDto.pstatus());
        hashMap.put(PARAM_EXTRA1, requestDto.pextra1());
        hashMap.put(PARAM_EXTRA2, requestDto.pextra2());
        hashMap.put(PARAM_EXTRA3, requestDto.pextra3());
        hashMap.put(PARAM_EXTRA4, requestDto.pextra4());
        hashMap.put(PARAM_EXTRA5, requestDto.pextra5());

        return hashMap;
    }

}
