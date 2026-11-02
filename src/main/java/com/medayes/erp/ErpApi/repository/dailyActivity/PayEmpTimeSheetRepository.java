package com.medayes.erp.ErpApi.repository.dailyActivity;



import com.medayes.erp.ErpApi.entity.PayEmpTimeSheetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface PayEmpTimeSheetRepository extends JpaRepository<PayEmpTimeSheetEntity, String>
{
   //List<PayEmpTimeSheetEntity> findByMobileNo(String mobile);
  // PayEmpTimeSheetEntity findTopByMobileNo(String mobileNo);
  // PayEmpTimeSheetEntity findTopByMobileNoOrderByRowIdDesc(String mobileNo);
   @Query(value = """
SELECT TOP 1 client_name, mobile_no, row_id,state_code,dist_code,taluka_code,villag_name
FROM pay_emp_timesheet
WHERE mobile_no = :mobileNo AND EMP_CODE = :userId
ORDER BY row_id DESC
""", nativeQuery = true)
   PayEmpTimeSheetEntity findClientNames(@Param("mobileNo") String mobileNo,
                                         @Param("userId") String userId
                                         );

    @Query(value = """
SELECT TOP 1 client_name, mobile_no, row_id,state_code,dist_code,taluka_code,villag_name
FROM pay_emp_timesheet


  WHERE mobile_no = :mobileNo AND EMP_CODE = :userId
ORDER BY row_id DESC
""", nativeQuery = true)
    PayEmpTimeSheetEntity findMobile(@Param("mobileNo") String mobileNo,
                                     @Param("userId") String userId);


}
