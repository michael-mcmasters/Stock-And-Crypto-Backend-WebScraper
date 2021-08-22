package com.mcmasters.webscraper.services;


import com.mcmasters.webscraper.exceptions.UnableToScrapeStockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class TickerSupportedChecker {

    @Autowired
    private WebScraper webScraper;

    // Cache results for a faster response if the same ticker is sent again
    Set<String> unsupportedStocks = new HashSet<>();
    Set<String> unsupportedCryptos = new HashSet<>();

    Set<String> supportedStocks = new HashSet<>();
    Set<String> supportedCryptos = new HashSet<>();

    // If stock can not be webscraped, then this API does not support it.
    public boolean checkIfStockIsScrapable(String ticker) {
        log.info("Checking if stock {} is supported by this API", ticker);
        try {
            webScraper.scrapeStockInfo(ticker);
        } catch (UnableToScrapeStockException e) {
            log.info("Stock {} is not supported by this API", ticker);
            unsupportedStocks.add(ticker);
            return false;
        }
        log.info("Stock {} is supported by this API", ticker);
        supportedStocks.add(ticker);
        return true;
    }

    public boolean checkIfCryptoIsScrapable(String ticker) {
        log.info("Checking if crypto {} is supported by this API", ticker);
        try {
            webScraper.scrapeStockInfo(ticker);
        } catch (UnableToScrapeStockException e) {
            log.info("Crypto {} is not supported by this API", ticker);
            unsupportedCryptos.add(ticker);
            return false;
        }
        log.info("Crypto {} is supported by this API", ticker);
        supportedCryptos.add(ticker);
        return true;
    }
}











