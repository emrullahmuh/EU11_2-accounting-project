package com.cydeo.fintracker.dto.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeResponse {

    private String date;
    private Usd usd;

}
