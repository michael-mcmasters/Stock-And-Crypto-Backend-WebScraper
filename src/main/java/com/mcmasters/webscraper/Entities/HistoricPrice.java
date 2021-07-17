package com.mcmasters.webscraper.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;

// The price/percentage increase from the previous price/percentage (for the day, month, year, etc).

@Data
@AllArgsConstructor
public class HistoricPrice {

    private double price;
    private double percentage;

}
