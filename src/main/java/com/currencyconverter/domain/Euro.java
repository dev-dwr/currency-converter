package com.currencyconverter.domain;

import lombok.Data;
import org.springframework.boot.jackson.JsonComponent;

import java.util.List;

@Data
public class Euro {
    private String table;
    private String currency;
    private String code;

    private List<Rates> rates;

    @Override
    public String toString() {
        return "Euro{" +
                "table='" + table + '\'' +
                ", currency='" + currency + '\'' +
                ", code='" + code + '\'' +
                ", rates=" + rates +
                '}';
    }
}
