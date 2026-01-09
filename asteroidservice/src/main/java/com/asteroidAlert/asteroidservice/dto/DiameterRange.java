package com.asteroidAlert.asteroidservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiameterRange {
    @JsonProperty("estimated_diameter_min")
    private Long minDiameter;

    @JsonProperty("estimated_diameter_max")
    private Long maxDiameter;
}
