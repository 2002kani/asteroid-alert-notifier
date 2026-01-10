package com.asteroidAlert.asteroidservice.client;

import com.asteroidAlert.asteroidservice.dto.Asteroid;
import com.asteroidAlert.asteroidservice.dto.AsteroidResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class NasaClient {
    @Value("${nasa.url}")
    private String nasaUrl;

    @Value("${nasa.apiKey}")
    private String apiKey;

    public List<Asteroid> getNeoAsteroids(LocalDate start_date, LocalDate end_date) {
        final RestTemplate restTemplate = new RestTemplate();

        String url = UriComponentsBuilder.
                fromUriString(nasaUrl)
                .queryParam("start_date", start_date)
                .queryParam("end_date", end_date)
                .queryParam("api_key", apiKey)
                .toUriString();

        AsteroidResponse response = restTemplate.getForObject(url, AsteroidResponse.class);

        List<Asteroid> asteroidsList = new ArrayList<>();
        if(response != null){
            asteroidsList.addAll(response.getNearEarthObjects().values().stream().flatMap(List::stream).toList());
        }

        return asteroidsList;
    }
}
