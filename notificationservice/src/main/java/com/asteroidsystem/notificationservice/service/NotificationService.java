package com.asteroidsystem.notificationservice.service;

import com.asteroidsystem.notificationservice.asteroidalerting.event.AsteroidCollisionEvent;
import com.asteroidsystem.notificationservice.entity.Notification;

public interface NotificationService {
    void alertEvent(AsteroidCollisionEvent event);
    Notification createNotification(AsteroidCollisionEvent event);
    void sendAlertingEmail();
}
