package com.example.pricing.controller;

import com.example.pricing.dto.RideResponse;
import com.example.pricing.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/calculate")
public class pricingController {

    @Autowired
    private PriceService priceService;






    @GetMapping("/price")
    public ResponseEntity<RideResponse> getRidePrice(@RequestParam double pickupLat,
                                                     @RequestParam double pickupLng,
                                                     @RequestParam double dropLat,
                                                     @RequestParam double dropLng){
        RideResponse response = priceService.calculateDistance(pickupLat, pickupLng, dropLat, dropLng);
        return ResponseEntity.ok(response);

    }

}
