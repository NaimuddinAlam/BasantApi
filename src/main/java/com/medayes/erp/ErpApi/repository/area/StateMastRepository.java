package com.medayes.erp.ErpApi.repository.area;

import com.medayes.erp.ErpApi.entity.StateMastEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateMastRepository extends JpaRepository<StateMastEntity, String> {
}

