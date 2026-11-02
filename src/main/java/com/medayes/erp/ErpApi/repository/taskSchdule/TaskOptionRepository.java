package com.medayes.erp.ErpApi.repository.taskSchdule;

import com.medayes.erp.ErpApi.dto.request.task.CmsTaskScheduleRequestDto;
import com.medayes.erp.ErpApi.dto.response.task.CmsTaskScheduleResponseDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskOptionResponseDto;
import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TaskOptionRepository {
    final  private JdbcTemplate jdbcTemplate;
      private SimpleJdbcCall call;
    TaskOptionRepository(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate=jdbcTemplate;
    }

    @PostConstruct
    public  void  init()
    {
        call=new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("CMS_TASK_SCHEDULE_INSUPDDEL")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("pcompcode", Types.VARCHAR),
                        new SqlParameter("pcentcode", Types.VARCHAR),
                        new SqlParameter("pbranchcode", Types.VARCHAR),
                        new SqlParameter("pleadcode", Types.VARCHAR),
                        new SqlParameter("popporcode", Types.VARCHAR),
                        new SqlParameter("pcallmetingcode", Types.VARCHAR),
                        new SqlParameter("pcallflag", Types.VARCHAR),
                        new SqlParameter("pphone", Types.VARCHAR),
                        new SqlParameter("psheduledate", Types.VARCHAR),
                        new SqlParameter("psheduletime", Types.VARCHAR),
                        new SqlParameter("ptaskenddate", Types.VARCHAR),
                        new SqlParameter("ptaskendtime", Types.VARCHAR),
                        new SqlParameter("psalespercode", Types.VARCHAR),
                        new SqlParameter("ptasksubject", Types.VARCHAR),
                        new SqlParameter("pattendees", Types.VARCHAR),
                        new SqlParameter("ptaskduration", Types.VARCHAR),
                        new SqlParameter("ptasktag", Types.VARCHAR),
                        new SqlParameter("ptasklocation", Types.VARCHAR),
                        new SqlParameter("pprivacyflag", Types.VARCHAR),
                        new SqlParameter("pshowtime", Types.VARCHAR),
                        new SqlParameter("pcallsummry", Types.VARCHAR),
                        new SqlParameter("pcalldescription", Types.VARCHAR),
                        new SqlParameter("precurrentflag", Types.VARCHAR),
                        new SqlParameter("prepeatfreq", Types.VARCHAR),
                        new SqlParameter("pfrequencytype", Types.VARCHAR),
                        new SqlParameter("puntilflag", Types.VARCHAR),
                        new SqlParameter("penddate", Types.VARCHAR),
                        new SqlParameter("pnoofrepetition", Types.VARCHAR),
                        new SqlParameter("pmon", Types.VARCHAR),
                        new SqlParameter("ptue", Types.VARCHAR),
                        new SqlParameter("pwed", Types.VARCHAR),
                        new SqlParameter("pthu", Types.VARCHAR),
                        new SqlParameter("pfri", Types.VARCHAR),
                        new SqlParameter("psat", Types.VARCHAR),
                        new SqlParameter("psun", Types.VARCHAR),
                        new SqlParameter("pdaydatemonth", Types.VARCHAR),
                        new SqlParameter("pmonthofweek", Types.VARCHAR),
                        new SqlParameter("pdayofweek", Types.VARCHAR),
                        new SqlParameter("PLOGGED_TIME", Types.VARCHAR),
                        new SqlParameter("PTASK_STATUS", Types.VARCHAR),
                        new SqlParameter("POLDTASK_CODE", Types.VARCHAR),
                        new SqlParameter("PALERTBEFORE", Types.VARCHAR),
                        new SqlParameter("PALERTBEFORETIME", Types.VARCHAR),
                        new SqlParameter("PACTUALMTIME", Types.VARCHAR),
                        new SqlParameter("PEXTRA1", Types.VARCHAR),
                        new SqlParameter("PEXTRA2", Types.VARCHAR),
                        new SqlParameter("PEXTRA3", Types.VARCHAR),
                        new SqlParameter("PEXTRA4", Types.VARCHAR),
                        new SqlParameter("PEXTRA5", Types.VARCHAR),
                        new SqlParameter("PEXTRA6", Types.VARCHAR),
                        new SqlParameter("PEXTRA7", Types.VARCHAR),
                        new SqlParameter("PEXTRA8", Types.VARCHAR),
                        new SqlParameter("PEXTRA9", Types.VARCHAR),
                        new SqlParameter("PEXTRA10", Types.VARCHAR),
                        new SqlParameter("pip", Types.VARCHAR),
                        new SqlParameter("puserid", Types.VARCHAR),
                        new SqlParameter("pdateformat", Types.VARCHAR),
                        new SqlParameter("ptype", Types.VARCHAR)
                )
                .returningResultSet("RESULT_SET_1",( rs, num)->
                        new TaskOptionResponseDto(
                                rs.getString("COMP_CODE"),
                                rs.getString("CENT_CODE"),
                                rs.getString("BRANCH_CODE"),
                                rs.getString("LEAD_CODE"),
                                rs.getString("OPPORTUNITY_CODE"),
                                rs.getString("TASK_SCHEDULE_CODE"),
                                rs.getString("CALL_FLAG"),
                                rs.getString("PHONE"),
                                rs.getString("SCHEDULE_DATE"),
                                rs.getString("SCHEDULE_TIME"),
                                rs.getString("TASKEND_DATE"),
                                rs.getString("TASKEND_TIME"),
                                rs.getString("SALES_PER_CODE"),
                                rs.getString("SALES_PER_NAME"),
                                rs.getString("SALES_TEAM_CODE"),
                                rs.getString("TASK_SUBJECT"),
                                rs.getString("ATTENDEES"),
                                rs.getString("TASK_DURATION"),
                                rs.getString("TASK_TAG"),
                                rs.getString("MEETING_TAG_NAME"),
                                rs.getString("TASK_LOCATION"),
                                rs.getString("PRIVACY_FLAG"),
                                rs.getString("SHOW_TIME_AS"),
                                rs.getString("CALL_SUMMARY"),
                                rs.getString("CALL_DESCRIPTION"),
                                rs.getString("RECURRENT_FLAG"),
                                rs.getString("REPEAT_FREQUENCY"),
                                rs.getString("FREQUENCY_TYPE"),
                                rs.getString("UNTIL_FLAG"),
                                rs.getString("END_DATE"),
                                rs.getString("NO_OF_REPETITIONS"),
                                rs.getString("MON"),
                                rs.getString("TUE"),
                                rs.getString("WED"),
                                rs.getString("THU"),
                                rs.getString("FRI"),
                                rs.getString("SAT"),
                                rs.getString("SUN"),
                                rs.getString("DAY_DATE_OF_MONTH"),
                                rs.getString("MONTH_WEEK"),
                                rs.getString("DAY_OF_WEEK"),
                                rs.getString("LOGGED_TIME"),
                                rs.getString("LOGGED_DATE"),
                                rs.getString("ALERT_BEFORE"),
                                rs.getString("ALERT_BEFORE_TIME"),
                                rs.getString("ACTUAL_TIME"),
                                rs.getString("ACTUAL_DATE"),
                                rs.getString("TASK_STATUS"),
                                rs.getString("MEETING_STATUS_NAME")
                        ));

    }



    public List<TaskOptionResponseDto> fetchOption(CmsTaskScheduleRequestDto task) {
        Map<String,Object> map= cmsParam(task);
        Map<String, Object> result=call.execute(map);
        return  (List<TaskOptionResponseDto>)result.get("RESULT_SET_1");
    }

    private Map<String, Object> cmsParam(CmsTaskScheduleRequestDto task)
    {

        Map<String, Object> map = new HashMap<>();

        map.put("pcompcode", task.pcompcode());
        map.put("pcentcode", task.pcentcode());
        map.put("pbranchcode", task.pbranchcode());
        map.put("pleadcode", task.pleadcode());
        map.put("popporcode", task.popporcode());
        map.put("pcallmetingcode", task.pcallmetingcode());
        map.put("pcallflag", task.pcallflag());
        map.put("pphone", task.pphone());
        map.put("psheduledate", task.psheduledate());
        map.put("psheduletime", task.psheduletime());
        map.put("ptaskenddate", task.ptaskenddate());
        map.put("ptaskendtime", task.ptaskendtime());
        map.put("psalespercode", task.psalespercode());
        map.put("ptasksubject", task.ptasksubject());
        map.put("pattendees", task.pattendees());
        map.put("ptaskduration", task.ptaskduration());
        map.put("ptasktag", task.ptasktag());
        map.put("ptasklocation", task.ptasklocation());
        map.put("pprivacyflag", task.pprivacyflag());
        map.put("pshowtime", task.pshowtime());
        map.put("pcallsummry", task.pcallsummry());
        map.put("pcalldescription", task.pcalldescription());
        map.put("precurrentflag", task.precurrentflag());
        map.put("prepeatfreq", task.prepeatfreq());
        map.put("pfrequencytype", task.pfrequencytype());
        map.put("puntilflag", task.puntilflag());
        map.put("penddate", task.penddate());
        map.put("pnoofrepetition", task.pnoofrepetition());
        map.put("pmon", task.pmon());
        map.put("ptue", task.ptue());
        map.put("pwed", task.pwed());
        map.put("pthu", task.pthu());
        map.put("pfri", task.pfri());
        map.put("psat", task.psat());
        map.put("psun", task.psun());
        map.put("pdaydatemonth", task.pdaydatemonth());
        map.put("pmonthofweek", task.pmonthofweek());
        map.put("pdayofweek", task.pdayofweek());
        map.put("PLOGGED_TIME", task.PLOGGED_TIME());
        map.put("PTASK_STATUS", task.PTASK_STATUS());
        map.put("POLDTASK_CODE", task.POLDTASK_CODE());
        map.put("PALERTBEFORE", task.PALERTBEFORE());
        map.put("PALERTBEFORETIME", task.PALERTBEFORETIME());
        map.put("PACTUALMTIME", task.PACTUALMTIME());
        map.put("PEXTRA1", task.PEXTRA1());
        map.put("PEXTRA2", task.PEXTRA2());
        map.put("PEXTRA3", task.PEXTRA3());
        map.put("PEXTRA4", task.PEXTRA4());
        map.put("PEXTRA5", task.PEXTRA5());
        map.put("PEXTRA6", task.PEXTRA6());
        map.put("PEXTRA7", task.PEXTRA7());
        map.put("PEXTRA8", task.PEXTRA8());
        map.put("PEXTRA9", task.PEXTRA9());
        map.put("PEXTRA10", task.PEXTRA10());
        map.put("pip", task.pip());
        map.put("puserid", task.puserid());
        map.put("pdateformat", task.pdateformat());
        map.put("ptype", task.ptype());

        return map;
    }

}
