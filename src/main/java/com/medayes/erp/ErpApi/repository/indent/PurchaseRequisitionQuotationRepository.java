package com.medayes.erp.ErpApi.repository.indent;


import com.medayes.erp.ErpApi.dto.request.indent.QuotationRequisitionRequestDTO;
import com.medayes.erp.ErpApi.dto.response.indent.QuotationRequisitionDTO;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class PurchaseRequisitionQuotationRepository {
    private   SimpleJdbcCall simpleJdbcCall;
    private  final JdbcTemplate template;


    @PostConstruct
    public  void  init()
    {
        simpleJdbcCall=new SimpleJdbcCall(template)
                .withCatalogName("dbo")
                .withProcedureName("INV_PR_APP_FETCH_QUOTATION_APP")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("P_COMP_CODE", Types.VARCHAR),
                        new SqlParameter("P_CENT_CODE", Types.VARCHAR),
                        new SqlParameter("P_BRANCH",Types.VARCHAR),
                        new SqlParameter("P_DEPTNO",Types.VARCHAR),
                        new SqlParameter("P_PRNO",Types.VARCHAR),
                        new SqlParameter("PCOLOR",Types.VARCHAR),
                        new SqlParameter("PSIZE",Types.VARCHAR),
                        new SqlParameter("P_ITEM_NAME",Types.VARCHAR),
                        new SqlParameter("dateformat",Types.VARCHAR)
                        ).returningResultSet("Data",(rs, index)->maptoDto(rs))
        ;


    }

    private QuotationRequisitionDTO maptoDto(ResultSet rs) throws SQLException {
        QuotationRequisitionDTO requisitionDTO= new QuotationRequisitionDTO();
        requisitionDTO.setQuotNo(rs.getString("QUOT_NO"));
        requisitionDTO.setQuotDate(rs.getString("QUOT_DATE"));
        requisitionDTO.setPartyCode(rs.getString("PARTY_CODE"));
        requisitionDTO.setSupplierName(rs.getString("SUPPLIER_NAME"));
        requisitionDTO.setStateName(rs.getString("STATE_CODE"));
        requisitionDTO.setRate(rs.getString("RATE"));
        requisitionDTO.setQuotQty(rs.getString("QUOT_QTY"));
        requisitionDTO.setAmount(rs.getString("AMOUNT"));
        requisitionDTO.setTolerancePer(rs.getString("TOLERANCE_PER"));
        requisitionDTO.setTaxAmt(rs.getString("TAX_AMT"));
        requisitionDTO.setLandingCost(rs.getString("LANDING_COST"));
        requisitionDTO.setItemCode(rs.getString("ITEM_CODE"));
        requisitionDTO.setColor(rs.getString("COLOR"));
        requisitionDTO.setSize(rs.getString("SIZE"));
        requisitionDTO.setManualDocNo(rs.getString("MANUAL_DOC_NO"));
        requisitionDTO.setPrApprovalRemarks(rs.getString("PR_APPROVAL_REMARKS"));

        return  requisitionDTO;

    }

    public List<QuotationRequisitionDTO> response(QuotationRequisitionRequestDTO dto)
    {
        Map<String, Object> params = buildParams(dto);

        Map<String, Object> result = simpleJdbcCall.execute(params);

        return (List<QuotationRequisitionDTO>) result.get("Data");
    }

    private Map<String, Object> buildParams(QuotationRequisitionRequestDTO dto) {
        Map<String,Object> map= new HashMap<>();
        map.put("P_COMP_CODE",dto.getCompCode());
        map.put("P_CENT_CODE",dto.getCentCode());
        map.put("P_BRANCH",dto.getBranch());
        map.put("P_DEPTNO",dto.getDeptNo());
        map.put("P_PRNO",dto.getPrNo());
        map.put("PCOLOR",dto.getColor());
        map.put("PSIZE",dto.getSize());
        map.put("P_ITEM_NAME",dto.getItemName());
        map.put("dateformat",dto.getDateFormat());
        return  map;

    }


}
