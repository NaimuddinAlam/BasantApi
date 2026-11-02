package com.medayes.erp.ErpApi.repository.mobileVersion;

import com.medayes.erp.ErpApi.entity.MobileVersionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MobileVersionRepository extends JpaRepository<MobileVersionEntity,String> {
}
