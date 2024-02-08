package com.cydeo.fintracker.repository;

import com.cydeo.fintracker.entity.Company;
import com.cydeo.fintracker.entity.InvoiceProduct;
import com.cydeo.fintracker.enums.InvoiceStatus;

import com.cydeo.fintracker.enums.InvoiceType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.lang.annotation.Native;
import java.util.List;

public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct, Long> {

    List<InvoiceProduct> findAllByInvoiceId(Long id);

    List<InvoiceProduct> findAllByIdAndIsDeleted(Long invoiceProductId, Boolean isDeleted);

    List<InvoiceProduct> findAll();

    List<InvoiceProduct> findAllByInvoiceIdAndIsDeleted(Long id, Boolean isDeleted);


    @Query(" Select ip from InvoiceProduct ip join Invoice i on ip.invoice.id=i.id" +
            " where i.invoiceStatus=?1 " +
            "And i.invoiceType=?2 And i.company.title=?3 And ip.product.id=?4 AND ip.remainingQuantity>0 ")
    List<InvoiceProduct> findAllByInvoiceProductsCompanyProductQuantityGreaterThanZero(InvoiceStatus invoiceStatus, InvoiceType invoiceType, String companyTitle, Long productId);

    List<InvoiceProduct> findByInvoice_CompanyAndInvoice_InvoiceStatusAndInvoice_InvoiceTypeOrderByInsertDateTime(Company company, InvoiceStatus invoiceStatus, InvoiceType invoiceType);

    List<InvoiceProduct> findByInvoice_CompanyAndInvoice_InvoiceStatus(Company company, InvoiceStatus invoiceStatus);


}
