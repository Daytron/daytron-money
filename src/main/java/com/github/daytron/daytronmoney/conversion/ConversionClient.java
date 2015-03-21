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
 * A client class to extract json object from the GetExchangeRate API.
 * @author Ryan Gilera
 */
class ConversionClient {
    private static final String API_URL = 
            "http://www.getexchangerates.com/api/latest.json";

    private ConversionClient() {
    }
    
    /**
     * Connects and parse json from the external API. 
     * 
     * @return Resulting json object as <code>JsonObject</code> 
     */
    static JsonObject connectAndExtractJsonObject() {
        try {
            // Connect to the URL using java's native library
            URL url = new URL(API_URL);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

            // Parse content as JsonElement object
            JsonParser jsonParser = new JsonParser();
            JsonElement rootElement = jsonParser.parse(new InputStreamReader(
                    (InputStream) request.getContent()));             

            return rootElement.getAsJsonObject();
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(CurrencyExchange.class
                    .getName()).log(Level.SEVERE, null, ex);
            
            return null;
        } catch (IOException ex) {
            Logger.getLogger(CurrencyExchange.class
                    .getName()).log(Level.SEVERE, null, ex);
            
            return null;
        }
    }
}
