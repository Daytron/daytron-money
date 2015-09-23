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
import java.time.LocalDateTime;
import java.time.Month;
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
    private static final LocalDateTime MIN_DATE_ALLOWED = LocalDateTime.of(2000,
            Month.JANUARY, 1, 0, 0);

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
     * @throws
     * com.github.daytron.daytronmoney.exception.MoneyConversionException
     */
    public Money convert(Money fromMoney, String toCurrencyCode)
            throws MoneyConversionException {
        return convert(fromMoney, toCurrencyCode, null);
    }

    /**
     * Convert a <code>Money</code> object to another currency on a specific 
     * point in time. Allowed historical currency conversion all the way back 
     * to year 2000. Some currency rates are not available on some dates, throws 
     * a MoneyConversionException if the rate or base is not available.
     * 
     * @param fromMoney <code>Money</code> object to be converted
     * @param toCurrencyCode <code>String</code> currency code to convert to
     * @param date date at which point the rate is retrieved
     * @return <code>Money</code> object
     * @throws MoneyConversionException For any error encountered
     */
    public Money convert(Money fromMoney, String toCurrencyCode,
            LocalDateTime date) throws MoneyConversionException {
        boolean isHistoricalConversion = true;
        if (date == null) {
            isHistoricalConversion = false;
        } else if (date.isBefore(MIN_DATE_ALLOWED)) {
            throw new MoneyConversionException("Cannot retrieve "
                    + "historical rate before year 2000.");
        } else if (date.isAfter(LocalDateTime.now())) {
            throw new MoneyConversionException("Cannot process future date.");
        } else if (isDateGivenToday(date)) {
            isHistoricalConversion = false;
        }

        validateMoney(fromMoney);
        validateCurrencyCode(toCurrencyCode);

        toCurrencyCode = toCurrencyCode.trim();
        toCurrencyCode = toCurrencyCode.toUpperCase();

        String rate;
        if (isHistoricalConversion) {
            rate = ConversionClient.getCurrencyRate(fromMoney.getCurrencyCode(),
                    toCurrencyCode, date);
        } else {
            rate = ConversionClient.getCurrencyRate(fromMoney.getCurrencyCode(),
                    toCurrencyCode);
        }

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
     * Verifies if given date is today.
     * 
     * @param date To compare with today's date
     * @return true if date is today, otherwise false
     */
    private boolean isDateGivenToday(LocalDateTime date) {
        LocalDateTime now = LocalDateTime.now();
        return date.getYear() == now.getYear()
                && date.getMonthValue() == now.getMonthValue()
                && date.getDayOfMonth() == now.getDayOfMonth();
    }

    /**
     * Get currency rate for a particular currency with a base currency. Returns
     * null if MoneyConversionException occurred. Cause runtime exceptions if
     * invalid arguments are presented.
     *
     * @param baseCurrency Base currency
     * @param toCurrency currency of the rate return
     * @return Currency rate in String value
     */
    public String getCurrencyRate(String baseCurrency, String toCurrency) {
        validateCurrencyCode(baseCurrency);
        validateCurrencyCode(toCurrency);

        try {
            String rate = ConversionClient.getCurrencyRate(baseCurrency, toCurrency);
            return rate;
        } catch (MoneyConversionException ex) {
            return null;
        }

    }

    /**
     * A helper method that validates the arguments for convert() method.
     *
     * @param fromMoney <code>Money</code> to validate
     * @param toCurrencyCode <code>Money</code> object
     */
    private void validateMoney(Money fromMoney) {
        if (fromMoney == null) {
            throw new NullPointerException("Null argument Money detected.");
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

    private void validateCurrencyCode(String currencyCode) {
        if (currencyCode == null) {
            throw new NullPointerException("Currency null value.");
        }

        currencyCode = currencyCode.trim();
        if (currencyCode.isEmpty()) {
            throw new IllegalArgumentException("Empty currency value.");
        }

        currencyCode = currencyCode.toUpperCase();
        if (!listOfRates.containsKey(currencyCode)) {
            throw new IllegalArgumentException("Currency code not recognized.");
        }
    }
}
