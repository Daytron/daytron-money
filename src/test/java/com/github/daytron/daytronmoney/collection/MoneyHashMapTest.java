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
package com.github.daytron.daytronmoney.collection;

import com.github.daytron.daytronmoney.currency.Money;
import com.github.daytron.daytronmoney.currency.MoneyFactory;
import com.github.daytron.daytronmoney.currency.SignValue;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for MoneyHashMap
 * @author Ryan Gilera
 */
public class MoneyHashMapTest {
    
    public MoneyHashMapTest() {
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
     * Test of different types of object creation for class MoneyHashMap.
     */
    @Test
    public void testMultipleConstructors() {
        // Given
        float floatingFactor = (float) 1.2;

        // When
        Map<String, Money> moneyHashMap1 = new MoneyHashMap<>(1);
        Map<String, Money> moneyHashMap2 = new MoneyHashMap<>(1,floatingFactor);
        Map<String, Money> moneyHashMap3 = new MoneyHashMap<>(moneyHashMap1);
        Map<String, Money> moneyHashMap4 = new MoneyHashMap<>();
        
        // Then
        assertNotNull(moneyHashMap1);
        assertNotNull(moneyHashMap2);
        assertNotNull(moneyHashMap3);
        assertNotNull(moneyHashMap4);
    }

    /**
     * Test of hasNegativeValue method, of class MoneyHashMap.
     */
    @Test
    public void testHasNegativeValue() {
         // Given
        MoneyHashMap<String, Money> moneyHashMapCase1 = new MoneyHashMap<>();
        moneyHashMapCase1.put("1", new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(1)
                .decimalUnit(2)
                .leadingDecimalZeroes(1)
                .build());
        moneyHashMapCase1.put("2", new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(123)
                .decimalUnit(1234)
                .build());
        moneyHashMapCase1.put("3", new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(131)
                .decimalUnit(3)
                .leadingDecimalZeroes(1)
                .build());
        MoneyHashMap<String, Money> moneyHashMapCase2 = new MoneyHashMap<>();
        moneyHashMapCase2.put("1", new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(1)
                .decimalUnit(2)
                .leadingDecimalZeroes(1)
                .build());
        moneyHashMapCase2.put("2", new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(123)
                .decimalUnit(1234)
                .build());
        moneyHashMapCase2.put("3", new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(131)
                .decimalUnit(3)
                .leadingDecimalZeroes(1)
                .build());
        MoneyHashMap[] listOfInputs = new MoneyHashMap[] {
            moneyHashMapCase1, moneyHashMapCase2
        };
        
        boolean[] expResults = new boolean[] {true,false};
        
        for (int i = 0; i < expResults.length; i++) {
            // When
            boolean result = listOfInputs[i].hasNegativeValue();
        
            // Then
            assertEquals(expResults[i], result);
        }
    }

    /**
     * Test of hasPositiveValue method, of class MoneyHashMap.
     */
    @Test
    public void testHasPositiveValue() {
         // Given
        MoneyHashMap<String, Money> moneyHashMapCase1 = new MoneyHashMap<>();
        moneyHashMapCase1.put("1", new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(1)
                .decimalUnit(2)
                .leadingDecimalZeroes(1)
                .build());
        moneyHashMapCase1.put("2", new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(131)
                .decimalUnit(3)
                .leadingDecimalZeroes(1)
                .build());
        moneyHashMapCase1.put("3", new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(131)
                .decimalUnit(3)
                .leadingDecimalZeroes(1)
                .build());
        MoneyHashMap<String, Money> moneyHashMapCase2 = new MoneyHashMap<>();
        moneyHashMapCase2.put("1", new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(1)
                .decimalUnit(2)
                .leadingDecimalZeroes(1)
                .build());
        moneyHashMapCase2.put("2", new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(123)
                .decimalUnit(1234)
                .build());
        moneyHashMapCase2.put("3", new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(131)
                .decimalUnit(3)
                .leadingDecimalZeroes(1)
                .build());
        
        MoneyHashMap[] listOfInputs = new MoneyHashMap[] {
            moneyHashMapCase1, moneyHashMapCase2
        };
        
        boolean[] expResults = new boolean[] {true,false};
        
        for (int i = 0; i < expResults.length; i++) {
            // When
            boolean result = listOfInputs[i].hasPositiveValue();
        
            // Then
            assertEquals(expResults[i], result);
        }
    }

    /**
     * Test of retrieveAllPositiveValues method, of class MoneyHashMap.
     */
    @Test
    public void testRetrieveAllPositiveValues() {
        // Given
        MoneyHashMap<String, Money> moneyHashMap = new MoneyHashMap<>();
        moneyHashMap.put("1", new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(1)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build());
        moneyHashMap.put("2", new Money.Builder()
                .sign(SignValue.Positive)
                .build());
        moneyHashMap.put("3", new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(123)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build());
        moneyHashMap.put("4", new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(13)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build());
        
        MoneyHashMap<String, Money> expResult = new MoneyHashMap<>();
        expResult.put("1", new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(1)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build());
        
        // When
        MoneyHashMap<String, Money> result = 
                moneyHashMap.retrieveAllPositiveValues();
        
        // Then
        assertEquals(expResult.size(), result.size());
        assertEquals(expResult.get("1"), result.get("1"));
    }

    /**
     * Test of retrieveAllNegativeValues method, of class MoneyHashMap.
     */
    @Test
    public void testRetrieveAllNegativeValues() {
        // Given
        MoneyHashMap<String, Money> moneyHashMap = new MoneyHashMap<>();
        moneyHashMap.put("1", new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(1)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build());
        moneyHashMap.put("2", new Money.Builder()
                .sign(SignValue.Positive)
                .build());
        moneyHashMap.put("3", new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(123)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build());
        moneyHashMap.put("4", new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(13)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build());
        
        MoneyHashMap<String, Money> expResult = new MoneyHashMap<>();
        expResult.put("1", new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(123)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build());
        expResult.put("2", new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(13)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build());
        
        // When
        MoneyHashMap<String, Money> result = 
                moneyHashMap.retrieveAllNegativeValues();
        
        // Then
        assertEquals(expResult.size(), result.size());
        assertEquals(expResult.get("1"), result.get("3"));
        assertEquals(expResult.get("2"), result.get("4"));
    }

    /**
     * Test of sum method, of class MoneyHashMap.
     */
    @Test
    public void testSum() {
        // Given
        MoneyHashMap<String, Money> moneyHashMap = new MoneyHashMap<>();
        moneyHashMap.put("1", new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(1)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build());
        moneyHashMap.put("2", new Money.Builder()
                .sign(SignValue.Positive)
                .build());
        moneyHashMap.put("3", new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(13)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build());
        
        Money expResult = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(12)
                .build();
        
        // When
        Money result = moneyHashMap.sum();
        
        // Then
        assertEquals(expResult, result);
    }

    /**
     * Test of difference method, of class MoneyHashMap.
     */
    @Test
    public void testDifference() {
        // Given
        MoneyHashMap<String, Money> moneyHashMap = new MoneyHashMap<>();
        moneyHashMap.put("1", new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(1)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build());
        moneyHashMap.put("2", new Money.Builder()
                .sign(SignValue.Positive)
                .build());
        moneyHashMap.put("3", new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(13)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build());
        
        Money expResult = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(14)
                .decimalUnit(10)
                .build();
        
        // When
        Money result = moneyHashMap.difference();
        
        // Then
        assertEquals(expResult, result);
    }

    /**
     * Test of product method, of class MoneyHashMap.
     */
    @Test
    public void testProduct() {
        // Given
        MoneyHashMap<String, Money> moneyHashMap = new MoneyHashMap<>();
        moneyHashMap.put("1", new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(1)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build());
        moneyHashMap.put("2", new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(2)
                .build());
        moneyHashMap.put("3", new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(13)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build());
        
        Money expResult = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(27)
                .decimalUnit(405)
                .build();
        
        // When
        Money result = moneyHashMap.product();
        
        // Then
        assertEquals(expResult, result);
    }

    /**
     * Test of quotient method, of class MoneyHashMap.
     */
    @Test
    public void testQuotient() {
        MoneyFactory mf = new MoneyFactory();
        
        // Given
        MoneyHashMap<String, Money> moneyHashMap = new MoneyHashMap<>();
        moneyHashMap.put("1", new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(142)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build());
        moneyHashMap.put("2", new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(2)
                .build());
        moneyHashMap.put("3", new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(13)
                .build());
        
        Money expResult = mf.valueOf("-5.4634615384615385");
        
        // When
        Money result = moneyHashMap.quotient();
        
        // Then
        assertEquals(expResult, result);
    }

    /**
     * Test of addEachWith method, of class MoneyHashMap.
     */
    @Test
    public void testAddEachWith() {
        // Given
        MoneyHashMap<String, Money> moneyHashMap = new MoneyHashMap<>();
        moneyHashMap.put("1", new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(1)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build());
        moneyHashMap.put("2", new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(13)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build());
        
        MoneyHashMap<String, Money> expResultMap = new MoneyHashMap<>();
        expResultMap.put("1", new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(3)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build());
        expResultMap.put("2", new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(11)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build());
        
        // When
        moneyHashMap.addEachWith(new Money.Builder()
                .wholeUnit(2).build());
        
        // Then
        assertEquals(moneyHashMap.get("1"), expResultMap.get("1"));
        assertEquals(moneyHashMap.get("2"), expResultMap.get("2"));
    }

    /**
     * Test of subtractEachWith method, of class MoneyHashMap.
     */
    @Test
    public void testSubtractEachWith() {
        // Given
        MoneyHashMap<String, Money> moneyHashMap = new MoneyHashMap<>();
        moneyHashMap.put("1", new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(10)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build());
        moneyHashMap.put("2", new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(13)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build());
        
        MoneyHashMap<String, Money> expResultMap = new MoneyHashMap<>();
        expResultMap.put("1", new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(8)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build());
        expResultMap.put("2", new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(15)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build());
        
        // When
        moneyHashMap.subtractEachWith(new Money.Builder()
                .wholeUnit(2).build());
        
        // Then
        assertEquals(moneyHashMap.get("1"), expResultMap.get("1"));
        assertEquals(moneyHashMap.get("2"), expResultMap.get("2"));
    }

    /**
     * Test of multiplyEachWith method, of class MoneyHashMap.
     */
    @Test
    public void testMultiplyEachWith() {
        // Given
        MoneyHashMap<String, Money> moneyHashMap = new MoneyHashMap<>();
        moneyHashMap.put("1", new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(10)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build());
        moneyHashMap.put("2", new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(13)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build());
        
        MoneyHashMap<String, Money> expResultMap = new MoneyHashMap<>();
        expResultMap.put("1", new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(20)
                .decimalUnit(10)
                .build());
        expResultMap.put("2", new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(26)
                .decimalUnit(10)
                .build());
        
        // When
        moneyHashMap.multiplyEachWith(new Money.Builder()
                .wholeUnit(2).build());
        
        // Then
        assertEquals(moneyHashMap.get("1"), expResultMap.get("1"));
        assertEquals(moneyHashMap.get("2"), expResultMap.get("2"));
    }

    /**
     * Test of divideEachWith method, of class MoneyHashMap.
     */
    @Test
    public void testDivideEachWith() {
        // Given
        MoneyHashMap<String, Money> moneyHashMap = new MoneyHashMap<>();
        moneyHashMap.put("1", new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(10)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build());
        moneyHashMap.put("2", new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(13)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build());
        
        MoneyHashMap<String, Money> expResultMap = new MoneyHashMap<>();
        expResultMap.put("1", new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(5)
                .decimalUnit(25)
                .leadingDecimalZeroes(1)
                .build());
        expResultMap.put("2", new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(6)
                .decimalUnit(525)
                .build());
        
        // When
        moneyHashMap.divideEachWith(new Money.Builder()
                .wholeUnit(2).build());
        
        // Then
        assertEquals(moneyHashMap.get("1"), expResultMap.get("1"));
        assertEquals(moneyHashMap.get("2"), expResultMap.get("2"));
    }
    
    @Test
    public void testCurrencySetThroughMoneyFactory() {
        // Given
        String currency = "CAD";
        MoneyHashMap<String, Money> map = new MoneyHashMap<>();
        map.setMoneyFactory(new MoneyFactory(currency));
        
        // When
        MoneyFactory mf = map.getMoneyFactory();
        
        // Then
        assertNotNull(mf);
        assertEquals(currency, mf.getCurrencyCode());
    }
    
    @Test
    public void testPutString() {
        // Given
        MoneyHashMap<String, Money> map = new MoneyHashMap<>();
        String key = "1";
        
        Money expMoney = new Money.Builder()
                .wholeUnit(1)
                .decimalUnit(23)
                .build();
        
        // When
        map.put(key, "1.23");
        
        // Then
        assertEquals(map.get(key), expMoney);
    }
    
    @Test
    public void testIsAllNegative() {
        // Given
        MoneyHashMap<String, Money> map = new MoneyHashMap<>();
        String key1 = "1", key2 = "2";
        
        map.put(key1, "-1.35");
        map.put(key2, "-23.45");
        
        // When
        boolean result = map.isAllNegativeValues();
        
        // Then
        assertTrue(result);
    }
    
    @Test
    public void testIsAllPositive() {
        // Given
        MoneyHashMap<String, Money> map = new MoneyHashMap<>();
        String key1 = "1", key2 = "2";
        
        map.put(key1, "1.35");
        map.put(key2, "23.45");
        
        // When
        boolean result = map.isAllPositiveValues();
        
        // Then
        assertTrue(result);
    }
    
    
}
