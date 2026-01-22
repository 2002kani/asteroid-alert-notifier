package com.asteroidAlert.asteroidservice.repository;

import com.asteroidAlert.asteroidservice.entity.KafkaAsteroid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KafkaAsteroidRepository extends JpaRepository<KafkaAsteroid, Long> {
    boolean existsByName(String name);
}
