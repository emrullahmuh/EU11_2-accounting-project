package com.cydeo.repository;

import com.cydeo.dto.CompanyDto;
import com.cydeo.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company,Long> {

    CompanyDto findByTitle(String title);



}