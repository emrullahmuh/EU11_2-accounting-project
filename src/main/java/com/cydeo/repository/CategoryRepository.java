package com.cydeo.repository;

import com.cydeo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CategoryRepository extends JpaRepository<Category,Long> {

    Category findByIdAndIsDeleted(Long id, boolean isDeleted);

    List<Category> findAllByIsDeleted(boolean isDeleted);
}
