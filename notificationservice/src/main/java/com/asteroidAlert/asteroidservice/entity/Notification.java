package com.asteroidAlert.asteroidservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "asteroid_name")
    private String asteroidName;

    @Column(name = "close_approach_date")
    private LocalDate closeApproachDate;

    @Column(name = "miss_distance_kilometers")
    private BigDecimal missDistanceKilometers;

    @Column(name = "estimated_diameter_avg_meters")
    private double estimatedDiameterAvgMeters;

    @Column(name = "email_sent")
    private boolean emailSent;
}
