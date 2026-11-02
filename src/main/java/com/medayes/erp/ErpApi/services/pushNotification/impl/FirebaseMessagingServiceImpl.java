package com.medayes.erp.ErpApi.services.pushNotification.impl;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.medayes.erp.ErpApi.dto.request.MobileTokenDTO;


import com.medayes.erp.ErpApi.dto.response.task.SendMailResponseDto;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleResponseDto;

import com.medayes.erp.ErpApi.repository.pushNotification.MobileTokenRepository;
import com.medayes.erp.ErpApi.repository.pushNotification.PushNotificationRepository;
import com.medayes.erp.ErpApi.services.email.EmailService;

import com.medayes.erp.ErpApi.services.email.WhatsAppServices;
import com.medayes.erp.ErpApi.services.pushNotification.FirebaseMessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class FirebaseMessagingServiceImpl implements FirebaseMessagingService {

    @Autowired
    private PushNotificationRepository pushNotificationRepository;
    @Autowired
    private MobileTokenRepository mobileTokenRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private WhatsAppServices whatsAppServices;

    @Scheduled(fixedRate = 60000)
    @Override
    public void sendNotification() {
        try {
            System.out.println("Checking database for scheduled tasks...");

            List<SendMailResponseDto> result = pushNotificationRepository.fetch();

            if (result == null || result.isEmpty()) {
                System.out.println("No schedule found.");
                return;
            }

            LocalDateTime now = LocalDateTime.now();
            String currentTime = String.format("%02d:%02d",
                    now.getHour(),
                    now.getMinute());  // HH:mm format

            for (SendMailResponseDto item : result) {

                String dbTime = item.ALERT_TIME();
                String  token="";
                if(item.token()!=null) {
                    token = item.token()
                            .replace("\"", "")
                            .replace("\n", "")
                            .replace("\r", "")
                            .trim();
                }
                String emailId = item.emialId();
                String taskName = item.taskName();
                String assignedBy = item.createdBy();
                String mobileNo = item.mobileNo();
                String priority = item.taskPriority();
                String personName = item.personName();
                String remake = item.remake();
                String  deadline = item.SCHEDULE_DATE()+"("+ item.SCHEDULE_TIME()+")";


                if (dbTime == null || dbTime.isEmpty()) {
                    System.out.println("❌ Invalid DB alert time. Skipping...");
                    continue;
                }

//                if (token == null || token.isEmpty()) {
//                    System.out.println("❌ Invalid token. Skipping...");
//                    continue;
//                }

                dbTime = dbTime.trim();
                System.out.println("Comparing DB Time = " + dbTime + " with Current Time = " + currentTime);

                // MATCH EXACTLY
                if (dbTime.equals(currentTime)) {

                    System.out.println("🔥 Time matched! Sending notification to: " + token);

                   sendPushMessage(token,taskName,assignedBy,deadline,priority,remake);
                   emailService.sendEmail(emailId,taskName,assignedBy,deadline,priority, personName, remake);
                   // whatsAppServices.sendWhatsSms(mobileNo,taskName,assignedBy,deadline,priority);
                    System.out.println("🎉 Notification sent successfully.");
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error: " + ex.getMessage());
        }
    }
    @Scheduled(fixedRate = 60000)
    @Override
    public void sendNotifications() {
        {
            try {
                System.out.println("Checking database for scheduled tasks...");

                List<SendMailResponseDto> result = pushNotificationRepository.fetchs();

                if (result == null || result.isEmpty()) {
                    System.out.println("No schedule found.");
                    return;
                }

                LocalDateTime now = LocalDateTime.now();
                String currentTime = String.format("%02d:%02d",
                        now.getHour(),
                        now.getMinute());  // HH:mm format

                for (SendMailResponseDto item : result) {

                    String dbTime = item.ALERT_TIME();
                    String  token="";
                    if(item.token()!=null)
                    {
                        token  = item.token()
                                .replace("\"", "")
                                .replace("\n", "")
                                .replace("\r", "")
                                .trim();
                    }


                    String emailId = item.emialId();
                    String taskName = item.taskName();
                    String assignedBy = item.createdBy();
                    String mobileNo = item.mobileNo();
                    String priority = item.taskPriority();
                    String personName = item.personName();
                    String remake = item.remake();
                    String  deadline = item.SCHEDULE_DATE()+"("+ item.SCHEDULE_TIME()+")";


                    if (dbTime == null || dbTime.isEmpty()) {
                        System.out.println("❌ Invalid DB alert time. Skipping...");
                        continue;
                    }

//                if (token == null || token.isEmpty()) {
//                    System.out.println("❌ Invalid token. Skipping...");
//                    continue;
//                }

                    dbTime = dbTime.trim();
                    System.out.println("Comparing DB Time = " + dbTime + " with Current Time = " + currentTime);

                    // MATCH EXACTLY
                    if (dbTime.equals(currentTime)) {

                        System.out.println("🔥 Time matched! Sending notification to: " + token);

                        sendPushMessage(token,taskName,assignedBy,deadline,priority,remake);
                        //emailService.sendEmail(emailId,taskName,assignedBy,deadline,priority, personName, remake);
                        // whatsAppServices.sendWhatsSms(mobileNo,taskName,assignedBy,deadline,priority);
                        System.out.println("🎉 Notification sent successfully.");
                    }
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    @Override
    public TaskMastResponsibleResponseDto<?> sendToken(MobileTokenDTO mobileTokenDTO) {
        mobileTokenRepository.send(mobileTokenDTO);
        return new TaskMastResponsibleResponseDto<>(
                "true",
                "token insert successfully",
                HttpStatus.OK.value(),
                null
        );
    }

    private void sendPushMessage(String token, String taskName, String assignedBy, String deadline,String priority, String remarks ) {
        try {

                    String body =
                    "Task Assigned By \"" + assignedBy + "\"\n\n" +
                            "Task Details\n" +
                            "Task Name: " + taskName + "\n" +
                            "Description: " + remarks + "\n" +
                            "Priority: " + priority + "\n" +
                            "Deadline: " + deadline;


            Notification notification = Notification.builder()
                    .setTitle("New Task Assigned")
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
