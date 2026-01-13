# Asteroid Alerting System

A small application that monitors potentially hazardous asteroids using NASA's NeoWs API and sends real-time email notifications to subscribed users. The system fetches asteroid data, identifies close approaches to Earth, and publishes collision events via Kafka to a notification service. Users with enabled notifications receive automated email alerts containing asteroid details such as name, approach date, estimated diameter, and miss distance. **No Production ready project, just for my interests.**

## Microservice Architecture Overview

![Backend Architecture](./assets/backend-architecture.png)


## Tech Stack
- Java (Spring Boot)
- Apache Kafka
- PostgreSQL
- Docker
- Mailtrap
- NASA NeoWs API
