package com.mcmasters.webscraper.controllers;

import com.mcmasters.webscraper.Entities.Stock;
import com.mcmasters.webscraper.services.WebScraper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class CryptoController {

    @Autowired
    private WebScraper webScraper;



    @GetMapping("/stock/{ticker}")
    public ResponseEntity<Stock> getStockPrice(@PathVariable String ticker) throws IOException {
        log.info("Scraping prices for stock, {}", ticker);
        Stock stock = webScraper.scrapeStockInfo(ticker);

        log.info("Retrieved prices for stock {}, {}", ticker, stock);
        return ResponseEntity.ok().body(stock);
    }

    @GetMapping("/crypto/{coin}")
    public ResponseEntity<Stock> getCyrptoPrice(@PathVariable String coin) throws IOException {
        log.info("Scraping prices for crypto, {}", coin);
        Stock crypto = webScraper.scrapeCryptoInfo(coin);

        log.info("Retrieved prices for crypto {}, {}", coin, crypto);
        return ResponseEntity.ok().body(webScraper.scrapeCryptoInfo(coin));
    }

    // Some stocks or index funds such as VTSAX can not be web scraped.
    // This returns if the given ticker is supported by this program.
    @GetMapping("/stock/{ticker}/verify")
    public ResponseEntity<Boolean> verifyStockCanBeFound(@PathVariable String ticker) {
        boolean result = true;
        return ResponseEntity.ok().body(result);      // TODO
    }

    @GetMapping("/crypto/{coin}/verify")
    public ResponseEntity<Boolean> verifyCryptoCanbeFound(@PathVariable String coin) {
        boolean result = true;
        return ResponseEntity.ok().body(result);      // TODO
    }
}
