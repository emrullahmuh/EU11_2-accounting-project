package com.cydeo.fintracker.service.impl;

import com.cydeo.fintracker.dto.CompanyDto;
import com.cydeo.fintracker.dto.InvoiceDto;
import com.cydeo.fintracker.dto.InvoiceProductDto;
import com.cydeo.fintracker.entity.InvoiceProduct;
import com.cydeo.fintracker.repository.InvoiceProductRepository;
import com.cydeo.fintracker.service.CompanyService;
import com.cydeo.fintracker.service.InvoiceProductService;
import com.cydeo.fintracker.service.InvoiceService;
import com.cydeo.fintracker.util.MapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InvoiceProductServiceImpl implements InvoiceProductService {

    private final InvoiceProductRepository invoiceProductRepository;
    private final MapperUtil mapperUtil;
    private final CompanyService companyService;
    private final InvoiceService invoiceService;

    public InvoiceProductServiceImpl(InvoiceProductRepository invoiceProductRepository, MapperUtil mapperUtil, CompanyService companyService, @Lazy InvoiceService invoiceService) {
        this.invoiceProductRepository = invoiceProductRepository;
        this.mapperUtil = mapperUtil;
        this.companyService = companyService;
        this.invoiceService = invoiceService;
    }

    @Override
    public InvoiceProductDto findById(Long id) {

        return mapperUtil.convert(invoiceProductRepository.findById(id), new InvoiceProductDto());
    }

    @Override
    public List<InvoiceProductDto> listAllInvoiceProduct(Long id) {

        List<InvoiceProduct> allByInvoiceIdAndIsDeleted = invoiceProductRepository.findAllByInvoiceIdAndIsDeleted(id, false);

        log.info("All non-deleted invoice products retrieved by invoice '{}'", allByInvoiceIdAndIsDeleted);

        return allByInvoiceIdAndIsDeleted.stream()
                .map(invoiceProduct -> calculateTotalInvoiceProduct(invoiceProduct.getId()))
                .map(invoiceProduct -> mapperUtil.convert(invoiceProduct, new InvoiceProductDto()))
                .collect(Collectors.toList());
    }

    @Override
    public InvoiceProductDto save(InvoiceProductDto invoiceProductDto, Long id) {
        CompanyDto companyDto = companyService.getCompanyDtoByLoggedInUser().get(0);

        log.info("Company retrieved by logged-in user '{}'", companyDto);

        InvoiceDto invoiceDto = invoiceService.findById(id);

        log.info("Invoice found by '{}' id", invoiceDto);

        invoiceDto.setCompany(companyDto);
        invoiceProductDto.setInvoice(invoiceDto);
        InvoiceProduct converted = mapperUtil.convert(invoiceProductDto, new InvoiceProduct());
        converted.setId(null);
        InvoiceProduct saved = invoiceProductRepository.save(converted);

        log.info("Invoice productDto saved '{}'", saved);

        return mapperUtil.convert(saved, new InvoiceProductDto());
    }

    @Override
    public InvoiceProductDto delete(Long id) {
        InvoiceProduct invoiceProduct = invoiceProductRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Invoice Product not found."));

        log.info("Invoice product found by '{}' id", invoiceProduct);

        invoiceProduct.setIsDeleted(true);
        InvoiceProduct saved = invoiceProductRepository.save(invoiceProduct);

        log.info("Invoice productDto deleted '{}'", saved);

        InvoiceProductDto invoiceProductDto = mapperUtil.convert(invoiceProduct, new InvoiceProductDto());
        return invoiceProductDto;
    }

    @Override
    public List<InvoiceProductDto> findAll() {

        List<InvoiceProduct> invoiceProductList = invoiceProductRepository.findAll();

        log.info("All invoice products retrieved '{}'", invoiceProductList.size());

        return invoiceProductList.stream()
                .map(invoiceProduct -> mapperUtil.convert(invoiceProduct, new InvoiceProductDto()))
                .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceProductDto> findAllByInvoiceIdAndIsDeleted(Long id, Boolean isDeleted) {

        List<InvoiceProduct> invoiceProductListNotDeleted = invoiceProductRepository.findAllByInvoiceIdAndIsDeleted(id, false);

        log.info("All non-deleted invoice products retrieved by invoice '{}'", invoiceProductListNotDeleted);

        return invoiceProductListNotDeleted.stream()
                .map(invoiceProduct -> calculateTotalInvoiceProduct(invoiceProduct.getId()))
                .map(p -> mapperUtil.convert(p, new InvoiceProductDto()))
                .collect(Collectors.toList());
    }

    private InvoiceProductDto calculateTotalInvoiceProduct(Long invoiceProductId) {
        InvoiceProductDto invoiceProductDto = findById(invoiceProductId);

        log.info("Invoice product found by '{}' id", invoiceProductDto);

        BigDecimal total = BigDecimal.ZERO;
        if (invoiceProductDto.getQuantity() == null || invoiceProductDto.getPrice() == null || invoiceProductDto.getTax() == null) {
            throw new NoSuchElementException("Quantity or price is null");
        }
        List<InvoiceProduct> list = invoiceProductRepository.findAllByIdAndIsDeleted(invoiceProductDto.getId(), false);

        log.info("All non-deleted invoice products retrieved by invoice product id '{}'", list.size());

        for (InvoiceProduct each : list) {
            total = total.add(each.getPrice().multiply(BigDecimal.valueOf(each.getQuantity())));//15
            total = total.add(total.multiply(BigDecimal.valueOf(each.getTax()).divide(BigDecimal.valueOf(100))));
        }
        invoiceProductDto.setTotal(total);

        return invoiceProductDto;
    }
}
