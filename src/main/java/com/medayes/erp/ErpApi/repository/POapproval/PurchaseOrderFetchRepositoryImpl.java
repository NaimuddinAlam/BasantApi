package com.medayes.erp.ErpApi.repository.POapproval;

import com.medayes.erp.ErpApi.dto.request.po.PurchaseOrderRequestDTO;
import com.medayes.erp.ErpApi.dto.request.po.PurchaseRequest;
import com.medayes.erp.ErpApi.dto.response.op.PurchaseOrderApprovalDTO;
import com.medayes.erp.ErpApi.dto.response.op.PurchaseOrderItemResponse;
import com.medayes.erp.ErpApi.dto.response.op.PurchaseOrderResponse;
import com.medayes.erp.ErpApi.dto.response.op.QuotationResponse;
import com.medayes.erp.ErpApi.dto.response.task.SendMailResponseDto;
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
public class PurchaseOrderFetchRepositoryImpl implements PurchaseOrderFetchRepository {

    private final JdbcTemplate jdbcTemplate;

    private SimpleJdbcCall poHeaderCall;
    private SimpleJdbcCall poItemCall;
    private SimpleJdbcCall quotationCall;
    private SimpleJdbcCall poApprovalSaveCall;
    // ===================== PARAM CONSTANTS =====================

    private static final String PARAM_COMP_CODE = "P_COMP_CODE";
    private static final String PARAM_CENT_CODE = "P_CENT_CODE";
    private static final String PARAM_BRANCH = "P_BRANCH";
    private static final String PARAM_DEPTNO = "P_DEPTNO";
    private static final String PARAM_PRNO = "P_PRNO";
    private static final String PARAM_ITEMTYPE = "P_ITEMTYPE";
    private static final String PARAM_ITEM = "P_ITEM";
    private static final String PARAM_PR_DATE_FROM = "P_PR_DATE_FROM";
    private static final String PARAM_PR_DATE_TO = "P_PR_DATE_TO";
    private static final String PARAM_DATEFORMAT = "DATEFORMAT";

    private static final String PARAM_COLOR = "PCOLOR";
    private static final String PARAM_SIZE = "PSIZE";
    private static final String PARAM_ITEM_NAME = "P_ITEM_NAME";

    private static final String PARAM_EXTRA = "PEXTRA";
    private static final String PARAM_EXTRA1 = "PEXTRA1";
    private static final String PARAM_EXTRA2 = "PEXTRA2";
    private static final String PARAM_EXTRA3 = "PEXTRA3";
    private static final String PARAM_EXTRA4 = "PEXTRA4";
    private static final String PARAM_EXTRA5 = "PEXTRA5";

    // ===================== CONSTRUCTOR =====================

    public PurchaseOrderFetchRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // ===================== INIT =====================

    @PostConstruct
    public void init() {

        // ===== PO HEADER =====
        poHeaderCall = basePoCall()
                .returningResultSet("data", (rs, rowNum) -> mapRowToDto(rs));

        // ===== PO ITEMS =====
        poItemCall = basePoCall()
                .returningResultSet("data", (rs, rowNum) -> mapRowToDtos(rs));

        // ===== QUOTATION =====
        quotationCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("INV_PR_APP_FETCH_QUOTATION")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter(PARAM_COMP_CODE, Types.VARCHAR),
                        new SqlParameter(PARAM_CENT_CODE, Types.VARCHAR),
                        new SqlParameter(PARAM_BRANCH, Types.VARCHAR),
                        new SqlParameter(PARAM_DEPTNO, Types.VARCHAR),
                        new SqlParameter(PARAM_PRNO, Types.VARCHAR),
                        new SqlParameter(PARAM_COLOR, Types.VARCHAR),
                        new SqlParameter(PARAM_SIZE, Types.VARCHAR),
                        new SqlParameter(PARAM_ITEM_NAME, Types.VARCHAR),
                        new SqlParameter(PARAM_DATEFORMAT, Types.VARCHAR)
                )
                .returningResultSet("data", (rs, rowNum) -> mapRowToQuotationDto(rs));


        poApprovalSaveCall   = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("INV_PO_APP_SAVE")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("pcompcode", Types.VARCHAR),
                        new SqlParameter("pcenter", Types.VARCHAR),
                        new SqlParameter("pbranch", Types.VARCHAR),
                        new SqlParameter("pdocno", Types.VARCHAR),
                        new SqlParameter("pdocdate", Types.VARCHAR),
                        new SqlParameter("pitemtype", Types.VARCHAR),
                        new SqlParameter("pitemcode", Types.VARCHAR),
                        new SqlParameter("puserid", Types.VARCHAR),
                        new SqlParameter("pappcencelfalg", Types.VARCHAR),
                        new SqlParameter("pstatus", Types.VARCHAR),

