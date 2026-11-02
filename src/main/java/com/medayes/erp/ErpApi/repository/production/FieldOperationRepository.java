package com.medayes.erp.ErpApi.repository.production;

import com.medayes.erp.ErpApi.dto.request.production.OperationRequestDto;
import com.medayes.erp.ErpApi.dto.response.production.OperationFiledResponseDto;
import com.medayes.erp.ErpApi.dto.response.production.OperationResponseDto;
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
public class FieldOperationRepository {

    private final SimpleJdbcCall simpleJdbcCall;

    public FieldOperationRepository(JdbcTemplate jdbcTemplate) {

        this.simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("dbo")
                .withProcedureName("PRD_FIELD_OPERATIONS_P")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("PCOMP_CODE", Types.VARCHAR),
                        new SqlParameter("PCENT_CODE", Types.VARCHAR),
                        new SqlParameter("PREF_DIST_NO", Types.VARCHAR),
                        new SqlParameter("PFINYEAR_CODE", Types.VARCHAR),
                        new SqlParameter("PSEASION_CODE", Types.VARCHAR),
                        new SqlParameter("PITEM_CODE", Types.VARCHAR),
                        new SqlParameter("PSHOWING_FEMALE_DATE", Types.VARCHAR),
                        new SqlParameter("PSHOWING_MALE_DATE", Types.VARCHAR),
                        new SqlParameter("PEXTRA", Types.VARCHAR),
                        new SqlParameter("PEXTRA1", Types.VARCHAR),
                        new SqlParameter("PEXTRA2", Types.VARCHAR),
                        new SqlParameter("PEXTRA3", Types.VARCHAR),
                        new SqlParameter("PEXTRA4", Types.VARCHAR),
                        new SqlParameter("PEXTRA5", Types.VARCHAR),
                        new SqlParameter("PEXTRA6", Types.VARCHAR)
                )
                .returningResultSet("data", this::mapResponse);

        log.info("FieldOperationDetailsRepository initialized");
    }

    private OperationFiledResponseDto mapResponse(
            ResultSet rs, int rowNum) throws SQLException {

        return OperationFiledResponseDto.builder()
                .operTypeCode(rs.getString("OPER_TYPE_CODE"))
                .operTypeName(rs.getString("OPER_TYPE_NAME"))
                .operCode(rs.getString("OPER_CODE"))
                .operName(rs.getString("OPER_NAME"))
                .targetDays(rs.getString("TARGET_DAYS"))
                .planDate(rs.getString("PLAN_DATE"))
                .actDate(rs.getString("ACT_DATE"))
                .value(rs.getString("VALUE"))
                .rating(rs.getString("RATING"))
                .status(rs.getString("STATUS"))
                .remarks(rs.getString("REMARKS"))
                .lstActDate(rs.getString("LST_ACT_DATE"))
                .lstValue(rs.getString("LST_VALUE"))
                .lstRating(rs.getString("LST_RATING"))
                .lstStatus(rs.getString("LST_STATUS"))
                .lstStatusName(rs.getString("LST_STATUS_NAME"))
                .lstRemarks(rs.getString("LST_REMARKS"))
                .inputType(rs.getString("INPUT_TYPE"))
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

        Object resultData = result.get("#result-set-3");

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
        params.put("PREF_DIST_NO", dto.getRefDistNo());
        params.put("PFINYEAR_CODE", dto.getFinYear());
        params.put("PSEASION_CODE", dto.getSeasionCode());
        params.put("PITEM_CODE", dto.getItemCode());
        params.put("PSHOWING_FEMALE_DATE", dto.getSowingFemaleDate());
        params.put("PSHOWING_MALE_DATE", dto.getSowingMaleDate());
        params.put("PEXTRA", dto.getExtra());
        params.put("PEXTRA1", dto.getExtra1());
        params.put("PEXTRA2", dto.getExtra2());
        params.put("PEXTRA3", dto.getExtra3());
        params.put("PEXTRA4", dto.getExtra4());
        params.put("PEXTRA5", dto.getExtra5());
        params.put("PEXTRA6", dto.getExtra6());

        return params;
    }


}
