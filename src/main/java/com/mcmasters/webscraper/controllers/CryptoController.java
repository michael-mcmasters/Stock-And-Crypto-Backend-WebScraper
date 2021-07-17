package com.mcmasters.webscraper.controllers;

import com.mcmasters.webscraper.Entities.Stock;
import com.mcmasters.webscraper.services.WebScraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:3001")
public class CryptoController {

    @Autowired
    private WebScraper webScraper;


    @GetMapping("/stock/{ticker}")
    public ResponseEntity<Stock> getStockPrice(@PathVariable String ticker) throws IOException {
        return ResponseEntity.ok().body(webScraper.scrapeStockInfo(ticker));
    }

    @GetMapping("/crypto/{coin}")
    public ResponseEntity<Stock> getPrice(@PathVariable String coin) throws IOException {
        return ResponseEntity.ok().body(webScraper.scrapeCryptoInfo(coin));
    }
}
