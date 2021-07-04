package com.mcmasters.webscraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Scraper {

    public void scrape() throws IOException {
        Document doc = Jsoup.connect("https://www.coindesk.com/price/ethereum").get();
        System.out.println(doc.title());

        Elements newsHeadlines = doc.select(".price-large");
        for (Element headline : newsHeadlines) {
            System.out.println(headline.text());
        }
    }
}
