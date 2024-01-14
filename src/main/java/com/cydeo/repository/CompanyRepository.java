package com.cydeo.repository;

import com.cydeo.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {

    Optional<Company> findById(Long companyId);

}
