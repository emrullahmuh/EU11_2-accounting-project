package com.cydeo.fintracker.service.impl;

import com.cydeo.fintracker.dto.CategoryDto;
import com.cydeo.fintracker.entity.Category;
import com.cydeo.fintracker.repository.CategoryRepository;
import com.cydeo.fintracker.service.CategoryService;
import com.cydeo.fintracker.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final MapperUtil mapperUtil;
    private final CategoryRepository categoryRepository;


    @Override
    public List<CategoryDto> listAllCategories() {

        List<Category> categories= categoryRepository.findAll();

        if (!categories.isEmpty()) {
            return categories.stream()
                    .map(category -> mapperUtil.convert(category, new CategoryDto()))
                    .collect(Collectors.toList());
        }
        return Collections.singletonList(mapperUtil.convert(categories,new CategoryDto()));
    }
}