                        new SqlParameter("pextra1", Types.VARCHAR),
                        new SqlParameter("pextra2", Types.VARCHAR),
                        new SqlParameter("pextra3", Types.VARCHAR),
                        new SqlParameter("pextra4", Types.VARCHAR),
                        new SqlParameter("pextra5", Types.VARCHAR),
                        new SqlParameter("pextra6", Types.VARCHAR),
                        new SqlParameter("pextra7", Types.VARCHAR)
                )
                .returningResultSet("data", (rs, rowNum) -> mapRowToApprovalDto(rs));


    }

    // ===================== BASE PO CALL =====================

    private SimpleJdbcCall basePoCall() {
        return new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("INV_PO_APP_FETCH")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter(PARAM_COMP_CODE, Types.VARCHAR),
                        new SqlParameter(PARAM_CENT_CODE, Types.VARCHAR),
                        new SqlParameter(PARAM_BRANCH, Types.VARCHAR),
                        new SqlParameter(PARAM_DEPTNO, Types.VARCHAR),
                        new SqlParameter(PARAM_PRNO, Types.VARCHAR),
                        new SqlParameter(PARAM_ITEMTYPE, Types.VARCHAR),
                        new SqlParameter(PARAM_ITEM, Types.VARCHAR),
                        new SqlParameter(PARAM_PR_DATE_FROM, Types.VARCHAR),
                        new SqlParameter(PARAM_PR_DATE_TO, Types.VARCHAR),
                        new SqlParameter(PARAM_DATEFORMAT, Types.VARCHAR),
                        new SqlParameter(PARAM_EXTRA, Types.VARCHAR),
                        new SqlParameter(PARAM_EXTRA1, Types.VARCHAR),
                        new SqlParameter(PARAM_EXTRA2, Types.VARCHAR),
                        new SqlParameter(PARAM_EXTRA3, Types.VARCHAR),
                        new SqlParameter(PARAM_EXTRA4, Types.VARCHAR),
                        new SqlParameter(PARAM_EXTRA5, Types.VARCHAR)
                );
    }

    // ===================== FETCH METHODS =====================

    @Override
    public List<PurchaseOrderResponse> purchaseFetch(PurchaseRequest request) {
        Map<String, Object> result = poHeaderCall.execute(buildParam(request));
        return (List<PurchaseOrderResponse>) result.get("data");
    }

    @Override
    public List<PurchaseOrderItemResponse> purchaseItemFetch(PurchaseRequest request) {
        Map<String, Object> result = poItemCall.execute(buildParam(request));
        return (List<PurchaseOrderItemResponse>) result.get("data");
    }

    @Override
    public List<QuotationResponse> quotationFetch(PurchaseRequest request) {
        Map<String, Object> result = quotationCall.execute(buildParamQuotation(request));
        return (List<QuotationResponse>) result.get("data");
    }

    @Override
    public List<PurchaseOrderApprovalDTO> savePurchaseOrder(PurchaseOrderRequestDTO purchaseOrderRequestDTO) {
        Map<String, Object> result = poApprovalSaveCall.execute(
                buildParamPoApproval(purchaseOrderRequestDTO));
        return  (List<PurchaseOrderApprovalDTO>)result.get("data");


    }

    // ===================== ROW MAPPERS =====================

    private PurchaseOrderResponse mapRowToDto(ResultSet rs) throws SQLException {
        return new PurchaseOrderResponse(
                rs.getString("REF_JOB_NO"),
                rs.getString("REF_JOB_DATE"),
                rs.getString("COMP_CODE"),
                rs.getString("CENT_CODE"),
                rs.getString("BRANCH_CODE"),
                rs.getString("PO_NO"),
                rs.getString("PO_DATE"),
                rs.getString("PARTY_CODE"),
                rs.getString("SUPP_NAME"),
                rs.getString("MODE"),
                rs.getString("DEL_DATE"),
                rs.getString("RATING"),
                rs.getString("ATTAHMENT"),
                rs.getString("REF_QUOT_NO"),
                rs.getString("RAISED_BY")
        );
    }

    private PurchaseOrderItemResponse mapRowToDtos(ResultSet rs) throws SQLException {
        return new PurchaseOrderItemResponse(
                rs.getString("REF_JOB_NO"),
                rs.getString("REF_JOB_DATE"),
                rs.getString("REF_FIN_ITEM_CODE"),
                rs.getString("COMP_CODE"),
                rs.getString("CENT_CODE"),
                rs.getString("BRANCH_CODE"),
                rs.getString("PO_NO"),
                rs.getString("PO_DATE"),
                rs.getString("PARTY_CODE"),
                rs.getString("SUPP_NAME"),
                rs.getString("RATING"),
                rs.getString("ITEM_TYPE"),
                rs.getString("ITEM_CODE"),
                rs.getString("MISC_MAST_NAME"),
                rs.getString("ITEM_NAME"),
                rs.getString("SIZE"),
                rs.getString("SIZE_NAME"),
                rs.getString("COLOR"),
                rs.getString("BASE_UOM"),
                rs.getString("BASE_NAME"),
                rs.getString("CONV_FACTOR"),
                rs.getString("PUR_UOM"),
                rs.getString("PO_QTY"),
                rs.getString("RATE"),
                rs.getString("AMOUNT"),
                rs.getString("LANDING_COST"),
                rs.getString("TOLERANCE_PER"),
                rs.getString("REMARKS"),
                rs.getString("STATUS"),
                rs.getString("REF_PR_NO"),
                rs.getString("COMMENTS"),
                rs.getString("REF_QUOT_NO"),
                rs.getString("GATE_REC_QTY"),
                rs.getString("PACKING_TYPE_CODE"),
                rs.getString("PACKING_TYPE_NAME"),
                rs.getString("MODE"),
                rs.getString("ATTAHMENT"),
                rs.getString("ITEM_NAME_OTH"),
                rs.getString("MIN_QUOT_REQUIRED")
        );
    }

    private QuotationResponse mapRowToQuotationDto(ResultSet rs) throws SQLException {
        return new QuotationResponse(
                rs.getString("QUOT_NO"),
                rs.getString("QUOT_DATE"),
                rs.getString("PARTY_CODE"),
                rs.getString("SUPPLIER_NAME"),
                rs.getString("RATING"),
                rs.getString("QUOT_QTY"),
                rs.getString("RATE"),
                rs.getString("AMOUNT"),
                rs.getString("TOLERANCE_PER"),
                rs.getString("TAX_AMT"),
                rs.getString("LANDING_COST"),
                rs.getString("ITEM_CODE"),
                rs.getString("COLOR"),
                rs.getString("SIZE"),
                rs.getString("MANUAL_DOC_NO"),
                rs.getString("PR_APPROVAL_REMARKS")
        );
    }

    private PurchaseOrderApprovalDTO mapRowToApprovalDto(ResultSet rs) throws SQLException {
        return new PurchaseOrderApprovalDTO(
                rs.getString("RES")

        );
    }


    // ===================== PARAM BUILDERS =====================

    private Map<String, Object> buildParam(PurchaseRequest pr) {
        Map<String, Object> map = new HashMap<>();
        map.put(PARAM_COMP_CODE, pr.pCompCode());
        map.put(PARAM_CENT_CODE, pr.pCentCode());
        map.put(PARAM_BRANCH, pr.pBranch());
        map.put(PARAM_DEPTNO, pr.pDeptNo());
        map.put(PARAM_PRNO, pr.pPrNo());
        map.put(PARAM_ITEMTYPE, pr.pItemType());
        map.put(PARAM_ITEM, pr.pItem());
        map.put(PARAM_PR_DATE_FROM, pr.pPrDateFrom());
        map.put(PARAM_PR_DATE_TO, pr.pPrDateTo());
        map.put(PARAM_DATEFORMAT, pr.dateFormat());
        map.put(PARAM_EXTRA, pr.pExtra());
        map.put(PARAM_EXTRA1, pr.pExtra1());
        map.put(PARAM_EXTRA2, pr.pExtra2());
        map.put(PARAM_EXTRA3, pr.pExtra3());
        map.put(PARAM_EXTRA4, pr.pExtra4());
        map.put(PARAM_EXTRA5, pr.pExtra5());
        return map;
    }

    private Map<String, Object> buildParamPoApproval(PurchaseOrderRequestDTO pr) {
        Map<String, Object> map = new HashMap<>();
        map.put("pcompcode", pr.getPcompcode());
        map.put("pcenter", pr.getPcenter());
        map.put("pbranch", pr.getPbranch());
        map.put("pdocno", pr.getPdocno());
        map.put("pdocdate", pr.getPdocdate());
        map.put("pitemtype", pr.getPitemtype());
        map.put("pitemcode", pr.getPitemcode());
        map.put("puserid", pr.getPuserid());
        map.put("pappcencelfalg", pr.getPappcencelfalg());
        map.put("pstatus", pr.getPstatus());


        map.put("pextra1", pr.getPextra1());
        map.put("pextra2", pr.getPextra2());
        map.put("pextra3", pr.getPextra3());
        map.put("pextra4", pr.getPextra4());
        map.put("pextra5", pr.getPextra5());
        map.put("pextra6", pr.getPextra6());
        map.put("pextra7", pr.getPextra7());
        return map;


    }

    private Map<String, Object> buildParamQuotation(PurchaseRequest pr) {
        Map<String, Object> map = new HashMap<>();
        map.put(PARAM_COMP_CODE, pr.pCompCode());
        map.put(PARAM_CENT_CODE, pr.pCentCode());
        map.put(PARAM_BRANCH, pr.pBranch());
        map.put(PARAM_DEPTNO, pr.pDeptNo());
        map.put(PARAM_PRNO, pr.pPrNo());
        map.put(PARAM_COLOR, pr.color());
        map.put(PARAM_SIZE, pr.size());
        map.put(PARAM_ITEM_NAME, pr.itemName());
        map.put(PARAM_DATEFORMAT, pr.dateFormat());
        return map;
    }
}
