package com.medayes.erp.ErpApi.repository.sale;

import com.medayes.erp.ErpApi.dto.response.sale.NotificationReportingResponse;

import com.medayes.erp.ErpApi.entity.UserMastEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationReportingRepository
        extends JpaRepository<UserMastEntity, String> {

    @Query(value = """
        SELECT
             MOBILE_NO AS mobileno,
            PUSH_NOTIFICATION AS token
        FROM ERP_USER_MAST
        WHERE EMP_CODE = (
            SELECT REPORTING_TO
            FROM PAY_EMP_MAST
            WHERE EMP_CODE = :empCode
        )
        """, nativeQuery = true)
    NotificationReportingResponse notification(
            @Param("empCode") String empCode
    );
}
