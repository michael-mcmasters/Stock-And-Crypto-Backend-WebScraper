package com.mcmasters.webscraper.services;

import com.mcmasters.webscraper.entities.HistoricPrice;
import com.mcmasters.webscraper.entities.Stock;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class WebScraper {

    @Autowired
    private RobinhoodScraperService robinhoodScraperService;

    @Autowired
    private BarchartScraperService barchartScraperService;

    public Stock scrapeStockInfo(String stockTicker) throws IOException {
        try {
            String robinhoodURI = "https://robinhood.com/stocks/" + stockTicker;
            String barchartURI = "https://www.barchart.com/stocks/quotes/" + stockTicker + "/performance";
            return scrape(stockTicker, "stock", robinhoodURI, barchartURI);
        } catch (Exception exc) {
            System.out.println(exc.getMessage());
            throw new IOException("Unable to fetch stock " + stockTicker);
        }
    }

    public Stock scrapeCryptoInfo(String coinTicker) throws IOException {
        try {
            String robinhoodURI = "https://robinhood.com/crypto/" + coinTicker;
            String barchartURI = "https://www.barchart.com/crypto/quotes/%5E" + coinTicker + "USD/performance";
            return scrape(coinTicker, "crypto", robinhoodURI, barchartURI);
        } catch (Exception exc) {
            throw new IOException("Unable to fetch crypto " + coinTicker);
        }
    }

    // Scrapes the given websites for the given stock/crypto ticker and returns data as a Stock object.
    private Stock scrape(String ticker, String type, String robinhoodURI, String barchartURI) throws IOException {
        log.info("Scraping for robinhoodURI = {} and barchartURI = {}", robinhoodURI, barchartURI);
        Document robinhoodDoc = Jsoup.connect(robinhoodURI).get();
        Document barchartDoc = Jsoup.connect(barchartURI).get();

        double price = robinhoodScraperService.scrapeCurrentPrice(robinhoodDoc);
        List<HistoricPrice> historicPrices = scrapeHistoricPrices(robinhoodDoc, barchartDoc);
        log.info("Price = {}", price);
        log.info("historicPrices = {}", historicPrices);

        return new Stock(ticker, type, price, historicPrices);
    }

    // Returns list of historic prices. 1D price is scraped from Robinhood, 5D/week/month etc is scraped from barchart.
    private List<HistoricPrice> scrapeHistoricPrices(Document robinhoodDoc, Document barchartDoc) {
        List<HistoricPrice> historicPrices = new ArrayList<>();

        // ToDo: Maybe change HistoricPrice name to PriceDifference?
        HistoricPrice todaysPriceDifference = robinhoodScraperService.scrapeTodaysHistoricPrice(robinhoodDoc);
        historicPrices.add(todaysPriceDifference);

        List<HistoricPrice> allHistoricPrices = barchartScraperService.scrapeHistoricPrices(barchartDoc);
        historicPrices.addAll(allHistoricPrices);
        return historicPrices;
    }
}







