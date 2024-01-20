package com.cydeo.fintracker.service.impl;

import com.cydeo.fintracker.dto.CategoryDto;
import com.cydeo.fintracker.entity.Category;
import com.cydeo.fintracker.exception.CategoryNotFoundException;
import com.cydeo.fintracker.repository.CategoryRepository;
import com.cydeo.fintracker.service.CategoryService;
import com.cydeo.fintracker.util.MapperUtil;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public CategoryDto getById(Long id)  {

       Category category = categoryRepository.findByIdAndIsDeleted(id,false)
               .orElseThrow(()-> new CategoryNotFoundException("Category Not Found"));
        return mapperUtil.convert(category, new CategoryDto()) ;
    }

    @Override
    public List<CategoryDto> listAllCategories() {

        List<Category> categoryList = categoryRepository.findAllByIsDeleted(false);
        return categoryList.stream()
                .map(category->mapperUtil.convert(category, new CategoryDto()))
                .collect(Collectors.toList());
    }

    @Override
    public void update(CategoryDto category)  {

        Category category1=categoryRepository.findByIdAndIsDeleted(category.getId(),false)
                .orElseThrow(()->new CategoryNotFoundException("Category Not Found"));

        Category convertedCategory = mapperUtil.convert(category, new Category());

        convertedCategory.setId(category1.getId());

        categoryRepository.save(convertedCategory);


    }

    @Override
    public void delete(Long id)  {

        Category category = categoryRepository.findByIdAndIsDeleted(id,false)
                .orElseThrow(()->new CategoryNotFoundException("Category Not Found"));

        if(checkIfCategoryCanBeDeleted(category)){

            category.setIsDeleted(true);

            categoryRepository.save(category);
        }
    }

    @Override
    public void save(CategoryDto category) {

        Category category1 = mapperUtil.convert(category, new Category());

        categoryRepository.save(category1);

    }

    private boolean checkIfCategoryCanBeDeleted(Category category){

       CategoryDto categoryDto = mapperUtil.convert(category,new CategoryDto());

        return !categoryDto.isHasProduct();
    }
}
