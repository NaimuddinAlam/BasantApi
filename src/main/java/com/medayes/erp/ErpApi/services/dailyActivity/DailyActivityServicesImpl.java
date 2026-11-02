package com.medayes.erp.ErpApi.services.dailyActivity;

import com.medayes.erp.ErpApi.config.ModelMapperConfig;

import com.medayes.erp.ErpApi.dto.request.TimeSheetDto;
import com.medayes.erp.ErpApi.dto.response.DailyActivityDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleResponseDto;
import com.medayes.erp.ErpApi.entity.DailyActivityEntity;
import com.medayes.erp.ErpApi.entity.PayEmpTimeSheetEntity;
import com.medayes.erp.ErpApi.repository.dailyActivity.DailyActivityRepository;
import com.medayes.erp.ErpApi.repository.dailyActivity.PayEmpTimeSheetRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DailyActivityServicesImpl  implements  DailyActivityServices{
    private final DailyActivityRepository  dailyActivityRepository;
    private final   ModelMapperConfig modelMapperConfig;
    private  final PayEmpTimeSheetRepository payEmpTimeSheetRepository;

    public DailyActivityServicesImpl(DailyActivityRepository dailyActivityRepository, ModelMapperConfig modelMapperConfig,PayEmpTimeSheetRepository payEmpTimeSheetRepository) {
        this.dailyActivityRepository = dailyActivityRepository;
        this.modelMapperConfig = modelMapperConfig;
        this.payEmpTimeSheetRepository=payEmpTimeSheetRepository;
    }



    @Override
    public TaskMastResponsibleResponseDto<?> getDailyActivity() {
        try {


            List<DailyActivityEntity> entities = dailyActivityRepository.findByStatus("AC0");
            if (entities.isEmpty()) {
                return new TaskMastResponsibleResponseDto<>(
                        "true",
                        "No data available",
                        HttpStatus.OK.value(),
                        List.of()   // empty list
                );
            }
            List<DailyActivityDto> dailyActivityDtos =
                    entities.stream()

                            .map(e -> modelMapperConfig.modelMapper().map(e, DailyActivityDto.class)).collect(Collectors.toList());

            return new TaskMastResponsibleResponseDto<>(
                    "true",
                    "Data fetch successful",
                    HttpStatus.OK.value(),
                    dailyActivityDtos
            );
        }
            catch (Exception ex) {



            return new TaskMastResponsibleResponseDto<>(
                    "false",
                    "Failed to fetch data",
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    null
            );
        }
    }

    @Override
    public TaskMastResponsibleResponseDto<?> getClientName(TimeSheetDto mobile) {
        try {


            PayEmpTimeSheetEntity entities = payEmpTimeSheetRepository.findClientNames(mobile.mobileNo(),mobile.userId());
            if (entities.getClientName().isEmpty()  ) {
                return new TaskMastResponsibleResponseDto<>(
                        "true",
                        "No data available",
                        HttpStatus.OK.value(),
                        List.of()   // empty list
                );
            }

            return new TaskMastResponsibleResponseDto<>(
                    "true",
                    "Data fetch successful",
                    HttpStatus.OK.value(),
                    entities
            );
        }
        catch (Exception ex) {



            return new TaskMastResponsibleResponseDto<>(
                    "false",
                    "Failed to fetch data",
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    null
            );
        }
    }

    @Override
    public TaskMastResponsibleResponseDto<?> getMobileNo(TimeSheetDto clientName)
    {
    try {


        PayEmpTimeSheetEntity entities = payEmpTimeSheetRepository.findMobile(clientName.mobileNo(), clientName.userId());
        if (entities.getClientName().isEmpty()  ) {
            return new TaskMastResponsibleResponseDto<>(
                    "true",
                    "No data available",
                    HttpStatus.OK.value(),
                    List.of()   // empty list
            );
        }

        return new TaskMastResponsibleResponseDto<>(
                "true",
                "Data fetch successful",
                HttpStatus.OK.value(),
                entities
        );
    }
        catch (Exception ex) {



        return new TaskMastResponsibleResponseDto<>(
                "false",
                "Failed to fetch data",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                null
        );
    }
}
}
