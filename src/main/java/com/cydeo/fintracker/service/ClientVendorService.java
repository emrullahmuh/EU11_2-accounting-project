package com.cydeo.fintracker.service;



import com.cydeo.fintracker.dto.ClientVendorDto;

import java.util.List;

public interface ClientVendorService {

    List<ClientVendorDto> getAllClientVendors();
    ClientVendorDto findById(Long id);
    ClientVendorDto findByClientVendorName(String username);
    ClientVendorDto saveClientVendor(ClientVendorDto clientVendorDto);
    ClientVendorDto update(Long id,ClientVendorDto clientVendor);
    void delete(Long id);
}
