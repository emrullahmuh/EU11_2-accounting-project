package com.cydeo.fintracker.service;

import com.cydeo.fintracker.dto.CategoryDto;
import com.cydeo.fintracker.dto.ProductDto;

import com.cydeo.fintracker.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductDto> getProducts();

    ProductDto updateProduct(ProductDto productDto);

    ProductDto findById(Long id);

    void delete(Long id);

    List<Product> getProductsByCompanyId(Long id);
    List<ProductDto> getProductsByCategory(Long id);

}
