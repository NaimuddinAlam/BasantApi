package com.medayes.erp.ErpApi.repository.tempTask;


import com.medayes.erp.ErpApi.dto.request.task.ResponsiblePersonRequestDto;
import com.medayes.erp.ErpApi.dto.response.task.ResponsiblePersonResponseDto;
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
public class ResponsiblePersonRepository {

    private   final JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall call;

    ResponsiblePersonRepository(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate=jdbcTemplate;
    }
    @PostConstruct
    public  void  init()
    {
        call= new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("CMS_GET_USER_RESPONSIBLE")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("PCOMPCODE", Types.VARCHAR),
                        new SqlParameter("PUSERNAME", Types.VARCHAR),
                        new SqlParameter("PRESPONSIBLE_FLAG", Types.VARCHAR),
                        new SqlParameter("PEXTRA1", Types.VARCHAR),
                        new SqlParameter("PEXTRA2", Types.VARCHAR)

                ).
                returningResultSet("RESULT_SET_1",(rs,num)->
                        new ResponsiblePersonResponseDto(
                                rs.getString("USERID"),
                                rs.getString("PERSON_NAME"),
                                rs.getString("EMAILID"),
                                rs.getString("USER_TYPE"),
                                rs.getString("MOBILENO")

                        ));
    }
    public List<ResponsiblePersonResponseDto> fetchResponsiblePerson(ResponsiblePersonRequestDto req)
    {
        Map<String, Object> map= buildParams(req);
        Map<String, Object> response= call.execute(map);
        return  (List<ResponsiblePersonResponseDto>)response.get("RESULT_SET_1");


    }


    public Map<String, Object> buildParams(ResponsiblePersonRequestDto req)
    {
        HashMap<String, Object> parsms= new HashMap<>();
        parsms.put("PCOMPCODE",req.PCOMPCODE());
        parsms.put("PUSERNAME",req.PUSERNAME());
        parsms.put("PRESPONSIBLE_FLAG",req.PRESPONSIBLE_FLAG());
        parsms.put("PEXTRA1",req.PEXTRA1());
        parsms.put("PEXTRA2",req.PEXTRA2());
       return  parsms;
    }





}
