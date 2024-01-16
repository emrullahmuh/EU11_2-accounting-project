package com.cydeo.service;

import com.cydeo.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto getById(Long id);
    List<CategoryDto> listAllCategories();
    CategoryDto update(CategoryDto category);
    void delete(Long id);
    void save(CategoryDto category);


}
