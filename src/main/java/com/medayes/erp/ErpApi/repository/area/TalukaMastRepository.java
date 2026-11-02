package com.medayes.erp.ErpApi.repository.area;

import com.medayes.erp.ErpApi.entity.TalukaMastEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TalukaMastRepository extends JpaRepository<TalukaMastEntity,String> {
    List<TalukaMastEntity> findByDistCodeOrderByTalukaNameAsc(String distCode);
}
