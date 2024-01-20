package com.cydeo.fintracker.repository;

import com.cydeo.fintracker.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}