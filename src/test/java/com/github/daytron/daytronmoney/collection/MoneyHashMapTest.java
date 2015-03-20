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
        moneyHashMapCase1.put("1", new Money(SignValue.Negative, 1, 2));
        moneyHashMapCase1.put("2", new Money(SignValue.Positive, 123, 1234));
        moneyHashMapCase1.put("3", new Money(SignValue.Positive, 131, 3));
        
        MoneyHashMap<String, Money> moneyHashMapCase2 = new MoneyHashMap<>();
        moneyHashMapCase2.put("1", new Money(SignValue.Positive, 1, 2));
        moneyHashMapCase2.put("2", new Money(SignValue.Positive, 123, 1234));
        moneyHashMapCase2.put("3", new Money(SignValue.Positive, 131, 3));
        
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
        moneyHashMapCase1.put("1", new Money(SignValue.Negative, 1, 2));
        moneyHashMapCase1.put("2", new Money(SignValue.Positive, 123, 1234));
        moneyHashMapCase1.put("3", new Money(SignValue.Positive, 131, 3));
        
        MoneyHashMap<String, Money> moneyHashMapCase2 = new MoneyHashMap<>();
        moneyHashMapCase2.put("1", new Money(SignValue.Negative, 1, 2));
        moneyHashMapCase2.put("2", new Money(SignValue.Negative, 123, 1234));
        moneyHashMapCase2.put("3", new Money(SignValue.Negative, 131, 3));
        
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
        moneyHashMap.put("1", new Money(SignValue.Positive, 1, 5));
        moneyHashMap.put("2", new Money(SignValue.Positive, 0, 0));
        moneyHashMap.put("3", new Money(SignValue.Negative, 123, 5));
        moneyHashMap.put("4", new Money(SignValue.Negative, 13, 5));
        
        MoneyHashMap<String, Money> expResult = new MoneyHashMap<>();
        expResult.put("1", new Money(SignValue.Positive, 1, 5));
        
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
        moneyHashMap.put("1", new Money(SignValue.Positive, 1, 5));
        moneyHashMap.put("2", new Money(SignValue.Positive, 0, 0));
        moneyHashMap.put("3", new Money(SignValue.Negative, 123, 5));
        moneyHashMap.put("4", new Money(SignValue.Negative, 13, 5));
        
        MoneyHashMap<String, Money> expResult = new MoneyHashMap<>();
        expResult.put("1", new Money(SignValue.Negative, 123, 5));
        expResult.put("2", new Money(SignValue.Negative, 13, 5));
        
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
        moneyHashMap.put("1", new Money(SignValue.Positive, 1, 5));
        moneyHashMap.put("2", new Money(SignValue.Positive, 0, 0));
        moneyHashMap.put("3", new Money(SignValue.Negative, 13, 5));
        
        Money expResult = new Money(SignValue.Negative, 
                12, 0, 0);
        
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
        moneyHashMap.put("1", new Money(SignValue.Positive, 1, 5));
        moneyHashMap.put("2", new Money(SignValue.Positive, 0, 0));
        moneyHashMap.put("3", new Money(SignValue.Negative, 13, 5));
        
        Money expResult = new Money(SignValue.Positive, 
                14, 10, 0);
        
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
        moneyHashMap.put("1", new Money(SignValue.Positive, 1, 5));
        moneyHashMap.put("2", new Money(SignValue.Positive, 2, 0));
        moneyHashMap.put("3", new Money(SignValue.Negative, 13, 5));
        
        Money expResult = new Money(SignValue.Negative, 
                27, 405, 0);
        
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
        moneyHashMap.put("1", new Money(SignValue.Positive, 142, 5));
        moneyHashMap.put("2", new Money(SignValue.Positive, 2, 0));
        moneyHashMap.put("3", new Money(SignValue.Negative, 13, 0));
        
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
        moneyHashMap.put("1", new Money(SignValue.Positive, 1, 5));
        moneyHashMap.put("2", new Money(SignValue.Negative, 13, 5));
        
        MoneyHashMap<String, Money> expResultMap = new MoneyHashMap<>();
        expResultMap.put("1", new Money(SignValue.Positive, 3, 5));
        expResultMap.put("2", new Money(SignValue.Negative, 11, 5));
        
        // When
        moneyHashMap.addEachWith(new Money(2));
        
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
        moneyHashMap.put("1", new Money(SignValue.Positive, 10, 5));
        moneyHashMap.put("2", new Money(SignValue.Negative, 13, 5));
        
        MoneyHashMap<String, Money> expResultMap = new MoneyHashMap<>();
        expResultMap.put("1", new Money(SignValue.Positive, 8, 5));
        expResultMap.put("2", new Money(SignValue.Negative, 15, 5));
        
        // When
        moneyHashMap.subtractEachWith(new Money(2));
        
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
        moneyHashMap.put("1", new Money(SignValue.Positive, 10, 5));
        moneyHashMap.put("2", new Money(SignValue.Negative, 13, 5));
        
        MoneyHashMap<String, Money> expResultMap = new MoneyHashMap<>();
        expResultMap.put("1", new Money(SignValue.Positive, 20, 10));
        expResultMap.put("2", new Money(SignValue.Negative, 26, 10));
        
        // When
        moneyHashMap.multiplyEachWith(new Money(2));
        
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
        moneyHashMap.put("1", new Money(SignValue.Positive, 10, 5));
        moneyHashMap.put("2", new Money(SignValue.Negative, 13, 5));
        
        MoneyHashMap<String, Money> expResultMap = new MoneyHashMap<>();
        expResultMap.put("1", new Money(SignValue.Positive, 5, 25, 1));
        expResultMap.put("2", new Money(SignValue.Negative, 6, 525));
        
        // When
        moneyHashMap.divideEachWith(new Money(2));
        
        // Then
        assertEquals(moneyHashMap.get("1"), expResultMap.get("1"));
        assertEquals(moneyHashMap.get("2"), expResultMap.get("2"));
    }
    
}
