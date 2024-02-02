package com.cydeo.fintracker.repository;

import com.cydeo.fintracker.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("SELECT r FROM Role r WHERE r.description IN ('Admin', 'Manager', 'Employee')")
    List<Role> getAllRoleForAdmin();

    @Query("SELECT r FROM Role r WHERE r.description = 'Admin'")
    Role getAllRoleForRoot();
}
