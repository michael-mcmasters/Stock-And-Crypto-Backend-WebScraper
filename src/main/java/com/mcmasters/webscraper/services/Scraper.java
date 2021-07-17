package com.mcmasters.webscraper.services;

import com.mcmasters.webscraper.Entities.HistoricPrice;
import com.mcmasters.webscraper.Entities.Stock;
import com.mcmasters.webscraper.util.Tuple;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class Scraper {

    public Stock scrapeCyptoPrice(String coin, String time) throws IOException {
        String robinhoodURI = "https://robinhood.com/crypto/" + coin;
        String barchartURI = "https://www.barchart.com/crypto/quotes/%5E" + coin + "USD/performance";
        return createNewStock(coin, robinhoodURI, barchartURI);
    }

    public Stock scrapeStockPrice(String ticker) throws IOException {
        String robinhoodURI = "https://robinhood.com/stocks/" + ticker;
        String barchartURI = "https://www.barchart.com/stocks/quotes/" + ticker + "/performance";
        return createNewStock(ticker, robinhoodURI, barchartURI);
    }



    private Stock createNewStock(String ticker, String robinhoodURI, String barchartURI) throws IOException {
        Document robinhoodDoc = Jsoup.connect(robinhoodURI).get();
        Document barchartDoc = Jsoup.connect(barchartURI).get();

        String price = getCurrentPrice(robinhoodDoc);
        List<HistoricPrice> historicPrices = getHistoricPrices(robinhoodDoc, barchartDoc);
        return new Stock(ticker, price, historicPrices);
    }

    private String getCurrentPrice(Document document) {
        String priceStr = document.selectFirst("._1Nw7xfQTjIvcCkNYkwQMzL").text();
        if (priceStr.startsWith("$")) {
            priceStr = priceStr.substring(1);
        }
        return priceStr;
    }

    private List<HistoricPrice> getHistoricPrices(Document robinhoodDoc, Document barchartDoc) {
        List<HistoricPrice> historicPrices = new ArrayList<>();

        String percentageStr = robinhoodDoc.selectFirst("._27rSsse3BjeLj7Y1bhIE_9").text();
        String[] arr = percentageStr.split(" ");
        String price = arr[0];
        String percentage = formatPercentage(arr[1]);
        historicPrices.add(new HistoricPrice(price, percentage));

        Elements elements = barchartDoc.select(".odd");
        for (Element e : elements) {
            arr = e.text().split(" ");
            price = arr[9];
            percentage = formatPercentage(arr[10]);
            historicPrices.add(new HistoricPrice(price, percentage));
        }
        return historicPrices;
    }

    // Percentage comes in as (+3.82%) ... This removes the parentheses and percentage sign.
    private String formatPercentage(String percentage) {
        return percentage.substring(1, percentage.length() - 2);
    }
}
