package com.medayes.erp.ErpApi.repository.dailyActivity;


import com.medayes.erp.ErpApi.entity.DailyActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DailyActivityRepository extends JpaRepository<DailyActivityEntity, String> {
    List<DailyActivityEntity> findByStatus(String status);


}
