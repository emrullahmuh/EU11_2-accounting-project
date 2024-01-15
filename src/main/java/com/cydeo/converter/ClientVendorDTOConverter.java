package com.cydeo.converter;

import com.cydeo.dto.ClientVendorDto;
import com.cydeo.service.ClientVendorService;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class ClientVendorDTOConverter implements Converter<Long, ClientVendorDto> {

    private final ClientVendorService clientVendorService;

    public ClientVendorDTOConverter(@Lazy ClientVendorService clientVendorService) {
        this.clientVendorService = clientVendorService;
    }

    @Override
    public ClientVendorDto convert(Long source) {
        if (source == null || source.equals("")) {
            return null;
        }
        return clientVendorService.findById(source);
    }
}
