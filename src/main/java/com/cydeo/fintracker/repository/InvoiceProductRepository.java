package com.cydeo.fintracker.repository;

import com.cydeo.fintracker.entity.InvoiceProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct, Long> {
    List<InvoiceProduct> findAllByInvoiceId(Long id);

    List<InvoiceProduct> findAllByIdAndIsDeleted(Long invoiceProductId, Boolean isDeleted);

    List<InvoiceProduct> findAll();

    List<InvoiceProduct> findAllByInvoiceIdAndIsDeleted(Long id, Boolean isDeleted);


//@Query(value = " select Sum(i.profitLoss)"+
//        "from InvoiceProduct i "+
//        "where year (i.insertDateTime)=:year "+
//        "and month (i.insertDateTime)=:month"+"and i.Invoice")
//@Query(value = "SELECT SUM(ip.profitLoss)" +
//        "FROM InvoiceProduct ip " +
//        "WHERE YEAR(ip.insertDateTime) = :year " +
//        "AND MONTH(ip.insertDateTime) = :month " +
//        "AND ip.invoice.company.id = :companyId " +
//        "AND ip.invoice.invoiceStatus = :invoiceStatus "+
//        "AND ip.invoice.invoiceType=:invoiceType")
//   BigDecimal findByYear_Month_CompanyId_InvoiceStatus_InvoiceType(int year, int month , Long id , InvoiceStatus invoiceStatus,InvoiceType invoiceType);
@Query(value = "SELECT SUM(ip.profitLoss)" +
        "FROM InvoiceProduct ip " +
        "WHERE YEAR(ip.insertDateTime) = :year " +
        "AND MONTH(ip.insertDateTime) = :month " +
        "AND ip.invoice.company.id = :companyId " +
        "AND ip.invoice.invoiceType = :invoiceType ")
BigDecimal getTotalProfitLossForMonthAndCompanyAndInvoiceType(int year, int month, Long companyId, InvoiceType invoiceType);

}
