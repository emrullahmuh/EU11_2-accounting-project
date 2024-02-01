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
import com.cydeo.fintracker.service.CompanyService;
import com.cydeo.fintracker.service.InvoiceService;
import com.cydeo.fintracker.service.SecurityService;
import com.cydeo.fintracker.util.MapperUtil;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClientVendorServiceImpl implements ClientVendorService {

    private final ClientVendorRepository clientVendorRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;
    private final InvoiceService invoiceService;
    private final CompanyService companyService;

    public ClientVendorServiceImpl(ClientVendorRepository clientVendorRepository, MapperUtil mapperUtil, SecurityService securityService, InvoiceService invoiceService, CompanyService companyService) {
        this.clientVendorRepository = clientVendorRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
        this.invoiceService = invoiceService;
        this.companyService = companyService;
    }


    @Override
    public List<ClientVendorDto> getAllClientVendors(ClientVendorType clientVendorType) {

        CompanyDto companyDto = companyService.getCompanyDtoByLoggedInUser().get(0);

        Company company = mapperUtil.convert(companyDto, new Company());

        Optional<List<ClientVendor>> storedClientVendors = clientVendorRepository.findAllByClientVendorTypeAndCompanyOrderByClientVendorName(ClientVendorType.VENDOR, company);

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

        List<ClientVendor> clientVendorlist = clientVendorRepository.findAllByCompany_IdAndIsDeleted(loggedUserCompanyId, false);

        if (clientVendorlist.isEmpty()) {
            return Collections.emptyList();
        }


        return clientVendorlist.stream().sorted(Comparator.comparing(ClientVendor::getClientVendorType).reversed().thenComparing(ClientVendor::getClientVendorName)).map(clientVendor ->
                mapperUtil.convert(clientVendor, new ClientVendorDto())).collect(Collectors.toList());
    }

    @Override
    public List<ClientVendorDto> getAllClientVendorsCompany() {
        UserDto loggedInUser = securityService.getLoggedInUser();
        List<ClientVendor> clientVendors = clientVendorRepository.findByCompanyIdAndIsDeleted(loggedInUser.getCompany().getId(),false);
        return clientVendors.stream().map(clientVendor -> {
                    boolean hasInvoice = isClientHasInvoice(clientVendor.getId());
                    ClientVendorDto convert = mapperUtil.convert(clientVendor, new ClientVendorDto());
                    convert.setHasInvoice(hasInvoice);
                    return convert;
                })
                .collect(Collectors.toList());
    }

    public boolean isClientHasInvoice(Long id) {
        return invoiceService.existsByClientVendorId(id);
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
        convertedClientVendor.setCompany( clientVendor1.getCompany() );

        //save the updated clientVendor in the db
        clientVendorRepository.save(convertedClientVendor);

        ClientVendor saved = clientVendorRepository.save(convertedClientVendor);
        return mapperUtil.convert(saved, new ClientVendorDto());
    }

    @Override
    public void delete(Long id) {

        Optional<ClientVendor> clientVendorToBeDeleted = clientVendorRepository.findById(id);
        if (clientVendorToBeDeleted.isPresent()){
            if (!invoiceService.existsByClientVendorId(id)) {
                clientVendorToBeDeleted.get().setIsDeleted(true);
                clientVendorRepository.save(clientVendorToBeDeleted.get());
            }else {
                ClientVendorDto clientVendorDto =mapperUtil.convert(clientVendorToBeDeleted, new ClientVendorDto());
                clientVendorDto.setHasInvoice(true);
            }
        }

    }
}

