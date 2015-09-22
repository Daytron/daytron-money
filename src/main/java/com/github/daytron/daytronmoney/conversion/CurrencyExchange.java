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
import com.github.daytron.daytronmoney.exception.MoneyConversionException;
import com.github.daytron.daytronmoney.exception.NegativeMoneyException;
import com.github.daytron.daytronmoney.exception.ZeroMoneyException;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class to handle all currency conversions. Can only request a single instance
 * of this class.
 *
 * @author Ryan Gilera
 */
public class CurrencyExchange {

    private Map<String, String> listOfRates;

    /**
     * Creates an instance private. Connects to API and extract latest currency
     * rates from its JSOn file. Saves the currency rates into a
     * <code>Map</code> object. Purpose of list is for currency code 
     * verification in conversion process.
     */
    private CurrencyExchange() {
        JsonObject tempObject;
        try {
            tempObject = ConversionClient.getLatestRatesJsonObject();
        } catch (MoneyConversionException ex) {
            Logger.getLogger(CurrencyExchange.class.getName())
                    .log(Level.SEVERE, null, ex);
            throw new InstantiationError("Cannot connect to the API.");
        }

        Set<Map.Entry<String, JsonElement>> rateList = tempObject.entrySet();

        // Creates a copy
        this.listOfRates = new ConcurrentHashMap<>();
        
        for (Map.Entry<String, JsonElement> rateItem : rateList) {
            listOfRates.put(rateItem.getKey(), rateItem.getValue().getAsString());
        }
    }

    /**
     * Retrieves the one and only instance of this class.
     *
     * @return <code>CurrencyExchange</code> object
     */
    public static CurrencyExchange get() {
        return MySingletonContainer.INSTANCE;
    }

    /**
     * A static inner class that holds the instance of CurrencyExchange class
     */
    private static final class MySingletonContainer {
        private static final CurrencyExchange INSTANCE = new CurrencyExchange();
    }

    /**
     * Converts a <code>Money</code> object to another currency.
     *
     * @param fromMoney <code>Money</code> object to be converted
     * @param toCurrencyCode <code>String</code> currency code to convert to
     * @return <code>Money</code> object
     * @throws com.github.daytron.daytronmoney.exception.MoneyConversionException
     */
    public Money convert(Money fromMoney, String toCurrencyCode) 
    throws MoneyConversionException {
        validateInput(fromMoney, toCurrencyCode);
        
        toCurrencyCode = toCurrencyCode.trim();
        toCurrencyCode = toCurrencyCode.toUpperCase();
        
        String rate = ConversionClient.getCurrencyRate(fromMoney, toCurrencyCode);

        if (fromMoney.getCurrencyCode().equalsIgnoreCase(toCurrencyCode)) {
            return fromMoney;
        }

        final MoneyFactory mf = new MoneyFactory(toCurrencyCode);

        // Change to new currency first to allow same currency operation
        Money baseMoney = new Money.Builder()
                .currencyCode(toCurrencyCode)
                .sign(fromMoney.getSign())
                .wholeUnit(fromMoney.getWholeUnit())
                .decimalUnit(fromMoney.getDecimalUnit())
                .leadingDecimalZeroes(fromMoney.getLeadingDecimalZeros())
                .build();

        return baseMoney.multiply(mf.valueOf(rate));
    }

    /**
     * A helper method that validates the arguments for convert() method.
     *
     * @param fromMoney <code>Money</code> to validate
     * @param toCurrencyCode <code>Money</code> object
     */
    private void validateInput(Money fromMoney, String toCurrencyCode) {
        if (fromMoney == null || toCurrencyCode == null) {
            throw new NullPointerException("Null argument detected.");
        }

        // Makes sure that currency code argument is valid 
        toCurrencyCode = toCurrencyCode.trim();
        toCurrencyCode = toCurrencyCode.toUpperCase();
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
}
