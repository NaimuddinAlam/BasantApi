
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

@Repository
@Slf4j
public class OperationRepository {

    private final SimpleJdbcCall simpleJdbcCall;

    // Constructor initializes SimpleJdbcCall automatically.
    public OperationRepository(JdbcTemplate jdbcTemplate) {

        this.simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("dbo")
                .withProcedureName("PRD_field_SEED_SOWING")
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
                .returningResultSet("data", this::response);

        log.info("SimpleJdbcCall initialized successfully");
    }

    private OperationResponseDto response(
            ResultSet rs, int rowNum) throws SQLException {

        return OperationResponseDto.builder()
                .compCode(rs.getString("COMP_CODE"))
                .compName(rs.getString("COMP_NAME"))
                .centCode(rs.getString("CENT_CODE"))

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
                .sowingDateMale1(rs.getString("SOWING_DATE_MALE_1"))
                .areaAcre(rs.getString("AREA_ACRE"))
                //.batchNo(rs.getString("BATCH_NO"))

               // .areaAcre(rs.getString("AREA_ACRE"))
               // .sowingDateMale1(rs.getString("SOWING_DATE_MALE_1"))
               // .totalRows(getNullableInteger(rs, "TOTAL_ROWS"))
               // .plantsInRow(getNullableInteger(rs, "PLANTS_IN_ROW"))
               // .prevCropCode(rs.getString("PREV_CROP_CODE"))
                //.prevCropName(rs.getString("PREV_CROP_NAME"))
                //.harvestDate(rs.getString("HARVEST_DATE"))
                .remarks(rs.getString("REMARKS"))
                .build();
    }

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

    public List<OperationResponseDto> responseData(
            OperationRequestDto dto) {

        Map<String, Object> params = builds(dto);

        Map<String, Object> result = simpleJdbcCall.execute(params);

        @SuppressWarnings("unchecked")
        List<OperationResponseDto> data =
                (List<OperationResponseDto>) result.get("data");

        if (data == null) {
            return Collections.emptyList();
        }

        log.debug("Seed sowing records fetched: {}", data.size());

        return data;
    }

    private Map<String, Object> builds(OperationRequestDto dto) {

        Map<String, Object> map = new HashMap<>();

        map.put("PCOMP_CODE", dto.getCompCode());
        map.put("PCENT_CODE", dto.getCentCode());
        map.put("PDIST_NO", dto.getDistNo());
        map.put("PREF_NO", dto.getRefNo());
        map.put("PORG_CODE", dto.getOrgCode());
        map.put("PDIST_FROM_DT", dto.getDistFromDt());
        map.put("PDIST_TO_DT", dto.getDistToDt());
        map.put("PFARMER_CODE", dto.getFarmerCode());
        map.put("PVILLAGE_CODE", dto.getVillageCode());
        map.put("PITEM_CODE", dto.getItemCode());
        map.put("PBATCH_MALE", dto.getBatchMale());
        map.put("PBATCH_FEMALE", dto.getBatchFemale());
        map.put("PFARMER_LOT_STATUS", dto.getFarmerLotStatus());
        map.put("PSTATUS", dto.getStatus());
        map.put("PEXTRA1", dto.getExtra1());
        map.put("PEXTRA2", dto.getExtra2());
        map.put("PEXTRA3", dto.getExtra3());
        map.put("PEXTRA4", dto.getExtra4());
        map.put("PEXTRA5", dto.getExtra5());

        return map;
    }
}
