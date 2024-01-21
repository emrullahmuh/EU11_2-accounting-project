package com.cydeo.fintracker.converter;

import com.cydeo.fintracker.dto.CategoryDto;
import com.cydeo.fintracker.exception.CategoryNotFoundException;
import com.cydeo.fintracker.service.CategoryService;
import org.springframework.core.convert.converter.Converter;

public class CategoryDTOConverter implements Converter<Long, CategoryDto> {

    private final CategoryService categoryService;

    public CategoryDTOConverter(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public CategoryDto convert(Long source) {

        if (source == null ) {
            return null;
        }

        try {
            return categoryService.getById(source);
        } catch (CategoryNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
