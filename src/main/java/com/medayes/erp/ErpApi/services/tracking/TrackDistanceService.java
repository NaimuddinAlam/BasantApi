package com.medayes.erp.ErpApi.services.tracking;

import com.medayes.erp.ErpApi.config.DistanceUtil;
import com.medayes.erp.ErpApi.entity.GpsPoint;

import com.medayes.erp.ErpApi.repository.distances.GpsPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TrackDistanceService {

    private final GpsPointRepository gpsPointRepository;

    public TrackDistanceService(GpsPointRepository gpsPointRepository) {
        this.gpsPointRepository = gpsPointRepository;
    }

    /** Total distance in meters for everything logged against this user. */
    public double totalDistanceForUser(Long userId, double maxSpeedKmh) {
        List<GpsPoint> points = gpsPointRepository.findByUserIdOrderByTimestampAsc(userId);
        return DistanceUtil.totalDistanceMeters(points, toMps(maxSpeedKmh));
    }

    /** Total distance in meters for a user within a date/time range (e.g. one shift or one day). */
    public double totalDistanceForUser(Long userId, LocalDateTime start, LocalDateTime end, double maxSpeedKmh) {
        List<GpsPoint> points = gpsPointRepository
                .findGpsPointsByDate(userId, start, end);
        return DistanceUtil.totalDistanceMeters(points, toMps(maxSpeedKmh));
    }

    private double toMps(double maxSpeedKmh) {
        return maxSpeedKmh > 0 ? maxSpeedKmh / 3.6 : -1;
    }
}