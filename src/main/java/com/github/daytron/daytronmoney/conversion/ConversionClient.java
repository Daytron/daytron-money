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
import com.github.daytron.daytronmoney.exception.MoneyConversionException;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * A client class for extracting JSON object from the exchange rate API.
 *
 * @author Ryan Gilera
 */
class ConversionClient {

    private static final String API_LATEST_URL = "https://api.fixer.io/latest?";
    private static final String API_BASED_PARAM = "base=";
    private static final String API_CURRENCY_PARAM = "currencies=";
    private static final String RATES_JSON_MEMBER = "rates";
    
    private ConversionClient() {
    }

    /**
     * Extracts the JSON element based on the passed URL parameter.
     * 
     * @param urlString
     * @return 
     */
    private static JsonObject extractJsonElement(String urlString) throws 
            MoneyConversionException {
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
            throw new MoneyConversionException("Invalid API URL", ex);
        } catch (IOException ex) {
            throw new MoneyConversionException("IO Exception occured.", ex);
        }
    }

    /**
     * Retrieves the latest exchange rates with USD as its base currency in 
     * the form of JsonObject. Throws NullPointerException if the JsonObject is 
     * deemed null.
     *
     * @return Resulting JSON object as <code>JsonObject</code>
     */
    static JsonObject getLatestRatesJsonObject() throws MoneyConversionException {
        JsonObject rootObject = extractJsonElement(API_LATEST_URL);
        validateJsonObject(rootObject, RATES_JSON_MEMBER);
        
        JsonObject ratesObject = rootObject.getAsJsonObject(RATES_JSON_MEMBER);
        validateJsonObject(ratesObject, "GBP"); // Can be any other code except
        // USD - since this is the base currency
        
        return ratesObject;
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
    static String getCurrencyRate(String baseCurrency, String toCurrency) 
    throws MoneyConversionException {
            JsonObject rootObj = extractJsonElement(API_LATEST_URL + API_BASED_PARAM
                    + baseCurrency + "&"
                    + API_CURRENCY_PARAM + toCurrency);
            validateJsonObject(rootObj, RATES_JSON_MEMBER);

            JsonObject ratesObject = rootObj.getAsJsonObject(RATES_JSON_MEMBER);
            validateJsonObject(ratesObject, toCurrency);
            
            return ratesObject.get(toCurrency).toString();
    }
    
    /**
     * Validates the <code>JsonObject</code> for null and invalid json format. 
     * Throws MoneyConversionException if invalid.
     * 
     * @param jsonObject The JsonObject to be inspected
     * @param member A JSON member string value for content validation
     * @throws MoneyConversionException if JsonObject is invalid
     */
    private static void validateJsonObject(JsonObject jsonObject, String member) 
    throws MoneyConversionException {
        if (jsonObject == null) {
            throw new MoneyConversionException("Cannot connect to API right now. Try "
                    + "again later.");
        }
        
        if (!jsonObject.has(member)) {
            throw new MoneyConversionException("Invalid JSON. Cannot find \"" +
                    member + "\" JSON member.");
        }
    }
}
