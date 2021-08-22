package com.mcmasters.webscraper.services;

import com.mcmasters.webscraper.Entities.HistoricPrice;
import com.mcmasters.webscraper.Entities.Stock;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class WebScraper {

    private static final String robinhoodCurrentPriceQuery = "._1Nw7xfQTjIvcCkNYkwQMzL";
    private static final String robinhoodCurrentDifferenceQuery = "._27rSsse3BjeLj7Y1bhIE_9";
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
        String priceStr = robinhoodDoc.selectFirst(robinhoodCurrentPriceQuery).text();
        return convertPriceToDouble(priceStr);
    }

    // Returns list of historic prices. 1D price is scraped from Robinhood, 5D/week/month etc is scraped from barchart.
    private List<HistoricPrice> getHistoricPrices(Document robinhoodDoc, Document barchartDoc) {
        List<HistoricPrice> historicPrices = new ArrayList<>();

        String percentageStr = robinhoodDoc.selectFirst(robinhoodCurrentDifferenceQuery).text();
        String[] arr = percentageStr.split(" ");
        double price = convertPriceToDouble(arr[0]);
        double percentage = convertPercentageToDouble(arr[1]);
        historicPrices.add(new HistoricPrice(price, percentage));

        Elements elements = barchartDoc.select(barchartHistoricPriceAndDifferenceQuery);
        for (Element e : elements) {
            arr = e.text().split(" ");
            price = convertPriceToDouble(arr[9]);
            percentage = convertPercentageToDouble(arr[10]);
            historicPrices.add(new HistoricPrice(price, percentage));
        }
        return historicPrices;
    }

    // Regex removes + $ and ,
    private double convertPriceToDouble(String price) {
        price = price.replaceAll("\\+|\\$|,", "");
        return Double.parseDouble(price);
    }

    // Percentage comes in as (+3.82%) ... Regex removes ( ) $ , and %
    private double convertPercentageToDouble(String percentage) {
        percentage = percentage.replaceAll("\\(|$|,|%|\\)", "");
        return Double.parseDouble(percentage);
    }
}
