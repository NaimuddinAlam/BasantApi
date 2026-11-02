package com.medayes.erp.ErpApi.repository.mis;

import com.medayes.erp.ErpApi.entity.ERPModuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ERPModuleRepository extends JpaRepository<ERPModuleEntity,String> {
    Optional<ERPModuleEntity> findByModuleCode(String code);
}
