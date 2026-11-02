package com.medayes.erp.ErpApi.repository.upload;

import com.medayes.erp.ErpApi.entity.PayEmployeeMastEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface EmployeeUploadRepository extends JpaRepository<PayEmployeeMastEntity, String> {
    Optional<PayEmployeeMastEntity> findByEmpCode(String empCode);


}
