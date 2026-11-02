package com.medayes.erp.ErpApi.repository.indent;

import com.medayes.erp.ErpApi.dto.request.indent.RequirementSlipRequestDto;
import com.medayes.erp.ErpApi.dto.response.indent.RequirementSlipReposeDto;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class RequirementSlipApprovalListRepository {

    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall call;

    @Autowired
    public RequirementSlipApprovalListRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // ---------------------------
    // PARAMETER CONSTANTS
    // ---------------------------
    private static final String PARAM_COMP_CODE = "P_COMP_CODE";
    private static final String PARAM_CENT_CODE = "P_CENT_CODE";
    private static final String PARAM_BRANCH = "P_BRANCH";
    private static final String PARAM_DEPTNO = "P_DEPTNO";
    private static final String PARAM_RSNO = "P_RSNO";
    private static final String PARAM_ITEMTYPE = "P_ITEMTYPE";
    private static final String PARAM_ITEM = "P_ITEM";
    private static final String PARAM_RS_DATE_FROM = "P_RS_Date_From";
    private static final String PARAM_RS_DATE_TO = "PRS_Date_To";
    private static final String PARAM_DATEFORMAT = "dateformat";
    private static final String PARAM_EXTRA = "PEXTRA";
    private static final String PARAM_EXTRA1 = "PEXTRA1";
    private static final String PARAM_EXTRA2 = "PEXTRA2";
    private static final String PARAM_EXTRA3 = "PEXTRA3";
    private static final String PARAM_EXTRA4 = "PEXTRA4";
    private static final String PARAM_EXTRA5 = "PEXTRA5";

    // ---------------------------
    // INIT PROCEDURE
    // ---------------------------
    @PostConstruct
    public void init() {

        call = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("INV_Req_Slip_App_FETCH_NEW_APP") // ← put your SP name
                .withoutProcedureColumnMetaDataAccess()

                // SQL INPUT PARAMETERS
                .declareParameters(
                        new SqlParameter(PARAM_COMP_CODE, Types.VARCHAR),
                        new SqlParameter(PARAM_CENT_CODE, Types.VARCHAR),
                        new SqlParameter(PARAM_BRANCH, Types.VARCHAR),
                        new SqlParameter(PARAM_DEPTNO, Types.VARCHAR),
                        new SqlParameter(PARAM_RSNO, Types.VARCHAR),
                        new SqlParameter(PARAM_ITEMTYPE, Types.VARCHAR),
                        new SqlParameter(PARAM_ITEM, Types.VARCHAR),
                        new SqlParameter(PARAM_RS_DATE_FROM, Types.TIMESTAMP),
                        new SqlParameter(PARAM_RS_DATE_TO, Types.TIMESTAMP),
                        new SqlParameter(PARAM_DATEFORMAT, Types.VARCHAR),
                        new SqlParameter(PARAM_EXTRA, Types.VARCHAR),
                        new SqlParameter(PARAM_EXTRA1, Types.VARCHAR),
                        new SqlParameter(PARAM_EXTRA2, Types.VARCHAR),
                        new SqlParameter(PARAM_EXTRA3, Types.VARCHAR),
                        new SqlParameter(PARAM_EXTRA4, Types.VARCHAR),
                        new SqlParameter(PARAM_EXTRA5, Types.VARCHAR),
                        new SqlReturnResultSet("Data", (rs, rowNum) -> mapRowToDto(rs))
                );

        // MAPPING ROWS → DTO

    }

    // ---------------------------
    // ROW MAPPER CLEAN METHOD
    // ---------------------------
    private RequirementSlipReposeDto mapRowToDto(ResultSet rs) throws SQLException {

        return new RequirementSlipReposeDto(

                rs.getString("CENT_CODE"),
                rs.getString("BRANCH_CODE"),
                rs.getString("WH_CODE"),

                rs.getString("CENT_NAME"),
                rs.getString("BRANCH_NAME"),
                rs.getString("WH_NAME"),

                rs.getString("REF_JOB_NO"),
                rs.getString("REF_JOB_DATE"),
                rs.getString("RS_DATE"),

                rs.getString("COMP_CODE"),

                rs.getString("RS_NO"),
                rs.getString("ITEM_TYPE"),
                rs.getString("ITEM_CODE"),
                rs.getString("ITEM_TYPE_NAME"),
                rs.getString("ITEM_NAME"),

                rs.getString("SIZE"),
                rs.getString("SIZE_NAME"),
                rs.getString("COLOR"),

                rs.getString("BASE_UOM"),
                rs.getString("BASE_NAME"),
                rs.getString("CONV_FACTOR"),

                rs.getString("PUR_UOM"),

                rs.getString("RS_RAISED_QTY"),
                rs.getString("RS_QTY"),
                rs.getString("PR_RAISED_QTY"),
                rs.getString("ISSUED_QTY"),
                rs.getString("RS_BALANCE_QTY"),

                rs.getString("RS_APPROVAL_DATE"),
                rs.getString("RS_CANCEL_DATE"),
                rs.getString("RS_CANCEL_USER"),

                rs.getString("RS_CLOSE_FLAG"),
                rs.getString("RS_CLOSE_USER"),
                rs.getString("RS_CLOSE_DATE"),

                rs.getString("REMARKS"),
                rs.getString("STATUS"),
                rs.getString("COMMENTS"),

                rs.getString("PRODUCT_CODE"),

                rs.getString("DEPTT_CODE"),
                rs.getString("DEPTT_NAME"),

                rs.getString("RAISED_BY"),
                rs.getString("RAISED_NAME"),
                rs.getString("PHONE1"),
                rs.getString("EMAIL_ID1"),

                rs.getString("ATTACHMENT"),
                rs.getString("CLOSING_STK"),

                rs.getString("SEQ3_APPROVAL_USER_NAME"),
                rs.getString("SEQ3_APPROVAL_REMARKS"),
                rs.getString("SEQ3_STATUS"),
                rs.getString("SEQ3_APPROVAL_DATE"),
                rs.getString("SEQ3_APPROVAL_QTY"),

                rs.getString("SEQ1_APPROVAL_USER_NAME"),
                rs.getString("SEQ1_APPROVAL_REMARKS"),
                rs.getString("SEQ1_STATUS"),
                rs.getString("SEQ1_APPROVAL_DATE"),
                rs.getString("SEQ1_APPROVAL_QTY"),

                rs.getString("SEQ2_APPROVAL_USER_NAME"),
                rs.getString("SEQ2_APPROVAL_REMARKS"),
                rs.getString("SEQ2_STATUS"),
                rs.getString("SEQ2_APPROVAL_DATE"),
                rs.getString("SEQ2_APPROVAL_QTY")

        );
    }

    // ---------------------------
    // RUN PROCEDURE
    // ---------------------------
    public List<RequirementSlipReposeDto> fetchRequirementSlip(RequirementSlipRequestDto dto) {

        Map<String, Object> params = buildParams(dto);

        Map<String, Object> result = call.execute(params);

        return (List<RequirementSlipReposeDto>) result.get("Data");
    }

    // ---------------------------
    // INPUT PARAMETER BUILDER
    // ---------------------------
    private Map<String, Object> buildParams(RequirementSlipRequestDto dto) {

        Map<String, Object> map = new HashMap<>();

        map.put(PARAM_COMP_CODE, dto.compCode());
        map.put(PARAM_CENT_CODE, dto.centCode());
        map.put(PARAM_BRANCH, dto.branchCode());
        map.put(PARAM_DEPTNO, dto.deptNo());
        map.put(PARAM_RSNO, dto.rsNo());
        map.put(PARAM_ITEMTYPE, dto.itemType());
        map.put(PARAM_ITEM, dto.item());
        map.put(PARAM_RS_DATE_FROM, dto.rsFromDate());
        map.put(PARAM_RS_DATE_TO, dto.rsToDate());
        map.put(PARAM_DATEFORMAT, dto.dateformat());
        map.put(PARAM_EXTRA, dto.extra());
        map.put(PARAM_EXTRA1, dto.extra1());
        map.put(PARAM_EXTRA2, dto.extra2());
        map.put(PARAM_EXTRA3, dto.extra3());
        map.put(PARAM_EXTRA4, dto.extra4());
        map.put(PARAM_EXTRA5, dto.extra5());

        return map;
    }
}
