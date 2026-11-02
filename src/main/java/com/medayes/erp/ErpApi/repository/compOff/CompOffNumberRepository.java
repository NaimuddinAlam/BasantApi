package com.medayes.erp.ErpApi.repository.compOff;

import com.medayes.erp.ErpApi.dto.request.compOff.CompOffRequestSaveDTO;
import com.medayes.erp.ErpApi.dto.response.compOff.CompOffNumberResponseDTO;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor

public class CompOffNumberRepository {
    private  final JdbcTemplate jdbcTemplate;
    private   SimpleJdbcCall simpleJdbcCall;



    @PostConstruct
    private  void compOffNumberGenerate()
    {
        simpleJdbcCall= new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("ERP_COM_NUM_GEN")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("PCOMP_CODE", Types.VARCHAR),
                        new SqlParameter("PCENT_CODE", Types.VARCHAR),
                        new SqlParameter("PTABLE",Types.VARCHAR),
                        new SqlParameter("PCOLUMN",Types.VARCHAR),
                        new SqlParameter("PNAME", Types.VARCHAR),
                        new SqlParameter("PEXTRA", Types.VARCHAR),
                        new SqlParameter("PEXTRA1", Types.VARCHAR),
                        new SqlParameter("PEXTRA2", Types.VARCHAR),
                        new SqlParameter("PEXTRA3", Types.VARCHAR)

                ).returningResultSet("data",(rs, rowNum) ->
                        response(rs));

    }
    private CompOffNumberResponseDTO response (ResultSet rs) throws SQLException {
        CompOffNumberResponseDTO responseDTO=  new CompOffNumberResponseDTO();
         responseDTO.setGenCode(rs.getString("GENCODE"));
          return  responseDTO;
    }
    public List<CompOffNumberResponseDTO> fetchGenCode(CompOffRequestSaveDTO compOffNumberRequestDTO)
    {
      Map<String,Object> request=  buildCompOffNumber(compOffNumberRequestDTO);
        Map<String,Object> response=     simpleJdbcCall.execute(request);
        return (List<CompOffNumberResponseDTO>) response.get("data");
    }

    private Map<String,Object> buildCompOffNumber(CompOffRequestSaveDTO compOffNumberRequestDTO)
    {
        Map<String,Object> map= new HashMap<>();
        map.put("PCOMP_CODE",compOffNumberRequestDTO.getCompCode());
        map.put("PCENT_CODE",compOffNumberRequestDTO.getCentCode());
        map.put("PTABLE","PAY_COMP_REQUEST_MAST");
        map.put("PCOLUMN","COMP_REQ_NO");
        map.put("PNAME","CR");
        map.put("PEXTRA","");
        map.put("PEXTRA1","");
        map.put("PEXTRA2","");
        map.put("PEXTRA3","");


        return  map;
    }

}
