package com.cydeo.fintracker.service.impl;

import com.cydeo.fintracker.service.ReportingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportingServiceImpl implements ReportingService {
    @Override
    public Map<String, BigDecimal> getMonthlyProfitLoss() {
        return null;
    }
}
