package com.driver.service;

import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DriverLocationService {
    private static final String GEO_KEY = "drivers:location";


    private RedisTemplate<String,String> redisTemplate;

    private GeoOperations<String,String> geoOps;

    public DriverLocationService(RedisTemplate<String,String >template){
        this.redisTemplate = template;
        this.geoOps = template.opsForGeo();
    }

    public void updateDriverLocation(String driverId,double lat,double lon){
        geoOps.add(GEO_KEY,new Point(lon,lat),driverId);
    }

    public List<String> findNearbyDrivers(double lat, double lon){
        Circle searchArea = new Circle(new Point(lon,lat), new Distance(5, Metrics.KILOMETERS));

        GeoResults< RedisGeoCommands.GeoLocation<String>> result = geoOps.radius(GEO_KEY,searchArea);
        if(result == null) return Collections.emptyList();
        List<String> collected = result.getContent().stream().map(res -> res.getContent().getName())
                .collect(Collectors.toList());
        System.out.println(collected);
        return collected;
    }

}

