package com.mcmasters.webscraper.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class Stock {

    private String tickerName;
    private String price;
    Map<String, String> historicPrices;

//    private String dayPrice;
//    private String dayPercentage;
//
//    private String weekPrice;
//    private String weekPercentage;
//
//    private String monthPrice;
//    private String monthPercentage;
//
//    private String YTDPrice;
//    private String YTDPercentage;
//
//    private String yearPrice;
//    private String yearPercentage;
}
