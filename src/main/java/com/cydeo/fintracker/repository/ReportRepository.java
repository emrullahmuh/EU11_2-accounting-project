package com.cydeo.fintracker.repository;

import com.cydeo.fintracker.entity.ReportView;
import com.cydeo.fintracker.enums.InvoiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface ReportRepository extends JpaRepository<ReportView, Long> {


    @Query("SELECT rv.year, rv.month, rv.invoiceType, SUM(rv.totalPrice) AS totalPrice " +
            "FROM ReportView rv " +
            "WHERE rv.company.id = :companyId " +
            "GROUP BY rv.year, rv.month, rv.invoiceType")
    List<Object[]> findReportsByCompanyIdAndInvoiceType(Long companyId);

}

