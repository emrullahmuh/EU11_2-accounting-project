package com.cydeo.converter;

import com.cydeo.dto.CategoryDto;
import com.cydeo.service.CategoryService;
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

        return categoryService.getById(source);
    }
}
