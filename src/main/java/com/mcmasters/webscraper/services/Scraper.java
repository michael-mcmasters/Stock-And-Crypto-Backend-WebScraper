package com.mcmasters.webscraper.services;

import com.mcmasters.webscraper.Entities.Stock;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.stream.Collectors;

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
        String ticker = "doge";
        boolean stock = false;

        String uri = "";
        if (stock) {
            uri = "https://www.barchart.com/stocks/quotes/" + ticker + "/performance";
        } else {
            uri = "https://www.barchart.com/crypto/quotes/%5E" + ticker + "USD/performance";
        }

        Document document = Jsoup.connect(uri).get();
        Elements elements = document.select(".odd");
        for (Element e : elements) {
            String[] arr = e.text().split(" ");
            System.out.println(arr[0]);
            System.out.println(arr[10]);
            System.out.println("\b");
        }
    }
}
