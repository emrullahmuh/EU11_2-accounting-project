package com.cydeo.fintracker.service.impl;

import com.cydeo.fintracker.dto.CompanyDto;
import com.cydeo.fintracker.enums.InvoiceType;
import com.cydeo.fintracker.repository.ReportRepository;
import com.cydeo.fintracker.service.*;
import com.cydeo.fintracker.dto.InvoiceProductDto;
import com.cydeo.fintracker.enums.InvoiceStatus;
import com.cydeo.fintracker.service.InvoiceProductService;
import com.cydeo.fintracker.service.ReportingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Month;
import java.util.*;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportingServiceImpl implements ReportingService {


    private final InvoiceProductService invoiceProductService;
    private final CompanyService companyService;
    private final ReportRepository reportRepository;


    @Override
    public Map<String, String> getMonthlyProfitLoss() {
        CompanyDto company = companyService.getCompanyDtoByLoggedInUser().get(0);
        Long companyId = company.getId();


        List<Object[]> results = reportRepository.findReportsByCompanyIdAndInvoiceType(companyId);

        Map<String, String> reportData = new HashMap<>();

        for (Object[] result : results) {
            int year = (int) result[0];
            int month = (int) result[1];
            String period = year + " " + Month.of(month).toString();
            InvoiceType invoiceType = (InvoiceType) result[2];
            BigDecimal totalProfitLoss = (BigDecimal) result[3];

            String value = "";
            if (invoiceType.equals(InvoiceType.SALES)) {
                value = "+ " + totalProfitLoss.toString();

            } else if (invoiceType.equals(InvoiceType.PURCHASE)) {
                value = "- " + totalProfitLoss.toString();
            }
            reportData.put(period, value);
        }
        log.info("Monthly profit/loss data has been retrieved successfully for company with ID {}. Total {} data points retrieved for the period.", companyId, reportData.size());

        return reportData;
    }


    @Override
    public List<InvoiceProductDto> generateStockReport() {

        return invoiceProductService.findAllApprovedInvoiceProducts(InvoiceStatus.APPROVED);
    }

}
