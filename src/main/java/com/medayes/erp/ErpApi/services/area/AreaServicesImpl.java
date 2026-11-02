package com.medayes.erp.ErpApi.services.area;

import com.medayes.erp.ErpApi.dto.response.area.DistrictMastDTO;
import com.medayes.erp.ErpApi.dto.response.area.StateMastDTO;
import com.medayes.erp.ErpApi.dto.response.area.TaluksMastDTO;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleResponseDto;
import com.medayes.erp.ErpApi.entity.DistrictMastEntity;
import com.medayes.erp.ErpApi.entity.StateMastEntity;
import com.medayes.erp.ErpApi.entity.TalukaMastEntity;
import com.medayes.erp.ErpApi.exeption.BusinessException;
import com.medayes.erp.ErpApi.repository.area.DistrictMastRepository;
import com.medayes.erp.ErpApi.repository.area.StateMastRepository;
import com.medayes.erp.ErpApi.repository.area.TalukaMastRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AreaServicesImpl implements  AreaServices {
    private final StateMastRepository stateMastRepository;
    private final DistrictMastRepository districtMastRepository;
    private  final TalukaMastRepository talukaMastRepository;
    private final ModelMapper mapper;
    @Override
    public TaskMastResponsibleResponseDto<?> getStateMast() {
     List<StateMastEntity>stateMastEntities=  stateMastRepository.findAll(Sort.by(Sort.Direction.ASC,"stateName"));
     if(stateMastEntities.isEmpty())
     {
          throw  new BusinessException("No dta found");
     }
     List<StateMastDTO> stateMastDTOS=stateMastEntities.stream()
             .map(stateMastEntity -> mapper.map(stateMastEntity, StateMastDTO.class) ).toList();

        return new TaskMastResponsibleResponseDto<>(
                "true",
                "Data fetch successful",
                HttpStatus.OK.value(),
                stateMastDTOS
        );
    }

    @Override
    public TaskMastResponsibleResponseDto<?> getDistrictMast(String stateCode) {
        List<DistrictMastEntity>districtMastEntities=  districtMastRepository.findByStateCodeOrderByDistNameAsc(stateCode);
        if(districtMastEntities.isEmpty())
        {
            throw  new BusinessException("No dta found");
        }
        List<DistrictMastDTO> districtMastDTOS=districtMastEntities.stream()
                .map(district -> mapper.map(district, DistrictMastDTO.class) ).toList();

        return new TaskMastResponsibleResponseDto<>(
                "true",
                "Data fetch successful",
                HttpStatus.OK.value(),
                districtMastDTOS
        );
    }

    @Override
    public TaskMastResponsibleResponseDto<?> getTaluka(String distCode) {
        List<TalukaMastEntity> entity=  talukaMastRepository.findByDistCodeOrderByTalukaNameAsc(distCode);
        DistrictMastEntity districtMast= districtMastRepository.findByDistCode(distCode);
        if(entity.isEmpty())
        {
            throw  new BusinessException("No dta found");
        }
        List<TaluksMastDTO> districtMastDTOS=entity.stream()
                .map(entitys ->
                        {
                            TaluksMastDTO dto=      mapper.map(entitys, TaluksMastDTO.class);
                            dto.setStateCode(districtMast.getStateCode());
                            return dto;
                        }
                        ).toList();

        return new TaskMastResponsibleResponseDto<>(
                "true",
                "Data fetch successful",
                HttpStatus.OK.value(),
                districtMastDTOS
        );
    }
}
