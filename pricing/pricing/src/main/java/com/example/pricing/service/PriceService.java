package com.example.pricing.service;

import com.example.pricing.dto.RideResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class PriceService {

    private final RestTemplate restTemplate= new RestTemplate();
    // Example fare calculation params
    private static final double BASE_FARE = 50.0;      // ₹50 base fare
    private static final double PER_KM_RATE = 12.0;

    public RideResponse calculateDistance(double pickupLat, double pickupLng, double dropLat, double dropLng) {
        String osrmUrl = String.format(
                "http://router.project-osrm.org/route/v1/driving/%f,%f;%f,%f?overview=false",
                pickupLng, pickupLat, dropLng, dropLat);

        Map<String, Object> result = restTemplate.getForObject(osrmUrl, Map.class);

        if (result == null || !result.containsKey("routes")) {
            throw new RuntimeException("Failed to get route from OSRM");
        }

        List<Map<String, Object>> routes = (List<Map<String, Object>>) result.get("routes");
        Map<String, Object> route = routes.get(0);

        double distanceMeters = ((Number) route.get("distance")).doubleValue();
        double durationSeconds = ((Number) route.get("duration")).doubleValue();

        // Convert distance to km
        double distanceKm = Math.round( (distanceMeters / 1000.0 )*100.0)/100.0;

        // Fare calculation
        double fare = Math.round((BASE_FARE + (distanceKm * PER_KM_RATE))*100.0)/100.0;


        // Duration in minutes
        double durationMinutes = durationSeconds / 60.0;

        String formattedTime;
        if (durationMinutes >= 60) {
            int hours = (int) (durationMinutes / 60);
            int minutes = (int) (durationMinutes % 60);
            formattedTime = String.format("%d hr %d min", hours, minutes);
        } else {
            formattedTime = String.format("%.2f min", durationMinutes);
        }

        return new RideResponse(distanceKm, formattedTime, fare);
    }
}
