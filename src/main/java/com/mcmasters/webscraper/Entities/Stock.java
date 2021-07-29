package com.mcmasters.webscraper.Entities;

import lombok.Data;

import java.util.List;

// Class is named Stock but is also used to store Crypto information.

@Data
public class Stock {

    private String tickerName;
    private String type;
    private double currentPrice;

    private HistoricPrice day;
    private HistoricPrice week;
    private HistoricPrice month;
    private HistoricPrice ytd;
    private HistoricPrice year;


    public Stock(String tickerName, String type, double price, List<HistoricPrice> historicPrices) {
        this.tickerName = tickerName.toUpperCase();
        this.type = type;
        this.currentPrice = price;

        day = historicPrices.get(0);
        week = historicPrices.get(1);
        month = historicPrices.get(2);
        ytd = historicPrices.get(3);
        year = historicPrices.get(4);
    }
}
