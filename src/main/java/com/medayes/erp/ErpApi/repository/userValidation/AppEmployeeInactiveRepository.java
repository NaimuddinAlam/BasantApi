package com.medayes.erp.ErpApi.repository.userValidation;

import com.medayes.erp.ErpApi.entity.AppEmployeeInactiveEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AppEmployeeInactiveRepository extends JpaRepository<AppEmployeeInactiveEntity,Long> {

    Optional<AppEmployeeInactiveEntity> findByEmpCodeAndInactiveDate(
            String empCode,
            LocalDate inactiveDate
    );
}
