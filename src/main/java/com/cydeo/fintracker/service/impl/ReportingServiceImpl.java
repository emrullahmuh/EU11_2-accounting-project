package com.cydeo.fintracker.service.impl;

import com.cydeo.fintracker.dto.CompanyDto;
import com.cydeo.fintracker.entity.Company;
import com.cydeo.fintracker.enums.InvoiceType;
import com.cydeo.fintracker.service.*;
import com.cydeo.fintracker.util.MapperUtil;
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
    private final MapperUtil mapperUtil;


    @Override
    public Map<String, BigDecimal> getMonthlyProfitLoss() {
        Map<String, BigDecimal> mapOfDateAndProfitLoss = new HashMap<>();
        CompanyDto company = securityService.getLoggedInUser().getCompany();
        Long companyId = company.getId();
        LocalDateTime companyInsertDate =getCompanyInsertDate(company);
        List<LocalDate> dateKeys = listDate(companyInsertDate);
        log.info("get all month from company insert date till now");
        for (LocalDate date : dateKeys) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MMMM");
            String key = date.format(formatter);
            BigDecimal profitLoss = invoiceProductService.getProfitLossBasedOnMonth
                    (date.getYear(), date.getMonthValue(), companyId, InvoiceType.SALES);
            mapOfDateAndProfitLoss.put(key.toUpperCase(), profitLoss);
log.info("put date and profitLoss to map");
        }
        return mapOfDateAndProfitLoss;
    }
    public List<LocalDate> listDate(LocalDateTime localDateTime) {
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
        log.info("this method will give list of month from company insert date till now ");
        return listOfDate;
    }
    public LocalDateTime getCompanyInsertDate(CompanyDto companyDto){
       Company company= mapperUtil.convert(companyDto,new Company());
       LocalDateTime companyInsertDate = company.insertDateTime;
    return companyInsertDate;
    }

}