package com.medayes.erp.ErpApi.repository.tempTask;

import com.medayes.erp.ErpApi.dto.request.task.TaskMastResponsibleRequestDto;
import com.medayes.erp.ErpApi.dto.request.task.TaskMastResponsibleTempRequestDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleTempResponseListDto;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.medayes.erp.ErpApi.utils.ApiParams.*;
import static com.medayes.erp.ErpApi.utils.Utils.buildDebugSql;

@Repository
public class TaskMastResponsibleTempRepositoryImpl implements TaskMastResponsibleTempRepository {

    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall call;

    @Autowired
    public TaskMastResponsibleTempRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void init() {

        this.call = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName(cmsTaskMastResponsible)
                .returningResultSet("DATA", (rs, rowNum) ->
                        new TaskMastResponsibleTempResponseListDto(
                                rs.getString("COMP_CODE"),
                                rs.getString("CENT_CODE"),
                                rs.getString("BRANCH_CODE"),
                                rs.getString("TASK_CODE"),
                                rs.getString("PERSON_CODE"),
                                rs.getString("PERSON_NAME"),
                                rs.getString("EMAILID"),
                                rs.getString("MOBILENO"),
                                rs.getString("ROW_ID"),
                                rs.getString("X1"),
                                rs.getString("X2"),
                                rs.getString("X3"),
                                rs.getString("X4"),
                                rs.getString("X5")
                        )
                );
    }

    @Override
    public Map<String, Object> execute(TaskMastResponsibleTempRequestDto req) {

        Map<String, Object> params = new HashMap<>();

        params.put(pCompCode, req.pCompCode());
        params.put(pCentCode, req.pCentCode());
        params.put(pBranchCode, req.pBranchCode());
        params.put(pTaskCode, req.pTaskCode());
        params.put(pRowId, req.pRowId());
        params.put(pPersonCode, req.pPersonCode());
        params.put(pPersonName, req.pPersonName());
        params.put(pEmail, req.pEmail());
        params.put(pMobileNo, req.pMobileNo());
        params.put(pX1, req.pX1());
        params.put(pX2, req.pX2());
        params.put(pX3, req.pX3());
        params.put(pX4, req.pX4());
        params.put(pX5, req.pX5());
        params.put(PEXTRA1, req.pEXTRA1());
        params.put(PEXTRA2, req.pEXTRA2());
        params.put(PEXTRA3, req.pEXTRA3());
        params.put(PEXTRA4, req.pEXTRA4());
        params.put(PEXTRA5, req.pEXTRA5());
        params.put(pType, req.pType());
        params.put(pEditFlag, req.pEditFlag());

        String logSql = buildDebugSql(params, cmsTaskMastResponsible);
        System.out.println("PROC PARAMS => " + logSql);

        return call.execute(params);
    }

    @Override
    public List<TaskMastResponsibleTempResponseListDto> fetch(TaskMastResponsibleTempRequestDto req) {

        Map<String, Object> result = execute(req);

        @SuppressWarnings("unchecked")
        List<TaskMastResponsibleTempResponseListDto> rows =
                (List<TaskMastResponsibleTempResponseListDto>) result.get("DATA");

        return rows;
    }

    @Override
    public Map<String, Object> delete(TaskMastResponsibleTempRequestDto req) {
     return   execute(req);



    }





}
