/*
 * The MIT License
 *
 * Copyright 2015 Ryan Gilera.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.github.daytron.daytronmoney.conversion;

import com.github.daytron.daytronmoney.currency.Money;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A client class for extracting JSON object from the exchange rate API.
 *
 * @author Ryan Gilera
 */
class ConversionClient {

    private static final String API_LATEST_URL = "https://api.fixer.io/latest?";
    private static final String API_BASED_PARAM = "base=";
    private static final String API_CURRENCY_PARAM = "currencies=";

    private ConversionClient() {
    }

    /**
     * Extracts the JSON element based on the passed URL parameter.
     * 
     * @param urlString
     * @return 
     */
    private static JsonObject extractJsonElement(String urlString) {
        try {
            // Connect to the URL using Java's native library
            URL url = new URL(urlString);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

            // Parse content as JsonElement object
            JsonParser jsonParser = new JsonParser();
            JsonElement rootElement = jsonParser.parse(new InputStreamReader(
                    (InputStream) request.getContent()));

            //System.out.println("root element: \n"+rootElement);
            return  rootElement.getAsJsonObject();

        } catch (MalformedURLException ex) {
            Logger.getLogger(ConversionClient.class
                    .getName()).log(Level.SEVERE, null, ex);

            return null;
        } catch (IOException ex) {
            Logger.getLogger(ConversionClient.class
                    .getName()).log(Level.SEVERE, null, ex);

            return null;
        }
    }

    /**
     * Retrieves the latest exchange rates with USD as its base currency in 
     * the form of JsonObject. Throws NullPointerException if the JsonObject is 
     * deemed null.
     *
     * @return Resulting JSON object as <code>JsonObject</code>
     */
    static JsonObject getLatestRatesJsonObject() {
        JsonObject rootObject = extractJsonElement(API_LATEST_URL);
        if (rootObject == null) {
            throw new NullPointerException("Cannot connect to API right now. Try "
                    + "again later.");
        }

        return rootObject.getAsJsonObject("rates");

    }

    /**
     * Retrieves the latest particular exchange rate with given base currency 
     * code in Money object and the currency code to be converted to. 
     * Throws NullPointerException if the JsonObject is deemed null. The 
     * extracted rate is return as String object.
     * 
     * @param moneyToConvert The Money object to be converted
     * @param toCurrency The outcome currency code
     * @return String value of the rate extracted
     */
    static String getCurrencyRate(Money moneyToConvert, String toCurrency) {
            JsonObject rootObj = extractJsonElement(API_LATEST_URL + API_BASED_PARAM
                    + moneyToConvert.getCurrencyCode() + "&"
                    + API_CURRENCY_PARAM + toCurrency);
            
            if (rootObj == null) {
                throw new NullPointerException("Cannot connect to API right now. Try "
                        + "again later.");
            }

            JsonObject ratesObject = rootObj.getAsJsonObject("rates");
            return ratesObject.get(toCurrency).toString();
    }
}
