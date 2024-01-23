package com.cydeo.fintracker.service.impl;

import com.cydeo.fintracker.dto.CategoryDto;
import com.cydeo.fintracker.entity.Category;
import com.cydeo.fintracker.exception.CategoryNotFoundException;
import com.cydeo.fintracker.repository.CategoryRepository;
import com.cydeo.fintracker.service.CategoryService;
import com.cydeo.fintracker.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final MapperUtil mapperUtil;

    public CategoryServiceImpl(CategoryRepository categoryRepository, MapperUtil mapperUtil) {
        this.categoryRepository = categoryRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public CategoryDto getById(Long id)  {

       Category category = categoryRepository.findByIdAndIsDeleted(id,false)
               .orElseThrow(()-> new CategoryNotFoundException("Category Not Found"));

       CategoryDto categoryResponse = mapperUtil.convert(category, new CategoryDto()) ;

       log.info("Category found by id: '{}', '{}'", id, categoryResponse);

       return categoryResponse;
    }

    @Override
    public List<CategoryDto> listAllCategories() {

        List<Category> categoryList = categoryRepository.findAllByIsDeleted(false);
        return categoryList.stream()
                .map(category->mapperUtil.convert(category, new CategoryDto()))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto update(CategoryDto category,Long id)  {

        Category storedCategory = categoryRepository.findByIdAndIsDeleted(id,false)
                .orElseThrow(()->new CategoryNotFoundException("Category Not Found"));

        Category convertedCategory = mapperUtil.convert(category, new Category());

        convertedCategory.setId(storedCategory.getId());
        convertedCategory.setCompany(storedCategory.getCompany());

        Category savedCategory = categoryRepository.save(convertedCategory);
        log.info("Category is updated '{}', '{}'", savedCategory.getDescription(), savedCategory);

        return mapperUtil.convert(savedCategory,new CategoryDto());


    }

    @Override
    public void delete(Long id)  {

        Category category = categoryRepository.findByIdAndIsDeleted(id,false)
                .orElseThrow(()->new CategoryNotFoundException("Category Not Found"));

        if(checkIfCategoryCanBeDeleted(category)){

            category.setIsDeleted(true);

            categoryRepository.save(category);
            log.info("Category is deleted '{}', '{}'", category.getDescription(), category);
        }
    }

    @Override
    public CategoryDto save(CategoryDto category) {

        Category convertedCategory = mapperUtil.convert(category, new Category());

        categoryRepository.save(convertedCategory);
        log.info("Category is saved with description: '{}'", convertedCategory.getDescription());

        CategoryDto createdCategory = mapperUtil.convert(convertedCategory, new CategoryDto());
        log.info("Category is created: '{}'", createdCategory.getDescription());


        return createdCategory;
    }

    @Override
    public boolean isCategoryDescriptionUnique(String description) {

        Category category = categoryRepository.findByDescription(description);
        return category == null;
    }

    private boolean checkIfCategoryCanBeDeleted(Category category){

       CategoryDto categoryDto = mapperUtil.convert(category,new CategoryDto());

        return !categoryDto.isHasProduct();
    }
}
