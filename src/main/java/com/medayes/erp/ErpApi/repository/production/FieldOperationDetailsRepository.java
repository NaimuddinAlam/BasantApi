
        package com.medayes.erp.ErpApi.repository.production;

import com.medayes.erp.ErpApi.dto.request.production.OperationRequestDto;
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
public class FieldOperationDetailsRepository {

    private final SimpleJdbcCall simpleJdbcCall;

    public FieldOperationDetailsRepository(JdbcTemplate jdbcTemplate) {

        this.simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("dbo")
                .withProcedureName("PRD_aggrmnt_sel")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("PCOMP_CODE", Types.VARCHAR),
                        new SqlParameter("PCENT_CODE", Types.VARCHAR),
                        new SqlParameter("PDIST_NO", Types.VARCHAR),
                        new SqlParameter("PREF_NO", Types.VARCHAR),
                        new SqlParameter("PORG_CODE", Types.VARCHAR),
                        new SqlParameter("PDIST_FROM_DT", Types.VARCHAR),
                        new SqlParameter("PDIST_TO_DT", Types.VARCHAR),
                        new SqlParameter("PFARMER_CODE", Types.VARCHAR),
                        new SqlParameter("PVILLAGE_CODE", Types.VARCHAR),
                        new SqlParameter("PITEM_CODE", Types.VARCHAR),
                        new SqlParameter("PBATCH_MALE", Types.VARCHAR),
                        new SqlParameter("PBATCH_FEMALE", Types.VARCHAR),
                        new SqlParameter("PFARMER_LOT_STATUS", Types.VARCHAR),
                        new SqlParameter("PSTATUS", Types.VARCHAR),
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
    private OperationResponseDto mapResponse(
            ResultSet rs, int rowNum) throws SQLException {

        return OperationResponseDto.builder()
                .compCode(rs.getString("COMP_CODE"))
                .compName(rs.getString("COMP_NAME"))
                .centCode(rs.getString("CENT_CODE"))
                .distrNo(rs.getString("DISTR_NO"))
                .distrDate(rs.getString("DISTR_DATE"))
                .refAgrEntryNo(rs.getString("REF_AGR_ENTRY_NO"))
                .refAgrDate(rs.getString("REF_AGR_DATE"))
                .finyearCode(rs.getString("FINYEAR_CODE"))
                .finyearNick(rs.getString("FINYEAR_NICK"))
                .seasonCode(rs.getString("SEASON_CODE"))
                .seasonName(rs.getString("SEASON_NAME"))
                .orgCode(rs.getString("ORG_CODE"))
                .orgName(rs.getString("ORG_NAME"))
                .farmerCode(rs.getString("FARMER_CODE"))
                .farmerName(rs.getString("FARMER_NAME"))
                .fatherName(rs.getString("FATHER_NAME"))
                .villageName(rs.getString("VILLAGE_NAME"))
                .cityName(rs.getString("CITY_NAME"))
                .itemType(rs.getString("ITEM_TYPE"))
                .itemTypeName(rs.getString("ITEM_TYPE_NAME"))
                .cropCode(rs.getString("CROP_CODE"))
                .cropName(rs.getString("CROP_NAME"))
                .itemCode(rs.getString("ITEM_CODE"))
                .itemName(rs.getString("ITEM_NAME"))
                .size(rs.getString("SIZE"))
                .sizeName(rs.getString("SIZE_NAME"))
                .color(rs.getString("COLOR"))
                .colorName(rs.getString("COLOR_NAME"))
                .packingTypeCode(rs.getString("PACKING_TYPE_CODE"))
                .packingTypeName(rs.getString("PACKING_TYPE_NAME"))
                .seedStage(rs.getString("SEED_STAGE"))
                .seedStageName(rs.getString("SEED_STAGE_NAME"))
                .sedVarietyCode(rs.getString("SED_VARIETY_CODE"))
                .sedVarietyName(rs.getString("SED_VARIETY_NAME"))
                .batchNo(rs.getString("BATCH_NO"))
                .distrQty(rs.getString("DISTR_QTY"))
                .areaAcre(rs.getString("AREA_ACRE"))
                .sowingDateMale1(rs.getString("SOWING_DATE_MALE_1"))
                .totalRows(getNullableInteger(rs, "TOTAL_ROWS"))
                .plantsInRow(getNullableInteger(rs, "PLANTS_IN_ROW"))
                .prevCropCode(rs.getString("PREV_CROP_CODE"))
                .prevCropName(rs.getString("PREV_CROP_NAME"))

                .remarks(rs.getString("REMARKS"))
                .build();
    }

    /**
     * Safely converts a nullable database value to Integer.
     */
    private Integer getNullableInteger(
            ResultSet rs, String column) throws SQLException {

        Object value = rs.getObject(column);

        if (value == null) {
            return null;
        }

        if (value instanceof Number number) {
            return number.intValue();
        }

        return Integer.valueOf(value.toString());
    }

    /**
     * Executes the stored procedure and returns its result.
     */
    public List<OperationResponseDto> responseData(
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
        List<OperationResponseDto> data =
                (List<OperationResponseDto>) resultData;

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
        params.put("PDIST_NO", dto.getDistNo());
        params.put("PREF_NO", dto.getRefNo());
        params.put("PORG_CODE", dto.getOrgCode());
        params.put("PDIST_FROM_DT", dto.getDistFromDt());
        params.put("PDIST_TO_DT", dto.getDistToDt());
        params.put("PFARMER_CODE", dto.getFarmerCode());
        params.put("PVILLAGE_CODE", dto.getVillageCode());
        params.put("PITEM_CODE", dto.getItemCode());
        params.put("PBATCH_MALE", dto.getBatchMale());
        params.put("PBATCH_FEMALE", dto.getBatchFemale());
        params.put("PFARMER_LOT_STATUS", dto.getFarmerLotStatus());
        params.put("PSTATUS", dto.getStatus());
        params.put("PEXTRA1", dto.getExtra1());
        params.put("PEXTRA2", dto.getExtra2());
        params.put("PEXTRA3", dto.getExtra3());
        params.put("PEXTRA4", dto.getExtra4());
        params.put("PEXTRA5", dto.getExtra5());

        return params;
    }
}
