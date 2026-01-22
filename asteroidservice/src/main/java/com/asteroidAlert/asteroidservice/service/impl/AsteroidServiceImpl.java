package com.asteroidAlert.asteroidservice.service.impl;

import com.asteroidAlert.asteroidservice.client.NasaClient;
import com.asteroidAlert.asteroidservice.dto.Asteroid;
import com.asteroidAlert.asteroidservice.event.AsteroidCollisionEvent;
import com.asteroidAlert.asteroidservice.service.AsteroidService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class AsteroidServiceImpl implements AsteroidService {
    private final NasaClient nasaClient;
    private KafkaTemplate<String, AsteroidCollisionEvent> kafkaTemplate;

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
        final List<Asteroid> dangerousAsteroids = asteroidList.stream()
                .filter(Asteroid::isPotentiallyHazardousAsteroid).toList();

        // Create alert and put on kafka topic
        List<AsteroidCollisionEvent> asteroidEvent = createAsteroidEvent(dangerousAsteroids);
        log.info("Sending {} asteroid alerts to Kafka", asteroidEvent.size());

        // Not the ideal way, better to create a unique key instead of .getAsteroidName()
        asteroidEvent.forEach(event -> {
            kafkaTemplate.send("asteroid-alert", event.getAsteroidName(), event);
            log.info("Asteroid alert sent to Kafka topic: {}", event);
        });
    }

    @Override
    public List<AsteroidCollisionEvent> createAsteroidEvent(final List<Asteroid> dangerousAsteroids) {
        return dangerousAsteroids.stream()
                .map(asteroid ->
                        AsteroidCollisionEvent.builder()
                                .asteroidName(asteroid.getName())
                                .closeApproachDate(asteroid.getCloseApproachData().getFirst().getApproachDate().toString())
                                .missDistanceKilometers(asteroid.getCloseApproachData().getFirst().getMissedDistance().getKilometers())
                                .estimatedDiameterAvgMeters((asteroid.getEstimatedDiameter().getMeters().getMinDiameter() +
                                        asteroid.getEstimatedDiameter().getMeters().getMaxDiameter()) / 2)
                                .build())
                .toList();
    }


}
