package com.cydeo.fintracker.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.cydeo.fintracker.dto.ClientVendorDto;
import com.cydeo.fintracker.entity.ClientVendor;
import com.cydeo.fintracker.exception.ClientVendorNotFoundException;
import com.cydeo.fintracker.repository.ClientVendorRepository;
import com.cydeo.fintracker.util.MapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ClientVendorServiceImplTest {

    @Mock
    private ClientVendorRepository clientVendorRepository;

    @Mock
    private MapperUtil mapperUtil;

    @InjectMocks
    private ClientVendorServiceImpl clientVendorServiceImpl;

    @Test
    public void testGetAllClientVendors() {

        ClientVendor clientVendor1 = new ClientVendor();
        ClientVendor clientVendor2 = new ClientVendor();

        List<ClientVendor> mockClientVendorList = Arrays.asList(clientVendor1, clientVendor2);

        when(clientVendorRepository.findAllByIsDeleted(false)).thenReturn(Optional.of(mockClientVendorList));

        ClientVendorDto clientVendorDto1 = new ClientVendorDto();
        ClientVendorDto clientVendorDto2 = new ClientVendorDto();

        when(mapperUtil.convert(any(ClientVendor.class), any(ClientVendorDto.class)))
                .thenReturn(clientVendorDto1, clientVendorDto2);

        List<ClientVendorDto> result = clientVendorServiceImpl.getAllClientVendors();

        verify(clientVendorRepository, times(1)).findAllByIsDeleted(false);
        verify(mapperUtil, times(2)).convert(any(ClientVendor.class), any(ClientVendorDto.class));

        assertEquals(2, result.size());
    }

    @Test
    public void testGetAllClientVendorsEmptyList() {

        when(clientVendorRepository.findAllByIsDeleted(false)).thenReturn(Optional.empty());

        Throwable throwable = assertThrows(ClientVendorNotFoundException.class,
                () -> clientVendorServiceImpl.getAllClientVendors());

        verify(clientVendorRepository, times(1)).findAllByIsDeleted(false);

        assertEquals("There are no ClientVendor found", throwable.getMessage());
    }
}
