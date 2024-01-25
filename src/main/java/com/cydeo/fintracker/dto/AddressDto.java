package com.cydeo.fintracker.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDto {

    private Long id;

    @NotBlank(message = "Address is a required field.")
    @Size(min = 2, max = 100, message = "Address should have 2-100 characters long.")
    private String addressLine1;

    @Size(max = 100, message = "Address should have maximum 100 characters long.")
    private String addressLine2;

    @NotBlank(message = "City is a required field.")
    @Size(min = 2, max = 50, message = "City should have 2-100 characters long.")
    private String city;

    @NotBlank(message = "State is a required field.")
    @Size(min = 2, max = 50, message = "State should have 2-50 characters long.")
    private String state;

    @NotBlank(message =  "Country is a required field.")
    @Size(message ="Country should have 2-50 characters long.")
    private String country;

    @NotBlank(message = "Zipcode is a required field.")
    @Pattern(regexp = "^\\d{5}(-\\d{4})?$", message = "Zipcode should have a valid form. Ex: 12345 or 12345-6789")
    private String zipCode;

}
