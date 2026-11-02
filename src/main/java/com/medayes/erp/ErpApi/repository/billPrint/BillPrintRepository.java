package com.medayes.erp.ErpApi.repository.billPrint;

import com.medayes.erp.ErpApi.dto.request.BillPrintRequestDTO;
import com.medayes.erp.ErpApi.dto.response.billPrint.BillPrintsDTO;
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
public class BillPrintRepository {

    private SimpleJdbcCall simple;
    private final JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {

        simple = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")   // SQL Server schema
                .withProcedureName("PPC_SALE_HDR_APP")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("pcompcode", Types.VARCHAR),
                        new SqlParameter("pcentcode", Types.VARCHAR),
                        new SqlParameter("party_code", Types.VARCHAR),
                        new SqlParameter("pfrom_date", Types.VARCHAR),
                        new SqlParameter("pto_date", Types.VARCHAR),
                        new SqlParameter("pbill_no", Types.VARCHAR),
                        new SqlParameter("pextra1", Types.VARCHAR),
                        new SqlParameter("pextra2", Types.VARCHAR),
                        new SqlParameter("pextra3", Types.VARCHAR),
                        new SqlParameter("pextra4", Types.VARCHAR),
                        new SqlParameter("pextra5", Types.VARCHAR)
                )
                .returningResultSet("Data",
                        (rs, rowNum) -> mapRowToDto(rs)
                );
    }

    private BillPrintsDTO mapRowToDto(ResultSet rs) throws SQLException {
        return new BillPrintsDTO(
                rs.getString("COMP_CODE"),
                rs.getString("CENT_CODE"),
                rs.getString("BRANCH_NAME"),
                rs.getString("INVOICE_TYPE_CODE"),
                rs.getString("INV_NO"),
                rs.getString("INV_DATE"),
                rs.getString("SALE_TYPE"),
                rs.getString("SALE_TYPE_NAME"),
                rs.getString("BILL_PARTY_CODE"),
                rs.getString("LEDGER_NAME"),
                rs.getString("AMOUNT")
        );
    }

    public List<BillPrintsDTO> fetchBillInvoice(BillPrintRequestDTO dto) {
        Map<String, Object> params = buildParams(dto);
        Map<String, Object> result = simple.execute(params);
        return (List<BillPrintsDTO>) result.get("Data");
    }


    private Map<String, Object> buildParams(BillPrintRequestDTO dto) {

        Map<String, Object> map = new HashMap<>();

        map.put("pcompcode", dto.getPcompcode());
        map.put("pcentcode", dto.getPcentcode());
        map.put("party_code", dto.getParty_code());
        map.put("pfrom_date", dto.getPfrom_date());
        map.put("pto_date", dto.getPto_date());
        map.put("pbill_no", dto.getPbill_no());
        map.put("pextra1", dto.getPextra1());
        map.put("pextra2", dto.getPextra2());
        map.put("pextra3", dto.getPextra3());
        map.put("pextra4", dto.getPextra4());
        map.put("pextra5", dto.getPextra5());


        return map;
    }
}