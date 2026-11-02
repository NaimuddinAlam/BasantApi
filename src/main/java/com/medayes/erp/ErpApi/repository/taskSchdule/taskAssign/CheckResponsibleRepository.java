package com.medayes.erp.ErpApi.repository.taskSchdule.taskAssign;

import com.medayes.erp.ErpApi.dto.request.indent.RequirementSlipRequestDto;
import com.medayes.erp.ErpApi.dto.response.task.CheckResponsibleResponseDto;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CheckResponsibleRepository {
    private SimpleJdbcCall call;
  final   private JdbcTemplate template;
    @Autowired
    CheckResponsibleRepository(JdbcTemplate template)

    {
        this.template=template;
    }
    private static final String PARAM_COMP_CODE = "PCOMP_CODE";
    private static final String PARAM_CENT_CODE = "PCENT_CODE";
    private static final String PARAM_EMP_CODE = "PTASK_ASSIGN_APP";

    @PostConstruct
    public void  init()
    {
        call=new SimpleJdbcCall(template)
                .withCatalogName("dbo")
                .withProcedureName("CMS_CHECK_RESPONSIBLE_PER")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                  new SqlParameter(PARAM_COMP_CODE, Types.VARCHAR) ,
                        new SqlParameter(PARAM_CENT_CODE, Types.VARCHAR) ,
                        new SqlParameter(PARAM_EMP_CODE, Types.VARCHAR)

                        ).returningResultSet("Data",(rs,num)->
                        new CheckResponsibleResponseDto(rs.getString("RES")));
    }
    public CheckResponsibleResponseDto fetch(RequirementSlipRequestDto requestDto)
    {
        Map<String,Object> map= buildParams(requestDto);
        Map<String,Object> response= call.execute(map);
        Object data = response.get("Data");
        if (data instanceof List<?> list && !list.isEmpty()) {
            return (CheckResponsibleResponseDto) list.get(0);
        }

        return null;
    }

    public Map<String,Object> buildParams(RequirementSlipRequestDto requestDto)
    {
        HashMap<String,Object> map= new HashMap<>();
        map.put(PARAM_COMP_CODE,requestDto.compCode());
        map.put(PARAM_CENT_CODE,requestDto.centCode());
        map.put(PARAM_EMP_CODE,requestDto.extra());
         return  map;
    }

}
