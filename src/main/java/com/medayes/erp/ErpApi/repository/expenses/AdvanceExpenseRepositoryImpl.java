package com.medayes.erp.ErpApi.repository.expenses;

import com.medayes.erp.ErpApi.dto.request.expense.ApprovalExpenseRequestDTO;
import com.medayes.erp.ErpApi.dto.request.expense.ExpenseRequestDTO;
import com.medayes.erp.ErpApi.dto.response.expense.AdvanceApprovalResponseDTO;
import com.medayes.erp.ErpApi.dto.response.expense.AdvanceExpenseResponseDTO;
import com.medayes.erp.ErpApi.dto.response.expense.ExpenseResponseDTO;
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
public class AdvanceExpenseRepositoryImpl  implements  AdvanceExpenseRepository{

    private SimpleJdbcCall saveCall;
    private SimpleJdbcCall fetchCall;
    private  SimpleJdbcCall approvalCall;
    private  SimpleJdbcCall approvalSaveCall;
    private final JdbcTemplate jdbcTemplate;

    public AdvanceExpenseRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /* =======================
       PARAMETER CONSTANTS
       ======================= */
    private static final String P_COMP_CODE = "PCOMP_CODE";
    private static final String P_CENT_CODE = "PCENT_CODE";
    private static final String P_BRANCH_CODE = "PBRANCH_CODE";
    private static final String P_LOCATION = "PLOCATION";
    private static final String P_ADV_REQ_ID = "PADV_REQ_ID";
    private static final String P_ADV_REQ_DATE = "PADV_REQ_DATE";
    private static final String P_SUGGESTED_EMP_CODE = "PSUGGESTED_EMP_CODE";
    private static final String P_ADVANCE_AMOUNT = "PADVANCE_AMOUNT";
    private static final String P_ESTIMATED_AMOUNT = "PESTIMATED_AMOUNT";
    private static final String P_PURPOSE = "PPURPOSE";
    private static final String P_ATTACH_IMAGE = "PATTACH_IMAGE";
    private static final String P_IP = "PIP";
    private  static  final  String P_FROM_DATE="PADV_REQ_DATE_FROM";
    private  static  final  String P_TO_DATE="PADV_REQ_DATE_TO";
    private static final String P_USER_ID = "PUSER_ID";
    private static final String P_TYPE = "PTYPE";
    private static final String P_EXTRA1 = "PEXTRA1";
    private static final String P_EXTRA2 = "PEXTRA2";
    private static final String P_EXTRA3 = "PEXTRA3";
    private static final String P_EXTRA4 = "PEXTRA4";
    private static final String P_EXTRA5 = "PEXTRA5";
    private static final String P_PDATEFORMAT = "PDATEFORMAT";
    private  static  final  String P_STATUS="PSTATUS";
    private  static  final  String P_APP_DATE="PAPP_DATE";
    private  static  final  String P_APP_FLAG="PAPP_FLAG";
    private  static  final  String P_APP_USER="PAPP_USER";
    private  static  final  String P_APPROVAL_AMT="PAPPROVAL_AMT";

    private  static  final  String P_APP_REMARKS="PAPP_REMARKS";

    private  static  final  String P_CAN_DATE= "PCAN_DATE";
    private  static  final  String P_CAN_FLAG= "PCAN_FLAG";

    private  static  final  String P_CAN_USER= "PCAN_USER";
    private  static  final  String P_CAN_REMARKS= "PCAN_REMARKS";
    private  static  final  String P_APP_TYPE= "PAPP_TYPE";


    /* =======================
       INIT STORED PROCEDURE
       ======================= */
    @PostConstruct
    public void init() {

        saveCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("PAY_ADV_EXP_REQUEST_INSUPDDEL_APP")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(getSqlParameters())
                .returningResultSet("data",  (rs, num)-> saveResponse(rs));

        fetchCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("PAY_ADV_EXP_REQUEST_INSUPDDEL_APP")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(getSqlParameters())
                .returningResultSet("data", (rs, num)-> fetchResponseS(rs));

        approvalCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("PAY_ADV_EXP_REQUESTSUBMIT_APP")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(getApprovalSqlParameters())
                .returningResultSet("data", (rs, num) -> fetchResponse(rs));

        approvalSaveCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("PAY_ADV_EXP_REQUEST_APP_SAVE")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(saveApprovalSqlParameters())
                .returningResultSet("data", (rs, num) ->
                        new AdvanceApprovalResponseDTO(rs.getString("RES")));



    }

