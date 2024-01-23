package com.cydeo.fintracker.dto;

import lombok.Data;

@Data
public class CountryDto {

    private Long id;
    private String name;
    private String iso2;

}

/*
Example Json Response
[
    {
        "id": 1,
        "name": "Afghanistan",
        "iso2": "AF"
    },
    {
        "id": 2,
        "name": "Aland Islands",
        "iso2": "AX"
    },
    {
        "id": 3,
        "name": "Albania",
        "iso2": "AL"
    }
]
 */