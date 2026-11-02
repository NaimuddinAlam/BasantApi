package com.medayes.erp.ErpApi.services.graph;

import com.medayes.erp.ErpApi.dto.request.AccountAnalysisRequestDto;
import com.medayes.erp.ErpApi.dto.response.AccountAnalysisResponseDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleResponseDto;
import com.medayes.erp.ErpApi.repository.graph.AccountAnalysisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountAnalysisServices {

    private final AccountAnalysisRepository analysisRepository;

    public TaskMastResponsibleResponseDto<List<AccountAnalysisResponseDto>> accountAnalysis(
            AccountAnalysisRequestDto dto) {

        try {

            List<AccountAnalysisResponseDto> response =
                    analysisRepository.graphResponse(dto);

            if(response.isEmpty())
            {
                return new TaskMastResponsibleResponseDto<>(
                        "false",
                        "not data found",
                        HttpStatus.OK.value(),
                        null
                );
            }

            return new TaskMastResponsibleResponseDto<>(
                    "true",
                    "Data fetched successfully.",
                    HttpStatus.OK.value(),
                    response
            );

        } catch (Exception ex) {

            log.error("Error while fetching account analysis", ex);

            return new TaskMastResponsibleResponseDto<>(
                    "false",
                    "Failed to fetch account analysis.",
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    null
            );
        }
    }
}