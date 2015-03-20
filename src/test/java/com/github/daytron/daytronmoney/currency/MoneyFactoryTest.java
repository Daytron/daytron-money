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
package com.github.daytron.daytronmoney.currency;

import java.util.Currency;
import java.util.Locale;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for MoneyFactory
 * @author Ryan Gilera
 */
public class MoneyFactoryTest {
    
    public MoneyFactoryTest() {
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
     * Test of getCurrencyCode method, of class MoneyFactory.
     */
    @Test
    public void testGetCurrencyCode() {
        // Case 1: MoneyFactory()
        // Given
        MoneyFactory moneyFactory = new MoneyFactory();
        String expCurrencyCode = 
                Currency.getInstance(Locale.getDefault()).getCurrencyCode();
        
        // When
        String currencyCodeResult = moneyFactory.getCurrencyCode();
        
        // Then
        assertEquals(expCurrencyCode, currencyCodeResult);
        
        // Case 2: MoneyFactory(String currencyCode)
        // Given
        MoneyFactory moneyFactory2 = new MoneyFactory("USD");
        String expCurrencyCode2 = "USD";
        
        // When
        String currencyCodeResult2 = moneyFactory2.getCurrencyCode();
        
        // Then
        assertEquals(expCurrencyCode2, currencyCodeResult2);
        
        // Case 3: MoneyFactory(Locale locale)
        // Given
        MoneyFactory moneyFactory3 = new MoneyFactory(Locale.getDefault());
        String expCurrencyCode3 = 
                Currency.getInstance(Locale.getDefault()).getCurrencyCode();
        
        // When
        String currencyCodeResult3 = moneyFactory3.getCurrencyCode();
        
        // Then
        assertEquals(expCurrencyCode3, currencyCodeResult3);
    }

    /**
     * Test of setCurrencyCode method, of class MoneyFactory.
     */
    @Test
    public void testSetCurrencyCode() {
        // Case 1: MoneyFactory()
        // Given
        MoneyFactory moneyFactory = new MoneyFactory();
        moneyFactory.setCurrencyCode("PHP");
        String expCurrencyCode = "PHP";
        
        // When
        String currencyCodeResult = moneyFactory.getCurrencyCode();
        
        // Then
        assertEquals(expCurrencyCode, currencyCodeResult);
        
        // Case 2: MoneyFactory(String currencyCode)
        // Given
        MoneyFactory moneyFactory2 = new MoneyFactory();
        moneyFactory2.setCurrencyCode("USD");
        String expCurrencyCode2 = "USD";
        
        // When
        String currencyCodeResult2 = moneyFactory2.getCurrencyCode();
        
        // Then
        assertEquals(expCurrencyCode2, currencyCodeResult2);
        
        // Case 3: MoneyFactory(Locale locale)
        // Given
        MoneyFactory moneyFactory3 = new MoneyFactory();
        moneyFactory3.setCurrencyCode("GBP");
        String expCurrencyCode3 = "GBP";
        
        // When
        String currencyCodeResult3 = moneyFactory3.getCurrencyCode();
        
        // Then
        assertEquals(expCurrencyCode3, currencyCodeResult3);
    }

    /**
     * Test of valueOf method, of class MoneyFactory.
     */
    @Test
    public void testValueOf_0args() {
        // Given
        MoneyFactory moneyFactory = new MoneyFactory("USD");
        Money expMoney = new Money("USD", SignValue.Positive, 
                0, 0, 0);
        
        // When
        Money resultMoney = moneyFactory.valueOf();
        
        // Then
        assertEquals(expMoney, resultMoney);
    }

    /**
     * Test of valueOf method, of class MoneyFactory.
     */
    @Test
    public void testValueOf_3args() {
        // Given
        long[] listOfWholeUnits = new long[]
        {-25,1256,1589647859,0,0};
        long[] listOfDecimalUnits = new long[]
        {0,5,88596,0,-856984478};
        long[] listOfLeadingZeroUnits = new long[]
        {9,0,7,0,2};
        
        Money[] expMoneis = new Money[]
        {new Money("USD", SignValue.Negative, 25, 0, 9),
        new Money("USD", SignValue.Positive, 1256, 5, 0),
        new Money("USD", SignValue.Positive, 1589647859, 88596, 7),
        new Money("USD", SignValue.Positive, 0, 0, 0),
        new Money("USD", SignValue.Negative, 0, 856984478, 2)};
        
        MoneyFactory moneyFactory = new MoneyFactory("USD");
        
        for (int i = 0; i < listOfWholeUnits.length; i++) {
            // When
            Money resultMoney = moneyFactory.valueOf(
                    listOfWholeUnits[i], 
                    listOfDecimalUnits[i], 
                    listOfLeadingZeroUnits[i]);
            
            // Then
            assertEquals(expMoneis[i], resultMoney);
        }
        
    }

    /**
     * Test of valueOf method, of class MoneyFactory.
     */
    @Test
    public void testValueOf_String() {
        // Given
        MoneyFactory moneyFactoryLocal = new MoneyFactory();
        MoneyFactory moneyFactoryUSD = new MoneyFactory();
        moneyFactoryUSD.setCurrencyCode("USD");
        
        String[] listOfInputs = new String[]
        {"GBP 12.5",
         "GBP12.5",
         "USD-12",
         "USD -896,586,785,785,896.0025634589",
         "",
         "+9.6212",
         "-8.0000000",
         "0.00",
         "-0",
         "-0.12",
         "0",
         "286",
         "0.0000005",
         "000000000000000.6",
         "12,856,896.00963"};
        
        Money specialCaseMoneyForLongInput = 
                moneyFactoryUSD.valueOf("USD -896,586,785,785,896.0025634589");
        
        Money[] expectedResults = new Money[]
        {new Money("gbp",SignValue.Positive, 12, 50, 0),
        new Money("gbp", SignValue.Positive, 12, 50, 0),
        new Money("USD", SignValue.Negative, 12, 0, 0),
        specialCaseMoneyForLongInput,
        new Money(
                Currency.getInstance(Locale.getDefault()).getCurrencyCode(), 
                SignValue.Positive, 0, 0, 0),
        new Money(
                Currency.getInstance(Locale.getDefault()).getCurrencyCode(), 
                SignValue.Positive, 9, 6212, 0),
        new Money(
                Currency.getInstance(Locale.getDefault()).getCurrencyCode(), 
                SignValue.Negative, 8, 0, 7),
        new Money(
                Currency.getInstance(Locale.getDefault()).getCurrencyCode(), 
                SignValue.Positive, 0, 0, 0),
        new Money(
                Currency.getInstance(Locale.getDefault()).getCurrencyCode(), 
                SignValue.Negative, 0, 0, 0),
        new Money(
                Currency.getInstance(Locale.getDefault()).getCurrencyCode(), 
                SignValue.Positive, 0, -12, 0),
        new Money(
                Currency.getInstance(Locale.getDefault()).getCurrencyCode(), 
                SignValue.Positive, 0, 0, 0),
        new Money(
                Currency.getInstance(Locale.getDefault()).getCurrencyCode(), 
                SignValue.Positive, 286, 0, 0),
        new Money(
                Currency.getInstance(Locale.getDefault()).getCurrencyCode(),
                SignValue.Positive, 0, 5, 6),
        new Money(
                Currency.getInstance(Locale.getDefault()).getCurrencyCode(),
                SignValue.Positive, 0, 60, 0),
        new Money(
                Currency.getInstance(Locale.getDefault()).getCurrencyCode(),
                SignValue.Positive, 12856896, 963, 2)
        };
        
        for (int i = 0; i < listOfInputs.length; i++) {
            // When
            Money resultMoney;
            if (i == 2 || i == 3) {
                resultMoney = moneyFactoryUSD.valueOf(listOfInputs[i]);
            } else {
                resultMoney = moneyFactoryLocal.valueOf(listOfInputs[i]);
            }
            
            // Then
            assertEquals(resultMoney, expectedResults[i]);
            
        }
    }

    /**
     * Test of valueOf method, of class MoneyFactory.
     */
    @Test
    public void testValueOf_long() {
        // Given
        MoneyFactory moneyFactory = new MoneyFactory(Locale.JAPAN);
        long[] listOfLongInputs = new long[]
        {
            8569,0,-896
        };
        
        Money[] listOfResults = new Money[]
        {
            new Money("JPY",SignValue.Positive,8569,0,0),
            new Money("JPY",SignValue.Positive,0,0,0),
            new Money("JPY",SignValue.Negative,-896,0,0)
        };
        
        for (int i = 0; i < listOfLongInputs.length; i++) {
            // When
            Money result = moneyFactory.valueOf(listOfLongInputs[i]);
            
            // Then
            assertEquals(result, listOfResults[i]);
        }
        
        
    }

    /**
     * Test of valueOf method, of class MoneyFactory.
     */
    @Test
    public void testValueOf_int() {
        // Given
        MoneyFactory moneyFactory = new MoneyFactory(Locale.FRANCE);
        int[] listOfLongInputs = new int[]
        {
            8569,0,-896
        };
        
        Money[] listOfResults = new Money[]
        {
            new Money("EUR",SignValue.Positive,8569,0,0),
            new Money("EUR",SignValue.Positive,0,0,0),
            new Money("EUR",SignValue.Negative,-896,0,0)
        };
        
        for (int i = 0; i < listOfLongInputs.length; i++) {
            // When
            Money result = moneyFactory.valueOf(listOfLongInputs[i]);
            // Then
            assertEquals(result, listOfResults[i]);
        }
    }
    
}
