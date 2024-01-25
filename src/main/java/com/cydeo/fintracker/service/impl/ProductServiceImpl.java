package com.cydeo.fintracker.service.impl;

import com.cydeo.fintracker.dto.InvoiceProductDto;
import com.cydeo.fintracker.dto.ProductDto;
import com.cydeo.fintracker.entity.Product;
import com.cydeo.fintracker.repository.ProductRepository;
import com.cydeo.fintracker.service.ProductService;
import com.cydeo.fintracker.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final MapperUtil mapperUtil;
    private final ProductRepository productRepository;


    @Override
    public List<ProductDto> getProducts() {

        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> mapperUtil.convert(product, new ProductDto()))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {

        Optional<Product> oldProduct = productRepository.findById(productDto.getId());

        Product product = oldProduct.get();

        log.info("Product will be updated : '{}'", product);

        Product newProduct = productRepository.save(mapperUtil.convert(productDto, new Product()));

        ProductDto updatedProduct = mapperUtil.convert(newProduct, productDto);
        log.info("Product is updated '{}', '{}': ", updatedProduct.getName(), updatedProduct);

        return updatedProduct;
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

        productRepository.deleteProductById(id);
        log.info("Product is deleted '{}', '{}'", id, id);

    }

    @Override
    public List<Product> getProductsByCompanyId(Long companyId) {

        return productRepository.getProductsById(companyId);

    }

    @Override

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
}
