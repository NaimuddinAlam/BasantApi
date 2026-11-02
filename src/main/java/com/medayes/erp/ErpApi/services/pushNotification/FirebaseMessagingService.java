package com.medayes.erp.ErpApi.services.pushNotification;

import com.medayes.erp.ErpApi.dto.request.MobileTokenDTO;
import com.medayes.erp.ErpApi.dto.response.task.TaskMastResponsibleResponseDto;

public interface FirebaseMessagingService {
  void    sendNotification();
  void    sendNotifications();
  TaskMastResponsibleResponseDto<?> sendToken(MobileTokenDTO mobileTokenDTO);
}
