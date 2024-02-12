package com.cydeo.fintracker.service.impl;

import com.cydeo.fintracker.dto.PaymentDto;
import com.cydeo.fintracker.dto.PaymentResultDto;
import com.cydeo.fintracker.dto.UserDto;
import com.cydeo.fintracker.entity.Payment;
import com.cydeo.fintracker.enums.Months;
import com.cydeo.fintracker.exception.PaymentException;
import com.cydeo.fintracker.repository.CompanyRepository;
import com.cydeo.fintracker.repository.PaymentRepository;
import com.cydeo.fintracker.service.PaymentService;
import com.cydeo.fintracker.service.SecurityService;
import com.cydeo.fintracker.util.MapperUtil;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;
    private final CompanyRepository companyRepository;
    @Value("${stripe.api.key.secret}")
    private String stripeSecretKey;

    @Value("${stripe.api.key}")
    private String stripePublishableKey;

    public PaymentServiceImpl(PaymentRepository paymentRepository, MapperUtil mapperUtil, SecurityService securityService, CompanyRepository companyRepository) {
        this.paymentRepository = paymentRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
        this.companyRepository = companyRepository;
    }

    @Override
    public List<PaymentDto> listAllPaymentsByYear(int year) {

        UserDto userDto = securityService.getLoggedInUser();
        Long loggedUserCompanyId = userDto.getCompany().getId();

        List<Payment> payments = paymentRepository.findAll().stream()
                .filter(payment -> payment.getYear() == year)
                .filter(payment -> Objects.equals(payment.getCompany().getId(), loggedUserCompanyId))
                .collect(Collectors.toList());

        int currentYear = Year.now().getValue();


        int companyOpeningYear = companyRepository.findById(loggedUserCompanyId).get().getInsertDateTime().getYear();
        int companyOpeningMonth = companyRepository.findById(loggedUserCompanyId).get().getInsertDateTime().getMonthValue();

        for (Months month : Months.values()) {

            if (year > currentYear) {
                break;
            }


            if (year == companyOpeningYear && month.ordinal() + 1 < companyOpeningMonth) {
                continue;
            }

            if (year < companyOpeningYear) {
                break;
            }

            boolean paymentExists = payments.stream()
                    .anyMatch(payment -> payment.getMonth() == month);
            if (!paymentExists) {
                Payment payment = new Payment();
                payment.setYear(year);
                payment.setMonth(month);
                payment.setAmount(new BigDecimal("250.00"));
                payment.setCompany(companyRepository.findById(loggedUserCompanyId).get());
                payment.setCompanyStripeId(stripePublishableKey);
                paymentRepository.save(payment);
            }
        }

        payments = paymentRepository.findAll().stream()
                .filter(payment -> payment.getYear() == year)
                .filter(payment -> Objects.equals(payment.getCompany().getId(), loggedUserCompanyId))
                .collect(Collectors.toList());


        payments.sort(Comparator.comparing(payment -> payment.getMonth().ordinal()));

        return payments.stream()
                .map(payment -> mapperUtil.convert(payment, new PaymentDto()))
                .collect(Collectors.toList());
    }

    @Override
    public PaymentDto findPaymentById(Long id) {
        Optional<Payment> payment = paymentRepository.findById(id);
        if (payment.isEmpty()) {
            throw new IllegalArgumentException("Payment not found with ID: " + id);
        }
        PaymentDto paymentDto = mapperUtil.convert(payment.get(), new PaymentDto());
        return paymentDto;
    }


    @Override
    public PaymentResultDto processPayment(long modelId, String stripeToken) {

        BigDecimal amount = paymentRepository.findById(modelId).get().getAmount();
        String currency = "usd";

        Map<String, Object> params = new HashMap<>();
        params.put("amount", amount.multiply(BigDecimal.valueOf(100)).intValue());
        params.put("currency", currency);
        params.put("source", stripeToken);

        try {
            Stripe.apiKey = stripeSecretKey;

            Charge charge = Charge.create(params);

            Optional<Payment> paymentById = paymentRepository.findById(modelId);

            paymentById.get().setPaid(true);
            paymentById.get().setPaymentDate(LocalDate.now());

            paymentRepository.save(paymentById.get());

            return new PaymentResultDto(modelId, charge.getStatus(), charge.getId(), charge.getBalanceTransaction());

        } catch (StripeException e) {

            throw new PaymentException("Error processing payment: " + e.getMessage());
        }
    }
}