    private Object fetchResponse(ResultSet rs)  throws SQLException {
        return  new AdvanceExpenseResponseDTO(
                rs.getString("COMP_CODE"),
                rs.getString("CENT_CODE"),
                rs.getString("BRANCH_CODE"),
                rs.getString("BRANCH_NAME"),
                rs.getString("LOCATION"),
                rs.getString("WH_NAME"),
                rs.getString("ADV_REQ_ID"),
                rs.getString("ADV_REQ_DATE"),
                rs.getDouble("ADVANCE_AMOUNT"),
                rs.getDouble("ESTIMATED_AMOUNT"),
                rs.getString("PURPOSE"),
                rs.getDouble("APPROVAL_AMT"),
                rs.getString("APPROVAL_DATE"),
                rs.getString("APPROVAL_FLAG") ,
                rs.getString("APPROVAL_USER"),
                rs.getString("EMP_NAME"),
                rs.getString("DESIG_NAME"),
                rs.getString("D_STATUS"),
                rs.getString("EMP_CODE")


        );
    }

    private Object fetchResponseS(ResultSet rs)  throws SQLException {
        return  new AdvanceExpenseResponseDTO(
                rs.getString("COMP_CODE"),
                rs.getString("CENT_CODE"),
                rs.getString("BRANCH_CODE"),
                rs.getString("BRANCH_NAME"),
                rs.getString("LOCATION"),
                rs.getString("WH_NAME"),
                rs.getString("ADV_REQ_ID"),
                rs.getString("ADV_REQ_DATE"),
                rs.getDouble("ADVANCE_AMOUNT"),
                rs.getDouble("ESTIMATED_AMOUNT"),
                rs.getString("PURPOSE"),
                rs.getDouble("APPROVAL_AMT"),
                rs.getString("APPROVAL_DATE"),
                rs.getString("APPROVAL_FLAG") ,
                rs.getString("APPROVAL_USER"),
                null, null,  null,null


        );
    }

    private Object saveResponse(ResultSet rs) throws SQLException {
        return  new ExpenseResponseDTO(rs.getString("ADV_REQ_ID"));
    }

    private SqlParameter[] getSqlParameters() {
        return new SqlParameter[]{
                new SqlParameter(P_COMP_CODE, Types.VARCHAR),
                new SqlParameter(P_CENT_CODE, Types.VARCHAR),
                new SqlParameter(P_BRANCH_CODE, Types.VARCHAR),
                new SqlParameter(P_LOCATION, Types.VARCHAR),
                new SqlParameter(P_ADV_REQ_ID, Types.VARCHAR),
                new SqlParameter(P_ADV_REQ_DATE, Types.TIMESTAMP),
                new SqlParameter(P_SUGGESTED_EMP_CODE, Types.VARCHAR),
                new SqlParameter(P_ADVANCE_AMOUNT, Types.NUMERIC),
                new SqlParameter(P_ESTIMATED_AMOUNT, Types.NUMERIC),
                new SqlParameter(P_PURPOSE, Types.VARCHAR),
                new SqlParameter(P_ATTACH_IMAGE, Types.VARCHAR),
                new SqlParameter(P_IP, Types.VARCHAR),
                new SqlParameter(P_USER_ID, Types.VARCHAR),
                new SqlParameter(P_TYPE, Types.VARCHAR),
                new SqlParameter(P_EXTRA1, Types.VARCHAR),
                new SqlParameter(P_EXTRA2, Types.VARCHAR),
                new SqlParameter(P_EXTRA3, Types.VARCHAR),
                new SqlParameter(P_EXTRA4, Types.VARCHAR),
                new SqlParameter(P_EXTRA5, Types.VARCHAR)
        };
    }

