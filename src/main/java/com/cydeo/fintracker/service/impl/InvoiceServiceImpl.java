package com.cydeo.fintracker.service.impl;


import com.cydeo.fintracker.dto.CompanyDto;
import com.cydeo.fintracker.dto.InvoiceDto;
import com.cydeo.fintracker.dto.InvoiceProductDto;
import com.cydeo.fintracker.dto.ProductDto;
import com.cydeo.fintracker.entity.Company;
import com.cydeo.fintracker.entity.Invoice;
import com.cydeo.fintracker.entity.InvoiceProduct;
import com.cydeo.fintracker.enums.InvoiceStatus;
import com.cydeo.fintracker.enums.InvoiceType;
import com.cydeo.fintracker.repository.InvoiceProductRepository;
import com.cydeo.fintracker.repository.InvoiceRepository;
import com.cydeo.fintracker.service.CompanyService;
import com.cydeo.fintracker.service.InvoiceProductService;
import com.cydeo.fintracker.service.InvoiceService;
import com.cydeo.fintracker.service.ProductService;
import com.cydeo.fintracker.util.MapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final MapperUtil mapperUtil;
    private final CompanyService companyService;
    private final InvoiceProductService invoiceProductService;
    private final ProductService productService;
    private final InvoiceProductRepository invoiceProductRepository;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, MapperUtil mapperUtil, CompanyService companyService, InvoiceProductService invoiceProductService, ProductService productService, InvoiceProductRepository invoiceProductRepository) {
        this.invoiceRepository = invoiceRepository;
        this.mapperUtil = mapperUtil;
        this.companyService = companyService;
        this.invoiceProductService = invoiceProductService;
        this.productService = productService;
        this.invoiceProductRepository = invoiceProductRepository;
    }

    @Override
    public InvoiceDto findById(Long id) {


        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Invoice does not exist!."));

        InvoiceDto invoiceDto = mapperUtil.convert(invoice, new InvoiceDto());

        log.info("InvoiceDto found by '{}' id", invoice);

        return invoiceDto;
    }

    @Override
    public List<InvoiceDto> listAllInvoices(InvoiceType invoiceType) {

        CompanyDto companyDto = companyService.getCompanyDtoByLoggedInUser().get(0);

        log.info("Company retrieved by logged-in user '{}'", companyDto);

        Company company = mapperUtil.convert(companyDto, new Company());

        List<Invoice> allByInvoiceTypeAndCompanyAndIsDeletedOrderByInvoiceNoDesc = invoiceRepository.findAllByInvoiceTypeAndCompanyAndIsDeletedOrderByInvoiceNoDesc(invoiceType, company, false);

        log.info("Invoice list retrieved by invoice type and company in desc order if not deleted '{}'", allByInvoiceTypeAndCompanyAndIsDeletedOrderByInvoiceNoDesc.size());

        return allByInvoiceTypeAndCompanyAndIsDeletedOrderByInvoiceNoDesc.stream()
                .map(invoice -> calculateTotal(invoice.getId()))
                .map(invoice -> mapperUtil.convert(invoice, new InvoiceDto()))
                .collect(Collectors.toList());
    }

    @Override
    public InvoiceDto save(InvoiceDto invoiceDto, InvoiceType invoiceType) {

        CompanyDto companyDto = companyService.getCompanyDtoByLoggedInUser().get(0);

        log.info("Company retrieved by logged-in user '{}'", companyDto);

        invoiceDto.setCompany(companyDto);
        Invoice invoice = mapperUtil.convert(invoiceDto, new Invoice());

        invoice.setInvoiceType(invoiceType);
        invoice.setInvoiceStatus(InvoiceStatus.AWAITING_APPROVAL);
        invoice.setDate(LocalDate.now());

        Invoice savedInvoice = invoiceRepository.save(invoice);

        log.info("invoice saved '{}'", savedInvoice);

        return mapperUtil.convert(savedInvoice, new InvoiceDto());
    }

    @Override
    public InvoiceDto update(InvoiceDto invoiceDto) {

        Invoice invoice = invoiceRepository.findById(invoiceDto.getId())
                .orElseThrow(() -> new NoSuchElementException("Invoice not found"));

        log.info("Invoice found by '{}' id", invoice);

        Invoice modifiedInvoice = mapperUtil.convert(invoiceDto, new Invoice());

        modifiedInvoice.setClientVendor(invoice.getClientVendor());

        invoiceRepository.save(modifiedInvoice);

        return mapperUtil.convert(modifiedInvoice, new InvoiceDto());
    }

    @Override
    public void delete(Long id) {

        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Invoice not found"));

        log.info("Invoice found by '{}' id", invoice);

        invoice.setIsDeleted(true);

        invoiceRepository.save(invoice);

        log.info("Invoice deleted by '{}' id", invoice);
    }

    @Override
    public InvoiceDto deleteByInvoice(Long invoiceId) {
        Invoice invoice = invoiceRepository.findByIdAndIsDeleted(invoiceId, false);

        log.info("Non-deleted invoice retrieved '{}'", invoice);

        if (InvoiceStatus.AWAITING_APPROVAL.equals(invoice.getInvoiceStatus())) {
            invoice.setIsDeleted(true);
        }

        List<InvoiceProduct> invoiceProductListById = invoiceProductRepository.findAllByInvoiceId(invoice.getId());

        log.info("invoice product list retrieved by id '{}'", invoiceProductListById.size());

        List<InvoiceProductDto> collect = invoiceProductListById.stream()
                .map(invoiceProduct -> invoiceProductService.delete(invoiceProduct.getId())).collect(Collectors.toList());

        Invoice savedInvoice = invoiceRepository.save(invoice);

        log.info("Each invoice product deleted and saved '{}', '{}'", collect.size(), savedInvoice);

        return mapperUtil.convert(invoice, new InvoiceDto());
    }

    @Override
    public InvoiceDto createNewPurchaseInvoice() {
        CompanyDto companyDto = companyService.getCompanyDtoByLoggedInUser().get(0);

        log.info("Company retrieved by logged-in user '{}'", companyDto);

        Company company = mapperUtil.convert(companyDto, new Company());
        InvoiceDto invoiceDto = new InvoiceDto();
        int no = invoiceRepository.findAllByInvoiceTypeAndCompanyOrderByInvoiceNoDesc(InvoiceType.PURCHASE, company).size() + 1;


        if (no < 10) {
            invoiceDto.setInvoiceNo("P-00" + no);
        } else if (no < 100 && no >= 10) {
            invoiceDto.setInvoiceNo("P-0" + no);
        }
        else {
            invoiceDto.setInvoiceNo("P-" + no);
        }


        invoiceDto.setDate(LocalDate.now());
        invoiceDto.setInvoiceStatus(InvoiceStatus.AWAITING_APPROVAL);

        log.info("Next purchase invoice no has been created '{}'", no);

        return invoiceDto;
    }

    @Override
    public String findInvoiceId() {
        return String.valueOf(invoiceRepository.findAll().size());
    }

    @Override
    @Transactional
    public InvoiceDto approve(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Invoice does not exist!."));

        log.info("Invoice found by '{}' id", invoice);

        if (invoice.getInvoiceType().getValue().equals(InvoiceType.PURCHASE.getValue())) {
            List<InvoiceProductDto> invoiceProductDtoList = invoiceProductService.listAllInvoiceProduct(id);

            log.info("Invoice product list has been retrieved '{}'", invoiceProductDtoList.size());

            for (InvoiceProductDto invoiceProductDto : invoiceProductDtoList) {
                Long productId = invoiceProductDto.getProduct().getId();
                Integer amount = invoiceProductDto.getQuantity();
                invoiceProductDto.setRemainingQuantity(amount);
//                invoiceProductService.save(invoiceProductDto, invoice.getId());
                matchRemainingQuantityPurchase(invoiceProductDto.getId());
                ProductDto productDto = productService.increaseProductInventory(productId, amount);

                log.info("Product quantity-in-stock increased by invoice product quantity '{}'", productDto);

            }

        } else if (invoice.getInvoiceType().getValue().equals(InvoiceType.SALES.getValue())) {
            List<InvoiceProductDto> invoiceProductDtoList = invoiceProductService.listAllInvoiceProduct(id);

            log.info("Invoice product list has been retrieved '{}'", invoiceProductDtoList.size());

            for (InvoiceProductDto invoiceProductDto : invoiceProductDtoList) {
                Long productId = invoiceProductDto.getProduct().getId();
                Integer amount = invoiceProductDto.getQuantity();
                invoiceProductDto.setRemainingQuantity(amount);
//                invoiceProductService.save(invoiceProductDto, invoice.getId());
                matchRemainingQuantitySales(invoiceProductDto.getId());
                invoiceProductService.setProfitLossForInvoiceProduct(invoiceProductDto);
                ProductDto productDto = productService.decreaseProductInventory(productId, amount);

                log.info("Product quantity-in-stock increased by invoice product quantity '{}'", productDto);

            }


        }



        invoice.setInvoiceStatus(InvoiceStatus.APPROVED);
        invoice.setDate(LocalDate.now());
        Invoice savedInvoice = invoiceRepository.save(invoice);

        log.info("Invoice status changed to APPROVED, local date changed to now, and invoice saved '{}'", savedInvoice);

        return mapperUtil.convert(invoice, new InvoiceDto());

    }

    @Override
    public InvoiceDto createNewSalesInvoice() {
        CompanyDto companyDto = companyService.getCompanyDtoByLoggedInUser().get(0);

        log.info("Company retrieved by logged-in user '{}'", companyDto);

        Company company = mapperUtil.convert(companyDto, new Company());
        InvoiceDto invoiceDto = new InvoiceDto();
        int no = invoiceRepository.retrieveInvoiceByTypeAndCompany(InvoiceType.SALES, company).size() + 1;
        invoiceDto.setInvoiceNo(no < 10 ? "S-00" + no : no < 100 ? "S-0" + no : "S-" + no);
        invoiceDto.setDate(LocalDate.now());
        invoiceDto.setInvoiceStatus(InvoiceStatus.AWAITING_APPROVAL);

        log.info("Next sales invoice no has been created '{}'", no);

        return invoiceDto;
    }

    @Override
    public boolean existsByClientVendorId(Long id) {

        boolean existsByClientVendorId = invoiceRepository.existsByClientVendorId(id);

        log.info("Checked if any client / vendor by id '{}'", existsByClientVendorId);

        return invoiceRepository.existsByClientVendorId(id);
    }

    private InvoiceDto calculateTotal(Long id) {
        InvoiceDto invoiceDto = findById(id);

        log.info("InvoiceDto found by id '{}'", invoiceDto);

        List<InvoiceProductDto> productList = invoiceProductService.listAllInvoiceProduct(id);

        log.info("Invoice product list has been retrieved '{}'", productList.size());

        BigDecimal totalPrice = BigDecimal.valueOf(0);
        BigDecimal totalWithTax = BigDecimal.valueOf(0);
        BigDecimal tax = BigDecimal.valueOf(0);
        for (InvoiceProductDto each : productList) {
            totalPrice = totalPrice.add(BigDecimal.valueOf(each.getQuantity()).multiply(each.getPrice()));
            tax = tax.add(BigDecimal.valueOf(each.getQuantity()).multiply(each.getPrice()).multiply(BigDecimal.valueOf(each.getTax()).divide(BigDecimal.valueOf(100))).setScale(2));
            totalWithTax = totalPrice.add(tax);

        }
        invoiceDto.setPrice(totalPrice);
        invoiceDto.setTax(tax);
        invoiceDto.setTotal(totalWithTax.setScale(2));
        return invoiceDto;

    }

    private void matchRemainingQuantityPurchase(Long id) {
        invoiceProductRepository.findById(id).get().setRemainingQuantity(invoiceProductRepository.findById(id).get().getQuantity());
    }

    private void matchRemainingQuantitySales(Long id) {

        int quantitySold = invoiceProductRepository.findById(id).get().getQuantity();

        log.info("Number of quantity to be sold retrieved '{}'", quantitySold);

        int quantityInStock = invoiceProductRepository.findById(id).get().getProduct().getQuantityInStock();

        log.info("Quantity in stock retrieved '{}'", quantityInStock);

        invoiceProductRepository.findById(id).get().setRemainingQuantity(quantityInStock - quantitySold);

    }
}
