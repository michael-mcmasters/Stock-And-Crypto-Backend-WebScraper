package com.mcmasters.webscraper.services;

import com.mcmasters.webscraper.Entities.Stock;
import com.mcmasters.webscraper.util.Tuple;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Scraper {

    // Get current price from robinhood
    // Get percentage change. If 1 day, from robinhood. If more, from other site.

    // Get historic price,
    // Get current price
    // Calculate percentage this way

    public Stock scrapeCyptoPrice(String coin, String time) throws IOException {
//        String uri = "https://robinhood.com/crypto/" + coin;
//        Document document = Jsoup.connect(uri).get();
//
//        String price = getCurrentPrice(document);
//        Tuple<String, String> difference = get1DPercentageDifference(document);
//        String priceDifference = difference.x;
//        String percentageDifference = difference.y;
//
//        return new Stock(coin, price, priceDifference, percentageDifference);



//        String currentPriceURI = "https://robinhood.com/crypto/" + coin;
//        String historicPriceURI = getHistoricPriceURI(coin, false);
//        Document currentPriceDoc = Jsoup.connect(currentPriceURI).get();
//        Document historicPriceDoc = Jsoup.connect(historicPriceURI).get();

        Document currentPriceDoc = Jsoup.connect("https://robinhood.com/crypto/" + coin).get();
        Document historicPriceDoc = Jsoup.connect(getHistoricPriceURI(coin, false)).get();

        String price = getCurrentPrice(currentPriceDoc);
        Map<String, String> historicPrices = getHistoricPrices(currentPriceDoc, historicPriceDoc);
        return new Stock(coin, price, historicPrices);



//        String uri = "https://robinhood.com/crypto/" + coin;
//        Document document = Jsoup.connect(uri).get();
//
//        String currentPrice = getCurrentPrice(document);
//        List<Tuple<String, String>> list = getHistoryAndPercentages(document);
//
//        Stock s = new Stock();
//        s.setDayPrice(list.get(0).x);
//        s.setDayPrice(list.get(0).y);
//        s.setMonthPrice(list.get(0).x);
//        s.setMonthPrice(list.get(0).y);
//        // etc ...



//        String price = scrape("https://robinhood.com/crypto/" + coin);
//        return new Stock(coin, price);
    }

    public Stock scrapeStockPrice(String ticker) throws IOException {
//        String price = scrape("https://robinhood.com/stocks/" + ticker);
//        return new Stock(ticker, price);
        return null;
    }

    private String getCurrentPrice(Document document) {
        String priceStr = document.selectFirst("._1Nw7xfQTjIvcCkNYkwQMzL").text();
        if (priceStr.startsWith("$")) {
            priceStr = priceStr.substring(1);
        }
        return priceStr;
    }

    private String getHistoricPriceURI(String ticker, boolean isStock) {
        if (isStock) {
            return "https://www.barchart.com/stocks/quotes/" + ticker + "/performance";
        } else {
            return "https://www.barchart.com/crypto/quotes/%5E" + ticker + "USD/performance";
        }
    }

    private Map<String, String> getHistoricPrices(Document currentPriceDoc, Document historicePriceDoc) {
        Map<String, String> map = new HashMap<>();

        String percentageStr = currentPriceDoc.selectFirst("._27rSsse3BjeLj7Y1bhIE_9").text();
        String[] arr = percentageStr.split(" ");
        map.put(arr[0], arr[1]);

        Elements elements = historicePriceDoc.select(".odd");
        for (Element e : elements) {
            arr = e.text().split(" ");
            System.out.println(arr[0]);
            System.out.println(arr[10]);
            System.out.println("\b");
            map.put(arr[0], arr[10]);
        }
        return map;
    }

    private Tuple<String, String> get1DPercentageDifference(Document document) {
        String percentageStr = document.selectFirst("._27rSsse3BjeLj7Y1bhIE_9").text();
        String[] arr = percentageStr.split(" ");
        return new Tuple<>(arr[0], arr[1]);
    }



    private String scrape(String uri) throws IOException {
        Document document = Jsoup.connect(uri).get();
        String priceStr = document.selectFirst("._1Nw7xfQTjIvcCkNYkwQMzL").text();
        if (priceStr.startsWith("$")) {
            priceStr = priceStr.substring(1);
        }

        String percentageStr = document.selectFirst("._27rSsse3BjeLj7Y1bhIE_9").text();
        String[] arr = percentageStr.split(" ");
        return "";
    }



    public static void main(String[] args) throws IOException, InterruptedException {
        String uri = "https://robinhood.com/crypto/eth";

        Document document = Jsoup.connect(uri).get();
        Element priceElement = document.selectFirst("._1Nw7xfQTjIvcCkNYkwQMzL");
        String text = priceElement.text();
        if (text.startsWith("$")) {
            text = text.substring(1);
        }

        Element percentageElement = document.selectFirst("._27rSsse3BjeLj7Y1bhIE_9");
        System.out.println(percentageElement.text());
    }

    public String getHistoricPrice(String ticker, boolean isStock) throws IOException {
        String uri = "";
        if (isStock) {
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
            return arr[10];
        }
        return null;
    }
}
