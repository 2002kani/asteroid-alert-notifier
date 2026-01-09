package com.asteroidAlert.asteroidservice.service.impl;

import com.asteroidAlert.asteroidservice.client.NasaClient;
import com.asteroidAlert.asteroidservice.dto.Asteroid;
import com.asteroidAlert.asteroidservice.service.AsteroidService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class AsteroidServiceImpl implements AsteroidService {
    private final NasaClient nasaClient;

    @Override
    public void alert() {
        log.info("Asteroid service called");

        // Calculate fromDate & endDate
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(7);

        // Call Nasa api
        log.info("Getting asteroid list from {} to {}", startDate, endDate);
        List<Asteroid> asteroidList = nasaClient.getNeoAsteroids(startDate, endDate);

        // Send alert (if any hazardous asteroids)
        final List<Asteroid> dangerousAsteroid = asteroidList.stream()
                .filter(Asteroid::isPotentiallyHazardousAsteroid).toList();

        // Create alert and put on kafka topic
    }
}
