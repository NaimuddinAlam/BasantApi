package com.medayes.erp.ErpApi.repository.distances;



import com.medayes.erp.ErpApi.entity.GpsPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface GpsPointRepository extends JpaRepository<GpsPoint, Long> {

    List<GpsPoint> findByUserIdOrderByTimestampAsc(Long userId);

    @Query("""
    SELECT g FROM GpsPoint g
    WHERE g.userId = :userId
      AND g.timestamp >= :start
      AND g.timestamp < :end
    ORDER BY g.timestamp ASC
""")
    List<GpsPoint> findGpsPointsByDate(
            @Param("userId") Long userId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    List<GpsPoint> findByUserIdAndTimestampBetweenOrderByTimestampAsc(
            Long userId, LocalDateTime start, LocalDateTime end);
}