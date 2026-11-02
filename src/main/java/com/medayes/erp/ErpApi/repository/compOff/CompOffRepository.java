package com.medayes.erp.ErpApi.repository.compOff;

import com.medayes.erp.ErpApi.entity.CompOffEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CompOffRepository extends JpaRepository<CompOffEntity, String> {
    List<CompOffEntity> findByEmpCodeOrderByCreateDateDesc(String empCode);
    List<CompOffEntity> findByEmpCode(String empCode);
    Optional<CompOffEntity> findByCompReqNo(String compReqNo);

    @Query(value = """
            
            SELECT e.PUSH_NOTIFICATION FROM PAY_COMP_REQUEST_MAST P INNER JOIN  ERP_USER_MAST e
            ON e.EMP_CODE=p.EMP_CODE  WHERE p.COMP_REQ_NO=:compReqNo
            """, nativeQuery = true)

    String token(String compReqNo);


}
