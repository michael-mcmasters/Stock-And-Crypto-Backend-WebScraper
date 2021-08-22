package com.mcmasters.webscraper.entities;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// A Ticker can be a Stock or Crypto.

@Slf4j
@Data
public class Ticker {

    private String tickerName;
    private String type;
    private double currentPrice;

    Map<String, PriceChange> priceChanges = new LinkedHashMap<>();      // day, week, month, ytd, year


    public Ticker(String tickerName, String type, double price, List<PriceChange> priceChangesList) {
        this.tickerName = tickerName.toUpperCase();
        this.type = type;
        this.currentPrice = price;

        if (priceChangesList.size() < 4)
            log.warn("Expected priceChanges list to have a size of 4 but it instead has a size of {}", priceChangesList.size());

        if (priceChangesList.size() > 0)
            priceChanges.put("day", priceChangesList.get(0));
        if (priceChangesList.size() > 1)
            priceChanges.put("week", priceChangesList.get(1));
        if (priceChangesList.size() > 2)
            priceChanges.put("month", priceChangesList.get(2));
        if (priceChangesList.size() > 3)
            priceChanges.put("ytd", priceChangesList.get(3));
        if (priceChangesList.size() > 4)
            priceChanges.put("year", priceChangesList.get(4));
    }
}
