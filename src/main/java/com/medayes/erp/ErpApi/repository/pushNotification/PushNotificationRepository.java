package com.medayes.erp.ErpApi.repository.pushNotification;


import com.medayes.erp.ErpApi.dto.response.task.SendMailResponseDto;
import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;
import java.util.Map;

@Repository
public class PushNotificationRepository {
    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall call;

    public PushNotificationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public  void  init()
    {
        call= new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                    .withProcedureName("PAY_TASK_NOTIFICATION_APP")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("PCOMP_CODE", Types.VARCHAR),
                        new SqlParameter("PCENT_CODE",Types.VARCHAR),
                        new SqlParameter("PTYPE",Types.VARCHAR)

                ).returningResultSet("RESULT_SET_1",(rs, num)->
                        new SendMailResponseDto(
                                rs.getString("SCHEDULE_DATE"),
                                rs.getString("SCHEDULE_TIME"),
                                rs.getString("ALERT_TIME"),
                                rs.getString("ALERT_BEFORE_TIME"),
                                rs.getString("TOKEN_ID"),
                                rs.getString("EMAILID"),
                                rs.getString("TASK_NAME"),
                                rs.getString("CREATED_BY"),
                                rs.getString("MOBILENO"),
                                rs.getString("TASK_PRIORITY"),
                                rs.getString("PERSON_NAME"),
                                rs.getString("CALL_DESCRIPTION")


                        )
                );
    }

    public List<SendMailResponseDto> fetch()
    {
        Map<String,Object> map= call.execute("31","","");
        return  (List<SendMailResponseDto>)map.get("RESULT_SET_1");

    }
    public List<SendMailResponseDto> fetchs()
    {
        Map<String,Object> map= call.execute("31","","N");
        return  (List<SendMailResponseDto>)map.get("RESULT_SET_1");

    }
}
