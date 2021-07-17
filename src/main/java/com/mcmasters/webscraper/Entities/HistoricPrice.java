package com.mcmasters.webscraper.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HistoricPrice {

    private String price;
    private String percentage;
}
