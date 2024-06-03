package com.example.pincodedistance.service;

import com.example.pincodedistance.entity.Route;
import com.example.pincodedistance.repository.RouteRepository;
import com.example.pincodedistance.dto.RouteData;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RouteService {

    private final RouteRepository routeRepository;
    private final GoogleMapsService googleMapsService;

    public RouteService(RouteRepository routeRepository, GoogleMapsService googleMapsService) {
        this.routeRepository = routeRepository;
        this.googleMapsService = googleMapsService;
    }

    @Cacheable("routes")
    @Transactional
    public Route getRoute(String fromPincode, String toPincode) {
        Route route = routeRepository.findByFromPincodeAndToPincode(fromPincode, toPincode);
        if (route == null) {
            RouteData routeData = googleMapsService.getRouteData(fromPincode, toPincode);
            route = new Route();
            route.setFromPincode(fromPincode);
            route.setToPincode(toPincode);
            route.setDistance(routeData.getDistance());
            route.setDuration(routeData.getDuration());
            route.setRoutePoints(routeData.getRoutePoints());
            routeRepository.save(route);
        }
        return route;
    }
}
