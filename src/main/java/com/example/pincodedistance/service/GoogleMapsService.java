package com.example.pincodedistance.service;

import com.example.pincodedistance.dto.GoogleMapsResponse;
import com.example.pincodedistance.dto.RouteData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleMapsService {

    @Value("${google.maps.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public GoogleMapsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public RouteData getRouteData(String fromPincode, String toPincode) {
        String url = String.format(
                "https://maps.googleapis.com/maps/api/directions/json?origin=%s&destination=%s&key=%s",
                fromPincode, toPincode, apiKey
        );

        GoogleMapsResponse response = restTemplate.getForObject(url, GoogleMapsResponse.class);
        if (response != null && "OK".equals(response.getStatus())) {
            return new RouteData(response);
        } else {
            throw new RuntimeException("Failed to fetch data from Google Maps API");
        }
    }
}
