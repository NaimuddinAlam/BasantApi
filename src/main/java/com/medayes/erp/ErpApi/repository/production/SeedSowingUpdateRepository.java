package com.medayes.erp.ErpApi.repository.production;


import com.medayes.erp.ErpApi.dto.request.production.SeedSowingUpdateRequestDto;
import com.medayes.erp.ErpApi.dto.response.production.SeedSowingUpdateDto;
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
public class SeedSowingUpdateRepository {

    private static final String PROCEDURE_NAME =
            "PRD_DIST_SEED_SOWING_UPDATE_APP";

    private static final String RESULT_DATA = "data";

    private final JdbcTemplate jdbcTemplate;

    private SimpleJdbcCall simpleJdbcCall;

    @PostConstruct
    public void init() {

        simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName("dbo")
                .withProcedureName(PROCEDURE_NAME)
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(

                        new SqlParameter("PCOMP_CODE", Types.VARCHAR),
                        new SqlParameter("PCENT_CODE", Types.VARCHAR),
                        new SqlParameter("PBRANCH_CODE", Types.VARCHAR),
                        new SqlParameter("PDIST_NO", Types.VARCHAR),
                        new SqlParameter("PORG_CODE", Types.VARCHAR),
                        new SqlParameter("PFARMER_CODE", Types.VARCHAR),
                        new SqlParameter("PITEM_CODE", Types.VARCHAR),
                        new SqlParameter("PSIZE_CODE", Types.VARCHAR),
                        new SqlParameter("PBATCH_NO", Types.VARCHAR),

                        new SqlParameter(
                                "PSOWING_DATE_1",
                                Types.VARCHAR
                        ),

                        new SqlParameter(
                                "PSOWING_DATE_2",
                                Types.VARCHAR
                        ),

                        new SqlParameter(
                                "PSURVEY_NO",
                                Types.VARCHAR
                        ),

                        new SqlParameter(
                                "PFARMER_LOT_NO",
                                Types.VARCHAR
                        ),

                        new SqlParameter(
                                "PPREV_CROP_CODE",
                                Types.VARCHAR
                        ),

                        new SqlParameter(
                                "PTOTAL_ROWS",
                                Types.VARCHAR
                        ),

                        new SqlParameter(
                                "PPLANTS_IN_ROW",
                                Types.VARCHAR
                        ),

                        new SqlParameter(
                                "PREF_AGR_NO",
                                Types.VARCHAR
                        ),

                        new SqlParameter(
                                "PHARVEST_DATE",
                                Types.VARCHAR
                        ),

                        new SqlParameter(
                                "PVARIETY_CODE",
                                Types.VARCHAR
                        ),

                        new SqlParameter(
                                "PSTAGE_CODE",
                                Types.VARCHAR
                        ),

                        new SqlParameter(
                                "PSTATUS",
                                Types.VARCHAR
                        ),

                        new SqlParameter(
                                "PIP",
                                Types.VARCHAR
                        ),

                        new SqlParameter(
                                "PUSER_ID",
                                Types.VARCHAR
                        ),

                        new SqlParameter(
                                "PEXTRA1",
                                Types.VARCHAR
                        ),

                        new SqlParameter(
                                "PEXTRA2",
                                Types.VARCHAR
                        ),

                        new SqlParameter(
                                "PEXTRA3",
                                Types.VARCHAR
                        ),

                        new SqlParameter(
                                "PEXTRA4",
                                Types.VARCHAR
                        ),

                        new SqlParameter(
                                "PEXTRA5",
                                Types.VARCHAR
                        )
                )
                .returningResultSet(
                        RESULT_DATA,
                        this::mapResponse
                );
    }

    public SeedSowingUpdateDto updateSeedSowing(
            SeedSowingUpdateRequestDto request) {

        Map<String, Object> parameters =
                buildParameters(request);

        Map<String, Object> result =
                simpleJdbcCall.execute(parameters);

        return extractResponse(result);
    }

    private Map<String, Object> buildParameters(
            SeedSowingUpdateRequestDto request) {

        Map<String, Object> parameters = new HashMap<>();

        parameters.put("PCOMP_CODE", request.compCode());
        parameters.put("PCENT_CODE", request.centCode());
        parameters.put("PBRANCH_CODE", request.branchCode());
        parameters.put("PDIST_NO", request.distNo());

        parameters.put("PORG_CODE", request.orgCode());
        parameters.put("PFARMER_CODE", request.farmerCode());
        parameters.put("PITEM_CODE", request.itemCode());
        parameters.put("PSIZE_CODE", request.sizeCode());
        parameters.put("PBATCH_NO", request.batchNo());

        parameters.put(
                "PSOWING_DATE_1",
                request.sowingDate1()
        );

        parameters.put(
                "PSOWING_DATE_2",
                request.sowingDate2()
        );

        parameters.put(
                "PSURVEY_NO",
                request.surveyNo()
        );

        parameters.put(
                "PFARMER_LOT_NO",
                request.farmerLotNo()
        );

        parameters.put(
                "PPREV_CROP_CODE",
                request.prevCropCode()
        );

        parameters.put(
                "PTOTAL_ROWS",
                request.totalRows()
        );

        parameters.put(
                "PPLANTS_IN_ROW",
                request.plantsInRow()
        );

        parameters.put(
                "PREF_AGR_NO",
                request.refAgrNo()
        );

        parameters.put(
                "PHARVEST_DATE",
                request.harvestDate()
        );

        parameters.put(
                "PVARIETY_CODE",
                request.varietyCode()
        );

        parameters.put(
                "PSTAGE_CODE",
                request.stageCode()
        );

        parameters.put(
                "PSTATUS",
                request.status()
        );

        parameters.put(
                "PIP",
                request.ip()
        );

        parameters.put(
                "PUSER_ID",
                request.userId()
        );

        parameters.put("PEXTRA1", request.extra1());
        parameters.put("PEXTRA2", request.extra2());
        parameters.put("PEXTRA3", request.extra3());
        parameters.put("PEXTRA4", request.extra4());
        parameters.put("PEXTRA5", request.extra5());

        return parameters;
    }

    private SeedSowingUpdateDto mapResponse(
            ResultSet rs,
            int rowNum) throws SQLException {

        return SeedSowingUpdateDto.builder()
                .res(rs.getString("RES"))
                .farmerCode(rs.getString("PFARMER_CODE"))

                .build();
    }

    private SeedSowingUpdateDto extractResponse(
            Map<String, Object> result) {

        Object data = result.get(RESULT_DATA);

        if (!(data instanceof List<?> list) || list.isEmpty()) {
            return null;
        }

        return (SeedSowingUpdateDto) list.get(0);
    }
}