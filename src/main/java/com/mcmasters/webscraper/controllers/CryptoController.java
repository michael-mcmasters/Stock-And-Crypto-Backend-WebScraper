package com.mcmasters.webscraper.controllers;

import com.mcmasters.webscraper.Entities.Stock;
import com.mcmasters.webscraper.services.Scraper;
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
    private Scraper scraper;


    @GetMapping("/crypto/{coin}/{time}")
    public ResponseEntity<Stock> getPrice(@PathVariable String coin, @PathVariable String time) throws IOException {
        return ResponseEntity.ok().body(scraper.scrapeCyptoPrice(coin, time));
    }

    @GetMapping("/stock/{ticker}/{time}")
    public ResponseEntity<Stock> getStockPrice(@PathVariable String ticker, @PathVariable String time) throws IOException {
        return ResponseEntity.ok().body(scraper.scrapeStockPrice(ticker));
    }
}
