package com.medayes.erp.ErpApi.repository.indent;

import com.medayes.erp.ErpApi.dto.request.indent.PendingPurchaseRequisitionRequestDTO;
import com.medayes.erp.ErpApi.dto.request.indent.PendingPurchaseRequisitionRequestStatusDTO;
import com.medayes.erp.ErpApi.dto.response.indent.IssueHeaderRequestDTO;
import com.medayes.erp.ErpApi.dto.response.indent.IssueRequestDTO;
import com.medayes.erp.ErpApi.dto.response.indent.PendingPurchaseRequisitionDTO;


import com.medayes.erp.ErpApi.dto.response.indent.PurchaseRequisitionResponseDTO;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;

import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class PendingPurchaseRequisitionRepository implements PurchaseRequisitionRepositoryServices {
    private final JdbcTemplate jdbcTemplate;
    private static final String RESULT_SET = "Data";
    private SimpleJdbcCall pendingRSCall;
    private SimpleJdbcCall prDetailCall;
    private SimpleJdbcCall prHeaderCall;
    private SimpleJdbcCall prStatusCall;
    private SimpleJdbcCall prApprovalCall;
    private final String compCode="P_COMP_CODE";
    private final String centCode="P_CENT_CODE";
    private final String branchCode="P_BRANCH";
    private final String deptCode="P_DEPTNO";
    private final String rsNo="P_RSNO";
    private final String itemtype="P_ITEMTYPE";
    private final String itemCode="P_ITEM";
    private final String rsDateFrom="P_RS_Date_From";
    private final String rdDateTo="PRS_Date_To";
    private final String dateFormat="dateformat";
    private final String pextra="PEXTRA";
    private final String pextra1="PEXTRA1";
    private final String pextra2="PEXTRA2";
    private final String pextra3="PEXTRA3";
    private final String pextra4="PEXTRA4";
    private final String pextra5="PEXTRA5";


    @PostConstruct
    public  void  init()
    {
        pendingRSCall= new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("dbo")
                .withProcedureName("INV_Pending_RSFORPR_FETCH_APP")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter(compCode, Types.VARCHAR),
                        new SqlParameter(centCode, Types.VARCHAR),
                        new SqlParameter(branchCode, Types.VARCHAR),
                        new SqlParameter(deptCode, Types.VARCHAR),
                        new SqlParameter(rsNo, Types.VARCHAR),
                        new SqlParameter(itemtype, Types.VARCHAR) ,
                        new SqlParameter(itemCode, Types.VARCHAR),
                        new SqlParameter(rsDateFrom, Types.VARCHAR),
                        new SqlParameter(rdDateTo, Types.VARCHAR),
                        new SqlParameter(dateFormat, Types.VARCHAR),
                        new SqlParameter(pextra, Types.VARCHAR),
                        new SqlParameter(pextra1, Types.VARCHAR),
                        new SqlParameter(pextra2, Types.VARCHAR),
                        new SqlParameter(pextra3, Types.VARCHAR),
                        new SqlParameter(pextra4, Types.VARCHAR),
                        new SqlParameter(pextra5, Types.VARCHAR)
                ).returningResultSet("Data",(rs,index)->mapToDTO( rs));
        prDetailCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("dbo")
                .withProcedureName("INV_PR_DET_INSUPDDEL_APP")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(

                        new SqlParameter("PCOMP_CODE", Types.VARCHAR),
                        new SqlParameter("PCENT_CODE", Types.VARCHAR),
                        new SqlParameter("PBRANCH_CODE", Types.VARCHAR),
                        new SqlParameter("PWH_CODE", Types.VARCHAR),
                        new SqlParameter("PISSUE_NO", Types.VARCHAR),
                        new SqlParameter("PISSUE_DET_NO", Types.VARCHAR),
                        new SqlParameter("PITEM_TYPE", Types.VARCHAR),
                        new SqlParameter("PITEM_CODE", Types.VARCHAR),
                        new SqlParameter("PSIZE", Types.VARCHAR),
                        new SqlParameter("PCOLOR", Types.VARCHAR),
                        new SqlParameter("PBASE_UOM", Types.VARCHAR),
                        new SqlParameter("PCONV_FACTOR", Types.VARCHAR),
                        new SqlParameter("PPUR_UOM", Types.VARCHAR),
                        new SqlParameter("PISSUE_QTY", Types.VARCHAR),
                        new SqlParameter("PQC", Types.VARCHAR),
                        new SqlParameter("PLANDING_COST", Types.VARCHAR),
                        new SqlParameter("PISSUE_AMOUNT", Types.VARCHAR),
                        new SqlParameter("PREMARKS", Types.VARCHAR),
                        new SqlParameter("PRACK_NO", Types.VARCHAR),
                        new SqlParameter("PBIN_NO", Types.VARCHAR),
                        new SqlParameter("PREF_RS_NO", Types.VARCHAR),
                        new SqlParameter("PREF_RS_DATE", Types.VARCHAR),
                        new SqlParameter("PSTATUS", Types.VARCHAR),
                        new SqlParameter("PIP", Types.VARCHAR),
                        new SqlParameter("PUSER", Types.VARCHAR),
                        new SqlParameter("Type", Types.VARCHAR),
                        new SqlParameter("PISS_NOTMP", Types.VARCHAR),
                        new SqlParameter("PRO_AGINT", Types.VARCHAR),
                        new SqlParameter("PREF_JOB_NO", Types.VARCHAR),
                        new SqlParameter("PREF_FIN_ITEM_CODE", Types.VARCHAR),
                        new SqlParameter("PEXTRA", Types.VARCHAR),
                        new SqlParameter("PEXTRA1", Types.VARCHAR),
                        new SqlParameter("PEXTRA2", Types.VARCHAR),
                        new SqlParameter("PEXTRA3", Types.VARCHAR),
                        new SqlParameter("PEXTRA4", Types.VARCHAR),
                        new SqlParameter("PEXTRA5", Types.VARCHAR),
                        new SqlParameter("PEXTRA6", Types.VARCHAR),
                        new SqlParameter("PEXTRA7", Types.VARCHAR),
                        new SqlParameter("PEXTRA8", Types.VARCHAR),
                        new SqlParameter("PEXTRA9", Types.VARCHAR),
                        new SqlParameter("PEXTRA10", Types.VARCHAR)
                )
                .returningResultSet("Data", (rs, index) -> rs.getString("RES"));


        prHeaderCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("dbo")
                .withProcedureName("INV_PR_HDR_INSUPDDEL_APP")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(

                        new SqlParameter("PCOMP_CODE", Types.VARCHAR),
                        new SqlParameter("PCENT_CODE", Types.VARCHAR),
                        new SqlParameter("PBRANCH", Types.VARCHAR),
                        new SqlParameter("PWH_CODE", Types.VARCHAR),
                        new SqlParameter("PISSUE_TYP", Types.VARCHAR),
                        new SqlParameter("PISSUE_AG", Types.VARCHAR),
                        new SqlParameter("PISSUE_NO", Types.VARCHAR),
                        new SqlParameter("PISSUE_DATE", Types.VARCHAR),
                        new SqlParameter("PDEPTT_CODE", Types.VARCHAR),
                        new SqlParameter("PREMARKS", Types.VARCHAR),
                        new SqlParameter("PSTATUS", Types.VARCHAR),
                        new SqlParameter("PIP", Types.VARCHAR),
                        new SqlParameter("PUSER", Types.VARCHAR),
                        new SqlParameter("PTYPE1", Types.VARCHAR),
                        new SqlParameter("PISSNOTMP", Types.VARCHAR),
                        new SqlParameter("PEXTRA1", Types.VARCHAR),
                        new SqlParameter("PEXTRA2", Types.VARCHAR),
                        new SqlParameter("PEXTRA3", Types.VARCHAR)

                )
                .returningResultSet("Data",(rs,index)->rs.getString( "RES"));

        prStatusCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("dbo")
                .withProcedureName("INV_PR_APP_FETCH")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("P_COMP_CODE", Types.VARCHAR),
                        new SqlParameter("P_CENT_CODE", Types.VARCHAR),
                        new SqlParameter("P_BRANCH", Types.VARCHAR),
                        new SqlParameter("P_DEPTNO", Types.VARCHAR),
                        new SqlParameter("P_PRNO", Types.VARCHAR),
                        new SqlParameter("P_ITEMTYPE", Types.VARCHAR),
                        new SqlParameter("P_ITEM", Types.VARCHAR),
                        new SqlParameter("P_PR_Date_From", Types.VARCHAR),
                        new SqlParameter("PPR_Date_To", Types.VARCHAR),
                        new SqlParameter("dateformat", Types.VARCHAR),
                        new SqlParameter("PEXTRA", Types.VARCHAR),
                        new SqlParameter("PEXTRA1", Types.VARCHAR),
                        new SqlParameter("PEXTRA2", Types.VARCHAR),
                        new SqlParameter("PEXTRA3", Types.VARCHAR),
                        new SqlParameter("PEXTRA4", Types.VARCHAR),
                        new SqlParameter("PEXTRA5", Types.VARCHAR)
                )
                .returningResultSet(RESULT_SET, (rs, rowNum) -> mapToDTOs(rs));
         prApprovalCall = new SimpleJdbcCall(jdbcTemplate)
            .withSchemaName("dbo")
            .withProcedureName("INV_PR_APP_SAVE_APPS")
            .withoutProcedureColumnMetaDataAccess()
            .declareParameters(
                    new SqlParameter("pcompcode", Types.VARCHAR),
                    new SqlParameter("pcenter", Types.VARCHAR),
                    new SqlParameter("pbranch", Types.VARCHAR),
                    new SqlParameter("pdocno", Types.VARCHAR),
                    new SqlParameter("pitemtype", Types.VARCHAR),
                    new SqlParameter("pitemcode", Types.VARCHAR),
                    new SqlParameter("puserid", Types.VARCHAR),
                    new SqlParameter("pappcencelfalg", Types.VARCHAR),
                    new SqlParameter("pextra1", Types.VARCHAR),
                    new SqlParameter("pextra2", Types.VARCHAR),
                    new SqlParameter("pextra3", Types.VARCHAR),
                    new SqlParameter("pextra4", Types.VARCHAR),
                    new SqlParameter("pextra5", Types.VARCHAR),
                    new SqlParameter("pextra6", Types.VARCHAR),
                    new SqlParameter("pextra7", Types.VARCHAR),
                    new SqlParameter("pextra8", Types.VARCHAR),
                    new SqlParameter("pextra9", Types.VARCHAR),
                    new SqlParameter("pextra10", Types.VARCHAR)

            )
                 .returningResultSet("Data",(rs,index)->rs.getString( "RES"));



    }
    




    public PendingPurchaseRequisitionDTO mapToDTO(ResultSet rs) throws SQLException {

        PendingPurchaseRequisitionDTO dto = new PendingPurchaseRequisitionDTO();
        dto.setRefJobNo(rs.getString("REF_JOB_NO"));
        dto.setRefJobDate(rs.getString("REF_JOB_DATE"));
        dto.setRefFinItemCode(rs.getString("REF_FIN_ITEM_CODE"));

        dto.setCompCode(rs.getString("COMP_CODE"));
        dto.setCentCode(rs.getString("CENT_CODE"));
        dto.setBranchCode(rs.getString("BRANCH_CODE"));
        dto.setCentName(rs.getString("CENT_NAME"));
        dto.setBranchName(rs.getString("BRANCH_NAME"));
        dto.setRsNo(rs.getString("RS_NO"));
        dto.setRsDate(rs.getString("RS_DATE"));



        dto.setDepttCode(rs.getString("DEPTT_CODE"));
        dto.setDepttName(rs.getString("DEPTT_NAME"));
        dto.setWorkCenterCode(rs.getString("WORK_CENTER_CODE"));
        dto.setWorkCenterName(rs.getString("WORK_CENTER_NAME"));
        dto.setItemType(rs.getString("ITEM_TYPE"));
        dto.setItemTypeName(rs.getString("ITEM_TYPE_NAME"));
        dto.setItemCode(rs.getString("ITEM_CODE"));
        dto.setItemName(rs.getString("ITME_NAME"));
        dto.setSize(rs.getString("SIZE"));
        dto.setSizeName(rs.getString("SIZE_NAME"));
        dto.setColor(rs.getString("COLOR"));
        dto.setBaseUom(rs.getString("BASE_UOM"));
        dto.setBaseName(rs.getString("BASE_NAME"));
        dto.setConvFactor(rs.getString("CONV_FACTOR"));
        dto.setPurUom(rs.getString("PUR_UOM"));
        dto.setPurUomName(rs.getString("PUR_UOM_NAME"));
        dto.setPackingTypeCode(rs.getString("PACKING_TYPE_CODE"));
        dto.setRsQty(rs.getString("RS_QTY"));
        dto.setPrRaisedQty(rs.getString("PR_RAISED_QTY"));
        dto.setIssuedQty(rs.getString("ISSUED_QTY"));
        dto.setRsDisappQty(rs.getString("RS_DISAPP_QTY"));
        dto.setRsBalanceQty(rs.getString("RS_BALANCE_QTY"));
        dto.setRsApprovalFlag(rs.getString("RS_APPROVAL_FLAG"));
        dto.setRsApprovalUser(rs.getString("RS_APPROVAL_USER"));
        dto.setRsApprovalDate(rs.getString("RS_APPROVAL_DATE"));
        dto.setRsCancelFlag(rs.getString("RS_CANCEL_FLAG"));
        dto.setRsCancelDate(rs.getString("RS_CANCEL_DATE"));
        dto.setRsCancelUser(rs.getString("RS_CANCEL_USER"));
        dto.setRsCloseFlag(rs.getString("RS_CLOSE_FLAG"));
        dto.setRsCloseUser(rs.getString("RS_CLOSE_USER"));
        dto.setRsCloseDate(rs.getString("RS_CLOSE_DATE"));
        dto.setClosingStk(rs.getString("CLOSING_STK"));
        dto.setAverageRate(rs.getString("AVERAGE_RATE"));
        dto.setRemarks(rs.getString("REMARKS"));
        dto.setLastPurRate(rs.getString("LAST_PUR_RATE"));
        dto.setDependancy(rs.getString("DEPENDANCY"));
        dto.setProductCode(rs.getString("PRODUCT_CODE"));

        return dto;
    }

    public PurchaseRequisitionResponseDTO mapToDTOs(ResultSet rs) throws SQLException {

        PurchaseRequisitionResponseDTO dto = new PurchaseRequisitionResponseDTO();



        dto.setRefJobNo(rs.getString("REF_JOB_NO"));
        dto.setRefJobDate(rs.getString("REF_JOB_DATE"));
        dto.setRefFinItemCode(rs.getString("REF_FIN_ITEM_CODE"));
        dto.setPrDate(rs.getString("PR_DATE"));
        dto.setRefQuotNo(rs.getString("REF_QUOT_NO"));

       dto.setCompCode(rs.getString("COMP_CODE"));
        dto.setCentCode(rs.getString("CENT_CODE"));
        dto.setBranchCode(rs.getString("BRANCH_CODE"));
        dto.setPrNo(rs.getString("PR_NO"));

        dto.setItemType(rs.getString("ITEM_TYPE"));
        dto.setItemCode(rs.getString("ITEM_CODE"));
        dto.setMiscMastName(rs.getString("MISC_MAST_NAME"));
        dto.setItemName(rs.getString("ITEM_NAME"));

        dto.setSize(rs.getString("SIZE"));
        dto.setSizeName(rs.getString("SIZE_NAME"));
        dto.setColor(rs.getString("COLOR"));

        dto.setBaseUom(rs.getString("BASE_UOM"));
        dto.setBaseName(rs.getString("BASE_NAME"));
        dto.setConvFactor(rs.getString("CONV_FACTOR"));

        dto.setPurUom(rs.getString("PUR_UOM"));
        dto.setPurUomName(rs.getString("PUR_UOM_NAME"));

        dto.setPrQty(rs.getString("PR_QTY"));
        dto.setPoRaisedQty(rs.getString("PO_RAISED_QTY"));
        dto.setPrBalanceQty(rs.getString("PR_BALANCE_QTY"));

        dto.setPrApprovalFlag(rs.getString("PR_APPROVAL_FLAG"));
        dto.setPrApprovalUser(rs.getString("PR_APPROVAL_USER"));
        dto.setPrApprovalDate(rs.getString("PR_APPROVAL_DATE"));

        dto.setPrCancelFlag(rs.getString("PR_CANCEL_FLAG"));
        dto.setPrCancelDate(rs.getString("PR_CANCEL_DATE"));
        dto.setPrCancelUser(rs.getString("PR_CANCEL_USER"));

        dto.setPrCloseFlag(rs.getString("PR_CLOSE_FLAG"));
        dto.setPrCloseUser(rs.getString("PR_CLOSE_USER"));
        dto.setPrCloseDate(rs.getString("PR_CLOSE_DATE"));

        dto.setRemarks(rs.getString("REMARKS"));
        dto.setStatus(rs.getString("STATUS"));
        dto.setComments(rs.getString("COMMENTS"));

        dto.setRefRsNo(rs.getString("REF_RS_NO"));
        dto.setQuotCnt(rs.getString("QUOT_CNT"));
        dto.setItemQuotCnt(rs.getString("ITEM_QUOT_CNT"));

        dto.setProductCode(rs.getString("PRODUCT_CODE"));
        dto.setMode(rs.getString("MODE"));
        dto.setConvOperator(rs.getString("CONV_OPERATOR"));

        return dto;
    }


