package com.cydeo.fintracker.repository;

import com.cydeo.fintracker.entity.InvoiceProduct;
import com.cydeo.fintracker.enums.InvoiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.math.BigDecimal;
import java.util.List;

public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct, Long> {
    List<InvoiceProduct> findAllByInvoiceId(Long id);

    List<InvoiceProduct> findAllByIdAndIsDeleted(Long invoiceProductId, Boolean isDeleted);

    List<InvoiceProduct> findAll();

    List<InvoiceProduct> findAllByInvoiceIdAndIsDeleted(Long id, Boolean isDeleted);


    @Query("SELECT SUM(ip.price) FROM InvoiceProduct ip " +
            "WHERE YEAR(ip.invoice.date) = :year " +
            "AND MONTH(ip.invoice.date) = :month " +
            "AND ip.invoice.company.id = :companyId " +
            "AND ip.invoice.invoiceType = :invoiceType")
    BigDecimal getTotalPriceForMonthAndCompanyAndInvoiceType(int year, int month, Long companyId, InvoiceType invoiceType);

}
