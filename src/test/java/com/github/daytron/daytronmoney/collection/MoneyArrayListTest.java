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
import com.github.daytron.daytronmoney.currency.SignValue;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for MoneyArrayList
 *
 * @author Ryan Gilera
 */
public class MoneyArrayListTest {

    public MoneyArrayListTest() {
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
     * Test of different types of object creation for class MoneyArrayList.
     */
    @Test
    public void testMultipleConstructors() {
        // When
        List<Money> moneyArrayList1 = new MoneyArrayList(1);
        List<Money> moneyArrayList2 = new MoneyArrayList(moneyArrayList1);
        List<Money> moneyArrayList3 = new MoneyArrayList();

        // Then
        assertNotNull(moneyArrayList1);
        assertNotNull(moneyArrayList2);
        assertNotNull(moneyArrayList3);
    }

    /**
     * Test of hasNegativeValue method, of class MoneyArrayList.
     */
    @Test
    public void testHasNegativeValue() {
        // Given
        MoneyArrayList moneyArrayListCase1 = new MoneyArrayList();
        moneyArrayListCase1.add(new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(1)
                .decimalUnit(2)
                .build());
        moneyArrayListCase1.add(new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(123)
                .decimalUnit(1234)
                .build());
        moneyArrayListCase1.add(new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(131)
                .decimalUnit(3)
                .build());

        MoneyArrayList moneyArrayListCase2 = new MoneyArrayList();
        moneyArrayListCase2.add(new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(1)
                .decimalUnit(2)
                .build());
        moneyArrayListCase2.add(new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(123)
                .decimalUnit(1234)
                .build());
        moneyArrayListCase2.add(new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(131)
                .decimalUnit(3)
                .build());

        MoneyArrayList[] listOfInputs = new MoneyArrayList[]{
            moneyArrayListCase1, moneyArrayListCase2
        };

        boolean[] expResults = new boolean[]{true, false};

        for (int i = 0; i < expResults.length; i++) {
            // When
            boolean result = listOfInputs[i].hasNegativeValue();

            // Then
            assertEquals(expResults[i], result);
        }
    }

    /**
     * Test of hasPositiveValue method, of class MoneyArrayList.
     */
    @Test
    public void testHasPositiveValue() {
        // Given
        MoneyArrayList moneyArrayListCase1 = new MoneyArrayList();
        moneyArrayListCase1.add(new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(1)
                .decimalUnit(2)
                .build());
        moneyArrayListCase1.add(new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(123)
                .decimalUnit(1234)
                .build());
        moneyArrayListCase1.add(new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(131)
                .decimalUnit(3)
                .build());

        MoneyArrayList moneyArrayListCase2 = new MoneyArrayList();
        moneyArrayListCase2.add(new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(1)
                .decimalUnit(2)
                .build());
        moneyArrayListCase2.add(new Money.Builder()
                .sign(SignValue.Positive)
                .build());

        MoneyArrayList moneyArrayListCase3 = new MoneyArrayList();
        moneyArrayListCase3.add(new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(1)
                .decimalUnit(2)
                .build());
        moneyArrayListCase3.add(new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(1)
                .build());

        MoneyArrayList[] listOfInputs = new MoneyArrayList[]{
            moneyArrayListCase1, moneyArrayListCase2, moneyArrayListCase3
        };

        boolean[] expResults = new boolean[]{true, false, false};

        for (int i = 0; i < expResults.length; i++) {
            // When
            boolean result = listOfInputs[i].hasPositiveValue();

            // Then
            assertEquals(expResults[i], result);
        }
    }

    /**
     * Test of retrieveAllPositiveValues method, of class MoneyArrayList.
     */
    @Test
    public void testRetrieveAllPositiveValues() {
        // Given
        MoneyArrayList moneyArrayList = new MoneyArrayList();
        moneyArrayList.add(new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(1)
                .decimalUnit(5)
                .build());
        moneyArrayList.add(new Money.Builder()
                .sign(SignValue.Positive)
                .build());
        moneyArrayList.add(new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(123)
                .decimalUnit(5)
                .build());
        moneyArrayList.add(new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(13)
                .decimalUnit(5)
                .build());

        MoneyArrayList expResult = new MoneyArrayList();
        expResult.add(new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(1)
                .decimalUnit(5)
                .build());

        // When
        MoneyArrayList result = moneyArrayList.retrieveAllPositiveValues();

        // Then
        assertEquals(expResult.size(), result.size());
        assertEquals(expResult.get(0), result.get(0));
    }

    /**
     * Test of retrieveAllNegativeValues method, of class MoneyArrayList.
     */
    @Test
    public void testRetrieveAllNegativeValues() {
        // Given
        MoneyArrayList moneyArrayList = new MoneyArrayList();
        moneyArrayList.add(new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(1)
                .decimalUnit(5)
                .build());
        moneyArrayList.add(new Money.Builder()
                .sign(SignValue.Positive)
                .build());
        moneyArrayList.add(new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(123)
                .decimalUnit(5)
                .build());
        moneyArrayList.add(new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(13)
                .decimalUnit(5)
                .build());

        MoneyArrayList expResult = new MoneyArrayList();
        expResult.add(new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(13)
                .decimalUnit(5)
                .build());

        // When
        MoneyArrayList result = moneyArrayList.retrieveAllNegativeValues();

        // Then
        assertEquals(expResult.size(), result.size());
        assertEquals(expResult.get(0), result.get(0));
    }

    /**
     * Test of removeAnyZeroValues method, of class MoneyArrayList.
     */
    @Test
    public void testRemoveAnyZeroValues() {
        // Given
        MoneyArrayList moneyArrayList = new MoneyArrayList();
        moneyArrayList.add(new Money.Builder()
                .build());
        moneyArrayList.add(new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(1)
                .decimalUnit(5)
                .build());

        MoneyArrayList expResult = new MoneyArrayList();
        expResult.add(new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(1)
                .decimalUnit(5)
                .build());

        // When
        moneyArrayList.removeAnyZeroValues();

        // Then
        assertEquals(expResult.size(), moneyArrayList.size());
        assertEquals(expResult.get(0), moneyArrayList.get(0));
    }

    /**
     * Test of removeAnyNegativeValues method, of class MoneyArrayList.
     */
    @Test
    public void testRemoveAnyNegativeValues() {
        // Given
        MoneyArrayList moneyArrayList = new MoneyArrayList();
        moneyArrayList.add(new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(12)
                .build());
        moneyArrayList.add(new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(1)
                .decimalUnit(5)
                .build());

        MoneyArrayList expResult = new MoneyArrayList();
        expResult.add(new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(1)
                .decimalUnit(5)
                .build());

        // When
        moneyArrayList.removeAnyNegativeValues();

        // Then
        assertEquals(expResult.size(), moneyArrayList.size());
        assertEquals(expResult.get(0), moneyArrayList.get(0));
    }

    /**
     * Test of removeAnyPositiveValues method, of class MoneyArrayList.
     */
    @Test
    public void testRemoveAnyPositiveValues() {
        // Given
        MoneyArrayList moneyArrayList = new MoneyArrayList();
        moneyArrayList.add(new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(12)
                .build());
        moneyArrayList.add(new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(1)
                .decimalUnit(5)
                .build());

        MoneyArrayList expResult = new MoneyArrayList();
        expResult.add(new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(1)
                .decimalUnit(5)
                .build());

        // When
        moneyArrayList.removeAnyPositiveValues();

        // Then
        assertEquals(expResult.size(), moneyArrayList.size());
        assertEquals(expResult.get(0), moneyArrayList.get(0));
    }

    /**
     * Test of sum method, of class MoneyArrayList.
     */
    @Test
    public void testSum() {
        // Given
        MoneyArrayList moneyArrayList = new MoneyArrayList();
        moneyArrayList.add(new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(1)
                .decimalUnit(5)
                .build());
        moneyArrayList.add(new Money.Builder()
                .sign(SignValue.Positive)
                .build());
        moneyArrayList.add(new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(13)
                .decimalUnit(5)
                .build());

        Money expResult = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(12)
                .build();

        // When
        Money result = moneyArrayList.sum();

        // Then
        assertEquals(expResult, result);
    }

    /**
     * Test of difference method, of class MoneyArrayList.
     */
    @Test
    public void testDifference() {
        // Given
        MoneyArrayList moneyArrayList = new MoneyArrayList();
        moneyArrayList.add(new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(1)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build());
        moneyArrayList.add(new Money.Builder()
                .sign(SignValue.Positive)
                .build());
        moneyArrayList.add(new Money.Builder()
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
        Money result = moneyArrayList.difference();

        // Then
        assertEquals(expResult, result);
    }

    /**
     * Test of product method, of class MoneyArrayList.
     */
    @Test
    public void testProduct() {
        // Given
        MoneyArrayList moneyArrayList = new MoneyArrayList();
        moneyArrayList.add(new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(1)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build());
        moneyArrayList.add(new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(13)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build());

        Money expResult = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(13)
                .decimalUnit(7025)
                .build();

        // When
        Money result = moneyArrayList.product();

        // Then
        assertEquals(expResult, result);
    }

    /**
     * Test of quotient method, of class MoneyArrayList.
     */
    @Test
    public void testQuotient() {
        // Given
        MoneyArrayList moneyArrayList = new MoneyArrayList();
        moneyArrayList.add(new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(100)
                .decimalUnit(50)
                .build());
        moneyArrayList.add(new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(20)
                .build());

        Money expResult = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(5)
                .decimalUnit(25)
                .leadingDecimalZeroes(1)
                .build();

        // When
        Money result = moneyArrayList.quotient();

        // Then
        assertEquals(expResult, result);
    }

    /**
     * Test of addEachWith method, of class MoneyArrayList.
     */
    @Test
    public void testAddEachWith() {
        // Given
        MoneyArrayList moneyArrayList = new MoneyArrayList();
        moneyArrayList.add(new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(1)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build());
        moneyArrayList.add(new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(13)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build());

        MoneyArrayList expResultList = new MoneyArrayList();
        expResultList.add(new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(3)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build());
        expResultList.add(new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(11)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build());

        // When
        moneyArrayList.addEachWith(new Money.Builder()
                .wholeUnit(2).build());

        // Then
        assertEquals(moneyArrayList.get(0), expResultList.get(0));
        assertEquals(moneyArrayList.get(1), expResultList.get(1));
    }

    /**
     * Test of subtractEachWith method, of class MoneyArrayList.
     */
    @Test
    public void testSubtractEachWith() {
        // Given
        MoneyArrayList moneyArrayList = new MoneyArrayList();
        moneyArrayList.add(new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(10)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build());
        moneyArrayList.add(new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(13)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build());

        MoneyArrayList expResultList = new MoneyArrayList();
        expResultList.add(new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(8)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build());
        expResultList.add(new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(15)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build());

        // When
        moneyArrayList.subtractEachWith(new Money.Builder()
                .wholeUnit(2).build());

        // Then
        assertEquals(moneyArrayList.get(0), expResultList.get(0));
        assertEquals(moneyArrayList.get(1), expResultList.get(1));
    }

    /**
     * Test of multiplyEachWith method, of class MoneyArrayList.
     */
    @Test
    public void testMultiplyEachWith() {
        // Given
        MoneyArrayList moneyArrayList = new MoneyArrayList();
        moneyArrayList.add(new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(10)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build());
        moneyArrayList.add(new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(13)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build());

        MoneyArrayList expResultList = new MoneyArrayList();
        expResultList.add(new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(20)
                .decimalUnit(10)
                .build());
        expResultList.add(new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(26)
                .decimalUnit(10)
                .build());

        // When
        moneyArrayList.multiplyEachWith(new Money.Builder()
                .wholeUnit(2).build());

        // Then
        assertEquals(moneyArrayList.get(0), expResultList.get(0));
        assertEquals(moneyArrayList.get(1), expResultList.get(1));
    }

    /**
     * Test of divideEachWith method, of class MoneyArrayList.
     */
    @Test
    public void testDivideEachWith() {
        // Given
        MoneyArrayList moneyArrayList = new MoneyArrayList();
        moneyArrayList.add(new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(10)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build());
        moneyArrayList.add(new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(13)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build());

        MoneyArrayList expResultList = new MoneyArrayList();
        expResultList.add(new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(5)
                .decimalUnit(25)
                .leadingDecimalZeroes(1)
                .build());
        expResultList.add(new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(6)
                .decimalUnit(525)
                .build());

        // When
        moneyArrayList.divideEachWith(new Money.Builder()
                .wholeUnit(2).build());

        // Then
        assertEquals(moneyArrayList.get(0), expResultList.get(0));
        assertEquals(moneyArrayList.get(1), expResultList.get(1));
    }

}
