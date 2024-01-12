package com.cydeo.dto;

import com.cydeo.enums.ClientVendorType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientVendorDto {

    private Long id;
    private String clientVendorName;
    private String phone;
    private String website;
    private ClientVendorType clientVendorType;
    private AddressDto address;
    private CompanyDto company;
    private boolean hasInvoice;

    }
/* 4. "ClientVendorDTO" class should have below fields:
 Long id
- String clientVendorName
- String phone
- String website
- ClientVendorType clientVendorType
- AddressDto address
- CompanyDto company
- boolean hasInvoice (only DTO)
5. Uncomment clientVendor part of "data.sql" file and verify that sample data saved to the database

 */