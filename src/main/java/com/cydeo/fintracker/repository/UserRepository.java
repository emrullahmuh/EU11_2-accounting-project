package com.cydeo.fintracker.repository;

import com.cydeo.fintracker.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    List<User> findAllByIsDeleted(Boolean deleted);
}
