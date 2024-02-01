package com.cydeo.fintracker.repository;

import com.cydeo.fintracker.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Override
    Optional<User> findById(Long aLong);

    Optional<User> findByUsername(String username);

    List<User> findAllByIsDeleted(Boolean deleted);
}
