package com.cydeo.service.impl;

import com.cydeo.dto.ClientVendorDto;
import com.cydeo.dto.CompanyDto;
import com.cydeo.dto.UserDto;
import com.cydeo.entity.ClientVendor;
import com.cydeo.entity.Company;
import com.cydeo.repository.ClientVendorRepository;
import com.cydeo.service.ClientVendorService;
import com.cydeo.service.SecurityService;
import com.cydeo.util.MapperUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ClientVendorServiceImpl implements ClientVendorService {

    private final ClientVendorRepository clientVendorRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;

    public ClientVendorServiceImpl(ClientVendorRepository clientVendorRepository, MapperUtil mapperUtil, SecurityService securityService) {
        this.clientVendorRepository = clientVendorRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
    }

    @Override
    public List<ClientVendorDto> listAllClientVendor() {

        List<ClientVendor> clientVendorlist = clientVendorRepository.findAll();
        return clientVendorlist.stream().map(clientVendor ->
                mapperUtil.convert(clientVendor,new ClientVendorDto())).collect(Collectors.toList());
    }

    @Override
    public ClientVendorDto findById(Long id) {

        ClientVendor clientVendor = clientVendorRepository.findById(id).orElseThrow(()->
                new NoSuchElementException("Client vendor cannot be found: "+id));
        return mapperUtil.convert(clientVendor,new ClientVendorDto());
    }

    @Override
    public ClientVendorDto findByClientVendorName(String username) {

        ClientVendor clientVendor= clientVendorRepository.findByClientVendorNameAndIsDeleted(username,false);
        return mapperUtil.convert(clientVendor,new ClientVendorDto());
    }

    @Override
    public ClientVendorDto saveClientVendor(ClientVendorDto clientVendorDto) {

        // security
        UserDto loggedInUser = securityService.getLoggedInUser();
        CompanyDto companyDto = loggedInUser.getCompany();

        // create company object
        Company company = mapperUtil.convert(companyDto, new Company());

        //convert dto to entity
        ClientVendor clientVendorToSave = mapperUtil.convert(clientVendorDto, new ClientVendor());

        // assign company info
        clientVendorToSave.setCompany(company);

        // saved DB
        ClientVendor savedClientVendor = clientVendorRepository.save(clientVendorToSave);

        //return converted object
        return mapperUtil.convert(savedClientVendor, new ClientVendorDto());

    }

    @Override
    public ClientVendorDto update(Long id,ClientVendorDto clientVendor) {

        //Find current ClientVendor
        ClientVendor clientVendor1 = clientVendorRepository.findById(id).orElseThrow();

        //update clientVendor dto to entity object
        ClientVendor convertedClientVendor = mapperUtil.convert(clientVendor, new ClientVendor());

        //set id to the converted object
        convertedClientVendor.setId(clientVendor1.getId());

        //save the updated clientVendor in the db
        clientVendorRepository.save(convertedClientVendor);

        ClientVendor saved = clientVendorRepository.save(convertedClientVendor);
        return mapperUtil.convert(saved,new ClientVendorDto());
    }

    @Override
    public void delete(Long id) {

        ClientVendor clientVendorId = clientVendorRepository.findById(id).orElseThrow();
        clientVendorId.setIsDeleted(Boolean.TRUE);
        clientVendorRepository.save(clientVendorId);

    }
}
