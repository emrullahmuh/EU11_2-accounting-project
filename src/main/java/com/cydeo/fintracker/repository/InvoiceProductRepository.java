package com.cydeo.fintracker.repository;

import com.cydeo.fintracker.entity.InvoiceProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct, Long> {
    List<InvoiceProduct> findAllByInvoiceId(Long id);

    List<InvoiceProduct> findAllByIdAndIsDeleted(Long invoiceProductId, Boolean isDeleted);

    List<InvoiceProduct> findAll();

    List<InvoiceProduct> findAllByInvoiceIdAndIsDeleted(Long id, Boolean isDeleted);
}
