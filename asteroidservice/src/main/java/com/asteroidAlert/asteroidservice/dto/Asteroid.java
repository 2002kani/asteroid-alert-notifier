package com.asteroidAlert.asteroidservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Asteroid {
    private String id;
    private String name;

    @JsonProperty("estimated_diameter")
    private EstimatedDiameter estimatedDiameter;

    @JsonProperty("is_potentially_hazardous_asteroid")
    private boolean isPotentiallyHazardousAsteroid;

    @JsonProperty("close_approach_data")
    private CloseApproachData closeApproachData;
}
