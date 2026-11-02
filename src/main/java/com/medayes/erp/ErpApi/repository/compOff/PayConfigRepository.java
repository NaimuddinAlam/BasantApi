package com.medayes.erp.ErpApi.repository.compOff;


import com.medayes.erp.ErpApi.entity.PayConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayConfigRepository extends JpaRepository<PayConfigEntity,String> {
    PayConfigEntity findTopBy();
}
