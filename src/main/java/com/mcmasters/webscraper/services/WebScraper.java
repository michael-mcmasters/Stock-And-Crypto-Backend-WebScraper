package com.mcmasters.webscraper.services;

import com.mcmasters.webscraper.entities.HistoricPrice;
import com.mcmasters.webscraper.entities.Stock;
import com.mcmasters.webscraper.utils.StringConverter;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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


    private static final String barchartHistoricPriceAndDifferenceQuery = ".odd";


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

        double price = getCurrentPrice(robinhoodDoc);
        log.info("Price = {}", price);

        List<HistoricPrice> historicPrices = getHistoricPrices(robinhoodDoc, barchartDoc);
        log.info("historicPrices = {}", historicPrices);

        return new Stock(ticker, type, price, historicPrices);
    }

    private double getCurrentPrice(Document robinhoodDoc) {
        return robinhoodScraperService.getCurrentPrice(robinhoodDoc);
    }

    // Returns list of historic prices. 1D price is scraped from Robinhood, 5D/week/month etc is scraped from barchart.
    private List<HistoricPrice> getHistoricPrices(Document robinhoodDoc, Document barchartDoc) {
        List<HistoricPrice> historicPrices = new ArrayList<>();

        HistoricPrice historicPrice = robinhoodScraperService.getCurrentPriceAndPercentageDifference(robinhoodDoc);
        historicPrices.add(historicPrice);

        Elements elements = barchartDoc.select("barchart-table-scroll tr");
        elements.remove(0);         // Remove first row because it is the name of each column
        for (Element e : elements) {
            log.info("Barchart element for historic prices and percentages = {}", e.text());
            String[] arr = e.text().split(" ");
            double price = StringConverter.convertPriceToDouble(arr[9]);
            double percentage = StringConverter.convertPercentageToDouble(arr[10]);
            historicPrices.add(new HistoricPrice(price, percentage));
        }
        return historicPrices;
    }
}
