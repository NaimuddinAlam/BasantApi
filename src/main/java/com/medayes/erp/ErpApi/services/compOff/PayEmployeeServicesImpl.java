package com.medayes.erp.ErpApi.services.compOff;



import com.medayes.erp.ErpApi.dto.request.compOff.CompOffRequestDTO;
import com.medayes.erp.ErpApi.dto.request.compOff.PayEmployeeDTO;
import com.medayes.erp.ErpApi.dto.response.APIResponse;
import com.medayes.erp.ErpApi.dto.response.ResponseUtils;


import com.medayes.erp.ErpApi.entity.PayEmployeeMastEntity;
import com.medayes.erp.ErpApi.entity.UserMastEntity;

import com.medayes.erp.ErpApi.repository.compOff.PayEmployeeRepository;
import com.medayes.erp.ErpApi.repository.compOff.UserMastRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PayEmployeeServicesImpl implements PayEmployeeServices{
    private  final PayEmployeeRepository compOffRepository;
    private  final UserMastRepository userMastRepository;

    private  final ModelMapper mapper;
    @Override
    public APIResponse<PayEmployeeDTO> fetchInfo(CompOffRequestDTO comp) {
        List<PayEmployeeMastEntity> compOffEntityList=  compOffRepository.findByEmpCode(comp.getEmpCode());

        if(compOffEntityList.isEmpty())
        {
            return ResponseUtils.error("No data found", HttpStatus.NOT_FOUND.value());
        }

        Optional<UserMastEntity> reportingTo =
                userMastRepository.findFirstByEmpCode(compOffEntityList.get(0).getReportingTo());

        Optional<UserMastEntity> reportingHr =
                userMastRepository.findFirstByEmpCode(compOffEntityList.get(0).getReportingHr());

        List<PayEmployeeDTO> employeeList = new ArrayList<>();

        if (reportingTo.isPresent() && reportingHr.isPresent()) {

            PayEmployeeDTO dto = new PayEmployeeDTO();

            dto.setRMobile(reportingTo.get().getMobile());
            dto.setRToken(reportingTo.get().getToken());

            dto.setHMobile(reportingHr.get().getMobile());
            dto.setHToken(reportingHr.get().getToken());

            employeeList.add(dto);
        }

        return ResponseUtils.success("Data fetch successfully", employeeList);



    }


}