    private SqlParameter[] getApprovalSqlParameters() {
        return new SqlParameter[]{
                new SqlParameter(P_COMP_CODE, Types.VARCHAR),
                new SqlParameter(P_CENT_CODE, Types.VARCHAR),
                new SqlParameter(P_BRANCH_CODE, Types.VARCHAR),
                new SqlParameter(P_LOCATION, Types.VARCHAR),
                new SqlParameter(P_ADV_REQ_ID, Types.VARCHAR),
                new SqlParameter(P_FROM_DATE, Types.VARCHAR),
                new SqlParameter(P_TO_DATE, Types.VARCHAR),
                new SqlParameter(P_SUGGESTED_EMP_CODE, Types.VARCHAR),
                new SqlParameter(P_PDATEFORMAT, Types.VARCHAR),
                new SqlParameter(P_STATUS, Types.VARCHAR),
                new SqlParameter(P_EXTRA1, Types.VARCHAR),
                new SqlParameter(P_EXTRA2, Types.VARCHAR),
                new SqlParameter(P_EXTRA3, Types.VARCHAR),
                new SqlParameter(P_EXTRA4, Types.VARCHAR),
                new SqlParameter(P_EXTRA5, Types.VARCHAR)
        };
    }

    private SqlParameter[] saveApprovalSqlParameters() {
        return new SqlParameter[]{
                new SqlParameter(P_COMP_CODE, Types.VARCHAR),
                new SqlParameter(P_CENT_CODE, Types.VARCHAR),
                new SqlParameter(P_ADV_REQ_ID, Types.VARCHAR),
                new SqlParameter(P_APP_DATE, Types.VARCHAR),
                new SqlParameter(P_APP_FLAG, Types.VARCHAR),
                new SqlParameter(P_APP_USER, Types.VARCHAR),
                new SqlParameter(P_APPROVAL_AMT, Types.VARCHAR),
                new SqlParameter(P_APP_REMARKS, Types.VARCHAR),
                new SqlParameter(P_CAN_DATE, Types.VARCHAR),
                new SqlParameter(P_CAN_FLAG, Types.VARCHAR),
                new SqlParameter(P_CAN_USER, Types.VARCHAR),
                new SqlParameter(P_CAN_REMARKS, Types.VARCHAR),
                new SqlParameter(P_APP_TYPE, Types.VARCHAR),
                new SqlParameter(P_EXTRA1, Types.VARCHAR),
                new SqlParameter(P_EXTRA2, Types.VARCHAR),
                new SqlParameter(P_EXTRA3, Types.VARCHAR),

        };
    }




    @Override
    public List<ExpenseResponseDTO> save(ExpenseRequestDTO expenseRequestDTO)
    {
        Map<String, Object> result= saveAdvanceExpense(expenseRequestDTO);
        Map<String, Object> results=saveCall.execute(result);
        return  (List<ExpenseResponseDTO>) results.get("data");
    }

    @Override
    public List<AdvanceExpenseResponseDTO> fetch(ExpenseRequestDTO expenseRequestDTO) {
        Map<String, Object> result= saveAdvanceExpense(expenseRequestDTO);
        Map<String, Object> results=fetchCall.execute(result);
        return  (List<AdvanceExpenseResponseDTO>) results.get("data");
    }

    @Override
    public List<AdvanceExpenseResponseDTO> fetchApproval(ApprovalExpenseRequestDTO expenseRequestDTO) {
        Map<String, Object> result= approvalAdvanceExpense(expenseRequestDTO);
        Map<String, Object> results=approvalCall.execute(result);
        return  (List<AdvanceExpenseResponseDTO>) results.get("data");
    }

    @Override
    public List<AdvanceApprovalResponseDTO> saveApproval(ApprovalExpenseRequestDTO expenseRequestDTO) {
        Map<String, Object> result= approvalAdvanceExpenseSave(expenseRequestDTO);
        Map<String, Object> results=approvalSaveCall.execute(result);
        return  (List<AdvanceApprovalResponseDTO>) results.get("data");

    }


