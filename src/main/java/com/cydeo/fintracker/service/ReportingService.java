package com.cydeo.fintracker.service;

import java.math.BigDecimal;
import java.util.Map;

public interface ReportingService {
    Map<String, BigDecimal> getMonthlyProfitLoss();
}
