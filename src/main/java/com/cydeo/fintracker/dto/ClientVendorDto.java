package com.cydeo.fintracker.dto;

import com.cydeo.fintracker.enums.ClientVendorType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientVendorDto {

    private Long id;

    @NotBlank(message = "Company Name is required field")
    @Size(min = 2, max = 100, message = "Company Name must be between 2 and 50 characters long.")
    private String clientVendorName;

    @NotBlank(message = "Phone Number is required field and may be in any valid phone number format.")
    private String phone;

    @Pattern(regexp = "^(www\\.)?[a-zA-Z0-9\\-]+\\.[a-zA-Z]{2,5}$", message = "Website should have a valid format. Ex: www.cydeo.com or cydeo.com")
    private String website;

    @NotNull(message = "Please select type")
    private ClientVendorType clientVendorType;

    @Valid
    private AddressDto address;

    @Valid
    private CompanyDto company;

    private boolean hasInvoice;

}