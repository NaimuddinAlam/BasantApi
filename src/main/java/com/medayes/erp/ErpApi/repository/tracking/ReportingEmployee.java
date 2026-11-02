package com.medayes.erp.ErpApi.repository.tracking;

import com.medayes.erp.ErpApi.dto.response.tracking.*;
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
public class ReportingEmployee {
    private SimpleJdbcCall simpleJdbcCall;
    private final JdbcTemplate template;
    private static final String PARAM_Emp_CODE = "PEMP_NAME";
    private static final String PARAM_Reprorting_CODE = "PREPORTING_CODE";



    public ReportingEmployee(JdbcTemplate template) {
        this.template = template;
    }

    @PostConstruct
    public  void  init()
    {
        simpleJdbcCall=new SimpleJdbcCall(template)

                .withCatalogName("dbo")
                .withProcedureName("FATCH_EMP")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter(PARAM_Emp_CODE, Types.VARCHAR),
                        new SqlParameter(PARAM_Reprorting_CODE, Types.VARCHAR)

                ) .returningResultSet("data", this::responseData);

    }



    private ReportingEmployeeResponseDTO responseData(ResultSet rs, int rowNum) throws SQLException {
        return   new ReportingEmployeeResponseDTO(
                rs.getString("EMP_NAME"),
                rs.getString("EMP_CODE")
        );
    }


    public List<ReportingEmployeeResponseDTO> getEmp(
            EmployeeTrackingRequestDto requestDto) {

        Map<String, Object> response =
                simpleJdbcCall.execute(build(requestDto));

        return
                (List<ReportingEmployeeResponseDTO>) response.get("data");






    }


    private  Map<String, Object> build(EmployeeTrackingRequestDto requestDto)
    {
        HashMap<String, Object> map= new HashMap<>();
        map.put(PARAM_Emp_CODE,requestDto.empCode());
        map.put(PARAM_Reprorting_CODE,requestDto.reportingTo());

        return  map;
    }
}
