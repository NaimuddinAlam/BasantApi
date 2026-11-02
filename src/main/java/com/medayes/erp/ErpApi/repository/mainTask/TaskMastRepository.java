package com.medayes.erp.ErpApi.repository.mainTask;



import com.medayes.erp.ErpApi.dto.request.task.TaskMastResponsibleRequestDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponseListDto;
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
public class TaskMastRepository {

    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall call;

    @Autowired
    public TaskMastRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void init() {

        this.call = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("CMS_TASK_MAST_INSUPDEL")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(

                        new SqlParameter("COMP_CODE", Types.VARCHAR),
                        new SqlParameter("TASK_CODE", Types.VARCHAR),
                        new SqlParameter("TASK_NAME", Types.VARCHAR),
                        new SqlParameter("TASK_NICK", Types.VARCHAR),
                        new SqlParameter("DEFAULT_FLAG", Types.VARCHAR),
                        new SqlParameter("X1", Types.VARCHAR),
                        new SqlParameter("X2", Types.VARCHAR),
                        new SqlParameter("X3", Types.VARCHAR),
                        new SqlParameter("X4", Types.VARCHAR),
                        new SqlParameter("X5", Types.VARCHAR),
                        new SqlParameter("SEQ_NO", Types.VARCHAR),
                        new SqlParameter("REMARKS", Types.VARCHAR),
                        new SqlParameter("STATUS", Types.VARCHAR),
                        new SqlParameter("IP", Types.VARCHAR),
                        new SqlParameter("PUSERID", Types.VARCHAR),
                        new SqlParameter("TYPE", Types.VARCHAR),

                        new SqlReturnResultSet("RESULT_SET_1", (rs, rowNum) ->
                                new TaskMastResponseListDto(
                                        rs.getString("COMP_CODE"),
                                        rs.getString("TASK_CODE"),
                                        rs.getString("TASK_NAME"),
                                        rs.getString("TASK_NICK"),
                                        rs.getString("DEFAULT_FLAG"),
                                        rs.getString("SEQ_NO"),
                                        rs.getString("REMARKS"),
                                        rs.getString("STATUSNAME")


                                )
                        )
                );
    }


    public List<TaskMastResponseListDto> fetch(TaskMastResponsibleRequestDto req) {
        try {
            Map<String, Object> params = buildParams(req);
            params.put("TYPE", "E");

            Map<String, Object> result = call.execute(params);

            return (List<TaskMastResponseListDto>) result.get("RESULT_SET_1");
        } catch (Exception e) {
            System.out.println("ERROR = " + e.getMessage());
            return null;
        }
    }

    // ---------------- COMMON PARAM BUILDER -----------------

    private Map<String, Object> buildParams(TaskMastResponsibleRequestDto req) {
        Map<String, Object> params = new HashMap<>();

        params.put("COMP_CODE", req.COMP_CODE());
        params.put("TASK_CODE", req.TASK_CODE());
        params.put("TASK_NAME", req.TASK_NAME());
        params.put("TASK_NICK", req.TASK_NICK());
        params.put("DEFAULT_FLAG", req.DEFAULT_FLAG());
        params.put("X1", req.X1());
        params.put("X2", req.X2());
        params.put("X3", req.X3());
        params.put("X4", req.X4());
        params.put("X5", req.X5());
        params.put("SEQ_NO", req.SEQ_NO());
        params.put("REMARKS", req.REMARKS());
        params.put("STATUS", req.STATUS());
        params.put("IP", req.IP());
        params.put("PUSERID", req.PUSERID());

        return params;
    }


}