@Override
    public List<PendingPurchaseRequisitionDTO> response( PendingPurchaseRequisitionRequestDTO dto)
    {
        Map<String, Object> params = buildParams(dto);

        Map<String, Object> result = pendingRSCall.execute(params);

        return (List<PendingPurchaseRequisitionDTO>) result.get("Data");
    }

    @Override
    public String savePRDETINSUPDDEL(IssueRequestDTO dto) {

        Map<String, Object> params = buildParams(dto);

        Map<String, Object> result=  prDetailCall.execute(params);

        List<String> data = (List<String>) result.get("Data");

        return (data != null && !data.isEmpty()) ? data.get(0) : null;
    }

    @Override
    public String savePRHDRINSUPDDEL(IssueHeaderRequestDTO dto) {

        Map<String, Object> params = buildParams(dto);

        Map<String, Object> result=  prHeaderCall.execute(params);

        List<String> data = (List<String>) result.get("Data");

        return (data != null && !data.isEmpty()) ? data.get(0) : null;
    }

    @Override
    public List<PurchaseRequisitionResponseDTO> purchaseRequisitionStatus(PendingPurchaseRequisitionRequestStatusDTO dto) {
        Map<String, Object> params = buildParamMap(dto);


        Map<String, Object> result = prStatusCall.execute(params);

        List<PurchaseRequisitionResponseDTO> list =
                (List<PurchaseRequisitionResponseDTO>) result.get("Data");

        return list != null ? list : Collections.emptyList();

    }

    @Override
    public String savePurchaseRequisitionApproval(PendingPurchaseRequisitionRequestStatusDTO dto) {
        Map<String, Object> params = buildParamsPRApproval(dto);

        Map<String, Object> result=  prApprovalCall.execute(params);

        List<String> data = (List<String>) result.get("Data");

        return (data != null && !data.isEmpty()) ? data.get(0) : null;
    }

    Map<String, Object> buildParams(PendingPurchaseRequisitionRequestDTO dto)
    {
        HashMap<String, Object> map= new HashMap<>();
        map.put(compCode,dto.getCompCode());
        map.put(centCode,dto.getCentCode());
        map.put(branchCode,dto.getBranchCode());
        map.put(deptCode,dto.getDeptCode());
        map.put(rsNo,dto.getRsNo());
        map.put(itemtype,dto.getItemtype());
        map.put(itemCode,dto.getItemCode());
        map.put(rsDateFrom,dto.getRsDateFrom());
        map.put(rdDateTo,dto.getRdDateTo());
        map.put(dateFormat,dto.getDateFormat());
        map.put(pextra,dto.getPextra());
        map.put(pextra1,dto.getPextra1());
        map.put(pextra2,dto.getPextra2());
        map.put(pextra3,dto.getPextra3());
        map.put(pextra4,dto.getPextra4());
        map.put(pextra5,dto.getPextra5());
       return   map;
    }
    Map<String, Object> buildParamsPRApproval(PendingPurchaseRequisitionRequestStatusDTO dto)
    {
        HashMap<String, Object> map= new HashMap<>();
        map.put("pcompcode",dto.getCompCode());
        map.put("pcenter",dto.getCentCode());
        map.put("pbranch",dto.getBranch());
        map.put("pdocno",dto.getPrNo());
        map.put("pitemtype",dto.getItemType());
        map.put("pitemcode",dto.getItem());
        map.put("puserid",dto.getUserId());
        map.put("pappcencelfalg",dto.getPappcencelfalg());
        map.put("pextra1",dto.getPextra1());
        map.put("pextra2",dto.getPextra2());
        map.put("pextra3",dto.getPextra3());
        map.put("pextra4",dto.getPextra4());
        map.put("pextra5",dto.getPextra5());
        map.put("pextra6",dto.getPextra6());
        map.put("pextra7",dto.getPextra7());
        map.put("pextra8",dto.getPextra8());
        map.put("pextra9",dto.getPextra9());
        map.put("pextra10",dto.getPextra10());
        return   map;


    }


    private  Map<String, Object> buildParams(IssueRequestDTO dto) {

        Map<String, Object> params = new HashMap<>();

        params.put("PCOMP_CODE", dto.getPcompCode());
        params.put("PCENT_CODE", dto.getPcentCode());
        params.put("PBRANCH_CODE", dto.getPbranchCode());
        params.put("PWH_CODE", dto.getPwhCode());
        params.put("PISSUE_NO", dto.getPissueNo());
        params.put("PISSUE_DET_NO", dto.getPissueDetNo());
        params.put("PITEM_TYPE", dto.getPitemType());
        params.put("PITEM_CODE", dto.getPitemCode());
        params.put("PSIZE", dto.getPsize());
        params.put("PCOLOR", dto.getPcolor());
        params.put("PBASE_UOM", dto.getPbaseUom());
        params.put("PCONV_FACTOR", dto.getPconvFactor());
        params.put("PPUR_UOM", dto.getPpurUom());
        params.put("PISSUE_QTY", dto.getPissueQty());
        params.put("PQC", dto.getPqc());
        params.put("PLANDING_COST", dto.getPlandingCost());
        params.put("PISSUE_AMOUNT", dto.getPissueAmount());
        params.put("PREMARKS", dto.getPremarks());
        params.put("PRACK_NO", dto.getPrackNo());
        params.put("PBIN_NO", dto.getPbinNo());
        params.put("PREF_RS_NO", dto.getPrefRsNo());
        params.put("PREF_RS_DATE", dto.getPrefRsDate());
        params.put("PSTATUS", dto.getPstatus());
        params.put("PIP", dto.getPip());
        params.put("PUSER", dto.getPuser());
        params.put("Type", dto.getType());
        params.put("PISS_NOTMP", dto.getPissNotmp());
        params.put("PRO_AGINT", dto.getProAgint());
        params.put("PREF_JOB_NO", dto.getPrefJobNo());
        params.put("PREF_FIN_ITEM_CODE", dto.getPrefFinItemCode());
        params.put("PEXTRA", dto.getPextra());
        params.put("PEXTRA1", dto.getPextra1());
        params.put("PEXTRA2", dto.getPextra2());
        params.put("PEXTRA3", dto.getPextra3());
        params.put("PEXTRA4", dto.getPextra4());
        params.put("PEXTRA5", dto.getPextra5());
        params.put("PEXTRA6", dto.getPextra6());
        params.put("PEXTRA7", dto.getPextra7());
        params.put("PEXTRA8", dto.getPextra8());
        params.put("PEXTRA9", dto.getPextra9());
        params.put("PEXTRA10", dto.getPextra10());

        return params;
    }
    private Map<String, Object> buildParams(IssueHeaderRequestDTO dto) {

        Map<String, Object> params = new HashMap<>();

        params.put("PCOMP_CODE", dto.getPcompCode());
        params.put("PCENT_CODE", dto.getPcentCode());
        params.put("PBRANCH", dto.getPbranch());
        params.put("PWH_CODE", dto.getPwhCode());
        params.put("PISSUE_TYP", dto.getPissueTyp());
        params.put("PISSUE_AG", dto.getPissueAg());
        params.put("PISSUE_NO", dto.getPissueNo());
        params.put("PISSUE_DATE", dto.getPissueDate());
        params.put("PDEPTT_CODE", dto.getPdepttCode());
        params.put("PREMARKS", dto.getPremarks());
        params.put("PSTATUS", dto.getPstatus());
        params.put("PIP", dto.getPip());
        params.put("PUSER", dto.getPuser());
        params.put("PTYPE1", dto.getPtype1());
        params.put("PISSNOTMP", dto.getPissnotmp());
        params.put("PEXTRA1", dto.getPextra1());
        params.put("PEXTRA2", dto.getPextra2());
        params.put("PEXTRA3", dto.getPextra3());

        return params;
    }

    public Map<String, Object> buildParamMap(PendingPurchaseRequisitionRequestStatusDTO request) {

        Map<String, Object> params = new HashMap<>();

        params.put("P_COMP_CODE", request.getCompCode());
        params.put("P_CENT_CODE", request.getCentCode());
        params.put("P_BRANCH", request.getBranch());

        params.put("P_DEPTNO", request.getDeptNo());
        params.put("P_PRNO", request.getPrNo());
        params.put("P_ITEMTYPE", request.getItemType());
        params.put("P_ITEM", request.getItem());

        params.put("P_PR_Date_From", request.getPrDateFrom());
        params.put("PPR_Date_To", request.getPrDateTo());

        params.put("dateformat", request.getDateFormat());

        params.put("PEXTRA", request.getPextra());
        params.put("PEXTRA1", request.getPextra1());
        params.put("PEXTRA2", request.getPextra2());
        params.put("PEXTRA3", request.getPextra3());
        params.put("PEXTRA4", request.getPextra4());
        params.put("PEXTRA5", request.getPextra5());

        return params;
    }

}
