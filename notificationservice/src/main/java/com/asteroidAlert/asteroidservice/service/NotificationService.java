package com.asteroidAlert.asteroidservice.service;

import com.asteroidAlert.asteroidservice.entity.Notification;
import com.asteroidAlert.asteroidservice.event.AsteroidCollisionEvent;

public interface NotificationService {
    void alertEvent(AsteroidCollisionEvent event);
    Notification createNotification(AsteroidCollisionEvent event);
    void sendAlertingEmail();
}
