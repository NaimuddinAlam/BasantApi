package com.medayes.erp.ErpApi.repository.production;

import com.medayes.erp.ErpApi.dto.request.production.QualityCheckRequestDto;
import com.medayes.erp.ErpApi.dto.response.production.ReasonResponseDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;






@Slf4j
@Repository
@RequiredArgsConstructor
public class ReasonRepository {

    private SimpleJdbcCall simpleJdbcCall;
    private SimpleJdbcCall simpleJdbcCalls;
    private final JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void init() {
        simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("PRD_BIND_REASON_PD")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("PCOMP_CODE", Types.VARCHAR),
                        new SqlParameter("PCENT_CODE", Types.VARCHAR),
                        new SqlParameter("PREASON_CODE", Types.VARCHAR),
                        new SqlParameter("PEXTRA1", Types.VARCHAR)

                )
                .returningResultSet("data", this::responseDto);
        simpleJdbcCalls = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("PRD_BIND_REASON_TYPE")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("PCOMP_CODE", Types.VARCHAR),
                        new SqlParameter("PCENT_CODE", Types.VARCHAR),
                        new SqlParameter("PREASON_CODE", Types.VARCHAR),
                        new SqlParameter("PEXTRA1", Types.VARCHAR)

                )
                .returningResultSet("data", this::responseDto);
    }


    private ReasonResponseDto responseDto(
            ResultSet rs, int rowNum) throws SQLException {

        return ReasonResponseDto.builder()
                .reasonCode(rs.getString("REASON_CODE"))
                .reasonName(rs.getString("REASON_NAME"))

                .build();
    }

    public List<ReasonResponseDto> response(
            QualityCheckRequestDto dto) {

        Map<String, Object> params = buildParams(dto);

        log.debug("Reason  request parameters: {}", params);

        Map<String, Object> result = simpleJdbcCall.execute(params);

        Object resultData = result.get("data");

        if (resultData == null) {
            log.debug("No Reason  records found");
            return Collections.emptyList();
        }

        @SuppressWarnings("unchecked")
        List<ReasonResponseDto> data =
                (List<ReasonResponseDto>) resultData;

        log.debug("Reason records fetched: {}", data.size());

        return data;
    }
    public List<ReasonResponseDto> responseType(
            QualityCheckRequestDto dto) {

        Map<String, Object> params = buildParams(dto);

        log.debug("Reason  request parameters: {}", params);

        Map<String, Object> result = simpleJdbcCalls.execute(params);

        Object resultData = result.get("data");

        if (resultData == null) {
            log.debug("No Reason  records found");
            return Collections.emptyList();
        }

        @SuppressWarnings("unchecked")
        List<ReasonResponseDto> data =
                (List<ReasonResponseDto>) resultData;

        log.debug("Reason records fetched: {}", data.size());

        return data;
    }

    private Map<String, Object> buildParams(
            QualityCheckRequestDto dto) {

        Map<String, Object> params = new HashMap<>();

        params.put("PCOMP_CODE", dto.getCompCode());
                params.put("PCENT_CODE", dto.getCentCode());
                params.put("PREASON_CODE", dto.getReasonCode());

                params.put("PEXTRA1",  dto.getExtra1());



        return params;
    }
}