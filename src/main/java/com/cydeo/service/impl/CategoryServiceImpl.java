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

        List<Category> categoryList = categoryRepository.findAllByIsDeleted(false);
        return categoryList.stream()
                .map(category->mapperUtil.convert(category, new CategoryDto()))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto update(CategoryDto category) {

        Optional<Category> category1=categoryRepository.findById(category.getId());

        Category convertedCategory = mapperUtil.convert(category, new Category());

        convertedCategory.setId(category1.get().getId());

        categoryRepository.save(convertedCategory);

        return getById(category.getId());
    }

    @Override
    public void delete(Long id) {

        Category category = categoryRepository.findByIdAndIsDeleted(id,false);

        category.setIsDeleted(true);

        categoryRepository.save(category);

    }

    @Override
    public void save(CategoryDto category) {

        Category category1 = mapperUtil.convert(category, new Category());

        categoryRepository.save(category1);

    }
}
