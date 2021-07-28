package com.currencyconverter.controller;

import com.currencyconverter.domain.Currency;
import com.currencyconverter.domain.Euro;
import com.currencyconverter.domain.Rates;
import com.currencyconverter.repository.CurrencyRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/")
@RequiredArgsConstructor
public class CurrencyController {
    private final ObjectMapper objectMapper;
    private final CurrencyRepository currencyRepository;
    @GetMapping
    public ModelAndView show(){
        List<Currency> currencies = (List<Currency>) currencyRepository.findAll();
        return new ModelAndView("index", "value", currencies.isEmpty() ? null: currencies.get(0).getValue());
    }
    @PostMapping("/currency")
    public ModelAndView convertedValue(@RequestParam Double value){
        final String url = "http://api.nbp.pl/api/exchangerates/rates/a/eur/?format=json";

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);

        try {
            List<Currency> values = (List<Currency>)currencyRepository.findAll();
            if(!values.isEmpty()){
                currencyRepository.delete(values.get(0));
            }
            Euro convertedToJson = objectMapper.readValue(result, Euro.class);
            double euroValue = (double) convertedToJson.getRates().get(0).getMid();
            currencyRepository.save(new Currency(value / euroValue));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }



        return new ModelAndView("redirect:/");
    }
}
