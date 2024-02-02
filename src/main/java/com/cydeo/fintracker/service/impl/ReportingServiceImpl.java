package com.cydeo.fintracker.service.impl;

import com.cydeo.fintracker.dto.CompanyDto;
import com.cydeo.fintracker.dto.InvoiceDto;
import com.cydeo.fintracker.dto.InvoiceProductDto;
import com.cydeo.fintracker.entity.Company;
import com.cydeo.fintracker.entity.Invoice;
import com.cydeo.fintracker.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportingServiceImpl implements ReportingService {
    private final SecurityService securityService;
    private final InvoiceProductService invoiceProductService;
    private final CompanyService companyService;
    private final InvoiceService invoiceService;

    @Override
    public Map<String, BigDecimal> getMonthlyProfitLoss() {
        Map <String ,BigDecimal> map=new HashMap<>();
        CompanyDto company = securityService.getLoggedInUser().getCompany();
        Long companyId = company.getId();
        List<LocalDate>listOfDate=listDate();
        for (LocalDate date:listOfDate) {

            BigDecimal profitLoss= invoiceProductService.getProfitLossBasedOnMonth(date.getYear(),date.getMonth(), companyId);
            map.put(date.toString(),profitLoss);
        }

        //  InvoiceDto firstInvoice=invoiceService.findById(1L);



        return map;
    }
    public List<LocalDate>listDate(){
        InvoiceDto firstInvoice=invoiceService.findById(1L);
        LocalDate localDate=firstInvoice.getDate();
        LocalDate now =LocalDate.now();
        List<LocalDate>listOfDate=new ArrayList<>();
        while (localDate.isBefore(now)){
            listOfDate.add(now);
            now.minusMonths(1);
        }

        return listOfDate; }

}