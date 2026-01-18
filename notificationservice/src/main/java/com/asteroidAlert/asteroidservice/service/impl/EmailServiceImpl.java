package com.asteroidAlert.asteroidservice.service.impl;

import com.asteroidAlert.asteroidservice.entity.Notification;
import com.asteroidAlert.asteroidservice.repository.NotificationRepository;
import com.asteroidAlert.asteroidservice.repository.UserRepository;
import com.asteroidAlert.asteroidservice.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    @Value("${email.service.from.email}")
    private String fromEmail;

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailServiceImpl(NotificationRepository notificationRepository, UserRepository userRepository,  JavaMailSender javaMailSender) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendAsteroidAlertEmail() {
        final String text = createEmailText();

        if(text == null) {
            log.info("No asteroids to send alerts for at {}", LocalDateTime.now());
            return;
        }

        final List<String> toEmails =  userRepository.findAllEmailsAndNotificationsEnabled();
        if(toEmails.isEmpty()) {
            log.info("No user to send an E-mail to.");
            return;
        }

        toEmails.forEach(toEmail -> sendEmail(toEmail, text));
        log.info("Email sent to : #{} users.",  toEmails.size());
    }

    private void sendEmail(String toEmail, String text){
        SimpleMailMessage email = new SimpleMailMessage();

        email.setTo(toEmail);
        email.setFrom(fromEmail);
        email.setSubject("Nasa Asteroid Collision Event!");
        email.setText(text);

        javaMailSender.send(email);
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
            sb.append("\n");
            sb.append("Asteroid Name: ").append(notification.getAsteroidName()).append("\n");
            sb.append("Close Approach Date: ").append(notification.getCloseApproachDate()).append("\n");
            sb.append("Estimated Diameter in Meter").append(notification.getEstimatedDiameterAvgMeters()).append("\n");
            sb.append("Missed Distance in Kilometer").append(notification.getMissDistanceKilometers()).append("\n");
            sb.append("------------------------------");
            sb.append("\n");
            notification.setEmailSent(true);
            notificationRepository.save(notification);
        });

        return sb.toString();
    }
}
