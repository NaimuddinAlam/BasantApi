package com.medayes.erp.ErpApi.services.servicesImpl.mainTask;

import com.medayes.erp.ErpApi.dto.request.task.TaskMastResponsibleRequestDto;

import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponseListDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleResponseDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleTempResponseDto;

import com.medayes.erp.ErpApi.repository.mainTask.TaskMastRepository;
import com.medayes.erp.ErpApi.repository.mainTask.TaskMastResponsibleMainRepositoryImpl;

import com.medayes.erp.ErpApi.services.mainTask.TaskMastResponsibleMainServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class TaskMastResponsibleMainServicesImpl implements TaskMastResponsibleMainServices {

    @Autowired
    private TaskMastResponsibleMainRepositoryImpl tempRepository;
    @Autowired
    private TaskMastRepository taskMastRepository;


    @Override
    @Transactional
    public TaskMastResponsibleTempResponseDto saveMainResponsible(TaskMastResponsibleRequestDto req) {

        try {

            Map<String, Object> result = tempRepository.executeMain(req);

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
    public TaskMastResponsibleResponseDto<List<TaskMastResponseListDto>>
    fetchMain(TaskMastResponsibleRequestDto request) {

        try {
           List<TaskMastResponseListDto> result = taskMastRepository.fetch(request);

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
                    "Data fetch successful",
                    HttpStatus.OK.value(),
                    result
           );

        }  catch (NullPointerException ex) {
           // MISSING REQUIRED INPUTS
            return new TaskMastResponsibleResponseDto<>(
                   "false",
                   "Null Value Error: " + ex.getMessage(),
                   HttpStatus.BAD_REQUEST.value(),null);

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
    public TaskMastResponsibleTempResponseDto deleteMain(TaskMastResponsibleRequestDto taskMastResponsibleTempRequestDto) {
        try {
            Map<String, Object> result = tempRepository.deleteMain(taskMastResponsibleTempRequestDto);


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




