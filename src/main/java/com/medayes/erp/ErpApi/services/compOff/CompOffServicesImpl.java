package com.medayes.erp.ErpApi.services.compOff;


import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.medayes.erp.ErpApi.dto.request.compOff.CompOffApprovalByDTO;
import com.medayes.erp.ErpApi.dto.request.compOff.CompOffApprovalDTO;
import com.medayes.erp.ErpApi.dto.request.compOff.CompOffRequestDTO;
import com.medayes.erp.ErpApi.dto.request.compOff.CompOffRequestSaveDTO;
import com.medayes.erp.ErpApi.dto.response.APIResponse;
import com.medayes.erp.ErpApi.dto.response.ResponseUtils;
import com.medayes.erp.ErpApi.dto.response.compOff.CompOffDTO;
import com.medayes.erp.ErpApi.dto.response.compOff.CompOffNumberResponseDTO;
import com.medayes.erp.ErpApi.entity.AttendanceEntity;
import com.medayes.erp.ErpApi.entity.CompOffEntity;
import com.medayes.erp.ErpApi.exeption.ResourceNotFoundException;
import com.medayes.erp.ErpApi.repository.compOff.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;



import java.sql.Timestamp;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CompOffServicesImpl implements CompOffServices{
    private  final CompOffRepository compOffRepository;
    private  final PayConfigRepository config;
    private  final ModelMapper mapper;
    private  final CompOffNumberRepository compOffNumberRepository;
    private  final UserMastRepository userMastRepository;
    private  final AttendanceRepository attendanceRepository;
    @Override
    public APIResponse<CompOffDTO> fetchCompOff(CompOffRequestDTO comp) {
      List<CompOffEntity> compOffEntityList=  compOffRepository.findByEmpCode(comp.getEmpCode());

      if(compOffEntityList.isEmpty())
      {
          return ResponseUtils.error("No data found", HttpStatus.NOT_FOUND.value());
      }

        String chAvailPeriod =
                 config.findTopBy().getChAvailDay();

      List<CompOffDTO> compOffDTOS=compOffEntityList
              .stream()
              .filter(compOff->
                      compOff.getApprovalFlag() != null &&
                              compOff.getApprovalFlag().trim().equals("Y"))
              .map(compOffEntity ->
                      {
                          CompOffDTO dto=      mapper.map(compOffEntity,CompOffDTO.class);
                          dto.setExpDate(expDate(compOffEntity.getCompHoliday(),chAvailPeriod)


                          );
                          return dto;
                      })


              .toList();


        return ResponseUtils.success("Data fetch successfully", compOffDTOS);
    }

    @Override
    @Transactional
    public APIResponse<?> saveCompOff(CompOffRequestSaveDTO comp) {
        try {

            List<CompOffNumberResponseDTO> res=compOffNumberRepository.fetchGenCode(comp);
            comp.setCompReqNo(res.get(0).getGenCode());
            CompOffEntity compOffEntity = mapper.map(comp, CompOffEntity.class);
            compOffEntity.setCreditDate(comp.getCompHoliday());
             compOffRepository.save(compOffEntity);

            try {
                String token = userMastRepository.token(comp.getEmpCode());

                if (token != null && !token.isBlank()) {
                    sendPushMessage(token, comp);
                }
            } catch (Exception ex) {
               // log.error("Notification failed", ex);
            }
            return ResponseUtils.success("Data save successfully", null);




        }catch (Exception e)
        {
            return ResponseUtils.error(" Failed to save data", HttpStatus.INTERNAL_SERVER_ERROR.value());

        }


    }





    @Override
    public APIResponse<CompOffApprovalDTO> fetchCompOffApproval(CompOffRequestDTO comp) {

        List<CompOffEntity> entities =
                compOffRepository.findAll(Sort.by(Sort.Direction.DESC, "createDate"));

        if (entities.isEmpty()) {
            return ResponseUtils.error("No data found", HttpStatus.NOT_FOUND.value());
        }

        List<CompOffApprovalDTO> dtos = entities.stream()
                .map(this::mapToApprovalDTO)
                .toList();

        return ResponseUtils.success("Data fetch successfully", dtos);
    }

    private CompOffApprovalDTO mapToApprovalDTO(CompOffEntity entity) {

        CompOffApprovalDTO dto = mapper.map(entity, CompOffApprovalDTO.class);

        dto.setStatus(getStatus(dto));
        dto.setFullHalf("F".equalsIgnoreCase(dto.getFullHalf())
                ? "Half Day"
                : "Full Day");

        dto.setEmpName(userMastRepository.getEmployeeName(dto.getEmpCode()));

        setAttendanceInfo(dto);

        return dto;
    }

    private String getStatus(CompOffApprovalDTO dto) {

        if ("Y".equalsIgnoreCase(dto.getApprovalFlag())) {
            return "Approved";
        }

        if ("Y".equalsIgnoreCase(dto.getCancelFlag())) {
            return "Canceled";
        }

        return "Pending";
    }

    private void setAttendanceInfo(CompOffApprovalDTO dto) {

        try {

            List<Optional<AttendanceEntity>> attendance =
                    getAttendanceTime(dto.getEmpCode(), dto.getCompHoliday());

            if (attendance == null || attendance.size() < 2) {
                return;
            }

            String inTime = attendance.get(0)
                    .map(AttendanceEntity::getTime)
                    .orElse(null);

            String outTime = attendance.get(1)
                    .map(AttendanceEntity::getTime)
                    .orElse(null);

            dto.setInTime(inTime);
            dto.setOutTime(outTime);

            if (inTime != null && outTime != null) {
                dto.setTotal_time(totalTime(inTime, outTime));
            }

        } catch (Exception e) {
            System.err.println("Attendance Error : " + e.getMessage());
        }
    }

    @Override
    public APIResponse<CompOffApprovalDTO> fetchCompOffRequest(CompOffRequestDTO comp) {
        List<CompOffEntity> compOffEntityList=  compOffRepository.findByEmpCodeOrderByCreateDateDesc(comp.getEmpCode());

        if(compOffEntityList.isEmpty())
        {
            return ResponseUtils.error("No data found", HttpStatus.NOT_FOUND.value());
        }



        List<CompOffApprovalDTO> compOffDTOS=compOffEntityList
                .stream()


                .map(compOffEntity ->
                {
                    CompOffApprovalDTO dto=      mapper.map(compOffEntity,CompOffApprovalDTO.class);
                    dto.setStatus(
                            dto.getApprovalFlag() != null &&
                                    !dto.getApprovalFlag().isBlank()
                                    ? "Approved"
                                    : dto.getCancelFlag() != null &&
                                    !dto.getCancelFlag().isBlank()
                                    ? "Canceled":
                                    "Pending"
                    );



                    dto.setFullHalf(dto.getFullHalf()!=null && !dto.getFullHalf().isBlank() && dto.getFullHalf().equals("F")
                            ?"Half Day":"Full Day");

                    dto.setEmpName(userMastRepository.getEmployeeName(dto.getEmpCode()));



                    return dto;
                })


                .toList();


        return ResponseUtils.success("Data fetch successfully", compOffDTOS);
    }

    @Override
    public APIResponse<?> updateCompOff(CompOffApprovalByDTO comp) {
        try {

            CompOffEntity compOffEntity = compOffRepository
                    .findByCompReqNo(comp.getCompReqNo())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Comp Off Request No Not Found: " + comp.getCompReqNo()));

            // Approval Details
            compOffEntity.setApprovalDate(comp.getApprovalDate());
            compOffEntity.setApprovalFlag(comp.getApprovalFlag());
            compOffEntity.setApprovalRemark(comp.getApprovalRemark());
            compOffEntity.setApprovalUser(comp.getApprovalRemark());

            // Cancel Details
            compOffEntity.setCancelUser(comp.getCancelUser());
            compOffEntity.setCancelFlag(comp.getCancelFlag());
            compOffEntity.setCancelRemarks(comp.getCancelRemark());
            compOffEntity.setCancelDate(comp.getCancelDate());

            compOffRepository.save(compOffEntity);

            try {
                String token = compOffRepository.token(comp.getCompReqNo());

                if (token != null && !token.isBlank()) {



                        if ("Y".equalsIgnoreCase(comp.getApprovalFlag())) {
                            sendPushMessageApproval(
                                    token,
                                    "Comp Off Approved",
                                    "Your Comp Off request has been approved."
                            );
                        } else if ("Y".equalsIgnoreCase(comp.getCancelFlag())) {
                            sendPushMessageApproval(
                                    token,
                                    "Comp Off Rejected",
                                    "Your Comp Off request has been rejected."
                            );
                        }


                }
            } catch (Exception ex) {
                // log.error("Notification failed", ex);
            }
            return ResponseUtils.success("Data updated successfully", null);

        } catch (ResourceNotFoundException e) {

            return ResponseUtils.error(e.getMessage(), HttpStatus.NOT_FOUND.value());

        } catch (Exception e) {

            e.printStackTrace(); // Log error

            return ResponseUtils.error(
                    "Failed to update data: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }


    private String expDate(String date, String days) {
        Timestamp timestamp = Timestamp.valueOf(date);

        return timestamp.toLocalDateTime()
                .plusDays(Integer.parseInt(days))
                .toLocalDate()
                .toString();
    }



    private void sendPushMessage(String token, CompOffRequestSaveDTO comp) {
        try {



            String body = String.format(
                    "Comp Off Request%nEmployee Code: %s%nWork Date: %s%nRequested Date: %s%nKindly approve.",
                    comp.getEmpCode(),
                    comp.getCompHoliday(),
                    comp.getCreditDate()
            );




            Notification notification = Notification.builder()
                    .setTitle("Comp Off Request")
                    .setBody(body)
                    .build();

            Message message = Message.builder()
                    .setToken(token)
                    .setNotification(notification)
                    .build();

            String res = FirebaseMessaging.getInstance().send(message);
            System.out.println("🎉 Push Notification Sent Successfully: " + res);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ Error sending FCM Notification: " + e.getMessage());
        }
    }

    private   List<Optional<AttendanceEntity>> getAttendanceTime( String empCode,String date)
    {
      return    attendanceRepository.getTime(date,empCode);

    }

    private String totalTime(String inTime, String outTime) {

        LocalDateTime start = LocalDateTime.of(
                java.time.LocalDate.now(),
                LocalTime.parse(inTime));

        LocalDateTime end = LocalDateTime.of(
                java.time.LocalDate.now(),
                LocalTime.parse(outTime));

        if (end.isBefore(start)) {
            end = end.plusDays(1);
        }

        Duration duration = Duration.between(start, end);

        return String.format("%02d:%02d",
                duration.toHours(),
                duration.toMinutesPart());
    }
    private void sendPushMessageApproval(String token, String title, String body) {
        try {

            Notification notification = Notification.builder()
                    .setTitle(title)
                    .setBody(body)
                    .build();

            Message message = Message.builder()
                    .setToken(token)
                    .setNotification(notification)
                    .build();

            String res = FirebaseMessaging.getInstance().send(message);
            System.out.println("🎉 Push Notification Sent Successfully: " + res);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ Error sending FCM Notification: " + e.getMessage());
        }
    }
}