    public  Map<String, Object> saveAdvanceExpense(ExpenseRequestDTO expenseRequestDTO) {

        Map<String, Object> params = new HashMap<>();

        params.put(P_COMP_CODE, expenseRequestDTO.getCompCode());
        params.put(P_CENT_CODE, expenseRequestDTO.getCentCode());
        params.put(P_BRANCH_CODE, expenseRequestDTO.getBranchCode());
        params.put(P_LOCATION, expenseRequestDTO.getLocation());
        params.put(P_ADV_REQ_ID, expenseRequestDTO.getAdvReqId());
        params.put(P_ADV_REQ_DATE, expenseRequestDTO.getAdvReqDate());
        params.put(P_SUGGESTED_EMP_CODE, expenseRequestDTO.getSuggestedEmpCode());
        params.put(P_ADVANCE_AMOUNT, expenseRequestDTO.getAdvanceAmount());
        params.put(P_ESTIMATED_AMOUNT, expenseRequestDTO.getEstimatedAmount());
        params.put(P_PURPOSE, expenseRequestDTO.getPurpose());
        params.put(P_ATTACH_IMAGE, expenseRequestDTO.getAttachImage());
        params.put(P_IP, expenseRequestDTO.getIp());
        params.put(P_USER_ID, expenseRequestDTO.getUserId());
        params.put(P_TYPE, expenseRequestDTO.getType());
        params.put(P_EXTRA1, expenseRequestDTO.getExtra1());
        params.put(P_EXTRA2, expenseRequestDTO.getExtra2());
        params.put(P_EXTRA3, expenseRequestDTO.getExtra3());
        params.put(P_EXTRA4, expenseRequestDTO.getExtra4());
        params.put(P_EXTRA5, expenseRequestDTO.getExtra5());

      return   params;
    }

    public  Map<String, Object> approvalAdvanceExpense(ApprovalExpenseRequestDTO expenseRequestDTO) {

        Map<String, Object> params = new HashMap<>();

        params.put(P_COMP_CODE, expenseRequestDTO.getCompCode());
        params.put(P_CENT_CODE, expenseRequestDTO.getCentCode());
        params.put(P_BRANCH_CODE, expenseRequestDTO.getBranchCode());
        params.put(P_LOCATION, expenseRequestDTO.getLocation());
        params.put(P_ADV_REQ_ID, expenseRequestDTO.getAdvReqId());
        params.put(P_FROM_DATE, expenseRequestDTO.getFromDate());
        params.put(P_TO_DATE, expenseRequestDTO.getToDate());
        params.put(P_SUGGESTED_EMP_CODE, expenseRequestDTO.getSuggestedEmpCode());
        params.put(P_PDATEFORMAT, expenseRequestDTO.getDateFormate());
        params.put(P_STATUS, expenseRequestDTO.getStatus());
        params.put(P_EXTRA1, expenseRequestDTO.getExtra1());
        params.put(P_EXTRA2, expenseRequestDTO.getExtra2());
        params.put(P_EXTRA3, expenseRequestDTO.getExtra3());
        params.put(P_EXTRA4, expenseRequestDTO.getExtra4());
        params.put(P_EXTRA5, expenseRequestDTO.getExtra5());

        return   params;
    }

    public  Map<String, Object> approvalAdvanceExpenseSave(ApprovalExpenseRequestDTO expenseRequestDTO) {

        Map<String, Object> params = new HashMap<>();

        params.put(P_COMP_CODE, expenseRequestDTO.getCompCode());
        params.put(P_CENT_CODE, expenseRequestDTO.getCentCode());
        params.put(P_ADV_REQ_ID, expenseRequestDTO.getAdvReqId());
        params.put(P_APP_DATE, expenseRequestDTO.getAppDate());
        params.put(P_APP_FLAG, expenseRequestDTO.getAppFlag());
        params.put(P_APP_USER, expenseRequestDTO.getAppUser());
        params.put(P_PDATEFORMAT, expenseRequestDTO.getApprovalRemark());
        params.put(P_APPROVAL_AMT, expenseRequestDTO.getApprovalAmt());
        params.put(P_APP_REMARKS, expenseRequestDTO.getApprovalRemark());
        params.put(P_CAN_DATE, expenseRequestDTO.getCanDate());
        params.put(P_CAN_FLAG, expenseRequestDTO.getCanFlag());
        params.put(P_CAN_USER, expenseRequestDTO.getCanUser());
        params.put(P_CAN_REMARKS, expenseRequestDTO.getCanRemarks());
        params.put(P_APP_TYPE, expenseRequestDTO.getAppType());
        params.put(P_EXTRA1, expenseRequestDTO.getExtra1());
        params.put(P_EXTRA2, expenseRequestDTO.getExtra2());
        params.put(P_EXTRA3, expenseRequestDTO.getExtra3());


        return   params;

}


}
