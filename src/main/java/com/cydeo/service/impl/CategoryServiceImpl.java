package com.cydeo.service.impl;

import com.cydeo.dto.CategoryDto;
import com.cydeo.entity.Category;
import com.cydeo.repository.CategoryRepository;
import com.cydeo.service.CategoryService;
import com.cydeo.util.MapperUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final MapperUtil mapperUtil;

    public CategoryServiceImpl(CategoryRepository categoryRepository, MapperUtil mapperUtil) {
        this.categoryRepository = categoryRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public CategoryDto getById(Long id) {

       Optional<Category> category = categoryRepository.findById(id);
        return mapperUtil.convert(category,new CategoryDto()) ;
    }

    @Override
    public List<CategoryDto> listAllCategories() {

        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream()
                .map(category->mapperUtil.convert(category, new CategoryDto()))
                .collect(Collectors.toList());
    }
}
