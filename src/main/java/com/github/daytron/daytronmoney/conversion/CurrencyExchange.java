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
import com.github.daytron.daytronmoney.currency.MoneyFactory;
import com.github.daytron.daytronmoney.exception.NegativeMoneyException;
import com.github.daytron.daytronmoney.exception.ZeroMoneyException;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Class to handle all currency conversions.
 * @author Ryan Gilera
 */
public class CurrencyExchange {
    private static final String BASE_CURRENCY_CODE = "USD";
    private static final long TIME_INTERVAL_SECONDS = 43200;
    private static final long TIME_MILLISECONDS_PER_SECOND = 1000;
    private static final String DATETIME_ELEMENT = "DateTime";
    
    private Map<String,String> listOfRates;
    private String dateStamp;
    

    private CurrencyExchange() {
        JsonObject tempObject = ConversionClient.connectAndExtractJsonObject();
        
        if (tempObject == null) {
            throw new InstantiationError("Cannot connect to API right now. Try "
                    + "again later.");
        }
        
        Set<Map.Entry<String,JsonElement>> rateList = tempObject.entrySet();
        
        // Creates a copy
        this.listOfRates = new HashMap<>();
        String date = "";
        for (Map.Entry<String, JsonElement> rateItem : rateList) {
            if (rateItem.getKey().equalsIgnoreCase(DATETIME_ELEMENT)) {
                date = rateItem.getValue().getAsString();
            } else {
                listOfRates.put(rateItem.getKey(), rateItem.getValue().getAsString());
            }
        }
        
        this.dateStamp = date;
    }
    
    public static CurrencyExchange getInstance() {
        return MySingletonContainer.INSTANCE;
    }
    
    private static class MySingletonContainer {
        private static final CurrencyExchange INSTANCE = new CurrencyExchange();
    }
    
    public Money convert(Money fromMoney, String toCurrencyCode) {
        validateInput(fromMoney, toCurrencyCode);
        
        toCurrencyCode = toCurrencyCode.trim();
        toCurrencyCode = toCurrencyCode.toUpperCase();
        
        if (fromMoney.getCurrencyCode().equalsIgnoreCase(toCurrencyCode)) {
            return fromMoney;
        }
        
        MoneyFactory mf = new MoneyFactory(toCurrencyCode);
        
        // Change to new currency first to allow same currency operation
        Money baseMoney = new Money(
                toCurrencyCode, 
                fromMoney.getSign(), 
                fromMoney.getWholeUnit(), 
                fromMoney.getDecimalUnit(), 
                fromMoney.getLeadingDecimalZeros());
        
        // If the given money match the base currency (USD)
        // do direct conversion
        if (fromMoney.getCurrencyCode().equalsIgnoreCase(BASE_CURRENCY_CODE)) {
            
            return baseMoney.multiply(mf.valueOf(listOfRates.get(toCurrencyCode)));
        } else {
            // Convert given money
            // USD/(given currency)  * (to currency)/USD * value
            // is equivalent to (to currency * value)/(given currency)
            Money denominator = mf.valueOf(listOfRates.get(fromMoney.getCurrencyCode()));
            Money toNewRate = mf.valueOf(listOfRates.get(toCurrencyCode));

            Money numerator = baseMoney.multiply(toNewRate);
            
            return numerator.divide(denominator);
        }
    }
    
    private void validateInput(Money fromMoney, String toCurrencyCode) {
        if (fromMoney == null || toCurrencyCode == null) {
            throw new NullPointerException("Null input detected.");
        }
        
        // Makes sure that currency code argument is valid 
        if (!listOfRates.containsKey(toCurrencyCode)) {
            throw new IllegalArgumentException("Invalid currency code input!");
        }
        
        // Filter negative money
        if (fromMoney.isLessThanZero()) {
            throw new NegativeMoneyException("Cannot convert negative money value.");
        }
        
        // Filter zero money
        if (fromMoney.isZero()) {
            throw new ZeroMoneyException("Cannot convert zero value.");
        }
    }
    
    public boolean connectAndTryToUpdateCurrencyRates() {
        
        long nowTime = (new Date()).getTime() / TIME_MILLISECONDS_PER_SECOND;
        long lastAccessedTime = Long.parseLong(dateStamp);
        
        long diff = nowTime - lastAccessedTime;
        
        // Can only refresh after 12 hours (43200 seconds)
        if (diff > TIME_INTERVAL_SECONDS) {
            JsonObject tempObject = ConversionClient.connectAndExtractJsonObject();

            if (tempObject == null) {
                return false;
            }

            Set<Map.Entry<String,JsonElement>> rateList = tempObject.entrySet();

            // Creates a copy without the datetime element
            this.listOfRates = new HashMap<>();
            String date = "";
            for (Map.Entry<String, JsonElement> rateItem : rateList) {
                if (rateItem.getKey().equalsIgnoreCase(DATETIME_ELEMENT)) {
                    date = rateItem.getValue().getAsString();
                } else {
                    listOfRates.put(rateItem.getKey(), rateItem.getValue().getAsString());
                }
            }

            this.dateStamp = date;
            
            return true;
        } else {
            return false;
        }
    }
    
}
