package com.medayes.erp.ErpApi.services.sale;



import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MessagingErrorCode;
import com.google.firebase.messaging.Notification;
import com.medayes.erp.ErpApi.dto.request.notification.NotificationInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Single place for sending Sales Order FCM pushes.
 * Used by both head office and reporting manager flows so payloads stay identical.
 */
@Slf4j
@Service
public class PushNotificationService {

    public enum SendResult { SENT, INVALID_TOKEN, FAILED }

    private static final String TITLE = "New Sales Order";

    public SendResult sendSalesOrderPush(String token, NotificationInfoDTO dto) {

        if (isBlank(token)) {
            log.warn("FCM token is null/empty. Skipping push for order {}", dto.getOrderNo());
            return SendResult.INVALID_TOKEN;
        }

        String body = String.format(
                "A new sales order %s has been created by %s (%s) for customer %s. "
                        + "Please review and approve the order.",
                dto.getOrderNo(), dto.getEmpName(), dto.getEmpCode(), dto.getCustomerName());

        try {
            Message message = Message.builder()
                    .setToken(token)
                    .setNotification(Notification.builder()
                            .setTitle(TITLE)
                            .setBody(body)
                            .build())
                    .putData("type", "SALES_ORDER")
                    .putData("orderNo", String.valueOf(dto.getOrderNo()))
                    .build();

            String messageId = FirebaseMessaging.getInstance().send(message);
            log.info("Sales order push sent. order={}, token={}, id={}",
                    dto.getOrderNo(), mask(token), messageId);
            return SendResult.SENT;

        } catch (FirebaseMessagingException e) {
            MessagingErrorCode code = e.getMessagingErrorCode();
            if (code == MessagingErrorCode.UNREGISTERED || code == MessagingErrorCode.INVALID_ARGUMENT) {
                log.warn("Stale/invalid FCM token {} ({})", mask(token), code);
                return SendResult.INVALID_TOKEN;
            }
            log.error("FCM send failed for order {} (code={})", dto.getOrderNo(), code, e);
            return SendResult.FAILED;

        } catch (Exception e) {
            log.error("Unexpected error sending push for order {}", dto.getOrderNo(), e);
            return SendResult.FAILED;
        }
    }

    public static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    /** Never log full tokens. */
    public static String mask(String token) {
        if (token == null || token.length() <= 10) return "***";
        return token.substring(0, 6) + "..." + token.substring(token.length() - 4);
    }
}