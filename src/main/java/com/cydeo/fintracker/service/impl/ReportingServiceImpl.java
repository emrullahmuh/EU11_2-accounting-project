package com.cydeo.fintracker.service.impl;

import com.cydeo.fintracker.dto.CompanyDto;
import com.cydeo.fintracker.entity.Company;
import com.cydeo.fintracker.enums.InvoiceType;
import com.cydeo.fintracker.service.*;
import com.cydeo.fintracker.util.MapperUtil;
import com.cydeo.fintracker.dto.InvoiceProductDto;
import com.cydeo.fintracker.entity.Invoice;
import com.cydeo.fintracker.entity.InvoiceProduct;
import com.cydeo.fintracker.enums.InvoiceStatus;
import com.cydeo.fintracker.service.InvoiceProductService;
import com.cydeo.fintracker.service.InvoiceService;
import com.cydeo.fintracker.service.ReportingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportingServiceImpl implements ReportingService {

    private final SecurityService securityService;
    private final InvoiceProductService invoiceProductService;
    private final CompanyService companyService;
    private final InvoiceService invoiceService;
    private final MapperUtil mapperUtil;


    @Override
    public Map<String, BigDecimal> getMonthlyProfitLoss() {

        Map<String, BigDecimal> mapOfDateAndProfitLoss = new HashMap<>();
        CompanyDto company = companyService.getCompanyDtoByLoggedInUser().get(0);

        Long companyId = company.getId();
        LocalDateTime companyInsertDate = company.getInsertDate();

        List<LocalDate> dateKeys = listDate(companyInsertDate);

        for (LocalDate date : dateKeys) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MMMM");
            String key = date.format(formatter);

            BigDecimal profitLoss = invoiceProductService.getProfitLossBasedOnMonth
                    (date.getYear(), date.getMonthValue(), companyId, InvoiceType.SALES);
            mapOfDateAndProfitLoss.put(key.toUpperCase(), profitLoss);

        }

        log.info("Monthly profit/loss data has been retrieved for company with ID {}.", companyId);

        return mapOfDateAndProfitLoss;
    }

    public List<LocalDate> listDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            throw new IllegalArgumentException("localDateTime cannot be null");
        }

        int year = localDateTime.getYear();
        Month month = localDateTime.getMonth();
        int day = localDateTime.getDayOfMonth();

        LocalDate startDate = LocalDate.of(year, month, day);
        LocalDate now = LocalDate.now();

        List<LocalDate> listOfDate = new ArrayList<>();

        while (startDate.isBefore(now)) {
            listOfDate.add(startDate);
            startDate = startDate.plusMonths(1);

        }
        log.info("list of months from company insert date till now: {} ", listOfDate);
        return listOfDate;
    }


    public List<InvoiceProductDto> generateStockReport() {

        return invoiceProductService.findAllApprovedInvoiceProducts(InvoiceStatus.APPROVED);
    }

}
