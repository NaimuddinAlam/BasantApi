package com.medayes.erp.ErpApi.repository.production;

import com.medayes.erp.ErpApi.dto.request.production.SeedSowingRequestSto;
import com.medayes.erp.ErpApi.dto.response.production.SeedSowingResponseDto;
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
public class SeedSowingRepository {

    private SimpleJdbcCall simpleJdbcCall;

    private final JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {

        simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("dbo")
                .withProcedureName("PRD_DIST_SEED_SOWING")
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
                .returningResultSet(
                        "data",
                        (rs, rowNum) -> seedSowingResponse(rs)
                );
    }

    private SeedSowingResponseDto seedSowingResponse(ResultSet rs)
            throws SQLException {

        return SeedSowingResponseDto.builder()

                .compCode(rs.getString("COMP_CODE"))
                .compName(rs.getString("COMP_NAME"))

                .centCode(rs.getString("CENT_CODE"))

                .distrNo(rs.getString("DISTR_NO"))
                .distrDate(rs.getString("DISTR_DATE"))
                .refAgrEntryNo(rs.getString("REF_AGR_ENTRY_NO"))

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

                .totalRows(rs.getString("TOTAL_ROWS"))
                .plantsInRow(rs.getString("PLANTS_IN_ROW"))

                .prevCropCode(rs.getString("PREV_CROP_CODE"))
                .prevCropName(rs.getString("PREV_CROP_NAME"))

                .harvestDate(rs.getString("HARVEST_DATE"))
                .farmerLotNo(getColumnValue(rs, "FARMER_LOT_NO"))
                .remarks(rs.getString("REMARKS"))
                //.farmerLotNo(rs.getString("FARMER_LOT_NO"))

                .build();
    }
    private String getColumnValue(ResultSet rs, String columnName)
            throws SQLException {

        var metaData = rs.getMetaData();

        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            if (columnName.equalsIgnoreCase(metaData.getColumnLabel(i))) {
                return rs.getString(i);
            }
        }

        return null;
    }
    public List<SeedSowingResponseDto> seedSowingResponses(
            SeedSowingRequestSto requestSto) {

        Map<String, Object> map = builds(requestSto);

        Map<String, Object> result = simpleJdbcCall.execute(map);

        return (List<SeedSowingResponseDto>) result.get("data");
    }

    private Map<String, Object> builds(SeedSowingRequestSto dto) {

        Map<String, Object> map = new HashMap<>();

        map.put("PCOMP_CODE", dto.compCode());
        map.put("PCENT_CODE", dto.centCode());
        map.put("PDIST_NO", dto.distNo());
        map.put("PREF_NO", dto.refNo());
        map.put("PORG_CODE", dto.orgCode());

        map.put("PDIST_FROM_DT", dto.distFromDt());
        map.put("PDIST_TO_DT", dto.distToDt());

        map.put("PFARMER_CODE", dto.farmerCode());
        map.put("PVILLAGE_CODE", dto.villageCode());
        map.put("PITEM_CODE", dto.itemCode());

        map.put("PBATCH_MALE", dto.batchMale());
        map.put("PBATCH_FEMALE", dto.batchFemale());
        map.put("PFARMER_LOT_STATUS", dto.farmerLotStatus());

        map.put("PSTATUS", dto.status());

        map.put("PEXTRA1", dto.extra1());
        map.put("PEXTRA2", dto.extra2());
        map.put("PEXTRA3", dto.extra3());
        map.put("PEXTRA4", dto.extra4());
        map.put("PEXTRA5", dto.extra5());

        return map;
    }
}