package com.cydeo.fintracker.converter;

import com.cydeo.fintracker.dto.PaymentDto;
import com.cydeo.fintracker.service.PaymentService;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PaymentDtoConverter implements Converter<String, PaymentDto> {

    private final PaymentService paymentService;

    public PaymentDtoConverter(@Lazy PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public PaymentDto convert(String id) {

        if (id.isBlank()) return null;

        return paymentService.findPaymentById(Long.valueOf(id));
    }
}
