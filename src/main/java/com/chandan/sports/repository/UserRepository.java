package com.chandan.sports.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chandan.sports.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}