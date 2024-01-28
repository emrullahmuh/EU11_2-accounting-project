package com.cydeo.fintracker.converter;

import com.cydeo.fintracker.dto.CategoryDto;
import com.cydeo.fintracker.service.CategoryService;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryDTOConverter implements Converter<Object, CategoryDto> {

    private final CategoryService categoryService;

    public CategoryDTOConverter(@Lazy CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public CategoryDto convert(Object source) {
        if (source == null) {
            return null;
        }

        if (source instanceof Long) {
            Long categoryId = (Long) source;
            return categoryService.getById(categoryId);
        }else if (source instanceof String) {
            // String tipinden gelen değeri kullanarak CategoryDto'yu oluşturun
            Long categoryId = Long.parseLong((String) source);
            return categoryService.getById(categoryId);
        }

        return null;
    }
}