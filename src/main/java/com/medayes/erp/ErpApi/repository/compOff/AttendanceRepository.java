package com.medayes.erp.ErpApi.repository.compOff;

import com.medayes.erp.ErpApi.entity.AttendanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository  extends JpaRepository<AttendanceEntity,String> {
    @Query(value = """
            select TIME from PAY_ATTENDANCE_APP WHERE CONVERT(date,ATT_DATE)=:date and EMP_CODE=:empCode
            """,nativeQuery = true)
    List<Optional<AttendanceEntity>> getTime(
            @Param("date") String date,
            @Param("empCode") String empCode);
}
