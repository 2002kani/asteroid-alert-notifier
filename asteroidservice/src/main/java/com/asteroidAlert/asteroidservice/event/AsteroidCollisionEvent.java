package com.asteroidAlert.asteroidservice.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AsteroidCollisionEvent {
    private String asteroidName;
    private String closeApproachDate;
    private Long missDistanceKilometers;
    private Long estimatedDiameterAvgMeters;
}
