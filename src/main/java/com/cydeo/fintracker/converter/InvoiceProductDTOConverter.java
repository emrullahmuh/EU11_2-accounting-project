package com.cydeo.fintracker.converter;

import com.cydeo.fintracker.dto.InvoiceProductDto;
import com.cydeo.fintracker.service.InvoiceProductService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class InvoiceProductDTOConverter implements Converter<String, InvoiceProductDto> {
    private final InvoiceProductService invoiceProductService;

    public InvoiceProductDTOConverter(InvoiceProductService invoiceProductService) {
        this.invoiceProductService = invoiceProductService;
    }

    @Override
    public InvoiceProductDto convert(String id) {

        if (id == null || id.equals("")) {
            return null;
        }

        return invoiceProductService.findById(Long.parseLong(id));
    }
}
