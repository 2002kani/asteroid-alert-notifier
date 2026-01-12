package com.asteroidsystem.notificationservice.service.impl;

import com.asteroidsystem.notificationservice.entity.Notification;
import com.asteroidsystem.notificationservice.entity.User;
import com.asteroidsystem.notificationservice.repository.NotificationRepository;
import com.asteroidsystem.notificationservice.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    private final NotificationRepository notificationRepository;

    @Autowired
    public EmailServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void sendAsteroidAlertEmail() {
        final String text = createEmailText();

        if(text == null) {
            log.info("No asteroids to send alerts for at {}", LocalDateTime.now());
        }
    }

    private String createEmailText() {
        // Check if there are any asteroids to send alerts for
        List<Notification> notifications = notificationRepository.findByEmailSent(false);

        if(notifications.isEmpty()) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Asteroid Alert: ");
        sb.append("------------------------------");

        notifications.forEach(notification -> {
            sb.append("Asteroid Name: ").append(notification.getAsteroidName()).append("\n");
            sb.append("Close Approach Date: ").append(notification.getCloseApproachDate()).append("\n");
            sb.append("Estimated Diameter in Meter").append(notification.getEstimatedDiameterAvgMeters()).append("\n");
            sb.append("Missed Distance in Kilometer").append(notification.getMissDistanceKilometers()).append("\n");
            sb.append("------------------------------");
            notification.setEmailSent(true);
            notificationRepository.save(notification);
        });

        return sb.toString();
    }
}
