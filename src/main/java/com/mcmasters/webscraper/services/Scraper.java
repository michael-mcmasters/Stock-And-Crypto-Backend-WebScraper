package com.mcmasters.webscraper.services;

import com.mcmasters.webscraper.Entities.Stock;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class Scraper {

    public Stock scrapeCyptoPrice(String coin) throws IOException {
        String price = scrape("https://robinhood.com/crypto/" + coin);
        return new Stock(coin, price);
    }

    public Stock scrapeStockPrice(String ticker) throws IOException {
        String price = scrape("https://robinhood.com/stocks/" + ticker);
        return new Stock(ticker, price);
    }

    private String scrape(String uri) throws IOException {
        Document document = Jsoup.connect(uri).get();
        Element priceElement = document.selectFirst("._1Nw7xfQTjIvcCkNYkwQMzL");
        String text = priceElement.text();
        if (text.startsWith("$")) {
            text = text.substring(1);
        }
        return text;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        // https://www.google.com/finance/quote/BOTZ:NASDAQ?window=5D
        // https://www.google.com/finance/quote/GME:NYSE?window=5D

        // try nasdaq, then try nyse if doesn't work.

        String ticker = "btc";
        String uri = String.format("https://www.marketwatch.com/investing/cryptocurrency/%susd", ticker);

        Document document = Jsoup.connect(uri).get();
//        System.out.println(document.outerHtml());
        Elements e = document.select("tr li");
        e.forEach(v -> System.out.println(v.text()));




//        System.out.println(e.outerHtml());

////        Document document = Jsoup.connect(uri).get();
//        Element priceElement = document.selectFirst(".enJeMd");
//        String text = priceElement.text();
//        if (text.startsWith("$")) {
//            text = text.substring(1);
//        }
//        System.out.println(text);
    }
}
