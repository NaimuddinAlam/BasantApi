package com.medayes.erp.ErpApi.repository.userValidation;

import com.medayes.erp.ErpApi.dto.request.UserValidationRequest;
import com.medayes.erp.ErpApi.dto.response.UserValidationResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public class MobileValidation {

    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall call;

    private static final String PARAM_MOBILE = "MOBILE";

    public MobileValidation(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void init() {
        call = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("CHECK_MOBILE")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(new SqlParameter(PARAM_MOBILE, Types.VARCHAR))
                .returningResultSet("data", (rs, rowNum) ->
                        new UserValidationResponse(rs.getString("MOBILE_EXISTS"))
                );
    }

    public UserValidationResponse mobileValidation(UserValidationRequest request) {

        Map<String, Object> map = new HashMap<>();
        map.put(PARAM_MOBILE, request.mobile());

        Map<String, Object> response = call.execute(map);

        // ✅ SAFELY extract List from ResultSet
        List<UserValidationResponse> list =
                (List<UserValidationResponse>) response.get("data");

        if (list == null || list.isEmpty()) {
            return null;   // Service layer already handles this safely
        }

        return list.get(0);  // ✅ Return first row safely
    }
}
