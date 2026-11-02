package com.medayes.erp.ErpApi.controller.notification;

import com.google.firebase.messaging.FirebaseMessaging;
import com.medayes.erp.ErpApi.dto.request.MobileTokenDTO;
import com.medayes.erp.ErpApi.dto.request.notification.NotificationInfoDTO;
import com.medayes.erp.ErpApi.dto.request.task.RecipientDTO;
import com.medayes.erp.ErpApi.dto.request.task.SendEmailDTO;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleResponseDto;
import com.medayes.erp.ErpApi.services.pushNotification.FirebaseMessagingService;
import com.medayes.erp.ErpApi.services.sale.HeadOfficeNotificationServices;
import com.medayes.erp.ErpApi.services.sale.SalesOrderNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/PAYROLL_APP_TEST/api/PayrollSale")
@RequiredArgsConstructor
public class MobileTokenController {

    private final FirebaseMessagingService firebaseMessagingService;

    private final SalesOrderNotificationService
            salesOrderNotificationService;

    private final HeadOfficeNotificationServices
            headOfficeNotificationServices;


    // ============================================================
    // SAVE FCM TOKEN
    // ============================================================

    @PostMapping(
            value = "/token",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<TaskMastResponsibleResponseDto<?>>
    sendToken(@RequestBody MobileTokenDTO mobileTokenDTO) {

        TaskMastResponsibleResponseDto<?> response =
                firebaseMessagingService.sendToken(
                        mobileTokenDTO
                );

        return ResponseEntity
                .status(response.code())
                .body(response);
    }


    // ============================================================
    // NORMAL NOTIFICATION
    // ============================================================

    @PostMapping(
            value = "/notificationSend",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> sendNotification(
            @RequestBody NotificationInfoDTO dto) {

        String token = cleanToken(dto.getToken());

        if (token.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body("FCM token is empty");
        }

        dto.setToken(token);

        // Your existing notification service
        // can be called here if you have one.
        sendPushMessage(dto);

        return ResponseEntity.ok("Sent");
    }


    // ============================================================
    // SALES ORDER - REPORTING MANAGER
    // ============================================================

    @PostMapping(
            value = "/notificationSendSale",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> sendNotificationSale(
            @RequestBody NotificationInfoDTO dto) {

        salesOrderNotificationService
                .sendSalesOrderNotification(dto);

        return ResponseEntity.ok(
                "Sales Order notification processed"
        );
    }


    // ============================================================
    // SALES ORDER - HEAD OFFICE
    // ============================================================

    @PostMapping(
            value = "/notificationSendSaleHeadOffice",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> sendNotificationHeadOffice(
            @RequestBody NotificationInfoDTO dto) {

        headOfficeNotificationServices
                .sendNotificationHeadOffice(dto);

        return ResponseEntity.ok(
                "Head Office notification processed"
        );
    }


    // ============================================================
    // TASK NOTIFICATION
    // ============================================================

    @PostMapping(
            value = "/notification",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> sendTaskNotification(
            @RequestBody SendEmailDTO sendEmailDTO) {

        for (RecipientDTO recipient :
                sendEmailDTO.recipients()) {

            String token = cleanToken(
                    recipient.token()
            );

            if (token.isEmpty()) {
                System.out.println(
                        "⚠️ Empty FCM token. Skipping recipient."
                );
                continue;
            }

            sendTaskPushMessage(
                    token,
                    sendEmailDTO.assignedBy(),
                    sendEmailDTO.taskName(),
                    sendEmailDTO.remake(),
                    sendEmailDTO.priority(),
                    sendEmailDTO.deadline()
            );
        }

        return ResponseEntity.ok("Sent");
    }


    // ============================================================
    // NORMAL PUSH
    // ============================================================

    private void sendPushMessage(
            NotificationInfoDTO dto) {

        try {

            String body;

            if ("E".equals(dto.getUserType())) {

                body = String.format(
                        "A leave request has been submitted by %s (%s) for the period %s to %s %s(%s). Your review and approval is requested.",
                        dto.getEmpName(),
                        dto.getEmpCode(),
                        dto.getFromDate(),
                        dto.getToDate(),
                        dto.getLeaveTypeName(),
                        dto.getLeaveType()
                );

            } else {

                body = dto.getMessage();
            }

            com.google.firebase.messaging.Notification notification =
                    com.google.firebase.messaging.Notification
                            .builder()
                            .setTitle(dto.getTitle())
                            .setBody(body)
                            .build();

            com.google.firebase.messaging.Message message =
                    com.google.firebase.messaging.Message
                            .builder()
                            .setToken(dto.getToken())
                            .setNotification(notification)
                            .build();

            String response =
                    FirebaseMessaging
                            .getInstance()
                            .send(message);

            System.out.println(
                    "🎉 Push Notification Sent: "
                            + response
            );

        } catch (Exception e) {

            e.printStackTrace();

            System.out.println(
                    "❌ Push Notification Error: "
                            + e.getMessage()
            );
        }
    }


    // ============================================================
    // TASK PUSH
    // ============================================================

    private void sendTaskPushMessage(
            String token,
            String assignedBy,
            String taskName,
            String remarks,
            String priority,
            String deadline) {

        try {

            String body =
                    "Task Assigned By \"" + assignedBy + "\"\n\n" +
                            "Task Details\n" +
                            "Task Name: " + taskName + "\n" +
                            "Description: " + remarks + "\n" +
                            "Priority: " + priority + "\n" +
                            "Deadline: " + deadline;


            com.google.firebase.messaging.Notification notification =
                    com.google.firebase.messaging.Notification
                            .builder()
                            .setTitle("New Task Assigned")
                            .setBody(body)
                            .build();


            com.google.firebase.messaging.Message message =
                    com.google.firebase.messaging.Message
                            .builder()
                            .setToken(token)
                            .setNotification(notification)
                            .build();


            String response =
                    FirebaseMessaging
                            .getInstance()
                            .send(message);


            System.out.println(
                    "🎉 Task Notification Sent: "
                            + response
            );

        } catch (Exception e) {

            e.printStackTrace();

            System.out.println(
                    "❌ Task Notification Error: "
                            + e.getMessage()
            );
        }
    }


    // ============================================================
    // TOKEN CLEANER
    // ============================================================

    private String cleanToken(String token) {

        if (token == null) {
            return "";
        }

        return token
                .replace("\"", "")
                .replace("\n", "")
                .replace("\r", "")
                .trim();
    }
}