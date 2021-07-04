package com.mcmasters.webscraper.controllers;

import com.mcmasters.webscraper.services.Scraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController()
public class CryptoController {

    @Autowired
    private Scraper scraper;

    @GetMapping("/bitcoin")
    public ResponseEntity<String> getBitcoinPrice() throws IOException {
        return ResponseEntity.ok().body(scraper.getBitcoinPrice());
    }

    @GetMapping("/ethereum")
    public ResponseEntity<String> getEthPrice() throws IOException {
        return ResponseEntity.ok().body(scraper.getEthereumPrice());
    }
}
