package com.medayes.erp.ErpApi.repository.taskSchdule;

import com.medayes.erp.ErpApi.dto.request.task.TaskScheduleReponsibalRequestDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleTempResponseListDto;
import jakarta.annotation.PostConstruct;
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
public class TaskReschedulerRepositoryImpl implements  TaskReschedulerRepository {
    private SimpleJdbcCall call;
    private final JdbcTemplate jdbcTemplate;

    TaskReschedulerRepositoryImpl(JdbcTemplate template) {
        this.jdbcTemplate = template;
    }

    @PostConstruct
    public void   init()
    {
        call = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("CMS_TASK_SCHEDULE_RESPONSIBLE_ISNUPD")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("PCOM_PCODE", Types.VARCHAR),
                        new SqlParameter("PCENTCODE", Types.VARCHAR),
                        new SqlParameter("PBRANCHCODE", Types.VARCHAR),
                        new SqlParameter("PTASK_SCHEDULE_CODE", Types.VARCHAR),
                        new SqlParameter("PTASKCODE", Types.VARCHAR),
                        new SqlParameter("PROWID", Types.VARCHAR),
                        new SqlParameter("PPERSONCODE", Types.VARCHAR),
                        new SqlParameter("PPERSON_NAME", Types.VARCHAR),
                        new SqlParameter("PEMAIL", Types.VARCHAR),
                        new SqlParameter("PMOBILENO", Types.VARCHAR),
                        new SqlParameter("PX1", Types.VARCHAR),
                        new SqlParameter("PX2", Types.VARCHAR),
                        new SqlParameter("PX3", Types.VARCHAR),
                        new SqlParameter("PX4", Types.VARCHAR),
                        new SqlParameter("PX5", Types.VARCHAR),
                        new SqlParameter("PEXTRA1", Types.VARCHAR),
                        new SqlParameter("PEXTRA2", Types.VARCHAR),
                        new SqlParameter("PEXTRA3", Types.VARCHAR),
                        new SqlParameter("PEXTRA4", Types.VARCHAR),
                        new SqlParameter("PEXTRA5", Types.VARCHAR),
                        new SqlParameter("PTYPE", Types.VARCHAR),
                        new SqlParameter("PEDITFLAG", Types.VARCHAR),
                        new SqlReturnResultSet("RESULT_SET_1", (rs, numRow) ->
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
                        )


                );
    }

    @Override
    public List<TaskMastResponsibleTempResponseListDto> fetchResponsiblePerson(TaskScheduleReponsibalRequestDto taskScheduleReponsibalRequestDto) {
        Map<String, Object> map= buildParams(taskScheduleReponsibalRequestDto);
        Map<String, Object> result= call.execute(map);


        return (List<TaskMastResponsibleTempResponseListDto>) result.get("RESULT_SET_1");
    }

    @Override
    public Map<String, Object> addResponsiblePerson(TaskScheduleReponsibalRequestDto taskScheduleReponsibalRequestDto) {
        Map<String, Object> map= buildParams(taskScheduleReponsibalRequestDto);
        return call.execute(map);
    }

    @Override
    public Map<String, Object> deleteResponsiblePerson(TaskScheduleReponsibalRequestDto taskScheduleReponsibalRequestDto) {
        Map<String, Object> map= buildParams(taskScheduleReponsibalRequestDto);
        return call.execute(map);
    }


    public Map<String, Object> buildParams(TaskScheduleReponsibalRequestDto taskScheduleReponsibalRequestDto) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("PCOM_PCODE", taskScheduleReponsibalRequestDto.pcomPcode());
        params.put("PCENTCODE", taskScheduleReponsibalRequestDto.pcentCode());
        params.put("PBRANCHCODE", taskScheduleReponsibalRequestDto.pbranchCode());
        params.put("PTASK_SCHEDULE_CODE", taskScheduleReponsibalRequestDto.ptaskScheduleCode());
        params.put("PTASKCODE", taskScheduleReponsibalRequestDto.ptaskCode());
        params.put("PROWID", taskScheduleReponsibalRequestDto.prowId());
        params.put("PPERSONCODE", taskScheduleReponsibalRequestDto.personCode());
        params.put("PPERSON_NAME", taskScheduleReponsibalRequestDto.personName());
        params.put("PEMAIL", taskScheduleReponsibalRequestDto.email());
        params.put("PMOBILENO", taskScheduleReponsibalRequestDto.mobileNo());
        params.put("PX1", taskScheduleReponsibalRequestDto.px1());
        params.put("PX2", taskScheduleReponsibalRequestDto.px2());
        params.put("PX3", taskScheduleReponsibalRequestDto.px3());
        params.put("PX4", taskScheduleReponsibalRequestDto.px4());
        params.put("PX5", taskScheduleReponsibalRequestDto.px5());
        params.put("PEXTRA1", taskScheduleReponsibalRequestDto.pextra1());
        params.put("PEXTRA2", taskScheduleReponsibalRequestDto.pextra2());
        params.put("PEXTRA3", taskScheduleReponsibalRequestDto.pextra3());
        params.put("PEXTRA4", taskScheduleReponsibalRequestDto.pextra4());
        params.put("PEXTRA5", taskScheduleReponsibalRequestDto.pextra5());
        params.put("PTYPE", taskScheduleReponsibalRequestDto.ptype());
        params.put("PEDITFLAG", taskScheduleReponsibalRequestDto.peditFlag());
        String logSql = buildDebugSql(params, "CMS_TASK_SCHEDULE_RESPONSIBLE_ISNUPD");
        System.out.println("PROC PARAMS => " + logSql);

        return params;


    }
}

