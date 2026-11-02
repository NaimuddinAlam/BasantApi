package com.medayes.erp.ErpApi.repository.upload;

import com.medayes.erp.ErpApi.entity.BannerMastEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerMastRepository  extends JpaRepository<BannerMastEntity, String> {
    List<BannerMastEntity> findByStatusOrderBySeqNoAsc(String status);
}
