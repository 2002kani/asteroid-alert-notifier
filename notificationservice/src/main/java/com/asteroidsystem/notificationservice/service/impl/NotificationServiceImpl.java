package com.asteroidsystem.notificationservice.service.impl;


import com.asteroidsystem.notificationservice.asteroidalerting.event.AsteroidCollisionEvent;
import com.asteroidsystem.notificationservice.entity.Notification;
import com.asteroidsystem.notificationservice.repository.NotificationRepository;
import com.asteroidsystem.notificationservice.service.NotificationService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    final private NotificationRepository notificationRepository;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @KafkaListener(topics = "asteroid-alert", groupId = "notification-service")
    @Override
    public void alertEvent(AsteroidCollisionEvent event) {
        log.info("Received Asteroid collision event: {}", event);

        Notification notification = createNotification(event);
        final Notification savedNotification = notificationRepository.saveAndFlush(notification);
        log.info("Saved notification: {}", savedNotification);


    }

    @Override
    public Notification createNotification(AsteroidCollisionEvent event) {
        return Notification.builder()
                .asteroidName(event.getAsteroidName())
                .closeApproachDate(LocalDate.parse(event.getCloseApproachDate()))
                .missDistanceKilometers(new BigDecimal(event.getMissDistanceKilometers()))
                .estimatedDiameterAvgMeters(event.getEstimatedDiameterAvgMeters())
                .emailSent(false)
                .build();
    }
}
