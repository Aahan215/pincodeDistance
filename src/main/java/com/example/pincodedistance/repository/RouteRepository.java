package com.example.pincodedistance.repository;

import com.example.pincodedistance.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepository extends JpaRepository<Route, Long> {
    Route findByFromPincodeAndToPincode(String fromPincode, String toPincode);
}
