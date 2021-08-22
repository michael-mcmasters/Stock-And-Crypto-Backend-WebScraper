package com.mcmasters.webscraper.controllers;

import com.mcmasters.webscraper.entities.Stock;
import com.mcmasters.webscraper.exceptions.UnableToScrapeStockException;
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
    public ResponseEntity<Stock> getStockPrice(@PathVariable String ticker) throws UnableToScrapeStockException {
        log.info("Scraping prices for stock, {}", ticker);

        Stock stock = webScraper.scrapeStockInfo(ticker);
        log.info("Retrieved prices for stock {}, {}", ticker, stock);
        return ResponseEntity.ok().body(stock);
    }

    @GetMapping("/crypto/{coin}")
    public ResponseEntity<Stock> getCyrptoPrice(@PathVariable String coin) throws UnableToScrapeStockException {
        log.info("Scraping prices for crypto, {}", coin);

        Stock crypto = webScraper.scrapeCryptoInfo(coin);
        log.info("Retrieved prices for crypto {}, {}", coin, crypto);
        return ResponseEntity.ok().body(webScraper.scrapeCryptoInfo(coin));
    }

    // If a stock/crypto/index fund can not be web scraped (such as VTSAX) then this API does not support it.
    @GetMapping("/stock/{ticker}/verify")
    public ResponseEntity<Boolean> verifyStockIsSupported(@PathVariable String ticker) {
        boolean result = tickerSupportedChecker.checkIfStockIsScrapable(ticker);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/crypto/{coin}/verify")
    public ResponseEntity<Boolean> verifyCrypIsSupported(@PathVariable String coin) {
        boolean result = tickerSupportedChecker.checkIfCryptoIsScrapable(coin);
        return ResponseEntity.ok().body(result);
    }
}
