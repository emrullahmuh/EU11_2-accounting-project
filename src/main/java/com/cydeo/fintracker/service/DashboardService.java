package com.cydeo.fintracker.service;

import com.cydeo.fintracker.dto.response.Usd;

import java.math.BigDecimal;
import java.util.Map;

public interface DashboardService {
    Map<String, BigDecimal> summaryCalculation();
    Usd getAllMoney();
}
