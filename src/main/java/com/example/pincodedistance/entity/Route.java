package com.example.pincodedistance.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fromPincode;
    private String toPincode;
    private double distance;
    private String duration;

    @ElementCollection
    private List<String> routePoints;

    // Constructors
    public Route() {
    }

    public Route(String fromPincode, String toPincode, double distance, String duration, List<String> routePoints) {
        this.fromPincode = fromPincode;
        this.toPincode = toPincode;
        this.distance = distance;
        this.duration = duration;
        this.routePoints = routePoints;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFromPincode() {
        return fromPincode;
    }

    public void setFromPincode(String fromPincode) {
        this.fromPincode = fromPincode;
    }

    public String getToPincode() {
        return toPincode;
    }

    public void setToPincode(String toPincode) {
        this.toPincode = toPincode;
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

    // toString() method for debugging purposes
    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", fromPincode='" + fromPincode + '\'' +
                ", toPincode='" + toPincode + '\'' +
                ", distance=" + distance +
                ", duration='" + duration + '\'' +
                ", routePoints=" + routePoints +
                '}';
    }
}
