package com.cydeo.fintracker.dto;

import com.cydeo.fintracker.enums.CompanyStatus;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyDto {

    private Long id;

    @NotBlank(message = "Title is a required field.")
    @Size(min = 2, max = 100, message = "Title should be 2-100 characters long.")
    private String title;

    @NotBlank(message = "Phone Number is a required field.")
    @Pattern(regexp = "\\+\\d{1,3} \\(\\d{3}\\) \\d{3}-\\d{4}", message = "Phone number should have a valid format. Ex: +1 (222) 333-4444")
    private String phone;

    @NotBlank(message = "Website is a required field.")
    @Pattern(regexp = "^https?://www\\.[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Website should have a valid format. Ex: https://www.cydeo.com or http://www.cydeo.com")
    private String website = "https://www.";

    @Valid
    private AddressDto address;

    private CompanyStatus companyStatus;

}
