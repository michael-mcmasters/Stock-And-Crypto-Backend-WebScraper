# Stock-And-Crypto-Backend-WebScraper
A REST API that returns the current price of the given stock or cryptocurrency by web scraping various websites. Built using Spring.
<br>
[You can see the React front end here.](https://github.com/michael-mcmasters/Stock-And-Crypto-Tracker)

## To Run Locally
Clone this repo, open the project in your IDE of choice, and in the WebScraperApplication class run the main() method.
<br>
The project will run on port 8080.
<br>
Then try out the following endpoints.

## Endpoints
Get stock's price and history
<br>
http://localhost:8080/stock/twtr
```json
{
    "tickerName": "TWTR",
    "type": "stock",
    "currentPrice": 62.52,
    "day": {
        "priceDifference": 0.47,
        "percentage": 0.76
    },
    "week": {
        "priceDifference": -2.3,
        "percentage": -3.55
    },
    "month": {
        "priceDifference": -5.42,
        "percentage": -7.98
    },
    "ytd": {
        "priceDifference": 7.81,
        "percentage": 14.28
    },
    "year": {
        "priceDifference": -9.76,
        "percentage": -13.5
    }
}
```

Get cryptocurrency's price and history
<br>
http://localhost:8080/crypto/btc
```json
{
    "tickerName": "BTC",
    "type": "crypto",
    "currentPrice": 48466.44,
    "day": {
        "priceDifference": -403.06,
        "percentage": -0.82
    },
    "week": {
        "priceDifference": 1075.9,
        "percentage": 2.26
    },
    "month": {
        "priceDifference": 18806.77,
        "percentage": 62.99
    },
    "ytd": {
        "priceDifference": 8617.2,
        "percentage": 21.52
    },
    "year": {
        "priceDifference": -6946.4,
        "percentage": -12.49
    }
}
```

Verify that the API supports a stock
<br>
http://localhost:8080/stock/twtr/verify
```json
true
```

Verify that the API supports a cryptocurrency
<br>
http://localhost:8080/crypto/ada/verify
```json
false
```