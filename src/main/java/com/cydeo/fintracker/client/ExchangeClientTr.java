package com.cydeo.fintracker.client;

import com.cydeo.fintracker.dto.response.ExchangeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(url="https://open.er-api.com/v6/latest/TRY",name = "exchangeClientTr")
public interface ExchangeClientTr {
    @GetMapping(value = "",consumes = MediaType.APPLICATION_JSON_VALUE)
    ExchangeResponse getExchangesRatesTr();
}
