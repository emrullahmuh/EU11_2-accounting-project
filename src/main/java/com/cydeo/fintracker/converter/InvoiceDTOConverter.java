package com.cydeo.fintracker.converter;

import com.cydeo.fintracker.dto.InvoiceDto;
import com.cydeo.fintracker.service.InvoiceService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class InvoiceDTOConverter implements Converter<String, InvoiceDto> {

    private final InvoiceService invoiceService;

    public InvoiceDTOConverter(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Override
    public InvoiceDto convert(String id) {

        if (id == null || id.equals("")) {
            return null;
        }

        return invoiceService.findById(Long.parseLong(id));

    }
}
