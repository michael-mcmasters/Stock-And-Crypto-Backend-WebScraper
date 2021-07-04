package com.mcmasters.webscraper.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class Scraper {

    public String getBitcoinPrice() throws IOException {
        return scrape("bitcoin");
    }

    public String getEthereumPrice() throws IOException {
        return scrape("ethereum");
    }

    private String scrape(String coin) throws IOException {
        Document document = Jsoup.connect("https://www.coindesk.com/price/" + coin).get();
        Elements newsHeadlines = document.select(".price-large");
        return newsHeadlines.get(0).text();
    }
}
