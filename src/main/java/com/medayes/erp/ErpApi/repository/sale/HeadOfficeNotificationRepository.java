package com.medayes.erp.ErpApi.repository.sale;

import com.medayes.erp.ErpApi.entity.HeadOfficeNotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeadOfficeNotificationRepository extends JpaRepository<HeadOfficeNotificationEntity, String> {
}
