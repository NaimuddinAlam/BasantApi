package com.medayes.erp.ErpApi.repository.taskSchdule;

import com.medayes.erp.ErpApi.dto.request.task.TaskSchedulerRequestDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskLocationResponseDto;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BindTaskLocationRepository {
    private  final JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall call;
    @Autowired
    BindTaskLocationRepository(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate=jdbcTemplate;
    }

    @PostConstruct
    public void init()
    {
        call= new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("ERP_BINDLOGINCENTER")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("pcompcode", Types.VARCHAR),
                        new SqlReturnResultSet("RESULT_SET_1",(rs, numRow)->
                                new TaskLocationResponseDto(
                                        rs.getString("CENT_CODE"),
                                        rs.getString("CENT_NAME")
                                )
                                )

                );
    }

    public List<TaskLocationResponseDto> bindLocation(TaskSchedulerRequestDto taskSchedulerRequestDto)
    {
        Map<String,Object> map=  buildParams(taskSchedulerRequestDto);
        Map<String,Object> response=     call.execute(map);
        return  (List<TaskLocationResponseDto>)response.get("RESULT_SET_1");
    }







    public Map<String,Object> buildParams(TaskSchedulerRequestDto taskSchedulerRequestDto)
    {
        HashMap<String,Object> map= new HashMap<>();
        map.put("pcompcode",taskSchedulerRequestDto.PCOMPCODE());
        return  map;
    }
}
