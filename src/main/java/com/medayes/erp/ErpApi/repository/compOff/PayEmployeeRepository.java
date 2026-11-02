package com.medayes.erp.ErpApi.repository.compOff;

import com.medayes.erp.ErpApi.entity.PayEmployeeMastEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PayEmployeeRepository extends JpaRepository<PayEmployeeMastEntity, String> {
    List<PayEmployeeMastEntity>findByEmpCode(String empCode);


}
