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
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
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
        return invoiceProductRepository.findAllByInvoiceId(id).stream()
                .map(invoiceProduct -> calculateTotalInvoiceProduct(invoiceProduct.getId()))
                .map(invoiceProduct -> mapperUtil.convert(invoiceProduct, new InvoiceProductDto()))
                .collect(Collectors.toList());
    }

    @Override
    public InvoiceProductDto save(InvoiceProductDto invoiceProductDto, Long id) {
        CompanyDto companyDto = companyService.getCompanyDtoByLoggedInUser().get(0);
        InvoiceDto invoiceDto = invoiceService.findById(id);
        invoiceDto.setCompany(companyDto);
        invoiceProductDto.setInvoice(invoiceDto);
        InvoiceProduct converted = mapperUtil.convert(invoiceProductDto, new InvoiceProduct());
        converted.setId((long) (findAll().size()+1));
        InvoiceProduct saved = invoiceProductRepository.save(converted);
        return mapperUtil.convert(saved, new InvoiceProductDto());
    }

    @Override
    public InvoiceProductDto delete(Long id) {
        InvoiceProduct invoiceProduct = invoiceProductRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Invoice Product not found."));
        invoiceProduct.setIsDeleted(true);
        invoiceProductRepository.save(invoiceProduct);
        InvoiceProductDto invoiceProductDto = mapperUtil.convert(invoiceProduct, new InvoiceProductDto());
        return invoiceProductDto;
    }

    @Override
    public List<InvoiceProductDto> findByInvoiceId(Long invoiceId) {

        List<InvoiceProduct> invoiceProducts = invoiceProductRepository.findAllByInvoiceId(invoiceId);

        return invoiceProducts.stream()
                .map(invoiceProduct -> mapperUtil.convert(invoiceProduct, new InvoiceProductDto()))
                .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceProductDto> findAll() {

        List<InvoiceProduct> invoiceProductList = invoiceProductRepository.findAll();

        return invoiceProductList.stream()
                .map(invoiceProduct -> mapperUtil.convert(invoiceProduct, new InvoiceProductDto()))
                .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceProductDto> findAllByInvoiceIdAndIsDeleted(Long id, Boolean isDeleted) {

        List<InvoiceProduct> invoiceProductListNotDeleted = invoiceProductRepository.findAllByInvoiceIdAndIsDeleted(id, false);

        return invoiceProductListNotDeleted.stream()
                .map(invoiceProduct -> calculateTotalInvoiceProduct(invoiceProduct.getId()))
                .map(p -> mapperUtil.convert(p, new InvoiceProductDto()))
                .collect(Collectors.toList());
    }

    private InvoiceProductDto calculateTotalInvoiceProduct(Long invoiceProductId) {
        InvoiceProductDto invoiceProductDTO = findById(invoiceProductId);
        BigDecimal total = BigDecimal.ZERO;
        if (invoiceProductDTO.getQuantity() == null || invoiceProductDTO.getPrice() == null || invoiceProductDTO.getTax() == null) {
            throw new NoSuchElementException("Quantity or price is null");
        }
        List<InvoiceProduct> list = invoiceProductRepository.findAllByIdAndIsDeleted(invoiceProductDTO.getId(), false);
        for (InvoiceProduct each : list) {
            total = total.add(each.getPrice().multiply(BigDecimal.valueOf(each.getQuantity())));//15
            total = total.add(total.multiply(BigDecimal.valueOf(each.getTax()).divide(BigDecimal.valueOf(100))));
        }
        invoiceProductDTO.setTotal(total);

        return invoiceProductDTO;
    }
}
