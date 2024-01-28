package com.cydeo.fintracker.repository;

import com.cydeo.fintracker.entity.Company;
import com.cydeo.fintracker.entity.Role;
import com.cydeo.fintracker.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Override
    Optional<User> findById(Long aLong);

   Optional<User> findByUsername(String username);

    List<User> findAllByIsDeleted(Boolean deleted);

    @Query("SELECT COUNT(u) FROM User u WHERE u.company = ?1 AND u.role.description =?2")
    Integer isUserOnlyAdmin(Company company, Role Description);

    @Query("SELECT u FROM User u WHERE u.company=?1 AND u.isDeleted=?2 ORDER BY u.role.description asc ")
    List<User> findAllUserWithCompanyAndIsDeleted(Company company,Boolean isDeleted);

    @Query("SELECT u FROM User u WHERE u.role.description=?1 AND u.isDeleted=?2")
    List<User>findAllAdminRole(String role, Boolean isDeleted);
}
