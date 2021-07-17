package com.mcmasters.webscraper.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class HistoricPrice {

    private String price;
    private String percentage;
}
