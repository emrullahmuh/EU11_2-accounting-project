package com.cydeo.fintracker.service;



import com.cydeo.fintracker.dto.ClientVendorDto;
import com.cydeo.fintracker.enums.ClientVendorType;

import java.util.List;

public interface ClientVendorService {

    List<ClientVendorDto> getAllClientVendors(ClientVendorType clientVendorType);

    List<ClientVendorDto> getAll();

    List<ClientVendorDto> getAllClientVendorsCompany();

    ClientVendorDto findById(Long id);

    ClientVendorDto findByClientVendorName(String username);

    ClientVendorDto saveClientVendor(ClientVendorDto clientVendorDto);

    ClientVendorDto update(Long id, ClientVendorDto clientVendor);

    void delete(Long id);
  
    boolean isClientHasInvoice(Long id);
}
