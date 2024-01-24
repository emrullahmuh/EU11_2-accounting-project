package com.cydeo.fintracker.converter;

import com.cydeo.fintracker.dto.ProductDto;
import com.cydeo.fintracker.service.ProductService;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class ProductDtoConverter implements Converter<String, ProductDto> {

    private final ProductService productService;

    public ProductDtoConverter(@Lazy ProductService productService) {
        this.productService = productService;
    }


    @Override
    public ProductDto convert(String id) {

        if (id==null || id.equals("")){
            return null;
        }

        return (ProductDto) productService.findById(Long.parseLong(id));
    }
}
