package com.medayes.erp.ErpApi.repository.taskSchdule;

import com.medayes.erp.ErpApi.dto.request.task.TaskSchedulerRequestDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskLocationResponseDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskSchedulerResponseDto;
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


import static com.medayes.erp.ErpApi.utils.Utils.buildDebugSql;

@Repository
public class TaskSchedulerRepositoryImpl implements  TaskSchedulerRepository{
    private  final JdbcTemplate jdbcTemplate;
    private   SimpleJdbcCall call;
    @Autowired
    TaskSchedulerRepositoryImpl(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate=jdbcTemplate;
    }

    @PostConstruct
    public  void  init()
    {
        call= new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("CMS_BIND_TASK")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("PCOMPCODE", Types.VARCHAR),
                        new SqlParameter("PCENTCODE", Types.VARCHAR),
                        new SqlParameter("PBRANCHCODE", Types.VARCHAR),
                        new SqlParameter("PTASKCODE", Types.VARCHAR),
                        new SqlParameter("PTASKNAME", Types.VARCHAR),
                        new SqlParameter("PTASKNICK", Types.VARCHAR),
                        new SqlParameter("PPEXTRA1", Types.VARCHAR),
                        new SqlReturnResultSet("RESULT_SET_1",(rs,rowNum)->
                                new TaskSchedulerResponseDto(
                                        rs.getString("TASK_CODE"),
                                        rs.getString("TASK_NAME")
                                )
                                )



                );
    }



    @Override
    public List<TaskSchedulerResponseDto> bindTaskMast(TaskSchedulerRequestDto schedulerRequestDto) {
        Map<String,Object> map= buildParams(schedulerRequestDto);
        Map<String,Object> result=     call.execute(map);

        return (List<TaskSchedulerResponseDto>)result.get("RESULT_SET_1");
    }



    public  Map<String,Object> buildParams(TaskSchedulerRequestDto schedulerRequestDto)
    {
        HashMap<String,Object> params= new HashMap<>();
        params.put("PCOMPCODE",schedulerRequestDto.PCOMPCODE());
        params.put("PCENTCODE",schedulerRequestDto.PCENTCODE());
        params.put("PBRANCHCODE",schedulerRequestDto.PBRANCHCODE());
        params.put("PTASKCODE",schedulerRequestDto.PTASKCODE());
        params.put("PTASKNAME",schedulerRequestDto.PTASKNAME());
        params.put("PTASKNICK",schedulerRequestDto.PTASKNICK());
        params.put("PPEXTRA1",schedulerRequestDto.PPEXTRA1());
        String logSql = buildDebugSql(params, "CMS_BIND_TASK");
        System.out.println("PROC PARAMS => " + logSql);
        return  params;

    }


}
