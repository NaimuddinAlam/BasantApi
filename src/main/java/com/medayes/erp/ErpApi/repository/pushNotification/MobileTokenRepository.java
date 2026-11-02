package com.medayes.erp.ErpApi.repository.pushNotification;

import com.medayes.erp.ErpApi.dto.request.MobileTokenDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

@Repository
public class MobileTokenRepository {
    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall call;

    public MobileTokenRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @PostConstruct
    public  void  init(){
        call= new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("ERP_PUSH_NOTIFICATION_INSUPDDEL_APP")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("PCOMP_CODE", Types.VARCHAR),
                        new SqlParameter("PCENT_CODE",Types.VARCHAR),
                        new SqlParameter("PEMP_CODE",Types.VARCHAR),
                        new SqlParameter("PPUSH_NOTI",Types.VARCHAR),
                        new SqlParameter("PTYPE",Types.VARCHAR)

                );
    }

    public void send(MobileTokenDTO requestDto)
    {
        Map<String,Object> map= buildParams(requestDto);
         call.execute(map);


    }

    public Map<String,Object> buildParams(MobileTokenDTO requestDto)
    {
        HashMap<String,Object> map= new HashMap<>();
        map.put("PCOMP_CODE",requestDto.getCompCode());
        map.put("PCENT_CODE",requestDto.getCentCode());
        map.put("PEMP_CODE",requestDto.getEmpCode());
        map.put("PPUSH_NOTI",requestDto.getToken());
        map.put("PTYPE",requestDto.getType());

        return  map;
    }
}
