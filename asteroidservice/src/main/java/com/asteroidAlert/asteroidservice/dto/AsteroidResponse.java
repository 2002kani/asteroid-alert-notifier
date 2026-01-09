package com.asteroidAlert.asteroidservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AsteroidResponse {
    @JsonProperty("near_earth_objects")
    private Map<String, List<Asteroid>> nearEarthObjects;

    @JsonProperty("element_count")
    private Long elementCount;
}
