package com.cydeo.fintracker.service.unit;

import com.cydeo.fintracker.dto.CompanyDto;
import com.cydeo.fintracker.dto.PaymentDto;
import com.cydeo.fintracker.dto.PaymentResultDto;
import com.cydeo.fintracker.dto.UserDto;
import com.cydeo.fintracker.entity.Company;
import com.cydeo.fintracker.entity.Payment;
import com.cydeo.fintracker.enums.Months;
import com.cydeo.fintracker.repository.CompanyRepository;
import com.cydeo.fintracker.repository.PaymentRepository;
import com.cydeo.fintracker.service.impl.PaymentServiceImpl;
import com.cydeo.fintracker.service.impl.SecurityServiceImpl;
import com.cydeo.fintracker.service.impl.UserServiceImpl;
import com.cydeo.fintracker.util.MapperUtil;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.hibernate.criterion.Projections.id;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplUnitTest {

    @Mock
    private UserServiceImpl userService;
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private SecurityServiceImpl securityService;
    @Mock
    private MapperUtil mapperUtil;
    @Mock
    private Charge charge;
    @Value("${stripe.api.key.secret}")
    private String stripeSecretKey;
    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Test
    void testListAllPaymentsByYear_noPaymentsExist() {
        // Arrange
        int year = 2021;
        UserDto userDto = new UserDto();
        CompanyDto companyDto = new CompanyDto();
        companyDto.setId(1L);
        companyDto.setTitle("Example Company");
        userDto.setCompany(companyDto);
        Company company = new Company();
        company.setInsertDateTime(LocalDateTime.parse("2015-01-01T00:00:00"));

        when(securityService.getLoggedInUser()).thenReturn(userDto);
        when(paymentRepository.findAll()).thenReturn(new ArrayList<>());
        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));



        // Act
        List<PaymentDto> result = paymentService.listAllPaymentsByYear(year);

        verify(paymentRepository, times(12)).save(any());
    }

    @Test
    void testListAllPaymentsByYear_paymentsExist() {
        // Arrange
        CompanyDto companyDto = new CompanyDto();
        companyDto.setId(1L);

        UserDto userDto = new UserDto();
        userDto.setCompany(companyDto);

        Company company = new Company();
        company.setId(1L);
        company.setInsertDateTime(LocalDateTime.parse("2015-01-01T00:00:00"));

        int year = 2021;

        List<Payment> payments = new ArrayList<>();
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setMonth(Months.JANUARY);
        payment.setYear(2021);
        payment.setCompany(company);
        payments.add(payment);
        Payment payment2 = new Payment();
        payment2.setId(2L);
        payment2.setMonth(Months.FEBRUARY);
        payment2.setYear(2021);
        payment2.setCompany(company);
        payments.add(payment2);

        when(securityService.getLoggedInUser()).thenReturn(userDto);
        when(paymentRepository.findAll()).thenReturn(payments);
        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));

        // Act
        List<PaymentDto> result = paymentService.listAllPaymentsByYear(year);

        verify(paymentRepository, times(10)).save(any());
    }

    @Test
    void testListAllPaymentsByYear_ifYearIsSmallerThanCompanyInsertedYear() {
        // Arrange
        CompanyDto companyDto = new CompanyDto();
        companyDto.setId(1L);

        UserDto userDto = new UserDto();
        userDto.setCompany(companyDto);

        Company company = new Company();
        company.setId(1L);
        company.setInsertDateTime(LocalDateTime.parse("2015-01-01T00:00:00"));

        int year = 2014;

        when(securityService.getLoggedInUser()).thenReturn(userDto);
        when(paymentRepository.findAll()).thenReturn(new ArrayList<>());
        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));

        // Act
        List<PaymentDto> result = paymentService.listAllPaymentsByYear(year);

        verify(paymentRepository, times(0)).save(any());
    }

    @Test
    void testListAllPaymentsByYear_ifYearIsBiggerThanCurrentYear() {
        // Arrange
        CompanyDto companyDto = new CompanyDto();
        companyDto.setId(1L);

        UserDto userDto = new UserDto();
        userDto.setCompany(companyDto);

        Company company = new Company();
        company.setId(1L);
        company.setInsertDateTime(LocalDateTime.parse("2015-01-01T00:00:00"));

        int year = 2014;

        when(securityService.getLoggedInUser()).thenReturn(userDto);
        when(paymentRepository.findAll()).thenReturn(new ArrayList<>());
        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));

        // Act
        List<PaymentDto> result = paymentService.listAllPaymentsByYear(year);

        verify(paymentRepository, times(0)).save(any());
    }

    @Test
    void testFindPaymentById_validId() {
        // Arrange
        Long id = 1L;
        Payment mockPayment = new Payment();
        mockPayment.setId(id);
        PaymentDto mockPaymentDto = new PaymentDto();
        mockPaymentDto.setId(id);

        when(paymentRepository.findById(id)).thenReturn(Optional.of(mockPayment));
        when(mapperUtil.convert(mockPayment, new PaymentDto())).thenReturn(mockPaymentDto);

        // Act
        PaymentDto result = paymentService.findPaymentById(id);

        // Assert
        assertNotNull(result);
        assertEquals(mockPayment.getId(), result.getId());
    }

    @Test
    void testFindPaymentById_invalidId() {
        // Arrange
        Long id = 2L;
        when(paymentRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> paymentService.findPaymentById(id));
    }



}