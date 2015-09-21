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
    private MoneyFactory localMoneyFactory;
    private MoneyFactory usdMoneyFactory;
    private MoneyFactory localDefaultMoneyFactory;
    private MoneyFactory localeJapanMoneyFactory;
    private MoneyFactory localFranceMoneyFactory;
    
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
        localMoneyFactory = new MoneyFactory();
        usdMoneyFactory = new MoneyFactory("USD");
        localDefaultMoneyFactory = new MoneyFactory(Locale.getDefault());
        localeJapanMoneyFactory = new MoneyFactory(Locale.JAPAN);
        localFranceMoneyFactory = new MoneyFactory(Locale.FRANCE);
    }
    
    @After
    public void tearDown() {
        localMoneyFactory = null;
        usdMoneyFactory = null;
        localDefaultMoneyFactory = null;
        localeJapanMoneyFactory = null;
        localFranceMoneyFactory = null;
    }

    /**
     * Test of getCurrencyCode method, of class MoneyFactory.
     */
    @Test
    public void testGetCurrencyCode() {
        // Case 1: MoneyFactory()
        // Given
        String expCurrencyCode = 
                Currency.getInstance(Locale.getDefault()).getCurrencyCode();
        
        // When
        String currencyCodeResult = localMoneyFactory.getCurrencyCode();
        
        // Then
        assertEquals(expCurrencyCode, currencyCodeResult);
        
        // Case 2: MoneyFactory(String currencyCode)
        // Given
        String expCurrencyCode2 = "USD";
        
        // When
        String currencyCodeResult2 = usdMoneyFactory.getCurrencyCode();
        
        // Then
        assertEquals(expCurrencyCode2, currencyCodeResult2);
        
        // Case 3: MoneyFactory(Locale locale)
        // Given
        String expCurrencyCode3 = 
                Currency.getInstance(Locale.getDefault()).getCurrencyCode();
        
        // When
        String currencyCodeResult3 = localDefaultMoneyFactory.getCurrencyCode();
        
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
        localMoneyFactory.setCurrencyCode("PHP");
        String expCurrencyCode = "PHP";
        
        // When
        String currencyCodeResult = localMoneyFactory.getCurrencyCode();
        
        // Then
        assertEquals(expCurrencyCode, currencyCodeResult);
        
        // Case 2: MoneyFactory(String currencyCode)
        // Given
        localMoneyFactory.setCurrencyCode("USD");
        String expCurrencyCode2 = "USD";
        
        // When
        String currencyCodeResult2 = localMoneyFactory.getCurrencyCode();
        
        // Then
        assertEquals(expCurrencyCode2, currencyCodeResult2);
        
        // Case 3: MoneyFactory(Locale locale)
        // Given
        localMoneyFactory.setCurrencyCode("GBP");
        String expCurrencyCode3 = "GBP";
        
        // When
        String currencyCodeResult3 = localMoneyFactory.getCurrencyCode();
        
        // Then
        assertEquals(expCurrencyCode3, currencyCodeResult3);
    }

    /**
     * Test of valueOf method, of class MoneyFactory.
     */
    @Test
    public void testValueOf_0args() {
        // Given
        Money expMoney = new Money.Builder()
                .currencyCode("USD")
                .sign(SignValue.Positive) 
                .build();
        
        // When
        Money resultMoney = usdMoneyFactory.valueOf();
        
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
        {0,1,7,0,2};
        
        Money[] expMoneis = new Money[]
        {new Money.Builder()
                .currencyCode("USD")
                .sign(SignValue.Negative)
                .wholeUnit(25)
                .leadingDecimalZeroes(0)
                .build(),
        new Money.Builder()
                .currencyCode("USD")
                .sign(SignValue.Positive)
                .wholeUnit(1256)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build(),
        new Money.Builder()
                .currencyCode("USD")
                .sign(SignValue.Positive)
                .wholeUnit(1589647859)
                .decimalUnit(88596)
                .leadingDecimalZeroes(7)
                .build(),
        new Money.Builder()
                .currencyCode("USD")
                .sign(SignValue.Positive)
                .build(),
        new Money.Builder()
                .currencyCode("USD")
                .sign(SignValue.Negative)
                .decimalUnit(856984478)
                .leadingDecimalZeroes(2)
                .build()};
        
        for (int i = 0; i < listOfWholeUnits.length; i++) {
            // When
            Money resultMoney = usdMoneyFactory.valueOf(
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
                usdMoneyFactory.valueOf("USD -896,586,785,785,896.0025634589");
        
        Money[] expectedResults = new Money[]
        {new Money.Builder()
                .currencyCode("gbp")
                .sign(SignValue.Positive)
                .wholeUnit(12)
                .decimalUnit(50)
                .build(),
        new Money.Builder()
                .currencyCode("gbp")
                .sign(SignValue.Positive)
                .wholeUnit(12)
                .decimalUnit(50)
                .build(),
        new Money.Builder()
                .currencyCode("USD")
                .sign(SignValue.Negative)
                .wholeUnit(12)
                .build(),
        specialCaseMoneyForLongInput,
        new Money.Builder()
                .currencyCode(Currency.getInstance(Locale.getDefault()).getCurrencyCode())
                .sign(SignValue.Positive)
                .build(),
        new Money.Builder()
                .currencyCode(Currency.getInstance(Locale.getDefault()).getCurrencyCode())
                .sign(SignValue.Positive)
                .wholeUnit(9)
                .decimalUnit(6212)
                .build(),
        new Money.Builder()
                .currencyCode(Currency.getInstance(Locale.getDefault()).getCurrencyCode())
                .sign(SignValue.Negative)
                .wholeUnit(8)
                .leadingDecimalZeroes(0)
                .build(),
        new Money.Builder()
                .currencyCode(Currency.getInstance(Locale.getDefault()).getCurrencyCode())
                .sign(SignValue.Positive)
                .build(),
        new Money.Builder()
                .currencyCode(Currency.getInstance(Locale.getDefault()).getCurrencyCode())
                .build(),
        new Money.Builder()
                .currencyCode(Currency.getInstance(Locale.getDefault()).getCurrencyCode())
                .sign(SignValue.Negative)
                .decimalUnit(12)
                .build(),
        new Money.Builder()
                .currencyCode(Currency.getInstance(Locale.getDefault()).getCurrencyCode())
                .sign(SignValue.Positive)
                .build(),
        new Money.Builder()
                .currencyCode(Currency.getInstance(Locale.getDefault()).getCurrencyCode())
                .sign(SignValue.Positive)
                .wholeUnit(286)
                .build(),
        new Money.Builder()
                .currencyCode(Currency.getInstance(Locale.getDefault()).getCurrencyCode())
                .sign(SignValue.Positive)
                .decimalUnit(5)
                .leadingDecimalZeroes(6)
                .build(),
        new Money.Builder()
                .currencyCode(Currency.getInstance(Locale.getDefault()).getCurrencyCode())
                .sign(SignValue.Positive)
                .decimalUnit(60)
                .build(),
        new Money.Builder()
                .currencyCode(Currency.getInstance(Locale.getDefault()).getCurrencyCode())
                .sign(SignValue.Positive)
                .wholeUnit(12856896)
                .decimalUnit(963)
                .leadingDecimalZeroes(2)
                .build()
        };
        
        for (int i = 0; i < listOfInputs.length; i++) {
            // When
            Money resultMoney;
            if (i == 2 || i == 3) {
                resultMoney = usdMoneyFactory.valueOf(listOfInputs[i]);
            } else {
                resultMoney = localMoneyFactory.valueOf(listOfInputs[i]);
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
        long[] listOfLongInputs = new long[]
        {
            8569,0,-896
        };
        
        Money[] listOfResults = new Money[]
        {
            new Money.Builder()
                .currencyCode("JPY")
                .sign(SignValue.Positive)
                .wholeUnit(8569)
                .build(),
            new Money.Builder()
                .currencyCode("JPY")
                .sign(SignValue.Positive)
                .build(),
            new Money.Builder()
                .currencyCode("JPY")
                .sign(SignValue.Negative)
                .wholeUnit(896)
                .build()
        };
        
        for (int i = 0; i < listOfLongInputs.length; i++) {
            // When
            Money result = localeJapanMoneyFactory.valueOf(listOfLongInputs[i]);
            
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
        int[] listOfLongInputs = new int[]
        {
            8569,0,-896
        };
        
        Money[] listOfResults = new Money[]
        {
            new Money.Builder()
                .currencyCode("EUR")
                .sign(SignValue.Positive)
                .wholeUnit(8569)
                .build(),
            new Money.Builder()
                .currencyCode("EUR")
                .sign(SignValue.Positive)
                .build(),
            new Money.Builder()
                .currencyCode("EUR")
                .sign(SignValue.Negative)
                .wholeUnit(896)
                .build()
        };
        
        for (int i = 0; i < listOfLongInputs.length; i++) {
            // When
            Money result = localFranceMoneyFactory.valueOf(listOfLongInputs[i]);
            // Then
            assertEquals(result, listOfResults[i]);
        }
    }
    
}
