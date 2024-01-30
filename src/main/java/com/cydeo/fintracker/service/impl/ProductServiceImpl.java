package com.cydeo.fintracker.service.impl;


import com.cydeo.fintracker.dto.CategoryDto;

import com.cydeo.fintracker.dto.CompanyDto;
import com.cydeo.fintracker.dto.InvoiceProductDto;

import com.cydeo.fintracker.dto.ProductDto;
import com.cydeo.fintracker.entity.Category;
import com.cydeo.fintracker.entity.Company;
import com.cydeo.fintracker.entity.Product;
import com.cydeo.fintracker.repository.ProductRepository;
import com.cydeo.fintracker.service.CompanyService;
import com.cydeo.fintracker.service.ProductService;
import com.cydeo.fintracker.service.SecurityService;
import com.cydeo.fintracker.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final MapperUtil mapperUtil;
    private final ProductRepository productRepository;
    private final CompanyService companyService;
    private final SecurityService securityService;



    @Override
    public List<ProductDto> getProducts() {

        CompanyDto companyDto = companyService.getCompanyDtoByLoggedInUser().get(0);

        Company company = mapperUtil.convert(companyDto, new Company());

        List<Product> products = productRepository.findAllByCompany(company, false);

        return products.stream()
                .map(product -> mapperUtil.convert(product, new ProductDto())).collect(Collectors.toList());
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {

        Optional<Product> oldProductOptional = productRepository.findById(productDto.getId());

        Product product = mapperUtil.convert(productDto, new Product());

        product.setId(oldProductOptional.get().getId());
        product.setQuantityInStock(oldProductOptional.get().getQuantityInStock());

        Product savedProduct = productRepository.save(product);
        log.info("Product will be updated : '{}'", savedProduct);

        ProductDto savedProductDto = mapperUtil.convert(savedProduct, new ProductDto());
        log.info("Product is updated '{}', '{}': ", savedProductDto.getName(), savedProductDto);

        return savedProductDto;
    }

    @Override
    public ProductDto findById(Long id) {

        Optional<Product> product = productRepository.findById(id);

        ProductDto productConvert = mapperUtil.convert(product, new ProductDto());
        log.info("Product is found by id: '{}', '{}'", id, productConvert);

        return productConvert;
    }

    @Override
    public void delete(Long id) {

        Optional<Product> product = productRepository.findById(id);

        product.get().setIsDeleted(true);
        productRepository.save(product.get());
        log.info("Product is deleted '{}', '{}'", id, id);

    }

    @Override
    public List<ProductDto> getProductsByCategory(Long id) {

        List<Product> products = productRepository.findByCategory(id);

        return products.stream().map(product -> mapperUtil.convert(product, new ProductDto()))
                .collect(Collectors.toList());

    }

    public boolean checkInventory(InvoiceProductDto invoiceProductDto) {
        if (invoiceProductDto.getProduct() == null) {
            return false;
        }
        Product product = productRepository.findByName(invoiceProductDto.getProduct().getName());
        return product.getQuantityInStock() < invoiceProductDto.getQuantity();
    }

    public ProductDto save(ProductDto product) {

        Product convertedProduct = mapperUtil.convert(product, new Product());

        productRepository.save(convertedProduct);
        log.info("Product is saved with description: '{}'", convertedProduct.getName());

        ProductDto createdProduct = mapperUtil.convert(convertedProduct, new ProductDto());
        log.info("Product is created with description: '{}'", createdProduct.getName());

        return createdProduct;


    }

    @Override
    public BindingResult uniqueName(ProductDto productDto, BindingResult bindingResult) {
        if (productRepository.existsByName(productDto.getName())){
            bindingResult.addError(new FieldError("newProduct","name","this product name already existed"));
        }
        return bindingResult;

    }
}

