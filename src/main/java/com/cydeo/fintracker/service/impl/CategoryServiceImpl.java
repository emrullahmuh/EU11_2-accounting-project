package com.cydeo.fintracker.service.impl;

import com.cydeo.fintracker.dto.CategoryDto;
import com.cydeo.fintracker.dto.CompanyDto;
import com.cydeo.fintracker.dto.ProductDto;
import com.cydeo.fintracker.dto.UserDto;
import com.cydeo.fintracker.entity.Category;
import com.cydeo.fintracker.entity.Company;
import com.cydeo.fintracker.exception.CategoryNotFoundException;
import com.cydeo.fintracker.repository.CategoryRepository;
import com.cydeo.fintracker.service.CategoryService;

import com.cydeo.fintracker.service.SecurityService;

import com.cydeo.fintracker.service.ProductService;

import com.cydeo.fintracker.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;
    private final ProductService productService;



    @Override
    public CategoryDto getById(Long id) {

        Category category = categoryRepository.findByIdAndIsDeleted(id, false)
                .orElseThrow(() -> new CategoryNotFoundException("Category Not Found"));

        CategoryDto categoryResponse = mapperUtil.convert(category, new CategoryDto());

        log.info("Category found by id: '{}', '{}'", id, categoryResponse);

        return categoryResponse;
    }

    @Override
    public List<CategoryDto> listAllCategories() {

        CompanyDto company = securityService.getLoggedInUser().getCompany();
        Long companyId = company.getId();
        List<Category> categoryList = categoryRepository.findAllByCompanyIdAndIsDeleted(companyId,false);
        log.info("Total :'{}' categories has been listed for company: '{}'", categoryList.size(), company.getTitle() );
        return categoryList.stream()
                .map(category -> mapperUtil.convert(category, new CategoryDto()))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto update(CategoryDto category, Long id) {

        Category storedCategory = categoryRepository.findByIdAndIsDeleted(id, false)
                .orElseThrow(() -> new CategoryNotFoundException("Category Not Found"));

        Category convertedCategory = mapperUtil.convert(category, new Category());

        convertedCategory.setId(storedCategory.getId());
        convertedCategory.setCompany(storedCategory.getCompany());

        Category savedCategory = categoryRepository.save(convertedCategory);
        log.info("Category is updated '{}', '{}'", savedCategory.getDescription(), savedCategory);

        return mapperUtil.convert(savedCategory, new CategoryDto());


    }

    @Override
    public void delete(Long id) {

        Category category = categoryRepository.findByIdAndIsDeleted(id, false)
                .orElseThrow(() -> new CategoryNotFoundException("Category Not Found"));

        if (checkIfCategoryCanBeDeleted(category)) {

            category.setIsDeleted(true);

            categoryRepository.save(category);
            log.info("Category is deleted '{}', '{}'", category.getDescription(), category);
        }
    }

    @Override
    public CategoryDto save(CategoryDto category) {

        CompanyDto companyDto = securityService.getLoggedInUser().getCompany();
        category.setCompany(companyDto);

        Category convertedCategory = mapperUtil.convert(category, new Category());

        convertedCategory.setCompany(company);
        categoryRepository.save(convertedCategory);

        log.info("Category is saved with description: '{}'", convertedCategory.getDescription());

        CategoryDto createdCategory = mapperUtil.convert(convertedCategory, new CategoryDto());
        log.info("Category is created: '{}'", createdCategory.getDescription());

        return createdCategory;
    }

    @Override

    public boolean hasProducts(CategoryDto category) {

        List<ProductDto> productDtoList = productService.getProductsByCategory(category.getId());

        return !productDtoList.isEmpty();
    }

    public boolean isCategoryDescriptionUnique(String description) {

        Category category = categoryRepository.findByDescription(description);
        return category == null;

    }

    private boolean checkIfCategoryCanBeDeleted(Category category) {

        CategoryDto categoryDto = mapperUtil.convert(category, new CategoryDto());

        return !categoryDto.isHasProduct();
    }
}

