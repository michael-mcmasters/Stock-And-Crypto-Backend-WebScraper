package com.mcmasters.webscraper.controllers;

import com.mcmasters.webscraper.Scraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class CryptoController {

    @Autowired
    private Scraper scraper;

    @GetMapping("/bitcoin")
    public String getBitcoinPrice() throws IOException {
        return scraper.getBitcoinPrice();
    }

    @GetMapping("/ethereum")
    public String getEthPrice() throws IOException {
        return scraper.getEthereumPrice();
    }
}
