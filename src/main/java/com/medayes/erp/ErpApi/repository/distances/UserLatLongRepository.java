package com.medayes.erp.ErpApi.repository.distances;

import com.medayes.erp.ErpApi.entity.UserLatLongEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
@Repository
public interface UserLatLongRepository extends JpaRepository<UserLatLongEntity, Long> {

    @Transactional(readOnly = true)
    @Query("""
        SELECT u
        FROM UserLatLongEntity u
        WHERE u.userId = :userId
          AND u.createDate >= :startDate
          AND u.createDate < :endDate
          AND u.latitude IS NOT NULL
          AND u.longitude IS NOT NULL
          AND u.latitude <> 0.0
          AND u.longitude <> 0.0
        ORDER BY u.createDate ASC, u.rowId ASC
    """)
    List<UserLatLongEntity> findLocations(
            @Param("userId") String userId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );


}