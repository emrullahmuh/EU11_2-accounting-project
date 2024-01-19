package com.cydeo.fintracker.dto;

import com.cydeo.fintracker.enums.CompanyStatus;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyDto {

    private Long id;

    private String title;

    private String phone;

    private String website;

    private AddressDto address;

    private CompanyStatus companyStatus;

}
