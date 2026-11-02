package com.medayes.erp.ErpApi.repository.sale;

import com.medayes.erp.ErpApi.dto.request.sale.SalePermissionRequestDto;
import com.medayes.erp.ErpApi.dto.request.sale.SeasonMastRequestDto;
import com.medayes.erp.ErpApi.dto.response.sale.SalePermissionResponseDto;
import com.medayes.erp.ErpApi.dto.response.sale.SeasonMastResponseDto;
import jakarta.annotation.PostConstruct;
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
public class SeasonMastRepository implements SeasonMastRepositoryImpl {

    private SimpleJdbcCall seasonJdbcCall;
    private SimpleJdbcCall permissionJdbcCall;
    private final JdbcTemplate jdbcTemplate;

    // Season params
    private static final String PARAM_COMP_CODE = "PCOMP_CODE";
    private static final String PARAM_CENT_CODE = "SEASON_CODE";
    private static final String PARAM_ACC_TYPE_CODE = "SEASON_NAME";
    private static final String PARAM_FILTER = "TYPE";

    // Permission params
    private static final String PARAMCOMPCODE = "compcode";
    private static final String PARAMUSERID = "userid";
    private static final String PARAMFORMNAME = "formname";
    private static final String PARAMMODULE = "pmodule";

    public SeasonMastRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void init() {

        // 🔹 Season SP
        seasonJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("BIND_SEASONCODE")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter(PARAM_COMP_CODE, Types.VARCHAR),
                        new SqlParameter(PARAM_CENT_CODE, Types.VARCHAR),
                        new SqlParameter(PARAM_ACC_TYPE_CODE, Types.VARCHAR),
                        new SqlParameter(PARAM_FILTER, Types.VARCHAR)
                )
                .returningResultSet("seasonData", this::mapRowToSeasonDto);

        // 🔹 Permission SP
        permissionJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("ERP_SHOWPERMISSION_APP")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter(PARAMCOMPCODE, Types.VARCHAR),
                        new SqlParameter(PARAMUSERID, Types.VARCHAR),
                        new SqlParameter(PARAMFORMNAME, Types.VARCHAR),
                        new SqlParameter(PARAMMODULE, Types.VARCHAR)
                )
                .returningResultSet("permissionData", this::mapRowToPermissionDto);
    }

    private SeasonMastResponseDto mapRowToSeasonDto(ResultSet rs, int rowNum) throws SQLException {
        return new SeasonMastResponseDto(
                rs.getString("SEASON_CODE"),
                rs.getString("SEASON_NAME")
        );
    }

    private SalePermissionResponseDto mapRowToPermissionDto(ResultSet rs, int rowNum) throws SQLException {
        return new SalePermissionResponseDto(
                rs.getString("BUTTION_ID")
        );
    }

    @Override
    public List<SeasonMastResponseDto> fetchSeasonMast(SeasonMastRequestDto dto) {
        Map<String, Object> result = seasonJdbcCall.execute(buildSeasonParam(dto));
        return (List<SeasonMastResponseDto>) result.get("seasonData");
    }

    @Override
    public SalePermissionResponseDto approvalId(SalePermissionRequestDto dto) {
        Map<String, Object> result = permissionJdbcCall.execute(buildPermissionParam(dto));
        List<SalePermissionResponseDto> list =
                (List<SalePermissionResponseDto>) result.get("permissionData");

        return list != null && !list.isEmpty() ? list.get(0) : null;
    }

    private Map<String, Object> buildSeasonParam(SeasonMastRequestDto dto) {
        Map<String, Object> map = new HashMap<>();
        map.put(PARAM_COMP_CODE, dto.pCompCode());
        map.put(PARAM_CENT_CODE, dto.pCentCode());
        map.put(PARAM_ACC_TYPE_CODE, dto.pAccTypeCode());
        map.put(PARAM_FILTER, dto.pFilter());
        return map;
    }

    private Map<String, Object> buildPermissionParam(SalePermissionRequestDto dto) {
        Map<String, Object> map = new HashMap<>();
        map.put(PARAMCOMPCODE, dto.compcode());
        map.put(PARAMUSERID, dto.userid());
        map.put(PARAMFORMNAME, dto.formname());
        map.put(PARAMMODULE, dto.pmodule());
        return map;
    }
}
