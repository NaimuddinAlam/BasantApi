package com.medayes.erp.ErpApi.services.servicesImpl;

import com.medayes.erp.ErpApi.dto.request.*;
import com.medayes.erp.ErpApi.dto.response.CheckFormPermissionLoanResponseDto;
import com.medayes.erp.ErpApi.dto.response.LoanResponse;
import com.medayes.erp.ErpApi.dto.response.LoanResponseDto;
import com.medayes.erp.ErpApi.dto.response.LoanResponseFetchDetDto;
import com.medayes.erp.ErpApi.services.InvReqSlipService;
import com.medayes.erp.ErpApi.utils.ApiParams;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import jakarta.validation.Valid;


import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.medayes.erp.ErpApi.utils.Utils.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoanServiceImpl implements InvReqSlipService {

    private final EntityManager em;

    @Override
    @Transactional
    public LoanResponseDto generateLoanNo(@Valid LoanRequestDto dto) {
        log.debug("Generating loan number for company: {}, center: {}", dto.getCompCode(), dto.getCentCode());

        String loanNo = executeGenerateLoanNo(dto);

        if (!StringUtils.hasText(loanNo)) {
            throw new IllegalStateException("Stored procedure PAY_GENLOANNO returned empty loan number");
        }

        log.info("Loan number generated: {}", loanNo);
        return LoanResponseDto.success(loanNo);
    }

    @Override
    @Transactional
    public LoanResponseDto saveLoanHdr(@Valid LoanSaveHdrRequestDto dto) {
        log.debug("Saving loan header for loanNo: {}", dto.getLoanNo());

        int rowsAffected = executeSaveLoanHdr(dto);

        if (rowsAffected <= 0) {
            throw new IllegalStateException("Loan header save affected 0 rows");
        }

        log.info("Loan header saved successfully for loanNo: {}", dto.getLoanNo());
        return LoanResponseDto.success("");
    }

    @Override
    @Transactional
    public LoanResponseDto saveLoanDet(@Valid LoanSaveDetRequestDto loanSaveDetRequestDto) {
        log.debug("Saving loan header for loanNo: {}", loanSaveDetRequestDto.getLoanNo());
        int rowsAffected=executeSaveLoanHDet(loanSaveDetRequestDto);

        if (rowsAffected <= 0) {
            throw new IllegalStateException("Loan header save affected 0 rows");
        }

        log.info("Loan header saved successfully for loanNo: {}", loanSaveDetRequestDto.getLoanNo());
        return LoanResponseDto.success("");
    }

    @Override
    @Transactional
    public LoanResponse<List<LoanResponseFetchDetDto>> fetchLoanData(LoanSaveDetRequestDto dto) {

        log.debug("Fetching loan details for loanNo: {}", dto.getCompCode());

        List<Object[]> result = executeFetchLoan(dto);

        if (result == null || result.isEmpty()) {
            log.info("No data found for loanNo: {}", dto.getLoanNo());
            return new LoanResponse<>(
                    "FAILED",
                    "No data found",
                    HttpStatus.OK.value(),              // ✅ 200 OK
                    Collections.emptyList()             // ✅ send empty list
            );
        }

        List<LoanResponseFetchDetDto> mappedList = mapLoanDetailList(result);

        return LoanResponse.success(mappedList);
    }

    @Override
    @Transactional
    public LoanResponse<List<LoanResponseFetchDetDto>> fetchLoanDataApproval(LoanFetchApprovalRequestDto dto) {

       List<Object[]> result= executeFetchLoanApproval(dto);
       if(result==null || result.isEmpty())
       {
           log.info("No data found for loanNo: {}", dto.getPemploytype());
           return  new LoanResponse<>("FAILED",
                   "No data found",
                   HttpStatus.OK.value(),              // ✅ 200 OK
                   Collections.emptyList()  );
       }
       List<LoanResponseFetchDetDto> mappedList=mapLoanDetailListApproval(result);
        return LoanResponse.success(mappedList);
    }

    @Override
    public LoanResponse<List<CheckFormPermissionLoanResponseDto>> checkFormPermissionLoan(CheckFormPermissionLoanRequestDto loanRequestDto) {
     List<Object[]> result=checkFromPermission(loanRequestDto);
        if(result==null || result.isEmpty())
        {
            log.info("No data found for loanNo: {}", loanRequestDto.getPempCode());
            return  new LoanResponse<>("FAILED",
                    "No data found",
                    HttpStatus.OK.value(),              // ✅ 200 OK
                    Collections.emptyList()  );
        }
        log.info("response","msg"+result);

     List<CheckFormPermissionLoanResponseDto> mapper=mapCheckFromPermission(result);
        return LoanResponse.success(mapper);
    }

    @Override
    public LoanResponseDto approvalLoanSave(LoanApprovalRequestDto loanSaveDetRequestDto) {
        log.debug("Saving loan Approval for appNo: {}", loanSaveDetRequestDto.getPappNo());
        int rowsAffected=executeApprovalSaveLoan(loanSaveDetRequestDto);

        if (rowsAffected <= 0) {
            throw new IllegalStateException("Loan Approval save affected 0 rows");
        }

        log.info("Loan Approval saved successfully for loanNo: {}", loanSaveDetRequestDto.getPappNo());
        return LoanResponseDto.success("");
    }


    private String toStringVal(Object obj) {
        return obj == null ? null : obj.toString();
    }
    private List<LoanResponseFetchDetDto> mapLoanDetailList(List<Object[]> rows) {
        return rows.stream().map(row -> {
            LoanResponseFetchDetDto d = new LoanResponseFetchDetDto();
            d.setEMP_CODE((String) row[0]);
            d.setCOMP_CODE((String) row[1]);
            d.setCENT_CODE((String) row[2]);
            d.setROW_ID((String) row[3]);
            d.setAPP_NO((String) row[4]);
            d.setAPP_DATE((String) row[5]);
            d.setLOAN_TYPE((String) row[6]);
            d.setLOAN_REASON((String) row[7]);
            d.setLOAN_TYPE_NAME((String) row[8]);
            d.setLOAN_AMT(toStringVal(row[9]));
            d.setINST_AMT(toStringVal(row[10]));
            d.setNO_OF_INST(toStringVal(row[11]));
            d.setREMARKS((String) row[12]);
            d.setLOAN_START_DATE((String) row[13]);
            d.setSANCTION_AMT(toStringVal(row[14]));
            d.setSANCTION_NO_OF_INST(toStringVal(row[15]));
            d.setSANCTION_INST_AMT(toStringVal(row[16]));
            d.setSTATUSNAME((String) row[17]);
            d.setStatus((String) row[18]);
           d.setStatus1 ((String)row[19]);
            d.setStatus2((String) row[20]);
            d.setApproval((String) row[21]);
            d.setApproval1((String) row[22]);
            d.setApproval2((String) row[23]);
            d.setApprovalCancel((String) row[24]);
            d.setApprovalCancel1((String) row[25]);
            d.setApprovalCancel2((String) row[26]);
            d.setApprovalDate((String) row[27]);
            d.setApprovalDate1((String) row[28]);
            d.setApprovalDate2((String) row[29]);
            return d;
        }).toList();
    }




    private List<LoanResponseFetchDetDto> mapLoanDetailListApproval(List<Object[]> rows) {
        return rows.stream().map(row -> {
            LoanResponseFetchDetDto d = new LoanResponseFetchDetDto();
            d.setEMP_CODE((String) row[0]);
            d.setEMP_NAME((String) row[1]);
            d.setAPP_DATE((String) row[2]);
            d.setAPP_NO((String) row[3]);
            d.setLOAN_TYPE((String) row[4]);
            d.setLOAN_TYPE_NAME((String) row[5]);
            d.setLOAN_AMT(toStringVal(row[6]));
            d.setINST_AMT(toStringVal(row[7]));
            d.setNO_OF_INST(toStringVal(row[8]));
            d.setLOAN_START_DATE((String) row[9]);
            d.setLOAN_REASON((String) row[10]);
            d.setSANCTION_AMT(toStringVal(row[11]));
            d.setSANCTION_INST_AMT(toStringVal(row[12]));
            d.setSANCTION_NO_OF_INST(toStringVal(row[13]));
//            d.setStatus((String) row[15]);
//            d.setStatus1((String) row[16]);
//            d.setStatus2((String) row[17]);
//            d.setApproval((String) row[18]);
//            d.setApproval1((String) row[19]);
//            d.setApproval2((String) row[21]);
//            d.setApprovalDate((String) row[25]);
//            d.setApprovalDate1((String) row[26]);
//            d.setApprovalDate2((String) row[27]);


            return d;
        }).toList();
    }
    private String str(Object value) {
        return value == null ? null : value.toString().trim();
    }
    private List<CheckFormPermissionLoanResponseDto> mapCheckFromPermission(List<Object[]> rows) {
        return rows.stream().map(row -> {
            CheckFormPermissionLoanResponseDto d = new CheckFormPermissionLoanResponseDto();

            String[] values = {str(row[0]), str(row[1]), str(row[2])};
            String[] names  = {"Reporting", "REPORTING_DEPT", "HR"};

            for (int i = 0; i < values.length; i++) {

                if ("Y".equals(values[i])) {
                    CheckFormPermissionLoanResponseDto.PermissionData pd =new CheckFormPermissionLoanResponseDto.PermissionData();

                    pd.setReporting(values[i]);              // always "Y"
                    pd.setReportingValue(String.valueOf(i)); // 0,1,2
                    pd.setReportingName(names[i]);           // Reporting, SEQ1, SEQ2

                    d.getReportingData().add(pd);
                }
            }
            return d;
        }).collect(Collectors.toList());
    }


    private String executeGenerateLoanNo(LoanRequestDto dto) {
        StoredProcedureQuery query = em.createStoredProcedureQuery(ApiParams.PAY_GENLOANNO);
        registerInParam(query, ApiParams.PCOMP_CODE, String.class, safeUpper(dto.getCompCode()));
        registerInParam(query, ApiParams.PCENT_CODE, String.class, safeUpper(dto.getCentCode()));
        registerInParam(query,  ApiParams.loanDate, String.class, safeUpper(dto.getLoanDate()));
        registerInParam(query,  ApiParams.PEXTRA1, String.class, safe(dto.getExtra1()));
        registerInParam(query, ApiParams.PEXTRA2, String.class, safe(dto.getExtra2()));
        registerInParam(query, ApiParams.PEXTRA3, String.class, safe(dto.getExtra3()));

        try {
            return (String) query.getSingleResult();
        } catch (NoResultException e) {
            log.warn("PAY_GENLOANNO returned no result");
            return null;
        }
    }

    private int executeSaveLoanHdr(LoanSaveHdrRequestDto dto) {
        StoredProcedureQuery query = em.createStoredProcedureQuery(ApiParams.PAY_LOAN_HDR_INSUPDDEL);
        registerInParam(query, ApiParams.PCOMP_CODE, String.class, safeUpper(dto.getCompCode()));
        registerInParam(query, ApiParams.PCENT_CODE, String.class, safeUpper(dto.getCentCode()));
        registerInParam(query, ApiParams.PBRANCH_CODE, String.class, safeUpper(dto.getBranchCode()));
        registerInParam(query, ApiParams.PAPP_DATE, String.class, safeUpper(dto.getAppDate()));
        registerInParam(query, ApiParams.PAPP_NO, String.class, safeUpper(dto.getLoanNo()));
        registerInParam(query, ApiParams.PEMP_CODE, String.class, safeUpper(dto.getEmpCode()));
        registerInParam(query, ApiParams.PLOAN_REASON, String.class, safeUpper(dto.getLoanReason()));
        registerInParam(query, ApiParams.PIP, String.class, safe(dto.getIp()));
        registerInParam(query, ApiParams.PUSERID, String.class, safeUpper(dto.getUserId()));
        registerInParam(query, ApiParams.TYPE, String.class, safeUpper(dto.getType()));

        return query.executeUpdate();
    }
    private int executeSaveLoanHDet(LoanSaveDetRequestDto dto) {
        StoredProcedureQuery query = em.createStoredProcedureQuery(ApiParams.PAY_LOAN_DET_INSUPDDEL);
        registerInParam(query, ApiParams.PCOMP_CODE, String.class, safeUpper(dto.getCompCode()));
        registerInParam(query,  ApiParams.PCENT_CODE, String.class, safeUpper(dto.getCentCode()));
        registerInParam(query,  ApiParams.PBRANCH_CODE, String.class, safeUpper(dto.getBranchCode()));
        registerInParam(query,  ApiParams.PAPP_DATE, String.class, safeUpper(dto.getAppDate()));
        registerInParam(query,  ApiParams.PAPP_NO, String.class, safeUpper(dto.getLoanNo()));
        registerInParam(query, ApiParams.PEMP_CODE, String.class, safeUpper(dto.getEmpCode()));
        registerInParam(query, ApiParams.PROW_ID, String.class, safeUpper(dto.getRowId()));
        registerInParam(query, ApiParams.PLOAN_TYPE, String.class, safeUpper(dto.getLoanType()));
        registerInParam(query, ApiParams.PLOAN_AMT, String.class, safeUpper(dto.getLoanAmt()));
        registerInParam(query, ApiParams.PINST_AMT, String.class, safeUpper(dto.getInstAmt()));
        registerInParam(query, ApiParams.PNO_OF_INST, String.class, safeUpper(dto.getNoOfInst()));
        registerInParam(query, ApiParams.PLOANSTARTDATE, String.class, safeUpper(dto.getLoanStartDate()));
        registerInParam(query, ApiParams.PREMARKS, String.class, safeUpper(dto.getLoanReason()));
        registerInParam(query, ApiParams.PSTATUS, String.class, safeUpper(dto.getStatus()));
        registerInParam(query, ApiParams.PIP, String.class, safe(dto.getIp()));
        registerInParam(query, ApiParams.PUSER, String.class, safeUpper(dto.getUserId()));
        registerInParam(query, ApiParams.TYPE, String.class, safeUpper(dto.getType()));
        return query.executeUpdate();
    }
    private int executeApprovalSaveLoan(LoanApprovalRequestDto dto) {
        StoredProcedureQuery query = em.createStoredProcedureQuery(ApiParams.approvalLoanSave);
        registerInParam(query, ApiParams.PCOMP_CODE, String.class, safeUpper(dto.getPcompCode()));
        registerInParam(query, ApiParams.PCENT_CODE, String.class, safeUpper(dto.getPcentCode()));
        registerInParam(query, ApiParams.PEMP_CODE, String.class, safeUpper(dto.getPempCode()));
        registerInParam(query, ApiParams.PAPP_NO, String.class, safeUpper(dto.getPappNo()));
        registerInParam(query, ApiParams.PAPP_DATE, String.class, safeUpper(dto.getPappDate()));
        registerInParam(query, ApiParams.PLOAN_TYPE, String.class, safeUpper(dto.getPloanType()));
        registerInParam(query, ApiParams.PAPP_FLAG, String.class, safeUpper(dto.getPappFlag()));
        registerInParam(query, ApiParams.PAPP_USER, String.class, safe(dto.getPappUser()));
        registerInParam(query, ApiParams.PAPP_REMARKS, String.class, safeUpper(dto.getPappRemarks()));
        registerInParam(query, ApiParams.PAPP_TYPE, String.class, safeUpper(dto.getPappType()));
        registerInParam(query, ApiParams.PSANCTION_AMT, String.class, safeUpper(dto.getPsanctionAmt()));
        registerInParam(query, ApiParams.PSANCTION_INST, String.class, safeUpper(dto.getPsanctionInst()));
        registerInParam(query, ApiParams.PSAN_NO_INST, String.class, safeUpper(dto.getPsanNoInst()));
        registerInParam(query, ApiParams.PEXTRA1, String.class, safe(dto.getPextra1()));
        registerInParam(query, ApiParams.PEXTRA2, String.class, safeUpper(dto.getPextra2()));
        registerInParam(query, ApiParams.PEXTRA3, String.class, safeUpper(dto.getPextra3()));
        registerInParam(query, ApiParams.PEXTRA4, String.class, safeUpper(dto.getPextra4()));
        registerInParam(query, ApiParams.PEXTRA5, String.class, safeUpper(dto.getPextra5()));
        log.info("test"+query);
        return query.executeUpdate();
    }




    private List executeFetchLoan(LoanSaveDetRequestDto dto) {
        StoredProcedureQuery query = em.createStoredProcedureQuery(ApiParams.PAY_SELECTLOANDET);
        registerInParam(query, ApiParams.PCOMP_CODE, String.class, safeUpper(dto.getCompCode()));
        registerInParam(query,  ApiParams.PCENT_CODE, String.class, safeUpper(dto.getCentCode()));
        registerInParam(query,  ApiParams.PAPP_DATE, String.class, safeUpper(dto.getAppDate()));
        registerInParam(query,  ApiParams.PAPP_NO, String.class, safeUpper(dto.getLoanNo()));
        registerInParam(query, ApiParams.PEMP_CODE, String.class, safeUpper(dto.getEmpCode()));
        registerInParam(query, ApiParams.PDATEFORMAT, String.class, safeUpper("MM/DD/YYYY"));
        registerInParam(query, ApiParams.PEXTRA1, String.class, safeUpper(dto.getLoanAmt()));
        registerInParam(query, ApiParams.PEXTRA2, String.class, safeUpper(dto.getInstAmt()));
        registerInParam(query, ApiParams.PEXTRA3, String.class, safeUpper(dto.getExtra3()));
        registerInParam(query, ApiParams.TYPE, String.class, safeUpper(dto.getType()));
        return query.getResultList();
    }

    private List executeFetchLoanApproval(LoanFetchApprovalRequestDto dto) {
        StoredProcedureQuery query = em.createStoredProcedureQuery(ApiParams.PAY_SELECTLOANDETApproval);

        registerInParam(query, ApiParams.pcomcode, String.class, safeUpper(dto.getPcomcode()));
        registerInParam(query, ApiParams.pcentercode, String.class, safeUpper(dto.getPcentercode()));
        registerInParam(query, ApiParams.pbranchcode, String.class, safeUpper(dto.getPbranchcode()));

        // ✅ Missing parameter added
        registerInParam(query, ApiParams.pemploytype, String.class, safeUpper(dto.getPemploytype()));

        registerInParam(query, ApiParams.pgroup, String.class, safeUpper(dto.getPgroup()));
        registerInParam(query, ApiParams.pdeptcode, String.class, safeUpper(dto.getPdeptcode()));

        registerInParam(query, ApiParams.pempcode, String.class, safeUpper(dto.getPempcode()));
        registerInParam(query, ApiParams.pfromdate, String.class, dto.getPfromdate());
        registerInParam(query, ApiParams.ptodate, String.class, dto.getPtodate());
        registerInParam(query, ApiParams.pdateformat, String.class, "MM/DD/YYYY");

        registerInParam(query, ApiParams.pextra, String.class, dto.getPextra());
        registerInParam(query, ApiParams.pextra1, String.class, dto.getPextra1());
        registerInParam(query, ApiParams.pextra2, String.class, dto.getPextra2());
        registerInParam(query, ApiParams.pextra3, String.class, dto.getPextra3());
        registerInParam(query, ApiParams.pextra4, String.class, dto.getPextra4());

        return query.getResultList();
    }

    private List checkFromPermission(CheckFormPermissionLoanRequestDto dto) {
        StoredProcedureQuery query = em.createStoredProcedureQuery(ApiParams.checkFromPermissionLoan);
        registerInParam(query, ApiParams.PCOMP_CODE, String.class, safeUpper(dto.getPcompCode()));
        registerInParam(query, ApiParams.PCENT_CODE, String.class, safeUpper(dto.getPcentCode()));
        registerInParam(query, ApiParams.PEMP_CODE, String.class, safeUpper(dto.getPempCode()));

        return query.getResultList();
    }










}