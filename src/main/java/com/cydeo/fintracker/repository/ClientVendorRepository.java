package com.cydeo.fintracker.repository;


import com.cydeo.fintracker.entity.ClientVendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientVendorRepository extends JpaRepository <ClientVendor,Long>{
    ClientVendor findByClientVendorNameAndIsDeleted(String username, Boolean deleted);
}
