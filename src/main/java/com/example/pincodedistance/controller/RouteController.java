package com.example.pincodedistance.controller;

import com.example.pincodedistance.entity.Route;
import com.example.pincodedistance.service.RouteService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping
    public Route getRoute(@RequestParam String fromPincode, @RequestParam String toPincode) {
        return routeService.getRoute(fromPincode, toPincode);
    }
}
