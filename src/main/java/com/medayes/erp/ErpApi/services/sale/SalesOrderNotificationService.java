package com.medayes.erp.ErpApi.services.sale;

import com.medayes.erp.ErpApi.dto.request.notification.NotificationInfoDTO;
import com.medayes.erp.ErpApi.dto.response.sale.NotificationReportingResponse;
import com.medayes.erp.ErpApi.repository.sale.NotificationReportingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SalesOrderNotificationService {

    private final NotificationReportingRepository notificationReportingRepository;
    private final HeadOfficeNotificationServices headOfficeNotificationServices;
    private final WhatsAppService whatsAppService;
    private final PushNotificationService pushService;

    public void sendSalesOrderNotification(NotificationInfoDTO info) {

        // 1. Head office (fully self-contained; never throws)
        try {
            headOfficeNotificationServices.sendNotificationHeadOffice(info);
        } catch (Exception e) {
            log.error("Head office notification failed for order {}", info.getOrderNo(), e);
        }

        // 2. Reporting manager
        try {
            NotificationReportingResponse manager =
                    notificationReportingRepository.notification(info.getEmpCode());

            if (manager == null) {
                log.warn("Reporting employee not found for EMP_CODE: {}", info.getEmpCode());
                return;
            }

            // Real values from the DB (hardcoded test mobile/token removed).
            String mobileNo = manager.getMobileNo();
            String token = manager.getToken();

            if (!PushNotificationService.isBlank(mobileNo)) {
                try {
                    whatsAppService.sendSalesOrderWhatsApp(
                            mobileNo.trim(),
                            info.getOrderNo(),
                            info.getEmpName()+ "("+info.getEmpCode() +")",
                            info.getCustomerName());
                } catch (Exception e) {
                    log.error("WhatsApp to reporting manager failed for order {}",
                            info.getOrderNo(), e);
                }
            } else {
                log.warn("Reporting manager has no mobile number (EMP_CODE: {})", info.getEmpCode());
            }

            // Same payload (type + orderNo) as head office, so deep-linking works.
            pushService.sendSalesOrderPush(token, info);

        } catch (Exception e) {
            log.error("Sales order notification error for order {}", info.getOrderNo(), e);
        }
    }
}