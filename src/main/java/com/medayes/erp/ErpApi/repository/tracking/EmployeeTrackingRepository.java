package com.medayes.erp.ErpApi.repository.tracking;

import com.medayes.erp.ErpApi.dto.response.tracking.EmployeeTrackingRequestDto;
import com.medayes.erp.ErpApi.dto.response.tracking.EmployeeTrackingResponseDto;
import com.medayes.erp.ErpApi.dto.response.tracking.EmployeeTrackingSummaryDto;
import com.medayes.erp.ErpApi.dto.response.tracking.EmployeeTrackingWrapperDto;
import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class EmployeeTrackingRepository {
    private   SimpleJdbcCall simpleJdbcCall;
    private final JdbcTemplate template;
    private static final String PARAM_COMP_CODE = "PCOMP_CODE";
    private static final String PARAM_Emp_CODE = "PEMP_CODE";
    private static final String PARAM_FROM_DATE = "PDATE";
    private static final String PARAM_TO_DATE = "TODATE";
    private static final String PARAM_Reprorting_CODE = "PREPORTING_CODE";

    public EmployeeTrackingRepository(JdbcTemplate template) {
        this.template = template;
    }

    @PostConstruct
    public  void  init()
    {
        simpleJdbcCall=new SimpleJdbcCall(template)

                .withCatalogName("dbo")
                .withProcedureName("PAY_LAT_LONG_DETAILS")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter(PARAM_COMP_CODE, Types.VARCHAR),
                        new SqlParameter(PARAM_Emp_CODE, Types.VARCHAR),
                        new SqlParameter(PARAM_FROM_DATE, Types.VARCHAR),
                        new SqlParameter(PARAM_TO_DATE, Types.VARCHAR),
        new SqlParameter(PARAM_Reprorting_CODE, Types.VARCHAR)

                ) .returningResultSet("data", this::responseData)
                .returningResultSet("#result-set-2", this::mapSummary);
    }
    private EmployeeTrackingSummaryDto mapSummary(ResultSet rs, int rowNum)
            throws SQLException {

        return new EmployeeTrackingSummaryDto(
                rs.getString("TOTAL_TRAVEL_DISTANCE_METERS")

        );
    }

    private EmployeeTrackingResponseDto responseData(ResultSet rs,int rowNum) throws SQLException {
      return   new EmployeeTrackingResponseDto(
                rs.getString("COMP_CODE"),
                rs.getString("USER_ID"),
                rs.getString("USER_NAME"),
                rs.getDouble("LAT"),
                rs.getDouble("LONG"),
                rs.getString("ROW_ID"),
                rs.getString("CREATION_DATE"),
                rs.getString("DISTANCE_METERS"),
                rs.getString("LOCATION")
        );
    }


    public EmployeeTrackingWrapperDto getEmpTracking(
            EmployeeTrackingRequestDto requestDto) {

        Map<String, Object> response =
                simpleJdbcCall.execute(build(requestDto));

        List<EmployeeTrackingResponseDto> data =
                (List<EmployeeTrackingResponseDto>) response.get("data");

        List<EmployeeTrackingSummaryDto> summaryList =
                (List<EmployeeTrackingSummaryDto>) response.get("#result-set-2");

        EmployeeTrackingSummaryDto summary =
                summaryList != null && !summaryList.isEmpty()
                        ? summaryList.get(0)
                        : null;

        return new EmployeeTrackingWrapperDto(data, summary);
    }


    private  Map<String, Object> build(EmployeeTrackingRequestDto requestDto)
    {
        HashMap<String, Object> map= new HashMap<>();
        map.put(PARAM_COMP_CODE,requestDto.compCode());
        map.put(PARAM_Emp_CODE,requestDto.empCode());
        map.put(PARAM_FROM_DATE,requestDto.fromDate());
        map.put(PARAM_TO_DATE,requestDto.toDate());
        map.put(PARAM_Reprorting_CODE,requestDto.reportingTo());

         return  map;
    }
}
