package com.asteroidAlert.asteroidservice.service;

import com.asteroidAlert.asteroidservice.dto.Asteroid;
import com.asteroidAlert.asteroidservice.event.AsteroidCollisionEvent;

import java.util.List;

public interface AsteroidService {
    void alert();
    List<AsteroidCollisionEvent> createAsteroidEvent(List<Asteroid> asteroids);
}
