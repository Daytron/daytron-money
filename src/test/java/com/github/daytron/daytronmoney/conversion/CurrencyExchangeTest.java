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
import java.time.LocalDateTime;
import java.time.Month;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for CurrencyExchange
 *
 * @author Ryan Gilera
 */
public class CurrencyExchangeTest {

    private CurrencyExchange cex;

    public CurrencyExchangeTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        cex = CurrencyExchange.get();
    }

    @After
    public void tearDown() {
        cex = null;
    }

    /**
     * Test of get method, of class CurrencyExchange.
     */
    @Test
    public void testGetInstance() {
        assertNotNull(cex);
    }

    /**
     * Test of convert method, of class CurrencyExchange.
     */
    @Test
    public void testConvert() {
        // Given
        Money fromMoney = new Money.Builder()
                .currencyCode("GBP")
                .wholeUnit(1)
                .build();

        String toCurrencyCode = "PHP";
        String expCurrency = "PHP";

        // When
        Money result;
        try {
            result = cex.convert(fromMoney, toCurrencyCode);

            // Then
            assertEquals(expCurrency, result.getCurrencyCode());
            assertNotNull(result);
        } catch (MoneyConversionException ex) {
            fail("MoneyConversionException has occurred. " + ex.getLocalizedMessage());
        }

    }

    /**
     * Test of convert method with the same currency, of class CurrencyExchange.
     */
    public void testConvertWithSameCurrency() {
        // Given
        Money fromMoney = new Money.Builder()
                .currencyCode("GBP")
                .wholeUnit(1)
                .build();
        String toCurrencyCode = "GBP";
        String expCurrency = "GBP";

        Money expMoney = new Money.Builder()
                .currencyCode("GBP")
                .wholeUnit(1)
                .build();

        // When
        Money result;
        try {
            result = cex.convert(fromMoney, toCurrencyCode);

            // Then
            assertEquals(expCurrency, result.getCurrencyCode());
            assertNotNull(result);
            assertEquals(expMoney, result);
        } catch (MoneyConversionException ex) {
            fail("MoneyConversionException has occurred. " + ex.getLocalizedMessage());
        }
    }

    /**
     * Test of getCurrencyRate method, of class CurrencyExchange.
     */
    @Test
    public void testGetCurrencyRate() {
        // Given
        String baseCurrency = "CAD";
        String toCurrency = "GBP";

        // When
        String result = cex.getCurrencyRate(baseCurrency, toCurrency);

        // Then
        assertNotNull(result);
    }

    /**
     * Test of getCurrencyRate method with null arguments, of class CurrencyExchange.
     */
    @Test(expected = NullPointerException.class)
    public void testNullCurrencyCode() {
        String result = cex.getCurrencyRate(null, null);
    }

    /**
     * Test of getCurrencyRate method with empty String arguments, 
     * of class CurrencyExchange.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testEmptyCurrencyCode() {
        String result = cex.getCurrencyRate("", "");
    }

    /**
     * Test of getCurrencyRate method with invalid code arguments, 
     * of class CurrencyExchange.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidCurrencyCode() {
        String result = cex.getCurrencyRate("QWERTY", "ASDF");
    }

    /**
     * Test of convert method historical, of class CurrencyExchange.
     */
    @Test
    public void testConvertHistorical() {
        // Given
        LocalDateTime dateTime = LocalDateTime.of(2001, Month.MARCH, 2, 3, 31);

        Money fromMoney = new Money.Builder()
                .currencyCode("GBP")
                .wholeUnit(1)
                .build();

        String toCurrencyCode = "CAD";
        String expCurrency = "CAD";

        // When
        Money result;
        try {
            result = cex.convert(fromMoney, toCurrencyCode, dateTime);

            // Then
            assertEquals(expCurrency, result.getCurrencyCode());
            assertNotNull(result);
        } catch (MoneyConversionException ex) {
            fail("MoneyConversionException has occurred. " + ex.getLocalizedMessage());
        }
    }

    /**
     * Test of convert method historical date before 2000, of class CurrencyExchange.
     */
    @Test(expected = MoneyConversionException.class)
    public void testConvertHistoricalBefore2000() throws MoneyConversionException {
        // Given
        LocalDateTime dateTime = LocalDateTime.of(1999, Month.MARCH, 2, 3, 31);

        Money fromMoney = new Money.Builder()
                .currencyCode("GBP")
                .wholeUnit(1)
                .build();

        String toCurrencyCode = "CAD";
        String expCurrency = "CAD";

        // When
        Money result = cex.convert(fromMoney, toCurrencyCode, dateTime);
    }
    
    /**
     * Test of convert method historical with future date, of class CurrencyExchange.
     */
    @Test(expected = MoneyConversionException.class)
    public void testConvertHistoricalFuture() throws MoneyConversionException {
        // Given
        LocalDateTime dateTime = LocalDateTime.of(2031, Month.MARCH, 2, 3, 31);

        Money fromMoney = new Money.Builder()
                .currencyCode("GBP")
                .wholeUnit(1)
                .build();

        String toCurrencyCode = "CAD";
        String expCurrency = "CAD";

        // When
        Money result = cex.convert(fromMoney, toCurrencyCode, dateTime);
    }
    
    /**
     * Test of convert method historical with null date, of class CurrencyExchange.
     */
    @Test
    public void testConvertHistoricalNull() {
        // Given
        LocalDateTime dateTime = null;

        Money fromMoney = new Money.Builder()
                .currencyCode("GBP")
                .wholeUnit(1)
                .build();

        String toCurrencyCode = "CAD";
        String expCurrency = "CAD";

        // When
        Money result;
        try {
            result = cex.convert(fromMoney, toCurrencyCode);

            // Then
            assertEquals(expCurrency, result.getCurrencyCode());
            assertNotNull(result);
        } catch (MoneyConversionException ex) {
            fail("MoneyConversionException has occurred. " + ex.getLocalizedMessage());
        }
    }
    
    /**
     * Test of convert method historical with today's date, of class CurrencyExchange.
     */
    @Test
    public void testConvertHistoricalToday() {
        // Given
        LocalDateTime dateTime = LocalDateTime.now();

        Money fromMoney = new Money.Builder()
                .currencyCode("GBP")
                .wholeUnit(1)
                .build();

        String toCurrencyCode = "CAD";
        String expCurrency = "CAD";

        // When
        Money result;
        try {
            result = cex.convert(fromMoney, toCurrencyCode);

            // Then
            assertEquals(expCurrency, result.getCurrencyCode());
            assertNotNull(result);
        } catch (MoneyConversionException ex) {
            fail("MoneyConversionException has occurred. " + ex.getLocalizedMessage());
        }
    }

}
