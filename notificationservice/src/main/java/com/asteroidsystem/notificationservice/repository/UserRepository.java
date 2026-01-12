package com.asteroidsystem.notificationservice.repository;

import com.asteroidsystem.notificationservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
