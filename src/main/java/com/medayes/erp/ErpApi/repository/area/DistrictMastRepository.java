package com.medayes.erp.ErpApi.repository.area;

import com.medayes.erp.ErpApi.entity.DistrictMastEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DistrictMastRepository extends JpaRepository<DistrictMastEntity, String> {

   List<DistrictMastEntity> findByStateCodeOrderByDistNameAsc(String stateCode);
   DistrictMastEntity findByDistCode(String distCode);
}
