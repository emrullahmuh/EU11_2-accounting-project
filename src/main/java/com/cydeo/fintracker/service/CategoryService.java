package com.cydeo.fintracker.service;


import com.cydeo.fintracker.exception.CategoryNotFoundException;
import com.cydeo.fintracker.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto getById(Long id) throws CategoryNotFoundException;
    List<CategoryDto> listAllCategories();
    void update(CategoryDto category) throws CategoryNotFoundException;
    void delete(Long id) throws CategoryNotFoundException;
    void save(CategoryDto category);


}
