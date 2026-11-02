package com.medayes.erp.ErpApi.services.taskSchedule.impl;

import com.medayes.erp.ErpApi.dto.request.indent.RequirementSlipRequestDto;
import com.medayes.erp.ErpApi.dto.request.task.*;
import com.medayes.erp.ErpApi.dto.response.task.*;
import com.medayes.erp.ErpApi.exeption.BadRequestException;
import com.medayes.erp.ErpApi.exeption.DatabaseException;
import com.medayes.erp.ErpApi.repository.taskSchdule.BindTaskLocationRepository;
import com.medayes.erp.ErpApi.repository.taskSchdule.TaskOptionRepository;
import com.medayes.erp.ErpApi.repository.taskSchdule.TaskReschedulerRepositoryImpl;
import com.medayes.erp.ErpApi.repository.taskSchdule.TaskSchedulerRepositoryImpl;
import com.medayes.erp.ErpApi.repository.taskSchdule.taskAssign.CheckResponsibleRepository;
import com.medayes.erp.ErpApi.repository.taskSchdule.taskAssign.CmsTaskScheduleRepositoryImpl;
import com.medayes.erp.ErpApi.repository.tempTask.ResponsiblePersonRepository;
import com.medayes.erp.ErpApi.services.taskSchedule.TaskScheduleServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class TaskScheduleServicesImpl implements TaskScheduleServices {
    @Autowired
    private TaskSchedulerRepositoryImpl taskSchedulerRepository;
    @Autowired
    private BindTaskLocationRepository bindTaskLocationRepository;
    @Autowired
    private TaskReschedulerRepositoryImpl taskReschedulerRepository;
    @Autowired
    private CmsTaskScheduleRepositoryImpl cmsTaskScheduleRepository;
    @Autowired
    private ResponsiblePersonRepository repository;
    @Autowired
    private TaskOptionRepository taskOptionRepository;
    @Autowired
    private CheckResponsibleRepository checkResponsibleRepository;
    @Override
    public TaskMastResponsibleResponseDto<List<TaskSchedulerResponseDto>> bindTaskMast(TaskSchedulerRequestDto schedulerRequestDto) {

        try {
            List<TaskSchedulerResponseDto> result = taskSchedulerRepository.bindTaskMast(schedulerRequestDto);


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

        } catch (NullPointerException ex) {
            // MISSING REQUIRED INPUTS
            return new TaskMastResponsibleResponseDto<>(
                    "false",
                    "Null Value Error: " + ex.getMessage(),
                    HttpStatus.BAD_REQUEST.value(), null);

        } catch (IllegalArgumentException ex) {
            // INVALID INPUT FORMAT
            return new TaskMastResponsibleResponseDto<>(
                    "false",
                    "Invalid Input: " + ex.getMessage(),
                    HttpStatus.BAD_REQUEST.value(), null
            );
        } catch (Exception ex) {
            return new TaskMastResponsibleResponseDto<>(
                    "false",
                    "Unexpected Error: " + ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    null
            );
        }
    }

    @Override
    public TaskMastResponsibleResponseDto<List<TaskLocationResponseDto>> bindLocation(TaskSchedulerRequestDto schedulerRequestDto) {
        try {
            List<TaskLocationResponseDto> result = bindTaskLocationRepository.bindLocation(schedulerRequestDto);


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

        } catch (NullPointerException ex) {
            // MISSING REQUIRED INPUTS
            return new TaskMastResponsibleResponseDto<>(
                    "false",
                    "Null Value Error: " + ex.getMessage(),
                    HttpStatus.BAD_REQUEST.value(), null);

        } catch (IllegalArgumentException ex) {
            // INVALID INPUT FORMAT
            return new TaskMastResponsibleResponseDto<>(
                    "false",
                    "Invalid Input: " + ex.getMessage(),
                    HttpStatus.BAD_REQUEST.value(), null
            );
        } catch (Exception ex) {
            return new TaskMastResponsibleResponseDto<>(
                    "false",
                    "Unexpected Error: " + ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    null
            );
        }
    }

    @Override
    public TaskMastResponsibleResponseDto<List<TaskMastResponsibleTempResponseListDto>> fetchResponsible(TaskScheduleReponsibalRequestDto taskMastResponsibleTempRequestDto) {
        try {
            List<TaskMastResponsibleTempResponseListDto> result = taskReschedulerRepository.fetchResponsiblePerson(taskMastResponsibleTempRequestDto);


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

        } catch (NullPointerException ex) {
            // MISSING REQUIRED INPUTS
            return new TaskMastResponsibleResponseDto<>(
                    "false",
                    "Null Value Error: " + ex.getMessage(),
                    HttpStatus.BAD_REQUEST.value(), null);

        } catch (IllegalArgumentException ex) {
            // INVALID INPUT FORMAT
            return new TaskMastResponsibleResponseDto<>(
                    "false",
                    "Invalid Input: " + ex.getMessage(),
                    HttpStatus.BAD_REQUEST.value(), null
            );
        } catch (Exception ex) {
            return new TaskMastResponsibleResponseDto<>(
                    "false",
                    "Unexpected Error: " + ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    null
            );
        }
    }

    @Override
    public TaskMastResponsibleTempResponseDto saveResponsible(TaskScheduleReponsibalRequestDto taskMastResponsibleTempRequestDto) {

        try {

            Map<String, Object> result = taskReschedulerRepository.addResponsiblePerson(taskMastResponsibleTempRequestDto);

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
    public TaskMastResponsibleTempResponseDto deleteResponsible(TaskScheduleReponsibalRequestDto taskMastResponsibleTempRequestDto) {
        try {
            Map<String, Object> result = taskReschedulerRepository.deleteResponsiblePerson(taskMastResponsibleTempRequestDto);


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

    @Override
    public TaskMastResponsibleResponseDto<List<CmsTaskScheduleResponseDto>> fetchCmsSchedule(CmsTaskScheduleRequestDto task) {
        try {
            List<CmsTaskScheduleResponseDto> result = cmsTaskScheduleRepository.fetchCmsTaskSchedule(task);


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

        } catch (NullPointerException ex) {
            // MISSING REQUIRED INPUTS
            return new TaskMastResponsibleResponseDto<>(
                    "false",
                    "Null Value Error: " + ex.getMessage(),
                    HttpStatus.BAD_REQUEST.value(), null);

        } catch (IllegalArgumentException ex) {
            // INVALID INPUT FORMAT
            return new TaskMastResponsibleResponseDto<>(
                    "false",
                    "Invalid Input: " + ex.getMessage(),
                    HttpStatus.BAD_REQUEST.value(), null
            );
        } catch (Exception ex) {
            return new TaskMastResponsibleResponseDto<>(
                    "false",
                    "Unexpected Error: " + ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    null
            );
        }
    }

    @Override
    public TaskMastResponsibleTempResponseDto saveCmsSchedule(CmsTaskScheduleRequestDto task) {
        try {

            Map<String, Object> result = cmsTaskScheduleRepository.saveCmsTaskSchedule(task);

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
    public TaskMastResponsibleResponseDto<List<ResponsiblePersonResponseDto>> fetchResponsiblePerson(ResponsiblePersonRequestDto requestDto) {
        try {
            List<ResponsiblePersonResponseDto> result = repository.fetchResponsiblePerson(requestDto);


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

        } catch (NullPointerException ex) {
            // MISSING REQUIRED INPUTS
            return new TaskMastResponsibleResponseDto<>(
                    "false",
                    "Null Value Error: " + ex.getMessage(),
                    HttpStatus.BAD_REQUEST.value(), null);

        } catch (IllegalArgumentException ex) {
            // INVALID INPUT FORMAT
            return new TaskMastResponsibleResponseDto<>(
                    "false",
                    "Invalid Input: " + ex.getMessage(),
                    HttpStatus.BAD_REQUEST.value(), null
            );
        } catch (Exception ex) {
            return new TaskMastResponsibleResponseDto<>(
                    "false",
                    "Unexpected Error: " + ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    null
            );
        }
    }

    @Override
    public TaskMastResponsibleResponseDto<List<TaskOptionResponseDto>> fetchCmsOption(CmsTaskScheduleRequestDto task) {
        try {
            List<TaskOptionResponseDto> result = taskOptionRepository.fetchOption(task);


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

        } catch (NullPointerException ex) {
            // MISSING REQUIRED INPUTS
            return new TaskMastResponsibleResponseDto<>(
                    "false",
                    "Null Value Error: " + ex.getMessage(),
                    HttpStatus.BAD_REQUEST.value(), null);

        } catch (IllegalArgumentException ex) {
            // INVALID INPUT FORMAT
            return new TaskMastResponsibleResponseDto<>(
                    "false",
                    "Invalid Input: " + ex.getMessage(),
                    HttpStatus.BAD_REQUEST.value(), null
            );
        } catch (Exception ex) {
            return new TaskMastResponsibleResponseDto<>(
                    "false",
                    "Unexpected Error: " + ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    null
            );
        }


    }

    @Override
    public TaskMastResponsibleResponseDto<CheckResponsibleResponseDto> fetchCheckFrom(RequirementSlipRequestDto task) {

        try {
            CheckResponsibleResponseDto result= checkResponsibleRepository.fetch(task);
            if (result.res()==null || result.res().isEmpty())
            {
                throw new BadRequestException("No Data Found");
            }
             return  new TaskMastResponsibleResponseDto<>("true","Data fetch successful",HttpStatus.OK.value(),result);

        }catch (DataAccessException ex)
        {
            throw  new DatabaseException("Database error occurred:"+ ex.getMostSpecificCause().getMessage(),ex);
        }

    }


}
