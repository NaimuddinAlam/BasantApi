package com.medayes.erp.ErpApi.config;

import com.medayes.erp.ErpApi.entity.GpsPoint;

import java.time.Duration;
import java.util.List;

/**
 * Straight-line (great-circle) distance using the Haversine formula.
 * Use this to sum distance across a user's GPS track — no external API
 * call, no rate limit, no cost. Only reach for a Google API when you need
 * real road distance between two specific points (see GoogleMapsDistanceService).
 */
public final class DistanceUtil {

    private static final double EARTH_RADIUS_METERS = 6_371_000;

    private DistanceUtil() {
    }

    public static double haversineMeters(double lat1, double lon1, double lat2, double lon2) {
        double phi1 = Math.toRadians(lat1);
        double phi2 = Math.toRadians(lat2);
        double dPhi = Math.toRadians(lat2 - lat1);
        double dLambda = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dPhi / 2) * Math.sin(dPhi / 2)
                + Math.cos(phi1) * Math.cos(phi2)
                * Math.sin(dLambda / 2) * Math.sin(dLambda / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_METERS * c;
    }

    /**
     * Sums straight-line distance across an ordered track.
     * maxSpeedMetersPerSecond > 0 skips segments that imply an impossible
     * speed given elapsed time — useful for filtering GPS glitches/jumps.
     */
    public static double totalDistanceMeters(List<GpsPoint> points, double maxSpeedMetersPerSecond) {
        double total = 0;
        for (int i = 1; i < points.size(); i++) {
            GpsPoint prev = points.get(i - 1);
            GpsPoint curr = points.get(i);

            double segment = haversineMeters(
                    prev.getLatitude(), prev.getLongitude(),
                    curr.getLatitude(), curr.getLongitude());

            if (maxSpeedMetersPerSecond > 0 && prev.getTimestamp() != null && curr.getTimestamp() != null) {
                long seconds = Duration.between(prev.getTimestamp(), curr.getTimestamp()).getSeconds();
                if (seconds > 0) {
                    double impliedSpeed = segment / seconds;
                    if (impliedSpeed > maxSpeedMetersPerSecond) {
                        continue; // likely GPS glitch
                    }
                }
            }

            total += segment;
        }
        return total;
    }

    public static double totalDistanceMeters(List<GpsPoint> points) {
        return totalDistanceMeters(points, -1);
    }
}