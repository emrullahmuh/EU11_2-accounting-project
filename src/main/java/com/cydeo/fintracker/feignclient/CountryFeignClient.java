package com.cydeo.fintracker.feignclient;

import com.cydeo.fintracker.dto.CountryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@Component
@FeignClient(name = "COUNTRY-FEIGN-CLIENT", url = "https://api.countrystatecity.in/v1")
public interface CountryFeignClient {

    @GetMapping("/countries")
    ResponseEntity<List<CountryDto>> getApiCountries(@RequestHeader("X-CSCAPI-KEY") String apiCountriesKey);

}
