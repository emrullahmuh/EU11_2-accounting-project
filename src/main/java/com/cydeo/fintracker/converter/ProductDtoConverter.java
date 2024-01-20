package com.cydeo.fintracker.converter;

import com.cydeo.fintracker.entity.Product;
import com.cydeo.fintracker.service.ProductService;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;

import java.util.Optional;

public class ProductDtoConverter implements Converter<String, Optional<Product>> {

    private final ProductService productService;

    public ProductDtoConverter(@Lazy ProductService productService) {
        this.productService = productService;
    }

    @Override
    public Optional<Product> convert(String id) {

        if(id==null || id.equals("")){
            return null;
        }
        return productService.findById(Long.parseLong(id));
    }
}
