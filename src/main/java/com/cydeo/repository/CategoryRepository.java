package com.cydeo.repository;

import com.cydeo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CategoryRepository extends JpaRepository<Category,Long> {

    Optional<Category> findByIdAndIsDeleted(Long id, boolean isDeleted);

    List<Category> findAllByIsDeleted(boolean isDeleted);
}
