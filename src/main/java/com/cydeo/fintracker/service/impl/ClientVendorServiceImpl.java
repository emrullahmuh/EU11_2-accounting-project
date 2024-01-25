package com.cydeo.fintracker.service.impl;


import com.cydeo.fintracker.dto.ClientVendorDto;
import com.cydeo.fintracker.dto.CompanyDto;
import com.cydeo.fintracker.dto.UserDto;
import com.cydeo.fintracker.entity.ClientVendor;
import com.cydeo.fintracker.entity.Company;
import com.cydeo.fintracker.enums.ClientVendorType;
import com.cydeo.fintracker.exception.ClientVendorNotFoundException;
import com.cydeo.fintracker.repository.ClientVendorRepository;
import com.cydeo.fintracker.service.ClientVendorService;
import com.cydeo.fintracker.service.SecurityService;
import com.cydeo.fintracker.util.MapperUtil;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
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
    public List<ClientVendorDto> getAllClientVendors(ClientVendorType clientVendorType) {

        Optional<List<ClientVendor>> storedClientVendors = clientVendorRepository.findByClientVendorType(clientVendorType);

        if (storedClientVendors.isEmpty()) {
            throw new NoSuchElementException();

        }
        List<ClientVendor> clientVendors = storedClientVendors.get();
        return clientVendors.stream()
                .map(each -> mapperUtil.convert(each, new ClientVendorDto()))
                .collect(Collectors.toList());

    }

    @Override
    public List<ClientVendorDto> getAll() {
        Long loggedUserCompanyId = securityService.getLoggedInUser().getCompany().getId();

        List<ClientVendor> clientVendorListByCompanyId = clientVendorRepository.findAllByCompany_IdAndIsDeleted(loggedUserCompanyId, false);

        List<ClientVendor> clientVendorlist = clientVendorRepository.findAllByCompany_IdAndIsDeleted(loggedUserCompanyId, false);

        if (clientVendorlist.isEmpty()) {
            throw new ClientVendorNotFoundException("There are no ClientVendor found");
        }

        return clientVendorlist.stream().sorted(Comparator.comparing(ClientVendor::getClientVendorType).reversed().thenComparing(ClientVendor::getClientVendorName)).map(clientVendor ->
                mapperUtil.convert(clientVendor, new ClientVendorDto())).collect(Collectors.toList());
    }

    @Override
    public ClientVendorDto findById(Long id) {

        ClientVendor clientVendor = clientVendorRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Client vendor cannot be found: " + id));
        return mapperUtil.convert(clientVendor, new ClientVendorDto());
    }

    @Override
    public ClientVendorDto findByClientVendorName(String username) {

        ClientVendor clientVendor = clientVendorRepository.findByClientVendorNameAndIsDeleted(username, false);
        return mapperUtil.convert(clientVendor, new ClientVendorDto());
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
    public ClientVendorDto update(Long id, ClientVendorDto clientVendor) {

        //Find current ClientVendor
        ClientVendor clientVendor1 = clientVendorRepository.findById(id).orElseThrow();

        //update clientVendor dto to entity object
        ClientVendor convertedClientVendor = mapperUtil.convert(clientVendor, new ClientVendor());

        //set id to the converted object
        convertedClientVendor.setId(clientVendor1.getId());

        //save the updated clientVendor in the db
        clientVendorRepository.save(convertedClientVendor);

        ClientVendor saved = clientVendorRepository.save(convertedClientVendor);
        return mapperUtil.convert(saved, new ClientVendorDto());
    }

    @Override
    public void delete(Long id) {

        ClientVendor clientVendorId = clientVendorRepository.findById(id).orElseThrow();
        clientVendorId.setIsDeleted(Boolean.TRUE);
        clientVendorRepository.save(clientVendorId);

    }
}
