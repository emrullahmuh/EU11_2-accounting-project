package com.cydeo.service;

import com.cydeo.dto.CategoryDto;
import com.cydeo.exception.CategoryNotFoundException;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    CategoryDto getById(Long id) throws CategoryNotFoundException;
    List<CategoryDto> listAllCategories();
    CategoryDto update(CategoryDto category) throws CategoryNotFoundException;
    void delete(Long id) throws CategoryNotFoundException;
    void save(CategoryDto category);


}
