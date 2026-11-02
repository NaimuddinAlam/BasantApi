package com.medayes.erp.ErpApi.repository.graph;

import com.medayes.erp.ErpApi.dto.request.AccountAnalysisRequestDto;
import com.medayes.erp.ErpApi.dto.response.AccountAnalysisResponseDto;
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
public class AccountAnalysisRepository {

    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall simpleJdbcCall;

    @PostConstruct
    private void init() {

        simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("PPC_ACCOUNT_ANALYSIS")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("pcompcode", Types.VARCHAR),
                        new SqlParameter("pcentcode", Types.VARCHAR),
                        new SqlParameter("pfrom_date", Types.VARCHAR),
                        new SqlParameter("pto_date", Types.VARCHAR),
                        new SqlParameter("pdateformat", Types.VARCHAR),
                        new SqlParameter("pexec_code", Types.VARCHAR),
                        new SqlParameter("pextra", Types.VARCHAR),
                        new SqlParameter("pextra1", Types.VARCHAR),
                        new SqlParameter("pextra2", Types.VARCHAR),
                        new SqlParameter("pextra3", Types.VARCHAR),
                        new SqlParameter("pextra4", Types.VARCHAR),
                        new SqlParameter("pextra5", Types.VARCHAR)
                )
                .returningResultSet("data", (rs, rowNum) -> mapResponse(rs));
    }

    private AccountAnalysisResponseDto mapResponse(ResultSet rs) throws SQLException {

        return new AccountAnalysisResponseDto(
                rs.getString("comp_code"),
                rs.getString("cent_code"),
                rs.getString("type_code"),
                rs.getString("type_desc"),
                rs.getString("amount") == null ? "0" : rs.getString("amount")
        );
    }

    @SuppressWarnings("unchecked")
    public List<AccountAnalysisResponseDto> graphResponse(AccountAnalysisRequestDto dto) {

        Map<String, Object> response = simpleJdbcCall.execute(buildParams(dto));

        return (List<AccountAnalysisResponseDto>) response.get("data");
    }

    private Map<String, Object> buildParams(AccountAnalysisRequestDto dto) {

        Map<String, Object> params = new HashMap<>();

        params.put("pcompcode", dto.getPcompcode());
        params.put("pcentcode", dto.getPcentcode());
        params.put("pfrom_date", dto.getPfrom_date());
        params.put("pto_date", dto.getPto_date());
        params.put("pdateformat", dto.getPdateformat());
        params.put("pexec_code", dto.getPexec_code());
        params.put("pextra", dto.getPextra());
        params.put("pextra1", dto.getPextra1());
        params.put("pextra2", dto.getPextra2());
        params.put("pextra3", dto.getPextra3());
        params.put("pextra4", dto.getPextra4());
        params.put("pextra5", dto.getPextra5());

        return params;
    }
}