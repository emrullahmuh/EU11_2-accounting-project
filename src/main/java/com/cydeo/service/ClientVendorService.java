package com.cydeo.service;

import com.cydeo.dto.ClientVendorDto;

import java.util.List;

public interface ClientVendorService {

    List<ClientVendorDto> listAllClientVendor();
    ClientVendorDto findById(Long id);
    ClientVendorDto findByClientVendorName(String username);
    void save(ClientVendorDto dto);
    ClientVendorDto update(Long id,ClientVendorDto clientVendor);
    void delete(Long id);
}
