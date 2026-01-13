package com.asteroidAlert.asteroidservice.service.impl;

import com.asteroidAlert.asteroidservice.entity.Notification;
import com.asteroidAlert.asteroidservice.event.AsteroidCollisionEvent;
import com.asteroidAlert.asteroidservice.repository.NotificationRepository;
import com.asteroidAlert.asteroidservice.service.EmailService;
import com.asteroidAlert.asteroidservice.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    final private NotificationRepository notificationRepository;
    final private EmailService emailService;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository,  EmailService emailService) {
        this.notificationRepository = notificationRepository;
        this.emailService = emailService;
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

    @Scheduled(fixedRate = 20000)
    @Override
    public void sendAlertingEmail() {
        log.info("Sending alerting email");
        emailService.sendAsteroidAlertEmail();
    }
}
