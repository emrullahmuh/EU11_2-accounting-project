package com.cydeo.fintracker.repository;

import com.cydeo.fintracker.entity.Company;
import com.cydeo.fintracker.entity.InvoiceProduct;
import com.cydeo.fintracker.enums.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct, Long> {

    List<InvoiceProduct> findAllByInvoiceId(Long id);

    List<InvoiceProduct> findAllByIdAndIsDeleted(Long invoiceProductId, Boolean isDeleted);

    List<InvoiceProduct> findAll();

    List<InvoiceProduct> findAllByInvoiceIdAndIsDeleted(Long id, Boolean isDeleted);

    List<InvoiceProduct> findByInvoice_CompanyAndInvoice_InvoiceStatus(Company company, InvoiceStatus invoiceStatus);

}
