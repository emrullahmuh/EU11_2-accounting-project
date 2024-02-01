package com.cydeo.fintracker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {

    private Long id;
    @NotNull
    @NotBlank(message = "Description is a required field")
    @Size(max = 100, min = 2, message = "Description should have 2-100 characters long")
    private String description;
    private String company;
    private boolean hasProduct;
}
