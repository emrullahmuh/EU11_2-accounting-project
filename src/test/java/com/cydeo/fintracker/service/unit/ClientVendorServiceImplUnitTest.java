package com.cydeo.fintracker.service.unit;

import com.cydeo.fintracker.dto.ClientVendorDto;
import com.cydeo.fintracker.entity.ClientVendor;
import com.cydeo.fintracker.enums.ClientVendorType;
import com.cydeo.fintracker.exception.ClientVendorNotFoundException;
import com.cydeo.fintracker.repository.ClientVendorRepository;
import com.cydeo.fintracker.service.impl.ClientVendorServiceImpl;
import com.cydeo.fintracker.util.MapperUtil;
import feign.Client;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientVendorServiceImplUnitTest {

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

        List<ClientVendorDto> result = clientVendorServiceImpl.getAll();

        verify(clientVendorRepository, times(1)).findAllByIsDeleted(false);
        verify(mapperUtil, times(2)).convert(any(ClientVendor.class), any(ClientVendorDto.class));

        assertEquals(2, result.size());
    }

    @Test
    public void testGetAllClientVendorsEmptyList() {

        when(clientVendorRepository.findAllByIsDeleted(false)).thenReturn(Optional.empty());

        Throwable throwable = assertThrows(ClientVendorNotFoundException.class,
                () -> clientVendorServiceImpl.getAll());

        verify(clientVendorRepository, times(1)).findAllByIsDeleted(false);

        assertEquals("There are no ClientVendor found", throwable.getMessage());
    }

    @Test
    public void should_throw_exception_when_client_vendor_not_found_by_id() {

        when(clientVendorRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        assertThrows(NoSuchElementException.class, () -> clientVendorServiceImpl.findById(1L));
    }

    @Test
    public void should_be_deleted_when_category_has_not_product() throws ClientVendorNotFoundException {

        ClientVendor clientVendor = new ClientVendor();

        when(clientVendorRepository.findById(1L)).thenReturn(Optional.of(clientVendor));

        clientVendorServiceImpl.delete(1L);

        assertTrue(clientVendor.getIsDeleted());

        verify(clientVendorRepository,times(1)).save(clientVendor);
    }

    @Test
    public void should_save_clientVendor_when_clientVendor_doesnt_exist(){

        ClientVendorDto clientVendorDto =new ClientVendorDto();

        when(mapperUtil.convert(Mockito.any(ClientVendorDto.class),Mockito.any(ClientVendor.class))).thenReturn(new ClientVendor());

        clientVendorServiceImpl.saveClientVendor(clientVendorDto);

        Mockito.verify(mapperUtil).convert(Mockito.eq(clientVendorDto),Mockito.any(ClientVendor.class));

        Mockito.verify(clientVendorRepository).save(Mockito.any(ClientVendor.class));

    }


}
