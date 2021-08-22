package com.mcmasters.webscraper.entities;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

// Class is named Stock but is also used to store Crypto information.

@Slf4j
@Data
public class Stock {

    private String tickerName;
    private String type;
    private double currentPrice;

    private PriceChange day;
    private PriceChange week;
    private PriceChange month;
    private PriceChange ytd;
    private PriceChange year;


    public Stock(String tickerName, String type, double price, List<PriceChange> priceChanges) {
        this.tickerName = tickerName.toUpperCase();
        this.type = type;
        this.currentPrice = price;

        if (priceChanges.size() < 4)
            log.warn("Expected historicPrices to have a size of 4 but it instead has a size of {}", priceChanges.size());

        day = priceChanges.get(0);
        week = priceChanges.get(1);
        month = priceChanges.get(2);
        ytd = priceChanges.get(3);
        year = priceChanges.get(4);
    }
}
