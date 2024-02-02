package com.cydeo.fintracker.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Usd {

    private BigDecimal EUR;
    private BigDecimal GBP;
    private BigDecimal CAD;
    private BigDecimal JPY;
    private BigDecimal TRY;

    public BigDecimal getEuro() {
        return EUR;
    }

    public BigDecimal getBritishPound() {
        return GBP;
    }

    public BigDecimal getCanadianDollar() {
        return CAD;
    }

    public BigDecimal getJapaneseYen() {
        return JPY;
    }

    public BigDecimal getTurkishLira() {
        return TRY;
    }
}
