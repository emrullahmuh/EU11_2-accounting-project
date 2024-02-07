package com.cydeo.fintracker.service;

import com.cydeo.fintracker.dto.PaymentDto;
import com.cydeo.fintracker.dto.PaymentResultDto;
import com.cydeo.fintracker.entity.PaymentResult;

import java.util.List;

public interface PaymentService {

    List<PaymentDto> listAllPaymentsByYear(int year);

    PaymentDto findPaymentById(Long id);

    PaymentResultDto processPayment(long modelId, String stripeToken);
}
