package com.medayes.erp.ErpApi.repository.production;

import com.medayes.erp.ErpApi.dto.request.production.QualityCheckRequestDto;
import com.medayes.erp.ErpApi.dto.response.production.OrganiserResponseDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
@Slf4j
public class OrganiserRepository {
    private SimpleJdbcCall simpleJdbcCall;
    private SimpleJdbcCall simpleJdbcCallFarmer;
    private  final JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void  init()
    {
        simpleJdbcCall= new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("PRD_BIND_ORGANISER_TYPEf2")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("PCOMP_CODE", Types.VARCHAR),
                        new SqlParameter("PCENT_CODE", Types.VARCHAR),
                       new SqlParameter("PLEDGER_CODE_SUBCODE", Types.VARCHAR),
                        new SqlParameter("PEXTRA1", Types.VARCHAR)


                ).returningResultSet("data",this::responseDto);
        simpleJdbcCallFarmer= new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("dbo")
                .withProcedureName("PRD_BIND_FARMER_TYPE")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("PCOMP_CODE", Types.VARCHAR),
                        new SqlParameter("PCENT_CODE", Types.VARCHAR),
                        new SqlParameter("PFARMER_CODE", Types.VARCHAR),
                        new SqlParameter("PEXTRA1", Types.VARCHAR)


                ).returningResultSet("data",this::farmerResponseDto);





    }

private OrganiserResponseDto responseDto(ResultSet rs, int rowNum) throws SQLException {
       log.debug("response {}",rs);

       return  OrganiserResponseDto
               .builder()
               .orgCode(rs.getString("LEDGER_CODE_SUBCODE"))
               .orgName(rs.getString("LEDGER_NAME"))
               .build();
}
    private OrganiserResponseDto farmerResponseDto(ResultSet rs, int rowNum) throws SQLException {
        log.debug("response {}",rs);

        return  OrganiserResponseDto
                .builder()
                .farmerCode(rs.getString("FARMER_CODE"))
                .farmerName(rs.getString("FARMER_NAME"))
                .build();
    }

    public List<OrganiserResponseDto> responseFarmer(QualityCheckRequestDto dto)
    {   log.debug("request {}",dto);
        Map<String, Object> map=buildParamsFarmer(dto);

        Map<String, Object> result= simpleJdbcCallFarmer.execute(map);
        Object resultData = result.get("data");

        if (resultData == null) {
            log.debug("No Reason  records found");
            return Collections.emptyList();
        }
        log.debug("responseList {}",resultData);
        return  (List<OrganiserResponseDto>) resultData;


    }
public List<OrganiserResponseDto> response(QualityCheckRequestDto dto)
{   log.debug("request {}",dto);
    Map<String, Object> map=buildParams(dto);

    Map<String, Object> result= simpleJdbcCall.execute(map);
    Object resultData = result.get("data");

    if (resultData == null) {
        log.debug("No Reason  records found");
        return Collections.emptyList();
    }
    log.debug("responseList {}",resultData);
   return  (List<OrganiserResponseDto>) resultData;


}
    private Map<String, Object> buildParams(
            QualityCheckRequestDto dto) {

        Map<String, Object> params = new HashMap<>();

        params.put("PCOMP_CODE", dto.getCompCode());
        params.put("PCENT_CODE", dto.getCentCode());
        params.put("PLEDGER_CODE_SUBCODE", dto.getLedgerCodeSubCode());

        params.put("PEXTRA1",  dto.getExtra1());



        return params;
    }
    private Map<String, Object> buildParamsFarmer(
            QualityCheckRequestDto dto) {

        Map<String, Object> params = new HashMap<>();

        params.put("PCOMP_CODE", dto.getCompCode());
        params.put("PCENT_CODE", dto.getCentCode());
        params.put("PFARMER_CODE", dto.getFarmerCode());

        params.put("PEXTRA1",  dto.getExtra1());



        return params;
    }

}
