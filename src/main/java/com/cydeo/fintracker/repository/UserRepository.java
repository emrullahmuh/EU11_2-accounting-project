package com.cydeo.fintracker.repository;

import com.cydeo.fintracker.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
