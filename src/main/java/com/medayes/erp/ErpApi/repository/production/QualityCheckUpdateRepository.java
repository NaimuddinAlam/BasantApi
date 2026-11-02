package com.medayes.erp.ErpApi.repository.production;


import com.medayes.erp.ErpApi.dto.request.production.QualityCheckUpdateRequestDto;

import com.medayes.erp.ErpApi.dto.response.production.QualityCheckUpdateResponseDto;
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
public class QualityCheckUpdateRepository {

    private SimpleJdbcCall simpleJdbcCall;
    private final JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void init() {
        simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("PRD_QUALITY_UPDATE_APP")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("PCOMP_CODE", Types.VARCHAR),
                        new SqlParameter("PCENT_CODE", Types.VARCHAR),
                        new SqlParameter("PDIST_NO", Types.VARCHAR),
                        new SqlParameter("PORG_CODE", Types.VARCHAR),
                        new SqlParameter("PFARMER_CODE", Types.VARCHAR),
                        new SqlParameter("PITEM_CODE", Types.VARCHAR),
                        new SqlParameter("PSIZE_CODE", Types.VARCHAR),
                        new SqlParameter("PBATCH_MALE", Types.VARCHAR),
                        new SqlParameter("PBATCH_FEMALE", Types.VARCHAR),
                        new SqlParameter("PSOWING_DATE_MALE_1", Types.VARCHAR),
                        new SqlParameter("PSOWING_DATE_FEMALE_1", Types.VARCHAR),
                        new SqlParameter("PSOWING_DATE_MALE_2", Types.VARCHAR),
                        new SqlParameter("PSOWING_DATE_FEMALE_2", Types.VARCHAR),
                        new SqlParameter("PSURVEY_NO", Types.VARCHAR),
                        new SqlParameter("PFARMER_LOT_NO", Types.VARCHAR),
                        new SqlParameter("PPREV_CROP_CODE", Types.VARCHAR),
                        new SqlParameter("PTOTAL_ROWS_MALE", Types.VARCHAR),
                        new SqlParameter("PTOTAL_ROWS_FEMALE", Types.VARCHAR),
                        new SqlParameter("PPLANTS_IN_ROW_MALE", Types.VARCHAR),
                        new SqlParameter("PPLANTS_IN_ROW_FEMALE", Types.VARCHAR),
                        new SqlParameter("PSTATUS", Types.VARCHAR),
                        new SqlParameter("PEXTRA1", Types.VARCHAR),
                        new SqlParameter("PEXTRA2", Types.VARCHAR),
                        new SqlParameter("PEXTRA3", Types.VARCHAR),
                        new SqlParameter("PEXTRA4", Types.VARCHAR),
                        new SqlParameter("PEXTRA5", Types.VARCHAR),
                        new SqlParameter("QC_STATUS", Types.VARCHAR),
                        new SqlParameter("QC_REJECT_REASON_CODE", Types.VARCHAR)
                )
                .returningResultSet("data", this::responseDto);
    }

    private QualityCheckUpdateResponseDto responseDto(
            ResultSet rs, int rowNum) throws SQLException {

        return QualityCheckUpdateResponseDto.builder()
                .res(rs.getString("RES"))

                .build();
    }


    public List<QualityCheckUpdateResponseDto> response(
            QualityCheckUpdateRequestDto dto) {

        Map<String, Object> params = buildParams(dto);

        log.debug("Quality check request parameters: {}", params);

        Map<String, Object> result = simpleJdbcCall.execute(params);

        Object resultData = result.get("data");

        if (resultData == null) {
            log.debug("No quality check records found");
            return Collections.emptyList();
        }

        @SuppressWarnings("unchecked")
        List<QualityCheckUpdateResponseDto> data =
                (List<QualityCheckUpdateResponseDto>) resultData;

        log.debug("Quality check records fetched: {}", data.size());

        return data;
    }

    private Map<String, Object> buildParams(
            QualityCheckUpdateRequestDto dto) {

        Map<String, Object> params = new HashMap<>();

        params.put("PCOMP_CODE", dto.getPcompCode());
        params.put("PCENT_CODE", dto.getPcentCode());
        params.put("PDIST_NO", dto.getPdistNo());
        params.put("PORG_CODE", dto.getPorgCode());
        params.put("PFARMER_CODE", dto.getPfarmerCode());
        params.put("PITEM_CODE", dto.getPitemCode());
        params.put("PSIZE_CODE", dto.getPsizeCode());

        params.put("PBATCH_MALE", dto.getPbatchMale());
        params.put("PBATCH_FEMALE", dto.getPbatchFemale());

        params.put("PSOWING_DATE_MALE_1", dto.getPsowingDateMale1());
        params.put("PSOWING_DATE_FEMALE_1", dto.getPsowingDateFemale1());
        params.put("PSOWING_DATE_MALE_2", dto.getPsowingDateMale2());
        params.put("PSOWING_DATE_FEMALE_2", dto.getPsowingDateFemale2());

        params.put("PSURVEY_NO", dto.getPsurveyNo());
        params.put("PFARMER_LOT_NO", dto.getPfarmerLotNo());
        params.put("PPREV_CROP_CODE", dto.getPprevCropCode());

        params.put("PTOTAL_ROWS_MALE", dto.getPtotalRowsMale());
        params.put("PTOTAL_ROWS_FEMALE", dto.getPtotalRowsFemale());

        params.put("PPLANTS_IN_ROW_MALE", dto.getPplantsInRowMale());
        params.put("PPLANTS_IN_ROW_FEMALE", dto.getPplantsInRowFemale());

        params.put("PSTATUS", dto.getPstatus());

        params.put("PEXTRA1", dto.getPextra1());
        params.put("PEXTRA2", dto.getPextra2());
        params.put("PEXTRA3", dto.getPextra3());
        params.put("PEXTRA4", dto.getPextra4());
        params.put("PEXTRA5", dto.getPextra5());

        params.put("QC_STATUS", dto.getQcStatus());
        params.put("QC_REJECT_REASON_CODE", dto.getQcRejectReasonCode());

        return params;
    }
}