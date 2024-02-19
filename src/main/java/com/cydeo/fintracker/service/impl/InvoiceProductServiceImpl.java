package com.cydeo.fintracker.service.impl;

import com.cydeo.fintracker.dto.CompanyDto;
import com.cydeo.fintracker.dto.InvoiceDto;
import com.cydeo.fintracker.dto.InvoiceProductDto;
import com.cydeo.fintracker.entity.Company;
import com.cydeo.fintracker.entity.InvoiceProduct;
import com.cydeo.fintracker.enums.InvoiceStatus;

import com.cydeo.fintracker.enums.InvoiceType;
import com.cydeo.fintracker.repository.InvoiceProductRepository;
import com.cydeo.fintracker.service.CompanyService;
import com.cydeo.fintracker.service.InvoiceProductService;
import com.cydeo.fintracker.service.InvoiceService;
import com.cydeo.fintracker.service.SecurityService;
import com.cydeo.fintracker.util.MapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InvoiceProductServiceImpl implements InvoiceProductService {

    private final InvoiceProductRepository invoiceProductRepository;
    private final MapperUtil mapperUtil;
    private final CompanyService companyService;
    private final InvoiceService invoiceService;
    private final SecurityService securityService;

    public InvoiceProductServiceImpl(InvoiceProductRepository invoiceProductRepository, MapperUtil mapperUtil, CompanyService companyService, @Lazy InvoiceService invoiceService, SecurityService securityService) {
        this.invoiceProductRepository = invoiceProductRepository;
        this.mapperUtil = mapperUtil;
        this.companyService = companyService;
        this.invoiceService = invoiceService;
        this.securityService = securityService;
    }

    @Override
    public InvoiceProductDto findById(Long id) {

        return mapperUtil.convert(invoiceProductRepository.findById(id), new InvoiceProductDto());
    }

    @Override
    public List<InvoiceProductDto> listAllInvoiceProduct(Long id) {

        List<InvoiceProduct> allByInvoiceIdAndIsDeleted = invoiceProductRepository.findAllByInvoiceIdAndIsDeleted(id, false);

        log.info("(1) - All non-deleted invoice products retrieved by invoice '{}'", allByInvoiceIdAndIsDeleted);

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
        invoiceProductDto.setProfitLoss(BigDecimal.ZERO);
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

        log.info("(2) - All non-deleted invoice products retrieved by invoice id '{}'", invoiceProductListNotDeleted);

        return invoiceProductListNotDeleted.stream()
                .map(invoiceProduct -> calculateTotalInvoiceProduct(invoiceProduct.getId()))
                .map(p -> mapperUtil.convert(p, new InvoiceProductDto()))
                .collect(Collectors.toList());
    }

    private InvoiceProductDto calculateTotalInvoiceProduct(Long invoiceProductId) {
        InvoiceProductDto invoiceProductDto = findById(invoiceProductId);

        log.info("Invoice product found by '{}' id", invoiceProductDto);

        BigDecimal total = BigDecimal.ZERO;

        if (Objects.isNull(invoiceProductDto.getQuantity()) || Objects.isNull(invoiceProductDto.getPrice()) || Objects.isNull(invoiceProductDto.getTax())) {

            throw new NoSuchElementException("Quantity or price is null");
        }
        List<InvoiceProduct> list = invoiceProductRepository.findAllByIdAndIsDeleted(invoiceProductDto.getId(), false);

        log.info("(3) - All non-deleted invoice products retrieved by invoice id '{}'", list.size());

        // Total cost of each invoiceProduct in the invoice is calculated including the tax
        total = list.stream()
                .map(each -> each.getPrice().multiply(BigDecimal.valueOf(each.getQuantity())).add(each.getPrice().multiply(BigDecimal.valueOf(each.getQuantity())).multiply(BigDecimal.valueOf(each.getTax()).divide(BigDecimal.valueOf(100)))))
                 .reduce(BigDecimal.ZERO, BigDecimal::add);

        invoiceProductDto.setTotal(total);

        return invoiceProductDto;
    }

    @Override
    public void setProfitLossForInvoiceProduct(InvoiceProductDto toBeSoldProduct) {

        List<InvoiceProduct> listOfApprovedPurchasedProducts = invoiceProductRepository
                .findAllByInvoiceProductsCompanyProductQuantityGreaterThanZero
                        (InvoiceStatus.APPROVED, InvoiceType.PURCHASE, toBeSoldProduct.getInvoice().getCompany().getTitle()
                                , toBeSoldProduct.getProduct().getId());

        listOfApprovedPurchasedProducts.sort(Comparator.comparing(p -> p.getInvoice().getDate()));

        BigDecimal profitLoss = BigDecimal.ZERO;
        toBeSoldProduct.setProfitLoss(profitLoss);
        toBeSoldProduct.setRemainingQuantity(toBeSoldProduct.getQuantity());

        for (InvoiceProduct purchasedProduct : listOfApprovedPurchasedProducts) {


            BigDecimal soldProductTax = toBeSoldProduct.getPrice()
                    .multiply(BigDecimal.valueOf(toBeSoldProduct.getTax())).divide(BigDecimal.valueOf(100));
            BigDecimal purchasedProductTax = purchasedProduct.getPrice()
                    .multiply(BigDecimal.valueOf(purchasedProduct.getTax())).divide(BigDecimal.valueOf(100));

            if (purchasedProduct.getRemainingQuantity() >= toBeSoldProduct.getRemainingQuantity()) {

                profitLoss = (toBeSoldProduct.getPrice().add(soldProductTax).subtract(purchasedProduct.getPrice()
                        .add(purchasedProductTax))).multiply(BigDecimal.valueOf(toBeSoldProduct.getRemainingQuantity()));

                BigDecimal updatedProfitLoss = toBeSoldProduct.getProfitLoss().add(profitLoss);

                toBeSoldProduct.setProfitLoss(updatedProfitLoss);

                purchasedProduct.setRemainingQuantity(purchasedProduct.getRemainingQuantity() - toBeSoldProduct.getRemainingQuantity());
                toBeSoldProduct.setRemainingQuantity(0);

                invoiceProductRepository.save(purchasedProduct);
                invoiceProductRepository.save(mapperUtil.convert(toBeSoldProduct, new InvoiceProduct()));
                break;
            } else {
                profitLoss = (toBeSoldProduct.getPrice().add(soldProductTax).subtract(purchasedProduct.getPrice()
                        .add(purchasedProductTax))).multiply(BigDecimal.valueOf(purchasedProduct.getRemainingQuantity()));

                BigDecimal updatedProfitLoss = toBeSoldProduct.getProfitLoss().add(profitLoss);

                toBeSoldProduct.setProfitLoss(updatedProfitLoss);

                toBeSoldProduct.setRemainingQuantity(toBeSoldProduct.getRemainingQuantity() - purchasedProduct.getRemainingQuantity());
                purchasedProduct.setRemainingQuantity(0);

                invoiceProductRepository.save(purchasedProduct);
                invoiceProductRepository.save(mapperUtil.convert(toBeSoldProduct, new InvoiceProduct()));
            }


        }
    }

    @Override
    public BindingResult checkProductStockBeforeAddingToInvoice(InvoiceProductDto productToAdd, Long invoiceId, BindingResult bindingResult) {

        if (productToAdd.getProduct() != null) {
            CompanyDto company = securityService.getLoggedInUser().getCompany();
            Company convertedCompany = mapperUtil.convert(company, new Company());

            List<InvoiceProduct> existingInvoiceProducts = invoiceProductRepository.findByInvoice_CompanyAndInvoice_InvoiceStatusAndInvoice_InvoiceTypeOrderByInsertDateTime(convertedCompany, InvoiceStatus.AWAITING_APPROVAL, InvoiceType.SALES);

            Integer totalAddedQuantity = existingInvoiceProducts.stream()
                    .filter(invoiceProduct -> invoiceProduct.getProduct().getId() == productToAdd.getProduct().getId())
                    .map(InvoiceProduct::getQuantity)
                    .reduce(0, Integer::sum);

            Integer stockQuantity = productToAdd.getProduct().getQuantityInStock();

            Integer requestedQuantity = productToAdd.getQuantity();

            if ((totalAddedQuantity + requestedQuantity) > stockQuantity) {
                String errorMessage = "There are already " + totalAddedQuantity + " " + productToAdd.getProduct().getName() + " added to other unapproved invoices. Available max. amount: " + (stockQuantity - totalAddedQuantity);
                FieldError stockError = new FieldError("newInvoiceProduct", "quantity", errorMessage);
                bindingResult.addError(stockError);
            }
        }

        return bindingResult;

    }


    @Override
    public List<InvoiceProductDto> findAllApprovedInvoiceProducts(InvoiceStatus invoiceStatus) {

        CompanyDto companyDto = securityService.getLoggedInUser().getCompany();
        Company company = mapperUtil.convert(companyDto, new Company());
        return invoiceProductRepository.findByInvoice_CompanyAndInvoice_InvoiceStatus(company,invoiceStatus).stream()
                .map(invoiceProduct -> mapperUtil.convert(invoiceProduct, new InvoiceProductDto()))
                .collect(Collectors.toList());

    }

}