package com.cydeo.fintracker.repository;

import com.cydeo.fintracker.entity.Company;
import com.cydeo.fintracker.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAll();

    List<Product> getProductsById(Long companyId);

    @Query("select p from Product p where p.category.id = ?1 ")
    List<Product> findByCategory(Long id);

    Product findByName(String name);

    boolean existsByName(String productName);

    List<Product> getProductsByInsertUserIdAndIsDeleted(Long id, boolean deleted);

    @Query("select p from Product p where p.category.company = ?1 and p.isDeleted = ?2")
    List<Product> findAllByCompany(Company company, Boolean isDeleted);


}
