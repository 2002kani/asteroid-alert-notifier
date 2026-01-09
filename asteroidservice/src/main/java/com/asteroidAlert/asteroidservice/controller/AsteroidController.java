package com.asteroidAlert.asteroidservice.controller;

import com.asteroidAlert.asteroidservice.service.AsteroidService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/asteroid-alerting")
@AllArgsConstructor
public class AsteroidController {
    private final AsteroidService asteroidService;

    @PostMapping("/alert")
    public ResponseEntity<Void> alert(){
        asteroidService.alert();
        return ResponseEntity.ok().build();
    }
}
