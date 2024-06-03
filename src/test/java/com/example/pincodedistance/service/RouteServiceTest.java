package com.example.pincodedistance.service;

import com.example.pincodedistance.entity.Route;
import com.example.pincodedistance.repository.RouteRepository;
import com.example.pincodedistance.dto.RouteData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class RouteServiceTest {

    @Mock
    private RouteRepository routeRepository;

    @Mock
    private GoogleMapsService googleMapsService;

    @InjectMocks
    private RouteService routeService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetRoute_ExistingRoute() {
        // Mock data
        String fromPincode = "12345";
        String toPincode = "67890";
        Route existingRoute = new Route();
        existingRoute.setFromPincode(fromPincode);
        existingRoute.setToPincode(toPincode);
        existingRoute.setDistance(10.0);
        existingRoute.setDuration("15 mins");
        existingRoute.setRoutePoints(Arrays.asList("Step 1", "Step 2"));

        // Mock repository behavior
        when(routeRepository.findByFromPincodeAndToPincode(fromPincode, toPincode))
                .thenReturn(existingRoute);

        // Call the service method
        Route result = routeService.getRoute(fromPincode, toPincode);

        // Verify repository method is called
        verify(routeRepository, times(1)).findByFromPincodeAndToPincode(fromPincode, toPincode);

        // Verify returned result
        assertEquals(existingRoute, result);
    }

    @Test
    public void testGetRoute_NewRoute() {
        // Mock data
        String fromPincode = "12345";
        String toPincode = "67890";
        RouteData routeData = new RouteData(createMockGoogleMapsResponse());
        Route newRoute = new Route();
        newRoute.setFromPincode(fromPincode);
        newRoute.setToPincode(toPincode);
        newRoute.setDistance(routeData.getDistance());
        newRoute.setDuration(routeData.getDuration());
        newRoute.setRoutePoints(routeData.getRoutePoints());

        // Mock repository behavior
        when(routeRepository.findByFromPincodeAndToPincode(fromPincode, toPincode))
                .thenReturn(null);
        when(googleMapsService.getRouteData(fromPincode, toPincode))
                .thenReturn(routeData);

        // Call the service method
        Route result = routeService.getRoute(fromPincode, toPincode);

        // Verify repository method is called
        verify(routeRepository, times(1)).findByFromPincodeAndToPincode(fromPincode, toPincode);

        // Verify GoogleMapsService method is called
        verify(googleMapsService, times(1)).getRouteData(fromPincode, toPincode);

        // Verify returned result
        assertEquals(newRoute.getFromPincode(), result.getFromPincode());
        assertEquals(newRoute.getToPincode(), result.getToPincode());
        assertEquals(newRoute.getDistance(), result.getDistance());
        assertEquals(newRoute.getDuration(), result.getDuration());
        assertEquals(newRoute.getRoutePoints(), result.getRoutePoints());

        // Verify repository save method is called
        verify(routeRepository, times(1)).save(any(Route.class));
    }

    // Helper method to create a mock GoogleMapsResponse for testing
    private GoogleMapsResponse createMockGoogleMapsResponse() {
        GoogleMapsResponse response = new GoogleMapsResponse();
        GoogleMapsResponse.Route route = new GoogleMapsResponse.Route();
        GoogleMapsResponse.Route.Leg leg = new GoogleMapsResponse.Route.Leg();
        GoogleMapsResponse.Route.Leg.Distance distance = new GoogleMapsResponse.Route.Leg.Distance();
        distance.setText("10 km");
        distance.setValue(10000); // in meters
        leg.setDistance(distance);
        leg.setDuration(new GoogleMapsResponse.Route.Leg.Duration("15 mins", 900)); // in seconds
        leg.setSteps(Arrays.asList(
                new GoogleMapsResponse.Route.Leg.Step("Step 1", distance, new GoogleMapsResponse.Route.Leg.Duration("5 mins", 300)),
                new GoogleMapsResponse.Route.Leg.Step("Step 2", distance, new GoogleMapsResponse.Route.Leg.Duration("10 mins", 600))
        ));
        route.setLegs(Arrays.asList(leg));
        response.setRoutes(Arrays.asList(route));
        return response;
    }
}
