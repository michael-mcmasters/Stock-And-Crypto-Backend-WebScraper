package com.mcmasters.webscraper.services;

import com.mcmasters.webscraper.Entities.HistoricPrice;
import com.mcmasters.webscraper.Entities.Stock;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class WebScraper {

    public Stock scrapeStockInfo(String ticker) throws IOException {
        String robinhoodURI = "https://robinhood.com/stocks/" + ticker;
        String barchartURI = "https://www.barchart.com/stocks/quotes/" + ticker + "/performance";
        return scrape(ticker, robinhoodURI, barchartURI);
    }

    public Stock scrapeCryptoInfo(String coin) throws IOException {
        String robinhoodURI = "https://robinhood.com/crypto/" + coin;
        String barchartURI = "https://www.barchart.com/crypto/quotes/%5E" + coin + "USD/performance";
        return scrape(coin, robinhoodURI, barchartURI);
    }



    // Scrapes the given websites for the current and historic prices of the given stock/crypto ticker.
    private Stock scrape(String ticker, String robinhoodURI, String barchartURI) throws IOException {
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

    // Get 1D price and percentage from Robinhood. Then get 5D, week, month, ytd and year from barchart.
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
