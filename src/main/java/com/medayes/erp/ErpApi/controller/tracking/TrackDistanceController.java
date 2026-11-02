package com.medayes.erp.ErpApi.controller.tracking;
import com.medayes.erp.ErpApi.services.tracking.GoogleMapsDistanceService;
import com.medayes.erp.ErpApi.services.tracking.TrackDistanceService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/distance")
public class TrackDistanceController {

    private final TrackDistanceService trackDistanceService;
    private final GoogleMapsDistanceService googleMapsDistanceService;

    public TrackDistanceController(TrackDistanceService trackDistanceService,
                                   GoogleMapsDistanceService googleMapsDistanceService) {
        this.trackDistanceService = trackDistanceService;
        this.googleMapsDistanceService = googleMapsDistanceService;
    }

    /** Total distance for a user's entire logged history. */
    @GetMapping("/user/{userId}")
    public DistanceResponse userTotalDistance(
            @PathVariable Long userId,
            @RequestParam(required = false, defaultValue = "-1") double maxSpeedKmh) {

        double meters = trackDistanceService.totalDistanceForUser(userId, maxSpeedKmh);
        return new DistanceResponse(meters, meters / 1000.0);
    }

    /** Total distance for a user within a date/time range, e.g. one day or one shift. */
    @GetMapping("/user/{userId}/range")
    public DistanceResponse userDistanceInRange(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            @RequestParam(required = false, defaultValue = "-1") double maxSpeedKmh) {

        double meters = trackDistanceService.totalDistanceForUser(userId, start, end, maxSpeedKmh);
        return new DistanceResponse(meters, meters / 1000.0);
    }

    /** Road distance between two specific points via Google Distance Matrix API. */
    @GetMapping("/road")
    public DistanceResponse roadDistance(
            @RequestParam double originLat, @RequestParam double originLng,
            @RequestParam double destLat, @RequestParam double destLng,
            @RequestParam(defaultValue = "driving") String mode) {

        double meters = googleMapsDistanceService.getRoadDistanceMeters(
                originLat, originLng, destLat, destLng, mode);
        return new DistanceResponse(meters, meters / 1000.0);
    }

    public record DistanceResponse(double meters, double kilometers) {
    }
}