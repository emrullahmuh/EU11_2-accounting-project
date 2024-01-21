package com.cydeo.fintracker.converter;


import com.cydeo.fintracker.dto.ClientVendorDto;
import com.cydeo.fintracker.service.ClientVendorService;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class ClientVendorDTOConverter implements Converter<String, ClientVendorDto> {

    private final ClientVendorService clientVendorService;

    protected ClientVendorDTOConverter(@Lazy ClientVendorService clientVendorService) {
        this.clientVendorService = clientVendorService;
    }

    @Override
    public ClientVendorDto convert(String  source){

        if(source.equals("")) return null;
        Long id = Long.valueOf(source);
        return clientVendorService.findById(id);
    }

}
