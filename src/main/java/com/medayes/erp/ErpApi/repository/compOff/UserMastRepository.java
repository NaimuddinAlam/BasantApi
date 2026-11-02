package com.medayes.erp.ErpApi.repository.compOff;


import com.medayes.erp.ErpApi.entity.UserMastEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Optional;


@Repository
public interface UserMastRepository extends JpaRepository<UserMastEntity, String> {
    Optional<UserMastEntity> findFirstByEmpCode(String empCode);

    @Query( value = """
            SELECT CONCAT(FIRSTNAME,' ',LASTNAME) AS fullName
            FROM ERP_USER_MAST WHERE EMP_CODE=:empCode
            """, nativeQuery = true)
    String getEmployeeName(@Param("empCode") String empCode);

    @Query(value = """
            SELECT e.PUSH_NOTIFICATION FROM PAY_EMP_MAST P INNER JOIN  ERP_USER_MAST e
            ON e.EMP_CODE=p.REPORTING_HR  WHERE p.EMP_CODE=:empCode
            """, nativeQuery = true)

    String token(@Param("empCode") String empCode);


}
