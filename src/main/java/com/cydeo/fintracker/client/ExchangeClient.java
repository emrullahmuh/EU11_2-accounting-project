package com.cydeo.fintracker.client;

import com.cydeo.fintracker.dto.response.ExchangeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(url="https://jsonplaceholder.typicode.com/todos/1",name = "exchangeClient")
public interface ExchangeClient {

    @GetMapping(value = "",consumes = MediaType.APPLICATION_JSON_VALUE)
    ExchangeResponse getExchangesRates();
}
