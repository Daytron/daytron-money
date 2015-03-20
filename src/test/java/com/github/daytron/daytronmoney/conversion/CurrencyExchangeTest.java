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
import com.github.daytron.daytronmoney.currency.SignValue;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ryan Gilera
 */
public class CurrencyExchangeTest {
    
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
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getInstance method, of class CurrencyExchange.
     */
    @Test
    public void testGetInstance() {
        // When
        CurrencyExchange result = CurrencyExchange.getInstance();
        
        // Then
        assertNotNull(result);
    }

    /**
     * Test of convert method, of class CurrencyExchange.
     */
    @Test
    public void testConvert() {
        // Given
        CurrencyExchange cex = CurrencyExchange.getInstance();
        Money fromMoney = new Money("USD", 
                SignValue.Positive, 2, 50, 0);
        
        String toCurrencyCode = "PHP";
        String expCurrency = "PHP";
        // When
        Money result = cex.convert(fromMoney, toCurrencyCode);
        
        // Then
        assertEquals(expCurrency, result.getCurrencyCode());
        assertNotNull(result);
    }

    /**
     * Test of connectAndTryToUpdateCurrencyRates method, of class CurrencyExchange.
     */
    @Test
    public void testConnectAndTryToUpdateCurrencyRates() {
        // Given
        CurrencyExchange instance = CurrencyExchange.getInstance();
        boolean expResult = false;
        
        // When
        boolean result = instance.connectAndTryToUpdateCurrencyRates();
        
        // Then
        assertEquals(expResult, result);
    }
    
}