package com.mcmasters.webscraper.exceptions;

public class UnableToScrapeStockException extends Exception {

    public UnableToScrapeStockException(String errorMessage) {
        super(errorMessage);
    }
}
