package com.cydeo.fintracker.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Usd {

    //The names in dashboard.html
    private BigDecimal eur;
    private BigDecimal gbp;
    private BigDecimal cad;
    private BigDecimal jpy;
    private BigDecimal inr;

    public BigDecimal getEuro() {
        return eur;
    }

    public BigDecimal getBritishPound() {
        return gbp;
    }

    public BigDecimal getCanadianDollar() {
        return cad;
    }

    public BigDecimal getJapaneseYen() {
        return jpy;
    }

    public BigDecimal getIndianRupee() {
        return inr;
    }
}
