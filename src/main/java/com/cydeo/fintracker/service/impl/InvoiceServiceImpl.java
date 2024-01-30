package com.cydeo.fintracker.service.impl;


import com.cydeo.fintracker.dto.CompanyDto;
import com.cydeo.fintracker.dto.InvoiceDto;
import com.cydeo.fintracker.dto.InvoiceProductDto;
import com.cydeo.fintracker.entity.Company;
import com.cydeo.fintracker.entity.Invoice;
import com.cydeo.fintracker.enums.InvoiceStatus;
import com.cydeo.fintracker.enums.InvoiceType;
import com.cydeo.fintracker.repository.InvoiceRepository;
import com.cydeo.fintracker.service.CompanyService;
import com.cydeo.fintracker.service.InvoiceProductService;
import com.cydeo.fintracker.service.InvoiceService;
import com.cydeo.fintracker.service.ProductService;
import com.cydeo.fintracker.util.MapperUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final MapperUtil mapperUtil;
    private final CompanyService companyService;
    private final InvoiceProductService invoiceProductService;
    private final ProductService productService;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, MapperUtil mapperUtil, CompanyService companyService, InvoiceProductService invoiceProductService, ProductService productService) {
        this.invoiceRepository = invoiceRepository;
        this.mapperUtil = mapperUtil;
        this.companyService = companyService;
        this.invoiceProductService = invoiceProductService;
        this.productService = productService;
    }

    @Override
    public InvoiceDto findById(Long id) {

        InvoiceDto invoiceDto = mapperUtil.convert(invoiceRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Invoice does not exist!.")), new InvoiceDto());

        return invoiceDto;
    }

    @Override
    public List<InvoiceDto> listAllInvoices(InvoiceType invoiceType) {

        CompanyDto companyDto = companyService.getCompanyDtoByLoggedInUser().get(0);
        Company company = mapperUtil.convert(companyDto, new Company());

        return invoiceRepository.findAllByInvoiceTypeAndCompanyAndIsDeletedOrderByInvoiceNoDesc(invoiceType, company, false).stream()
                .map(invoice -> calculateTotal(invoice.getId()))
                .map(invoice -> mapperUtil.convert(invoice, new InvoiceDto()))
                .collect(Collectors.toList());

    }

    @Override
    public InvoiceDto save(InvoiceDto invoiceDto, InvoiceType invoiceType) {

        CompanyDto companyDto = companyService.getCompanyDtoByLoggedInUser().get(0);

        Company company = mapperUtil.convert(companyDto, new Company());

        invoiceDto.setCompany(companyDto);
        Invoice invoice = mapperUtil.convert(invoiceDto, new Invoice());

        invoice.setInvoiceType(invoiceType);
        invoice.setInvoiceStatus(InvoiceStatus.AWAITING_APPROVAL);

        Invoice savedInvoice = invoiceRepository.save(invoice);

        return mapperUtil.convert(savedInvoice, new InvoiceDto());
    }

    @Override
    public InvoiceDto update(InvoiceDto invoiceDto) {

        Invoice invoice = invoiceRepository.findById(invoiceDto.getId())
                .orElseThrow(() -> new NoSuchElementException("Invoice not found"));

        Invoice modifiedInvoice = mapperUtil.convert(invoiceDto, new Invoice());

        modifiedInvoice.setId(invoice.getId());

        return mapperUtil.convert(modifiedInvoice, new InvoiceDto());
    }

    @Override
    public void delete(Long id) {

        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Invoice not found"));

        invoice.setIsDeleted(true);

        invoiceRepository.save(invoice);
    }

    @Override
    public InvoiceDto createNewPurchaseInvoice() {
        CompanyDto companyDto = companyService.getCompanyDtoByLoggedInUser().get(0);
        Company company = mapperUtil.convert(companyDto, new Company());
        InvoiceDto invoiceDto = new InvoiceDto();
        int no = invoiceRepository.findAllByInvoiceTypeAndCompanyOrderByInvoiceNoDesc(InvoiceType.PURCHASE, company).size() + 1;
        if (no < 10) invoiceDto.setInvoiceNo("P-00" + no);
        else if (no < 100 && no >= 10) invoiceDto.setInvoiceNo("P-0" + no);
        else invoiceDto.setInvoiceNo("P-" + no);
        invoiceDto.setDate(LocalDate.now());
        invoiceDto.setInvoiceStatus(InvoiceStatus.AWAITING_APPROVAL);
        return invoiceDto;
    }

    @Override
    public String findInvoiceId() {
        return String.valueOf(invoiceRepository.findAll().size());
    }

    @Override
    public InvoiceDto approve(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Invoice does not exist!."));
        invoice.setInvoiceStatus(InvoiceStatus.APPROVED);
        invoiceRepository.save(invoice);
        return mapperUtil.convert(invoice, new InvoiceDto());

    }

    @Override
    public InvoiceDto createNewSalesInvoice() {
        CompanyDto companyDTO = companyService.getCompanyDtoByLoggedInUser().get(0);
        Company company = mapperUtil.convert(companyDTO, new Company());
        InvoiceDto invoiceDto = new InvoiceDto();
        int no = invoiceRepository.retrieveInvoiceByTypeAndCompany(InvoiceType.SALES, company).size() + 1;
        invoiceDto.setInvoiceNo(no < 10 ? "S-00" + no : no < 100 ? "S-0" + no : "S-" + no);
        invoiceDto.setDate(LocalDate.now());
        invoiceDto.setInvoiceStatus(InvoiceStatus.AWAITING_APPROVAL);
        return invoiceDto;
    }

    @Override
    public boolean existsByClientVendorId(Long id) {
        return invoiceRepository.existsByClientVendorId(id);
    }

    private InvoiceDto calculateTotal(Long id) {
        InvoiceDto invoiceDto = findById(id);
        List<InvoiceProductDto> productList = invoiceProductService.listAllInvoiceProduct(id);
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
}
