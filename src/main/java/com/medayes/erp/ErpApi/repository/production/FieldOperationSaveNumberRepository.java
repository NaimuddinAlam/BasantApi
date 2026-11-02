package com.medayes.erp.ErpApi.repository.production;

import com.medayes.erp.ErpApi.dto.request.production.SeedSowingUpdateRequestDto;
import com.medayes.erp.ErpApi.dto.response.production.SeedSowingUpdateDto;
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
public class FieldOperationSaveNumberRepository {
    private SimpleJdbcCall simpleJdbcCall;

    private final JdbcTemplate jdbcTemplate;
    private static final String PROCEDURE_NAME =
            "col_doc_genration_withtable";

    private static final String RESULT_DATA = "data";
    @PostConstruct
    public void  init()
    {
        simpleJdbcCall= new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName(".dbo")
                .withProcedureName(PROCEDURE_NAME)
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("PCOMPCODE", Types.VARCHAR),
                        new SqlParameter("PCENT_CODE", Types.VARCHAR),
                        new SqlParameter("PBRANC_CODE", Types.VARCHAR),
                        new SqlParameter("PMODULECODE", Types.VARCHAR),
                        new SqlParameter("PDOCTYPE", Types.VARCHAR),
                        new SqlParameter("PDOCNUMTYPE", Types.VARCHAR),
                        new SqlParameter("RCPDATE", Types.VARCHAR),
                        new SqlParameter("PAGENCYCODE_SUBCODE", Types.VARCHAR),
                        new SqlParameter("PEXTRA", Types.VARCHAR),
        new SqlParameter("PEXTRA1", Types.VARCHAR),
        new SqlParameter("PEXTRA2", Types.VARCHAR),
        new SqlParameter("PEXTRA3", Types.VARCHAR),
        new SqlParameter("PEXTRA4", Types.VARCHAR),
        new SqlParameter("PEXTRA5", Types.VARCHAR)

                ).returningResultSet(RESULT_DATA, this::mapResponse);
    }


    public SeedSowingUpdateDto generateFieldOperationNumber(
            SeedSowingUpdateRequestDto request) {

        Map<String, Object> parameters =
                buildParameters(request);

        Map<String, Object> result =
                simpleJdbcCall.execute(parameters);

        return extractResponse(result);
    }
    private SeedSowingUpdateDto mapResponse(
            ResultSet rs,
            int rowNum) throws SQLException {

        return SeedSowingUpdateDto.builder()
                .res(rs.getString("GEN_DOC_NO"))


                .build();
    }
    private Map<String, Object> buildParameters(
            SeedSowingUpdateRequestDto request) {

        Map<String, Object> parameters = new HashMap<>();

        parameters.put("PCOMPCODE", request.compCode());
        parameters.put("PCENT_CODE", request.centCode());
        parameters.put("PBRANC_CODE", request.branchCode());
        parameters.put("PMODULECODE",   request.moduleCode());
        parameters.put("PDOCTYPE",   request.docType());
        parameters.put("PDOCNUMTYPE",   request.docNumType());
        parameters.put("RCPDATE",   request.rcpDate());
        parameters.put("PAGENCYCODE_SUBCODE",   request.agencyCode());
        parameters.put("PEXTRA", request.extra());
        parameters.put("PEXTRA1", request.extra1());
        parameters.put("PEXTRA2", request.extra2());
        parameters.put("PEXTRA3", request.extra3());
        parameters.put("PEXTRA4", request.extra4());
        parameters.put("PEXTRA5", request.extra5());



        return parameters;
    }
    private SeedSowingUpdateDto extractResponse(
            Map<String, Object> result) {

        Object data = result.get(RESULT_DATA);

        if (!(data instanceof List<?> list) || list.isEmpty()) {
            return null;
        }

        return (SeedSowingUpdateDto) list.get(0);
    }

}
