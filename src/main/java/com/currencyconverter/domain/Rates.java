package com.currencyconverter.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Rates {
    private Double mid;
    private String no;
    private String effectiveDate;
    @Override
    public String toString() {
        return "Rates{" +
                "mid=" + mid +
                '}';
    }
}
