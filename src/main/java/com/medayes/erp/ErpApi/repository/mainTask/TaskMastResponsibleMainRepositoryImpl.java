package com.medayes.erp.ErpApi.repository.mainTask;

import com.medayes.erp.ErpApi.dto.request.task.TaskMastResponsibleRequestDto;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

import static com.medayes.erp.ErpApi.utils.ApiParams.*;
import static com.medayes.erp.ErpApi.utils.Utils.buildDebugSql;

@Repository
public class TaskMastResponsibleMainRepositoryImpl implements TaskMastResponsibleMainRepository {

    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall call;

    @Autowired
    public TaskMastResponsibleMainRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void init() {

        this.call = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName(cmsTaskMastInsupdel)
                .returningResultSet("RESULT_SET_1", (rs, rowNum) ->
                        new TaskMastResponsibleRequestDto(

                                rs.getString("COMP_CODE"),
                                rs.getString("TASK_CODE"),
                                rs.getString("TASK_NAME"),
                                rs.getString("TASK_NICK"),
                                rs.getString("DEFAULT_FLAG"),
                                rs.getString("X1"),
                                rs.getString("X2"),
                                rs.getString("X3"),
                                rs.getString("X4"),
                                rs.getString("X5"),
                                rs.getString("SEQ_NO"),
                                rs.getString("REMARKS"),
                                rs.getString("STATUS"),
                                rs.getString("IP"),
                                rs.getString("TYPE"),
                                rs.getString("PUSERID")
                        )
                );
    }




    @Override
    public Map<String, Object> executeMain(TaskMastResponsibleRequestDto req) {
        try {


            Map<String, Object> params = new HashMap<>();
            params.put("comp_code", req.COMP_CODE());
            params.put("task_code", req.TASK_CODE());
            params.put("task_name", req.TASK_NAME());
            params.put("task_nick", req.TASK_NICK());
            params.put("default_flag", req.DEFAULT_FLAG());
            params.put("x1", req.X1());
            params.put("x2", req.X2());
            params.put("x3", req.X3());
            params.put("x4", req.X4());
            params.put("x5", req.X5());
            params.put("seq_no", req.SEQ_NO());
            params.put("remarks", req.REMARKS());
            params.put("status", req.STATUS());
            params.put("ip", req.IP());
            params.put("type", req.TYPE());
            params.put("puserid", req.PUSERID());
            String logSql = buildDebugSql(params, cmsTaskMastInsupdel);
            System.out.println("PROC PARAMS => " + logSql);
            return call.execute(params);
        }catch (Exception e)
        {
            Map<String,Object> map= new HashMap<>();
            map.put("message",e);
            map.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            return  map;
        }
    }

    @Override
    public Map<String, Object> deleteMain(TaskMastResponsibleRequestDto req) {
        return executeMain(req);
    }


}
