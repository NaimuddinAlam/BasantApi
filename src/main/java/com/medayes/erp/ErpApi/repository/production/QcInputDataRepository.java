package com.medayes.erp.ErpApi.repository.production;

import com.medayes.erp.ErpApi.dto.request.production.OperationRequestDto;
import com.medayes.erp.ErpApi.dto.response.production.OperationFiledResponseDto;
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
public class QcInputDataRepository {

    private final SimpleJdbcCall simpleJdbcCall;

    public QcInputDataRepository(JdbcTemplate jdbcTemplate) {

        this.simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("dbo")
                .withProcedureName("PRD_QC_INPUT_DATA_BIND")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("PCOMP_CODE", Types.VARCHAR),
                        new SqlParameter("PCENT_CODE", Types.VARCHAR),
                        new SqlParameter("PQC_TYPE", Types.VARCHAR),

                        new SqlParameter("PEXTRA1", Types.VARCHAR),
                        new SqlParameter("PEXTRA2", Types.VARCHAR),
                        new SqlParameter("PEXTRA3", Types.VARCHAR)

                )
                .returningResultSet("data", this::mapResponse);

        log.info("FieldOperationDetailsRepository initialized");
    }

    /**
     * Maps a stored procedure result row to the response DTO.
     */
    private OperationFiledResponseDto mapResponse(
            ResultSet rs, int rowNum) throws SQLException {

        return OperationFiledResponseDto.builder()
                .rowId(rs.getString("ROWID"))
                .inputValue(rs.getString("INPUT_VALUE"))

                .build();
    }





    public List<OperationFiledResponseDto> responseData(
            OperationRequestDto dto) {

        if (dto == null) {
            throw new IllegalArgumentException(
                    "OperationRequestDto must not be null");
        }

        Map<String, Object> result =
                simpleJdbcCall.execute(buildParameters(dto));

        Object resultData = result.get("data");

        if (resultData == null) {
            log.debug("No field operation records found");
            return Collections.emptyList();
        }

        @SuppressWarnings("unchecked")
        List<OperationFiledResponseDto> data =
                (List<OperationFiledResponseDto>) resultData;

        log.debug("Field operation records fetched: {}", data.size());

        return data;
    }

    /**
     * Builds stored procedure input parameters.
     */
    private Map<String, Object> buildParameters(
            OperationRequestDto dto) {

        Map<String, Object> params = new HashMap<>(20);

        params.put("PCOMP_CODE", dto.getCompCode());
        params.put("PCENT_CODE", dto.getCentCode());
        params.put("PQC_TYPE", dto.getQcType());
        params.put("PEXTRA1", dto.getExtra1());
        params.put("PEXTRA2", dto.getExtra2());
        params.put("PEXTRA3", dto.getExtra3());


        return params;
    }


}




