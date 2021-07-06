package com.mcmasters.webscraper.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class Scraper {

    public String scrapeCyptoPrice(String coin) throws IOException {
        return scrape("https://robinhood.com/crypto/" + coin);
    }

    public String scrapeStockPrice(String ticker) throws IOException {
        return scrape("https://robinhood.com/stocks/" + ticker);
    }

    private String scrape(String uri) throws IOException {
        Document document = Jsoup.connect(uri).get();
        Element priceElement = document.selectFirst("._1Nw7xfQTjIvcCkNYkwQMzL");
        return priceElement.text();
    }
}
