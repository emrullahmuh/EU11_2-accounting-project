package com.cydeo.fintracker.repository;


import com.cydeo.fintracker.entity.ClientVendor;
import com.cydeo.fintracker.entity.Company;
import com.cydeo.fintracker.enums.ClientVendorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientVendorRepository extends JpaRepository<ClientVendor, Long> {
    ClientVendor findByClientVendorNameAndIsDeleted(String username, Boolean deleted);

    Optional<List<ClientVendor>> findAllByIsDeleted(Boolean deleted);

    Optional<List<ClientVendor>> findByClientVendorType(ClientVendorType clientVendorType);

    List<ClientVendor> findAllByCompanyId(Long companyId);

    List<ClientVendor> findAllByCompany_IdAndIsDeleted(Long loggedUserCompanyId, boolean isDeleted);

    List<ClientVendor> findByCompanyIdAndIsDeleted(Long id,boolean deleted);

    Optional<List<ClientVendor>> findAllByClientVendorTypeAndCompanyOrderByClientVendorName(ClientVendorType clientVendorType, Company company);

}
