package com.medayes.erp.ErpApi.services.tracking;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

/**
 * Wraps Google's Distance Matrix API for road/driving/walking distance
 * between two specific points. Not for summing a GPS track — use
 * TrackDistanceService for that (free, no rate limit).
 */
@Service
public class GoogleMapsDistanceService {

    private final RestTemplate restTemplate;

    @Value("${google.maps.api-key}")
    private String apiKey;

    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/distancematrix/json";

    public GoogleMapsDistanceService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public double getRoadDistanceMeters(double originLat, double originLng,
                                        double destLat, double destLng,
                                        String mode) {

        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam("origins", originLat + "," + originLng)
                .queryParam("destinations", destLat + "," + destLng)
                .queryParam("mode", mode)
                .queryParam("key", apiKey)
                .toUriString();

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if (response == null || !"OK".equals(response.get("status"))) {
            throw new IllegalStateException("Distance Matrix API call failed: " + response);
        }

        List<Map<String, Object>> rows = (List<Map<String, Object>>) response.get("rows");
        List<Map<String, Object>> elements = (List<Map<String, Object>>) rows.get(0).get("elements");
        Map<String, Object> element = elements.get(0);

        if (!"OK".equals(element.get("status"))) {
            throw new IllegalStateException("No route found: " + element.get("status"));
        }

        Map<String, Object> distance = (Map<String, Object>) element.get("distance");
        return ((Number) distance.get("value")).doubleValue();
    }
}