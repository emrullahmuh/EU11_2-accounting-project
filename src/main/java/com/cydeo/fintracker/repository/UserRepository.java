package com.cydeo.fintracker.repository;

import com.cydeo.fintracker.entity.Company;
import com.cydeo.fintracker.entity.Role;
import com.cydeo.fintracker.entity.User;

import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Override
    Optional<User> findById(Long aLong);

    Optional<User> findByUsername(String username);

    List<User> findAllByIsDeleted(Boolean deleted);


@Query("SELECT COUNT(u) FROM User u WHERE u.company = :company AND u.role.id = :roleId")
Integer isUserOnlyAdmin(@Param("company") Company company, @Param("roleId") Long roleId);

}
