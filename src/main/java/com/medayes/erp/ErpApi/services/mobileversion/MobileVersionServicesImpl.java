package com.medayes.erp.ErpApi.services.mobileversion;

import com.medayes.erp.ErpApi.config.ModelMapperConfig;

import com.medayes.erp.ErpApi.dto.response.MobileVersionDTO;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleResponseDto;

import com.medayes.erp.ErpApi.entity.MobileVersionEntity;

import com.medayes.erp.ErpApi.repository.mobileVersion.MobileVersionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MobileVersionServicesImpl implements  MobileVersionServices{
    private final MobileVersionRepository mobileVersionRepository;
    private final ModelMapperConfig modelMapperConfig;
    public MobileVersionServicesImpl(MobileVersionRepository mobileVersionRepository, ModelMapperConfig modelMapperConfig) {
        this.mobileVersionRepository = mobileVersionRepository;
        this.modelMapperConfig = modelMapperConfig;
    }


    @Override
    public TaskMastResponsibleResponseDto<?> getMobileVersion() {
        try {


            List<MobileVersionEntity> entities = mobileVersionRepository.findAll();
            if (entities.isEmpty()) {
                return new TaskMastResponsibleResponseDto<>(
                        "true",
                        "No data available",
                        HttpStatus.OK.value(),
                        List.of()   // empty list
                );
            }
            List<MobileVersionDTO> mobileVersionDTOS =
                    entities.stream()

                            .map(e -> modelMapperConfig.modelMapper().map(e, MobileVersionDTO.class)).collect(Collectors.toList());

            return new TaskMastResponsibleResponseDto<>(
                    "true",
                    "Data fetch successful",
                    HttpStatus.OK.value(),
                    mobileVersionDTOS
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
