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
public class FieldOperationDetSaveRepository {

    private final SimpleJdbcCall simpleJdbcCall;

    public FieldOperationDetSaveRepository(JdbcTemplate jdbcTemplate) {

        this.simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("dbo")
                .withProcedureName("PRD_FIELD_OPER_DET_SAVE_App")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("PCOMP_CODE", Types.VARCHAR),
                        new SqlParameter("PCENT_CODE", Types.VARCHAR),
                        new SqlParameter("PBRANCH_CODE", Types.VARCHAR),
                        new SqlParameter("PROWID", Types.VARCHAR),
                        new SqlParameter("PREF_AGR_ENTRY_NO", Types.VARCHAR),
                        new SqlParameter("PREF_ISSUE_NO", Types.VARCHAR),
                        new SqlParameter("PREF_DISTR_NO", Types.VARCHAR),
                        new SqlParameter("PVISIT_DATE", Types.TIMESTAMP),
                        new SqlParameter("PENTRY_DATE", Types.TIMESTAMP),
                        new SqlParameter("PFINYEAR_CODE", Types.VARCHAR),
                        new SqlParameter("PSEASON_CODE", Types.VARCHAR),
                        new SqlParameter("PORG_CODE", Types.VARCHAR),
                        new SqlParameter("PFARMER_CODE", Types.VARCHAR),
                        new SqlParameter("PITEM_CODE", Types.VARCHAR),
                        new SqlParameter("PSOWING_DATE_FEMALE_1", Types.TIMESTAMP),
                        new SqlParameter("PSOWING_DATE_MALE_1", Types.TIMESTAMP),
                        new SqlParameter("PSOWING_DATE_FEMALE_2", Types.TIMESTAMP),
                        new SqlParameter("PSOWING_DATE_MALE_2", Types.TIMESTAMP),
                        new SqlParameter("PSURVEY_NO", Types.VARCHAR),
                        new SqlParameter("PBATCH_NO_MALE", Types.VARCHAR),
                        new SqlParameter("PBATCH_NO_FEMALE", Types.VARCHAR),
                        new SqlParameter("PFARMER_LOT_NO", Types.VARCHAR),
                        new SqlParameter("PAREA_ACRE", Types.VARCHAR),
                        new SqlParameter("PPLD_ACRE", Types.VARCHAR),
                        new SqlParameter("PNSA_ACRE", Types.VARCHAR),
                        new SqlParameter("PPLD_REASON_CODE", Types.VARCHAR),
                        new SqlParameter("POPER_TYPE_CODE", Types.VARCHAR),
                        new SqlParameter("POPER_CODE", Types.VARCHAR),
                        new SqlParameter("PTARGET_DAYS", Types.VARCHAR),
                        new SqlParameter("PTARGET_DATE", Types.TIMESTAMP),
                        new SqlParameter("PACTUAL_DATE", Types.TIMESTAMP),
                        new SqlParameter("PVALUE_TEXT", Types.VARCHAR),
                        new SqlParameter("PVALUE_NUM", Types.VARCHAR),
                        new SqlParameter("PRATING", Types.VARCHAR),
                        new SqlParameter("POPER_STATUS", Types.VARCHAR),
                        new SqlParameter("POPER_COMMENTS", Types.VARCHAR),
                        new SqlParameter("PREMARKS", Types.VARCHAR),
                        new SqlParameter("POBSERV_REMARKS", Types.VARCHAR),
                        new SqlParameter("PIP", Types.VARCHAR),
                        new SqlParameter("PCREATE_USER", Types.VARCHAR),
                        new SqlParameter("PEXTRA", Types.VARCHAR),
                        new SqlParameter("PEXTRA1", Types.VARCHAR),
                        new SqlParameter("PEXTRA2", Types.VARCHAR),
                        new SqlParameter("PEXTRA3", Types.VARCHAR),
                        new SqlParameter("PEXTRA4", Types.VARCHAR),
                        new SqlParameter("PEXTRA5", Types.VARCHAR)

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
                .res(rs.getString("RES"))


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
    private Map<String, Object> buildParameters(OperationRequestDto dto) {

        Map<String, Object> params = new HashMap<>(46);

        params.put("PCOMP_CODE", dto.getCompCode());
        params.put("PCENT_CODE", dto.getCentCode());
        params.put("PBRANCH_CODE", dto.getBranchCode());
        params.put("PROWID", dto.getRowId());

        params.put("PREF_AGR_ENTRY_NO", dto.getRefAgrEntryNo());
        params.put("PREF_ISSUE_NO", dto.getRefIssueNo());
        params.put("PREF_DISTR_NO", dto.getDistNo());

        params.put("PVISIT_DATE", dto.getVisitDate());
        params.put("PENTRY_DATE", dto.getEntryDate());

        params.put("PFINYEAR_CODE", dto.getFinYear());
        params.put("PSEASON_CODE", dto.getSeasionCode());
        params.put("PORG_CODE", dto.getOrgCode());
        params.put("PFARMER_CODE", dto.getFarmerCode());
        params.put("PITEM_CODE", dto.getItemCode());

        params.put("PSOWING_DATE_FEMALE_1", dto.getSowingDateFemale1());
        params.put("PSOWING_DATE_MALE_1", dto.getSowingDateMale1());
        params.put("PSOWING_DATE_FEMALE_2", dto.getSowingDateFemale2());
        params.put("PSOWING_DATE_MALE_2", dto.getSowingDateMale2());

        params.put("PSURVEY_NO", dto.getSurveyNo());
        params.put("PBATCH_NO_MALE", dto.getBatchMale());
        params.put("PBATCH_NO_FEMALE", dto.getBatchFemale());
        params.put("PFARMER_LOT_NO", dto.getFarmerLotNo());

        params.put("PAREA_ACRE", dto.getAreaAcre());
        params.put("PPLD_ACRE", dto.getPldAcre());
        params.put("PNSA_ACRE", dto.getNsaAcre());
        params.put("PPLD_REASON_CODE", dto.getPldReasonCode());

        params.put("POPER_TYPE_CODE", dto.getOperTypeCode());
        params.put("POPER_CODE", dto.getOperCode());
        params.put("PTARGET_DAYS", dto.getTargetDays());
        params.put("PTARGET_DATE", dto.getTargetDate());
        params.put("PACTUAL_DATE", dto.getActualDate());

        params.put("PVALUE_TEXT", dto.getValueText());
        params.put("PVALUE_NUM", dto.getValueNum());
        params.put("PRATING", dto.getRating());
        params.put("POPER_STATUS", dto.getOperStatus());
        params.put("POPER_COMMENTS", dto.getOperComments());

        params.put("PREMARKS", dto.getRemarks());
        params.put("POBSERV_REMARKS", dto.getObservRemarks());

        params.put("PIP", dto.getIp());
        params.put("PCREATE_USER", dto.getCreateUser());

        params.put("PEXTRA", dto.getExtra());
        params.put("PEXTRA1", dto.getExtra1());
        params.put("PEXTRA2", dto.getExtra2());
        params.put("PEXTRA3", dto.getExtra3());
        params.put("PEXTRA4", dto.getExtra4());
        params.put("PEXTRA5", dto.getExtra5());

        return params;
    }


}





