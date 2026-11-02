package com.medayes.erp.ErpApi.repository.mis;

import com.medayes.erp.ErpApi.dto.request.sale.SalePermissionRequestDto;
import com.medayes.erp.ErpApi.dto.response.sale.SalePermissionResponseDto;
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
public class ERPModulePermissionRepository {
    private final JdbcTemplate jdbcTemplate;
    public   SimpleJdbcCall simpleJdbcCall;


    @PostConstruct
    private  void  init()
    {
        simpleJdbcCall= new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("ERP_SHOWPERMISSION_CHK_APP")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("compcode", Types.VARCHAR),
                        new SqlParameter("userid", Types.VARCHAR),
                        new SqlParameter("userid", Types.VARCHAR),
                        new SqlParameter("formname", Types.VARCHAR),
                        new SqlParameter("pmodule", Types.VARCHAR)
                ).returningResultSet("permissionData",this::mapRowToPermissionDto );
    }
    private SalePermissionResponseDto mapRowToPermissionDto(ResultSet rs, int rowNum) throws SQLException {
        return new SalePermissionResponseDto(
                rs.getString("BUTTION_ID")
        );
    }

    public SalePermissionResponseDto approvalId(SalePermissionRequestDto dto) {
        Map<String, Object> result = simpleJdbcCall.execute(buildPermissionParam(dto));
        List<SalePermissionResponseDto> list =
                (List<SalePermissionResponseDto>) result.get("permissionData");

        return list != null && !list.isEmpty() ? list.get(0) : null;
    }

    private Map<String, Object> buildPermissionParam(SalePermissionRequestDto dto) {
        Map<String, Object> map = new HashMap<>();
        map.put("compcode", dto.compcode());
        map.put("userid", dto.userid());
        map.put("formname","");
        map.put("pmodule", dto.pmodule());
        return map;
    }
}
