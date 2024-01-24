package com.cydeo.fintracker.repository;

import com.cydeo.fintracker.entity.Company;
import com.cydeo.fintracker.entity.Invoice;
import com.cydeo.fintracker.enums.InvoiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findAllByInvoiceTypeAndCompanyOrderByInvoiceNoDesc(InvoiceType invoiceType, Company company);

    List<Invoice> findAllByInvoiceTypeAndCompanyAndIsDeletedOrderByInvoiceNoDesc(InvoiceType invoiceType, Company company, Boolean isDeleted);

    @Query("SELECT i FROM Invoice i WHERE i.invoiceType = ?1 AND i.company = ?2 ORDER BY i.invoiceNo DESC ")
    List<Invoice> retrieveInvoiceByTypeAndCompany(InvoiceType invoiceType, Company company);

}
