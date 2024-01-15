package com.cydeo.service.impl;

import com.cydeo.dto.ClientVendorDto;
import com.cydeo.entity.ClientVendor;
import com.cydeo.mapper.ClientVendorMapper;
import com.cydeo.repository.ClientVendorRepository;
import com.cydeo.service.ClientVendorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientVendorServiceImpl implements ClientVendorService {

    private final ClientVendorRepository clientVendorRepository;
    private final ClientVendorMapper clientVendorMapper;

    public ClientVendorServiceImpl(ClientVendorRepository clientVendorRepository, ClientVendorMapper clientVendorMapper) {
        this.clientVendorRepository = clientVendorRepository;
        this.clientVendorMapper = clientVendorMapper;
    }

    @Override
    public List<ClientVendorDto> listAllClientVendor() {
        List<ClientVendor> clientVendorlist = clientVendorRepository.findAll();
       return clientVendorlist.stream().map(clientVendorMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public ClientVendorDto findById(Long id) {
        return clientVendorMapper.convertToDto(clientVendorRepository.findById(id).get());
    }

    @Override
    public ClientVendorDto findByClientVendorName(String username) {
        ClientVendor clientVendor= clientVendorRepository.findByClientVendorNameAndIsDeleted(username,false);
        return clientVendorMapper.convertToDto(clientVendor);
    }

    @Override
    public void save(ClientVendorDto dto) {

        clientVendorRepository.save(clientVendorMapper.convertToEntity(dto));
    }

    @Override
    public ClientVendorDto update(Long id,ClientVendorDto clientVendor) {
        //Find current ClientVendor
        ClientVendor clientVendor1 = clientVendorRepository.findById(id).orElseThrow();
        //update clientVendor dto to entity object
        ClientVendor convertedClientVendor = clientVendorMapper.convertToEntity(clientVendor);
        //set id to the converted object
        convertedClientVendor.setId(clientVendor1.getId());
        //save the updated clientVendor in the db
        clientVendorRepository.save(convertedClientVendor);

        return findByClientVendorName(clientVendor.getClientVendorName());
    }

    @Override
    public void delete(Long id) {

        ClientVendor clientVendor = clientVendorRepository.findById(id).orElseThrow();
        clientVendor.setIsDeleted(Boolean.TRUE);
        clientVendorRepository.save(clientVendor);

    }
}
