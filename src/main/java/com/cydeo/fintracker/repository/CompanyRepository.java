package com.cydeo.fintracker.repository;

import com.cydeo.fintracker.entity.Company;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {

    Optional<Company> findById(Long companyId);

    @Query("select c from Company c")
    List<Company> getCompanies(Sort sort);

    boolean existsByTitle(String title);

    boolean existsByTitleAndIdNot(String title, Long id);

    @Query("select c from Company c where c.id != ?1")
    List<Company> getAllCompaniesForRoot(Long id);

    @Query("select c from Company c where c.id = ?1")
    Company getCompanyForCurrent(Long id);

}
