package com.example.pincodedistance.dto;

import com.example.pincodedistance.dto.GoogleMapsResponse.Route.Leg;
import java.util.List;
import java.util.stream.Collectors;

public class RouteData {
    private double distance;
    private String duration;
    private List<String> routePoints;

    public RouteData(GoogleMapsResponse response) {
        Leg leg = response.getRoutes().get(0).getLegs().get(0);
        this.distance = leg.getDistance().getValue() / 1000.0; // Convert meters to kilometers
        this.duration = leg.getDuration().getText();
        this.routePoints = leg.getSteps().stream()
                .map(step -> step.getHtml_instructions())
                .collect(Collectors.toList());
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public List<String> getRoutePoints() {
        return routePoints;
    }

    public void setRoutePoints(List<String> routePoints) {
        this.routePoints = routePoints;
    }
}
