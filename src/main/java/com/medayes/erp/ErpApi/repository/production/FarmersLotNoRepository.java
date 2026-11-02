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
public class FarmersLotNoRepository {
    private SimpleJdbcCall simpleJdbcCall;

    private final JdbcTemplate jdbcTemplate;
    private static final String PROCEDURE_NAME =
            "PRD_FARMER_LOT_GEN_APP";

    private static final String RESULT_DATA = "data";
    @PostConstruct
    public void  init()
    {
        simpleJdbcCall= new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName(".dbo")
                .withProcedureName(PROCEDURE_NAME)
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("PCOMP_CODE", Types.VARCHAR),
                        new SqlParameter("PCENT_CODE", Types.VARCHAR),
                        new SqlParameter("PORG_CODE", Types.VARCHAR),
                        new SqlParameter("P_DATE", Types.VARCHAR),
                        new SqlParameter("PEXTRA1", Types.VARCHAR),
                        new SqlParameter("PEXTRA2", Types.VARCHAR),
                        new SqlParameter("PEXTRA3", Types.VARCHAR)

                ).returningResultSet(RESULT_DATA, this::mapResponse);
    }

    public SeedSowingUpdateDto generateFarmersLotNo(
            SeedSowingUpdateRequestDto request) {

        Map<String, Object> parameters =
                buildParameters(request);

        Map<String, Object> result =
                simpleJdbcCall.execute(parameters);

        return extractResponse(result);
    }
    private SeedSowingUpdateDto mapResponse(
            ResultSet rs,
            int rowNum) throws SQLException {

        return SeedSowingUpdateDto.builder()
                .res(rs.getString("FARMER_LOT_NO"))


                .build();
    }
    private Map<String, Object> buildParameters(
            SeedSowingUpdateRequestDto request) {

        Map<String, Object> parameters = new HashMap<>();

        parameters.put("PCOMP_CODE", request.compCode());
        parameters.put("PCENT_CODE", request.centCode());
        parameters.put("PORG_CODE", request.extra4());
        parameters.put("P_DATE",   request.extra5());
        parameters.put("PEXTRA1", request.extra1());
        parameters.put("PEXTRA2", request.extra2());
        parameters.put("PEXTRA3", request.extra3());



        return parameters;
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
