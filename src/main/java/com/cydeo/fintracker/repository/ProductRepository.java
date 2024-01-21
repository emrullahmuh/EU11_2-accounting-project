package com.cydeo.fintracker.repository;

import com.cydeo.fintracker.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findAll();


    void deleteProductById(Long id);

    List<Product> getProductsById(Long companyId);
}
