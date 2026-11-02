package com.medayes.erp.ErpApi.repository.indent;

import com.medayes.erp.ErpApi.dto.request.indent.RequirementSlipRequestDto;
import com.medayes.erp.ErpApi.dto.response.indent.PermissionResponseDto;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RequirementSlipPermissionRepository {
    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall call;

    @Autowired
    public RequirementSlipPermissionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private static final String PARAM_COMP_CODE = "PCOMP_CODE";
    private static final String PARAM_CENT_CODE = "PCENT_CODE";
    private static final String PARAM_EMP_CODE = "PEMP_CODE";


    @PostConstruct
    public  void  init()
    {
        call=new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("INV_BIND_APP_TYPE_APP_NEW")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter(PARAM_COMP_CODE, Types.VARCHAR),
                        new SqlParameter(PARAM_CENT_CODE, Types.VARCHAR),
                        new SqlParameter(PARAM_EMP_CODE, Types.VARCHAR),

                        new SqlReturnResultSet("Data",(rs,num)->mapRowToDto(rs))


                );
    }

    private PermissionResponseDto mapRowToDto(ResultSet rs) throws SQLException {

       return new  PermissionResponseDto(
               rs.getString("REPORTING"),
               rs.getString("SEQ1"),
               rs.getString("SEQ2")
               );
    }
    public List<PermissionResponseDto> getPermission(RequirementSlipRequestDto requestDto)
    {
        Map<String, Object> map= buildParams(requestDto);
        Map<String, Object> response= call.execute(map);
        return ( List<PermissionResponseDto>) response.get("Data");
    }

    private Map<String, Object> buildParams(RequirementSlipRequestDto requestDto)
    {
        HashMap<String, Object> map= new HashMap<>();
        map.put(PARAM_COMP_CODE,requestDto.compCode());
        map.put(PARAM_CENT_CODE,requestDto.centCode());
        map.put(PARAM_EMP_CODE,requestDto.extra());
        return  map;
    }

}
