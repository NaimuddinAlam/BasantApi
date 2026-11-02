package com.medayes.erp.ErpApi.services.servicesImpl.tempTask;

import com.medayes.erp.ErpApi.dto.request.task.TaskMastResponsibleTempRequestDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleResponseDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleTempResponseDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleTempResponseListDto;
import com.medayes.erp.ErpApi.repository.tempTask.TaskMastResponsibleTempRepositoryImpl;
import com.medayes.erp.ErpApi.services.tempTask.TaskMastResponsibleTempServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class TaskMastResponsibleTempServicesImpl implements TaskMastResponsibleTempServices {

    @Autowired
    private TaskMastResponsibleTempRepositoryImpl tempRepository;

    @Override
    @Transactional
    public TaskMastResponsibleTempResponseDto saveResponsible(TaskMastResponsibleTempRequestDto req) {

        try {

            Map<String, Object> result = tempRepository.execute(req);

            if (result == null || result.isEmpty()) {
                return new TaskMastResponsibleTempResponseDto(
                        "false",
                        "Stored procedure returned no data",
                        HttpStatus.INTERNAL_SERVER_ERROR.value()
                );
            }


            return new TaskMastResponsibleTempResponseDto(
                    "true",
                    "Data saved successfully",
                    HttpStatus.OK.value()
            );

        } catch (NullPointerException ex) {
            // MISSING REQUIRED INPUTS
            return new TaskMastResponsibleTempResponseDto(
                    "false",
                    "Null Value Error: " + ex.getMessage(),
                    HttpStatus.BAD_REQUEST.value()
            );

        } catch (IllegalArgumentException ex) {
            // INVALID INPUT FORMAT
            return new TaskMastResponsibleTempResponseDto(
                    "false",
                    "Invalid Input: " + ex.getMessage(),
                    HttpStatus.BAD_REQUEST.value()
            );

        } catch (Exception ex) {
            // UNKNOWN ISSUES
            return new TaskMastResponsibleTempResponseDto(
                    "false",
                    "Unexpected Error: " + ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
        }
    }

    @Override
    @Transactional
    public TaskMastResponsibleResponseDto<List<TaskMastResponsibleTempResponseListDto>>
    fetchResponsible(TaskMastResponsibleTempRequestDto request) {

        try {
            List<TaskMastResponsibleTempResponseListDto> result = tempRepository.fetch(request);

            if (result == null || result.isEmpty()) {
                return new TaskMastResponsibleResponseDto<>(
                        "false",
                        "No data found",
                        HttpStatus.OK.value(),
                        Collections.emptyList()
                );
            }

            return new TaskMastResponsibleResponseDto<>(
                    "true",
                    "Data fetched successfully",
                    HttpStatus.OK.value(),
                    result
            );

        }  catch (NullPointerException ex) {
            // MISSING REQUIRED INPUTS
            return new TaskMastResponsibleResponseDto<>(
                    "false",
                    "Null Value Error: " + ex.getMessage(),
                    HttpStatus.BAD_REQUEST.value(),null
            );

        } catch (IllegalArgumentException ex) {
            // INVALID INPUT FORMAT
            return new TaskMastResponsibleResponseDto<>(
                    "false",
                    "Invalid Input: " + ex.getMessage(),
                    HttpStatus.BAD_REQUEST.value(),null
            );

        }
        catch (Exception ex) {
            return new TaskMastResponsibleResponseDto<>(
                    "false",
                    "Unexpected Error: " + ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    null
            );
        }
    }

    @Override
    @Transactional
    public TaskMastResponsibleTempResponseDto deleteResponsible(TaskMastResponsibleTempRequestDto taskMastResponsibleTempRequestDto) {

        try {
            Map<String, Object> result = tempRepository.delete(taskMastResponsibleTempRequestDto);

            Integer deleted = (Integer) result.get("#update-count-1");

            if (deleted != null) {
                if (deleted>0) {
                    return new TaskMastResponsibleTempResponseDto(
                            "true",
                            "Deleted successfully",
                            HttpStatus.OK.value()

                    );
                } else {
                    return new TaskMastResponsibleTempResponseDto(
                            "false",
                            "Record not found for deletion",
                            HttpStatus.NOT_FOUND.value()

                    );
                }
            }

            return new TaskMastResponsibleTempResponseDto(
                    "false",
                    "Unexpected response from stored procedure",
                    HttpStatus.INTERNAL_SERVER_ERROR.value()

            );

        } catch (Exception ex) {
            return new TaskMastResponsibleTempResponseDto(
                    "false",
                    "Unexpected Error: " + ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value()

            );
        }
    }


}
