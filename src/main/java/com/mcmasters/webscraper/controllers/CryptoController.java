package com.mcmasters.webscraper.controllers;

import com.mcmasters.webscraper.entities.Ticker;
import com.mcmasters.webscraper.exceptions.UnableToScrapeTickerException;
import com.mcmasters.webscraper.services.TickerSupportedChecker;
import com.mcmasters.webscraper.services.WebScraper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class CryptoController {

    @Autowired
    private WebScraper webScraper;

    @Autowired
    private TickerSupportedChecker tickerSupportedChecker;


    @GetMapping("/stock/{ticker}")
    public ResponseEntity<Ticker> getStockPrice(@PathVariable String tickerName) throws UnableToScrapeTickerException {
        log.info("Scraping prices for stock, {}", tickerName);

        Ticker stock = webScraper.scrapeStockInfo(tickerName);
        log.info("Retrieved prices for stock {}, {}", tickerName, stock);
        return ResponseEntity.ok().body(stock);
    }

    @GetMapping("/crypto/{coin}")
    public ResponseEntity<Ticker> getCyrptoPrice(@PathVariable String tickerName) throws UnableToScrapeTickerException {
        log.info("Scraping prices for crypto, {}", tickerName);

        Ticker crypto = webScraper.scrapeCryptoInfo(tickerName);
        log.info("Retrieved prices for crypto {}, {}", tickerName, crypto);
        return ResponseEntity.ok().body(crypto);
    }

    // If a stock/crypto/index fund can not be web scraped (such as VTSAX) then this API does not support it.
    @GetMapping("/stock/{ticker}/verify")
    public ResponseEntity<Boolean> verifyStockIsSupported(@PathVariable String tickerName) {
        boolean result = tickerSupportedChecker.checkIfStockIsScrapable(tickerName);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/crypto/{coin}/verify")
    public ResponseEntity<Boolean> verifyCrypIsSupported(@PathVariable String tickerName) {
        boolean result = tickerSupportedChecker.checkIfCryptoIsScrapable(tickerName);
        return ResponseEntity.ok().body(result);
    }
}
