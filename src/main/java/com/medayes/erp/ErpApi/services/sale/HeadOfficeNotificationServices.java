package com.medayes.erp.ErpApi.services.sale;

import com.medayes.erp.ErpApi.dto.request.notification.NotificationInfoDTO;
import com.medayes.erp.ErpApi.entity.HeadOfficeNotificationEntity;
import com.medayes.erp.ErpApi.repository.sale.HeadOfficeNotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HeadOfficeNotificationServices {

    private final HeadOfficeNotificationRepository headOfficeNotificationRepository;
    private final WhatsAppService whatsAppService;
    private final PushNotificationService pushService;

    /**
     * Send Sales Order notification (WhatsApp + push) to all Head Office users.
     * Each user is isolated: a failure for one never stops the others.
     */
    public void  sendNotificationHeadOffice(NotificationInfoDTO info) {

        List<HeadOfficeNotificationEntity> users;
        try {
            users = headOfficeNotificationRepository.findAll();
        } catch (Exception e) {
            log.error("Could not load head office notification users", e);
            return;
        }

        if (users.isEmpty()) {
            log.warn("No Head Office notification users found.");
            return;
        }

        log.info("Notifying {} head office users for order {}", users.size(), info.getOrderNo());

        for (HeadOfficeNotificationEntity user : users) {
            notifyUser(user, info);
        }
    }

    private void notifyUser(HeadOfficeNotificationEntity user, NotificationInfoDTO info) {

        // WhatsApp is independent of push: send even if the FCM token is missing.
        String mobileNo = user.getMobileNo();
        if (!PushNotificationService.isBlank(mobileNo)) {
            try {
                whatsAppService.sendSalesOrderWhatsApp(
                        mobileNo.trim(),
                        info.getOrderNo(),
                        info.getEmpName()+ "("+info.getEmpCode() +")",
                        info.getCustomerName());
            } catch (Exception e) {
                log.error("WhatsApp failed for order {} (mobile ending {})",
                        info.getOrderNo(), lastDigits(mobileNo), e);
            }
        }

        try {
            PushNotificationService.SendResult result =
                    pushService.sendSalesOrderPush(user.getToken(), info);

            if (result == PushNotificationService.SendResult.INVALID_TOKEN) {
                // Hook: clear/delete the stale token here if desired, e.g.
                // user.setToken(null); headOfficeNotificationRepository.save(user);
                log.warn("Head office user has an invalid FCM token (mobile ending {})",
                        lastDigits(mobileNo));
            }
        } catch (Exception e) {
            log.error("Push failed for head office user (mobile ending {})", lastDigits(mobileNo), e);
        }
    }

    private static String lastDigits(String s) {
        return (s == null || s.length() < 4) ? "****" : s.substring(s.length() - 4);
    }
}