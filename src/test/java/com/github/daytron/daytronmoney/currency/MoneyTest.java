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
 * Test class for Money
 *
 * @author Ryan Gilera
 */
public class MoneyTest {

    public MoneyTest() {
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
     * Test of getSign method, of class Money.
     */
    @Test
    public void testMoney() {
        // CASE 1: Money()
        // Given:
        Money instance = new Money.Builder().build();
        long expWholeResult1 = 0;
        long expDecResult1 = 0;
        long expLeadingZeroResult1 = 0;
        SignValue expSignResult1 = SignValue.Positive;
        String expCurrenyCodeResult1
                = Currency.getInstance(Locale.getDefault()).getCurrencyCode();

        // When:
        long wholeResult1 = instance.getWholeUnit();
        long decResult1 = instance.getDecimalUnit();
        long leadingZeroResult1 = instance.getLeadingDecimalZeros();
        SignValue signResult1 = instance.getSign();
        String currenyCodeResult1 = instance.getCurrencyCode();

        // Then:
        assertEquals(expWholeResult1, wholeResult1);
        assertEquals(expDecResult1, decResult1);
        assertEquals(expLeadingZeroResult1, leadingZeroResult1);
        assertEquals(expSignResult1, signResult1);
        assertEquals(expCurrenyCodeResult1, currenyCodeResult1);

        // CASE 2: Money(long wholeUnit)
        // Given:
        Money instance2 = new Money.Builder()
                .wholeUnit(12)
                .build();
        long expWholeResult2 = 12;
        long expDecResult2 = 0;
        long expLeadingZeroResult2 = 0;
        SignValue expSignResult2 = SignValue.Positive;
        String expCurrenyCodeResult2
                = Currency.getInstance(Locale.getDefault()).getCurrencyCode();

        // When:
        long wholeResult2 = instance2.getWholeUnit();
        long decResult2 = instance2.getDecimalUnit();
        long leadingZeroResult2 = instance2.getLeadingDecimalZeros();
        SignValue signResult2 = instance2.getSign();
        String currenyCodeResult2 = instance2.getCurrencyCode();

        // Then:
        assertEquals(expWholeResult2, wholeResult2);
        assertEquals(expDecResult2, decResult2);
        assertEquals(expLeadingZeroResult2, leadingZeroResult2);
        assertEquals(expSignResult2, signResult2);
        assertEquals(expCurrenyCodeResult2, currenyCodeResult2);

        // CASE 3: Money(long wholeUnit, long decimalUnit)
        // Given:
        Money instance3 = new Money.Builder()
                .wholeUnit(12)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build();
        long expWholeResult3 = 12;
        long expDecResult3 = 5;
        long expLeadingZeroResult3 = 1;
        SignValue expSignResult3 = SignValue.Positive;
        String expCurrenyCodeResult3
                = Currency.getInstance(Locale.getDefault()).getCurrencyCode();

        // When:
        long wholeResult3 = instance3.getWholeUnit();
        long decResult3 = instance3.getDecimalUnit();
        long leadingZeroResult3 = instance3.getLeadingDecimalZeros();
        SignValue signResult3 = instance3.getSign();
        String currenyCodeResult3 = instance3.getCurrencyCode();

        // Then:
        assertEquals(expWholeResult3, wholeResult3);
        assertEquals(expDecResult3, decResult3);
        assertEquals(expLeadingZeroResult3, leadingZeroResult3);
        assertEquals(expSignResult3, signResult3);
        assertEquals(expCurrenyCodeResult3, currenyCodeResult3);

        // CASE 4: Money(SignValue sign, long wholeUnit, long decimalUnit)
        // Given:
        Money instance4 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(12)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build();
        long expWholeResult4 = 12;
        long expDecResult4 = 5;
        long expLeadingZeroResult4 = 1;
        SignValue expSignResult4 = SignValue.Negative;
        String expCurrenyCodeResult4
                = Currency.getInstance(Locale.getDefault()).getCurrencyCode();

        // When:
        long wholeResult4 = instance4.getWholeUnit();
        long decResult4 = instance4.getDecimalUnit();
        long leadingZeroResult4 = instance4.getLeadingDecimalZeros();
        SignValue signResult4 = instance4.getSign();
        String currenyCodeResult4 = instance4.getCurrencyCode();

        // Then:
        assertEquals(expWholeResult4, wholeResult4);
        assertEquals(expDecResult4, decResult4);
        assertEquals(expLeadingZeroResult4, leadingZeroResult4);
        assertEquals(expSignResult4, signResult4);
        assertEquals(expCurrenyCodeResult4, currenyCodeResult4);

        // CASE 5: Money(SignValue sign, long wholeUnit, long decimalUnit, 
        //        leadingDecimalZeroes )
        // Given:
        Money instance5 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(12)
                .decimalUnit(5)
                .leadingDecimalZeroes(3)
                .build();
        long expWholeResult5 = 12;
        long expDecResult5 = 5;
        long expLeadingZeroResult5 = 3;
        SignValue expSignResult5 = SignValue.Negative;
        String expCurrenyCodeResult5
                = Currency.getInstance(Locale.getDefault()).getCurrencyCode();

        // When:
        long wholeResult5 = instance5.getWholeUnit();
        long decResult5 = instance5.getDecimalUnit();
        long leadingZeroResult5 = instance5.getLeadingDecimalZeros();
        SignValue signResult5 = instance5.getSign();
        String currenyCodeResult5 = instance5.getCurrencyCode();

        // Then:
        assertEquals(expWholeResult5, wholeResult5);
        assertEquals(expDecResult5, decResult5);
        assertEquals(expLeadingZeroResult5, leadingZeroResult5);
        assertEquals(expSignResult5, signResult5);
        assertEquals(expCurrenyCodeResult5, currenyCodeResult5);

        // CASE 6: Money(String currencyCode, SignValue sign, 
        //      long wholeUnit, long decimalUnit, leadingDecimalZeroes )
        // Given:
        Money instance6 = new Money.Builder()
                .currencyCode("USD")
                .sign(SignValue.Negative)
                .wholeUnit(12)
                .decimalUnit(5)
                .leadingDecimalZeroes(3)
                .build();
        long expWholeResult6 = 12;
        long expDecResult6 = 5;
        long expLeadingZeroResult6 = 3;
        SignValue expSignResult6 = SignValue.Negative;
        String expCurrenyCodeResult6 = "USD";

        // When:
        long wholeResult6 = instance6.getWholeUnit();
        long decResult6 = instance6.getDecimalUnit();
        long leadingZeroResult6 = instance6.getLeadingDecimalZeros();
        SignValue signResult6 = instance6.getSign();
        String currenyCodeResult6 = instance6.getCurrencyCode();

        // Then:
        assertEquals(expWholeResult6, wholeResult6);
        assertEquals(expDecResult6, decResult6);
        assertEquals(expLeadingZeroResult6, leadingZeroResult6);
        assertEquals(expSignResult6, signResult6);
        assertEquals(expCurrenyCodeResult6, currenyCodeResult6);
    }

    /**
     * Test of getSign method, of class Money.
     */
    @Test
    public void testGetSign() {
        // Given:
        Money instance = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(12)
                .decimalUnit(5)
                .build();
        SignValue expResult = SignValue.Positive;

        // When:
        SignValue result = instance.getSign();

        // Then:
        assertEquals(expResult, result);
    }

    /**
     * Test of getWholeUnit method, of class Money.
     */
    @Test
    public void testGetWholeUnit() {
        // Given:
        Money instance = new Money.Builder()
                .wholeUnit(12)
                .decimalUnit(5)
                .build();
        long expResult = 12;

        // When:
        long result = instance.getWholeUnit();

        // Then:
        assertEquals(expResult, result);
    }

    /**
     * Test of getDecimalUnit method, of class Money.
     */
    @Test
    public void testGetDecimalUnit() {
        // Given:
        Money instance = new Money.Builder()
                .wholeUnit(12)
                .decimalUnit(5)
                .build();
        long expResult = 5;

        // When:
        long result = instance.getDecimalUnit();

        // Then:
        assertEquals(expResult, result);
    }

    /**
     * Test of getLeadingDecimalZeros method, of class Money.
     */
    @Test
    public void testGetLeadingDecimalZeros() {
        // Given:
        Money instance = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(12)
                .decimalUnit(5)
                .leadingDecimalZeroes(7)
                .build();
        long expResult = 7;

        // When:
        long result = instance.getLeadingDecimalZeros();

        // Then:
        assertEquals(expResult, result);
    }

    /**
     * Test of getCurrencyCode method, of class Money.
     */
    @Test
    public void testGetCurrencyCode() {
        // Given:
        Money instance = new Money.Builder()
                .currencyCode("USD")
                .sign(SignValue.Positive)
                .wholeUnit(12)
                .decimalUnit(5)
                .build();
        String expResult = "USD";

        // When:
        String result = instance.getCurrencyCode();

        // Then:
        assertEquals(expResult, result);
    }

    /**
     * Test of negate method, of class Money.
     */
    @Test
    public void testGetReverseSignMoney() {
        // Given:
        Money instance = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(12)
                .decimalUnit(5)
                .build();
        Money expResult = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(12)
                .decimalUnit(5)
                .build();

        // When:
        Money result = instance.negate();

        // Then:
        assertEquals(expResult, result);
    }

    /**
     * Test of add method, of class Money.
     */
    @Test
    public void testAdd() {
        // CASE 1: (+)(+) & money1 > money2
        // Given:
        Money money1 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(12)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build();
        Money money2 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(3)
                .decimalUnit(15)
                .build();

        Money expResult = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(15)
                .decimalUnit(20)
                .build();

        // When:
        Money result = money1.add(money2);

        // Then:
        assertEquals("Case 1: Invalid sign.",
                expResult.getSign(), result.getSign());
        assertEquals("Case 1: Invalid pounds value.",
                expResult.getWholeUnit(), result.getWholeUnit());
        assertEquals("Case 1: Invalid pence value.",
                expResult.getDecimalUnit(), result.getDecimalUnit());

        // CASE 2: (+)(+) & money1 < money2
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(3)
                .decimalUnit(15)
                .build();

        money2 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(12)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build();

        expResult = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(15)
                .decimalUnit(20)
                .build();

        // When:
        result = money1.add(money2);

        // Then:
        assertEquals("Case 2: Invalid sign.",
                expResult.getSign(), result.getSign());
        assertEquals("Case 2: Invalid pounds value.",
                expResult.getWholeUnit(), result.getWholeUnit());
        assertEquals("Case 2: Invalid pence value.",
                expResult.getDecimalUnit(), result.getDecimalUnit());

        // CASE 3: (+)(+) & money1 = money2
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(3)
                .decimalUnit(15)
                .build();

        money2 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(3)
                .decimalUnit(15)
                .build();

        expResult = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(6)
                .decimalUnit(30)
                .build();

        // When:
        result = money1.add(money2);

        // Then:
        assertEquals("Case 3: Invalid sign.",
                expResult.getSign(), result.getSign());
        assertEquals("Case 3: Invalid pounds value.",
                expResult.getWholeUnit(), result.getWholeUnit());
        assertEquals("Case 3: Invalid pence value.",
                expResult.getDecimalUnit(), result.getDecimalUnit());

        // CASE 4: (+)(-) & money1 > money2
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(12)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build();

        money2 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(3)
                .decimalUnit(15)
                .build();

        expResult = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(8)
                .decimalUnit(90)
                .build();

        // When:
        result = money1.add(money2);

        // Then:
        assertEquals("Case 4: Invalid sign.",
                expResult.getSign(), result.getSign());
        assertEquals("Case 4: Invalid pounds value.",
                expResult.getWholeUnit(), result.getWholeUnit());
        assertEquals("Case 4: Invalid pence value.",
                expResult.getDecimalUnit(), result.getDecimalUnit());

        // CASE 5: (+)(-) & money1 < money2
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(3)
                .decimalUnit(15)
                .build();

        money2 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(12)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build();

        expResult = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(8)
                .decimalUnit(90)
                .build();

        // When:
        result = money1.add(money2);

        // Then:
        assertEquals("Case 5: Invalid sign.",
                expResult.getSign(), result.getSign());
        assertEquals("Case 5: Invalid pounds value.",
                expResult.getWholeUnit(), result.getWholeUnit());
        assertEquals("Case 5: Invalid pence value.",
                expResult.getDecimalUnit(), result.getDecimalUnit());

        // CASE 6: (+)(-) & money1 = money2
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(3)
                .decimalUnit(15)
                .build();

        money2 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(3)
                .decimalUnit(15)
                .build();

        expResult = new Money.Builder()
                .sign(SignValue.Positive)
                .build();

        // When:
        result = money1.add(money2);

        // Then:
        assertEquals("Case 6: Invalid sign.",
                expResult.getSign(), result.getSign());
        assertEquals("Case 6: Invalid pounds value.",
                expResult.getWholeUnit(), result.getWholeUnit());
        assertEquals("Case 6: Invalid pence value.",
                expResult.getDecimalUnit(), result.getDecimalUnit());

        // CASE 7: (-)(+) & money1 > money2
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(12)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build();

        money2 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(3)
                .decimalUnit(15)
                .build();

        expResult = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(8)
                .decimalUnit(90)
                .build();

        // When:
        result = money1.add(money2);

        // Then:
        assertEquals("Case 7: Invalid sign.",
                expResult.getSign(), result.getSign());
        assertEquals("Case 7: Invalid pounds value.",
                expResult.getWholeUnit(), result.getWholeUnit());
        assertEquals("Case 7: Invalid pence value.",
                expResult.getDecimalUnit(), result.getDecimalUnit());

        // CASE 8: (-)(+) & money1 < money2
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(3)
                .decimalUnit(15)
                .build();

        money2 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(12)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build();

        expResult = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(8)
                .decimalUnit(90)
                .build();

        // When:
        result = money1.add(money2);

        // Then:
        assertEquals("Case 8: Invalid sign.",
                expResult.getSign(), result.getSign());
        assertEquals("Case 8: Invalid pounds value.",
                expResult.getWholeUnit(), result.getWholeUnit());
        assertEquals("Case 8: Invalid pence value.",
                expResult.getDecimalUnit(), result.getDecimalUnit());

        // CASE 9: (-)(+) & money1 = money2
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(3)
                .decimalUnit(15)
                .build();

        money2 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(3)
                .decimalUnit(15)
                .build();

        expResult = new Money.Builder()
                .sign(SignValue.Positive)
                .build();

        // When:
        result = money1.add(money2);

        // Then:
        assertEquals("Case 9: Invalid sign.",
                expResult.getSign(), result.getSign());
        assertEquals("Case 9: Invalid pounds value.",
                expResult.getWholeUnit(), result.getWholeUnit());
        assertEquals("Case 9: Invalid pence value.",
                expResult.getDecimalUnit(), result.getDecimalUnit());

        // CASE 10: (-)(-) & money1 > money2
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(12)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build();

        money2 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(3)
                .decimalUnit(15)
                .build();

        expResult = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(15)
                .decimalUnit(20)
                .build();

        // When:
        result = money1.add(money2);

        // Then:
        assertEquals("Case 10: Invalid sign.",
                expResult.getSign(), result.getSign());
        assertEquals("Case 10: Invalid pounds value.",
                expResult.getWholeUnit(), result.getWholeUnit());
        assertEquals("Case 10: Invalid pence value.",
                expResult.getDecimalUnit(), result.getDecimalUnit());

        // CASE 11: (-)(-) & money1 < money2
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(3)
                .decimalUnit(15)
                .build();

        money2 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(12)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build();

        expResult = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(15)
                .decimalUnit(20)
                .build();

        // When:
        result = money1.add(money2);

        // Then:
        assertEquals("Case 11: Invalid sign.",
                expResult.getSign(), result.getSign());
        assertEquals("Case 11: Invalid pounds value.",
                expResult.getWholeUnit(), result.getWholeUnit());
        assertEquals("Case 11: Invalid pence value.",
                expResult.getDecimalUnit(), result.getDecimalUnit());

        // CASE 12: (-)(-) & money1 = money2
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(3)
                .decimalUnit(15)
                .build();

        money2 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(3)
                .decimalUnit(15)
                .build();

        expResult = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(6)
                .decimalUnit(30)
                .build();

        // When:
        result = money1.add(money2);

        // Then:
        assertEquals("Case 12: Invalid sign.",
                expResult.getSign(), result.getSign());
        assertEquals("Case 12: Invalid pounds value.",
                expResult.getWholeUnit(), result.getWholeUnit());
        assertEquals("Case 12: Invalid pence value.",
                expResult.getDecimalUnit(), result.getDecimalUnit());
    }

    /**
     * Test of subtract method, of class Money.
     */
    @Test
    public void testSubtract() {
        // CASE 1: (+)(+) & money1 > money2
        // Given:
        Money money1 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(12)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build();

        Money money2 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(3)
                .decimalUnit(15)
                .build();

        Money expResult = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(8)
                .decimalUnit(90)
                .build();

        // When:
        Money result = money1.subtract(money2);

        // Then:
        assertEquals("Case 1: Invalid sign.",
                expResult.getSign(), result.getSign());
        assertEquals("Case 1: Invalid pounds value.",
                expResult.getWholeUnit(), result.getWholeUnit());
        assertEquals("Case 1: Invalid pence value.",
                expResult.getDecimalUnit(), result.getDecimalUnit());

        // CASE 2: (+)(+) & money1 < money2
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(3)
                .decimalUnit(15)
                .build();

        money2 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(12)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build();

        expResult = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(8)
                .decimalUnit(90)
                .build();

        // When:
        result = money1.subtract(money2);

        // Then:
        assertEquals("Case 2: Invalid sign.",
                expResult.getSign(), result.getSign());
        assertEquals("Case 2: Invalid pounds value.",
                expResult.getWholeUnit(), result.getWholeUnit());
        assertEquals("Case 2: Invalid pence value.",
                expResult.getDecimalUnit(), result.getDecimalUnit());

        // CASE 3: (+)(+) & money1 = money2
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(3)
                .decimalUnit(15)
                .build();

        money2 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(3)
                .decimalUnit(15)
                .build();

        expResult = new Money.Builder()
                .sign(SignValue.Positive)
                .build();

        // When:
        result = money1.subtract(money2);

        // Then:
        assertEquals("Case 3: Invalid sign.",
                expResult.getSign(), result.getSign());
        assertEquals("Case 3: Invalid pounds value.",
                expResult.getWholeUnit(), result.getWholeUnit());
        assertEquals("Case 3: Invalid pence value.",
                expResult.getDecimalUnit(), result.getDecimalUnit());

        // CASE 4: (+)(-) & money1 > money2
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(12)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build();

        money2 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(3)
                .decimalUnit(15)
                .build();

        expResult = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(15)
                .decimalUnit(20)
                .build();

        // When:
        result = money1.subtract(money2);

        // Then:
        assertEquals("Case 4: Invalid sign.",
                expResult.getSign(), result.getSign());
        assertEquals("Case 4: Invalid pounds value.",
                expResult.getWholeUnit(), result.getWholeUnit());
        assertEquals("Case 4: Invalid pence value.",
                expResult.getDecimalUnit(), result.getDecimalUnit());

        // CASE 5: (+)(-) & money1 < money2
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(3)
                .decimalUnit(15)
                .build();

        money2 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(12)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build();

        expResult = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(15)
                .decimalUnit(20)
                .build();

        // When:
        result = money1.subtract(money2);

        // Then:
        assertEquals("Case 5: Invalid sign.",
                expResult.getSign(), result.getSign());
        assertEquals("Case 5: Invalid pounds value.",
                expResult.getWholeUnit(), result.getWholeUnit());
        assertEquals("Case 5: Invalid pence value.",
                expResult.getDecimalUnit(), result.getDecimalUnit());

        // CASE 6: (+)(-) & money1 = money2
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(3)
                .decimalUnit(15)
                .build();

        money2 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(3)
                .decimalUnit(15)
                .build();

        expResult = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(6)
                .decimalUnit(30)
                .build();

        // When:
        result = money1.subtract(money2);

        // Then:
        assertEquals("Case 6: Invalid sign.",
                expResult.getSign(), result.getSign());
        assertEquals("Case 6: Invalid pounds value.",
                expResult.getWholeUnit(), result.getWholeUnit());
        assertEquals("Case 6: Invalid pence value.",
                expResult.getDecimalUnit(), result.getDecimalUnit());

        // CASE 7: (-)(+) & money1 > money2
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(12)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build();

        money2 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(3)
                .decimalUnit(15)
                .build();

        expResult = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(15)
                .decimalUnit(20)
                .build();

        // When:
        result = money1.subtract(money2);

        // Then:
        assertEquals("Case 7: Invalid sign.",
                expResult.getSign(), result.getSign());
        assertEquals("Case 7: Invalid pounds value.",
                expResult.getWholeUnit(), result.getWholeUnit());
        assertEquals("Case 7: Invalid pence value.",
                expResult.getDecimalUnit(), result.getDecimalUnit());

        // CASE 8: (-)(+) & money1 < money2
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(3)
                .decimalUnit(15)
                .build();

        money2 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(12)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build();

        expResult = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(15)
                .decimalUnit(20)
                .build();

        // When:
        result = money1.subtract(money2);

        // Then:
        assertEquals("Case 8: Invalid sign.",
                expResult.getSign(), result.getSign());
        assertEquals("Case 8: Invalid pounds value.",
                expResult.getWholeUnit(), result.getWholeUnit());
        assertEquals("Case 8: Invalid pence value.",
                expResult.getDecimalUnit(), result.getDecimalUnit());

        // CASE 9: (-)(+) & money1 = money2
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(3)
                .decimalUnit(15)
                .build();

        money2 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(3)
                .decimalUnit(15)
                .build();

        expResult = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(6)
                .decimalUnit(30)
                .build();

        // When:
        result = money1.subtract(money2);

        // Then:
        assertEquals("Case 9: Invalid sign.",
                expResult.getSign(), result.getSign());
        assertEquals("Case 9: Invalid pounds value.",
                expResult.getWholeUnit(), result.getWholeUnit());
        assertEquals("Case 9: Invalid pence value.",
                expResult.getDecimalUnit(), result.getDecimalUnit());

        // CASE 10: (-)(-) & money1 > money2
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(12)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build();

        money2 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(3)
                .decimalUnit(15)
                .build();

        expResult = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(8)
                .decimalUnit(90)
                .build();

        // When:
        result = money1.subtract(money2);

        // Then:
        assertEquals("Case 10: Invalid sign.",
                expResult.getSign(), result.getSign());
        assertEquals("Case 10: Invalid pounds value.",
                expResult.getWholeUnit(), result.getWholeUnit());
        assertEquals("Case 10: Invalid pence value.",
                expResult.getDecimalUnit(), result.getDecimalUnit());

        // CASE 11: (-)(-) & money1 < money2
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(3)
                .decimalUnit(15)
                .build();

        money2 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(12)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build();

        expResult = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(8)
                .decimalUnit(90)
                .build();

        // When:
        result = money1.subtract(money2);

        // Then:
        assertEquals("Case 11: Invalid sign.",
                expResult.getSign(), result.getSign());
        assertEquals("Case 11: Invalid pounds value.",
                expResult.getWholeUnit(), result.getWholeUnit());
        assertEquals("Case 11: Invalid pence value.",
                expResult.getDecimalUnit(), result.getDecimalUnit());

        // CASE 12: (-)(-) & money1 = money2
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(3)
                .decimalUnit(15)
                .build();

        money2 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(3)
                .decimalUnit(15)
                .build();

        expResult = new Money.Builder()
                .sign(SignValue.Positive)
                .build();

        // When:
        result = money1.subtract(money2);

        // Then:
        assertEquals("Case 12: Invalid sign.",
                expResult.getSign(), result.getSign());
        assertEquals("Case 12: Invalid pounds value.",
                expResult.getWholeUnit(), result.getWholeUnit());
        assertEquals("Case 12: Invalid pence value.",
                expResult.getDecimalUnit(), result.getDecimalUnit());
    }

    /**
     * Test of isPositive method, of class Money.
     */
    @Test
    public void testIsPositive() {
        // Case 1: value > 0
        // Given:
        Money money = new Money.Builder()
                .wholeUnit(32)
                .build();
        boolean expResult = true;

        // When:
        boolean result = money.isPositive();

        // Then:
        assertEquals("Case 1: Value > 0. Should be " + expResult
                + ", but instead it's " + result, expResult, result);

        // Case 2: value = 0
        // Given:
        money = new Money.Builder().build();
        expResult = false;

        // When:
        result = money.isPositive();

        // Then:
        assertEquals("Case 2: Value = 0. Should be " + expResult
                + ", but instead it's " + result, expResult, result);

        // Case 3: value < 0
        // Given:
        money = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(12)
                .decimalUnit(80)
                .build();
        expResult = false;

        // When:
        result = money.isPositive();

        // Then:
        assertEquals("Case 3: Value < 0. Should be " + expResult
                + ", but instead it's " + result, expResult, result);
    }

    /**
     * Test of isNegative method, of class Money.
     */
    @Test
    public void testIsNegative() {
        // Case 1: value > 0
        // Given:
        Money money = new Money.Builder()
                .wholeUnit(32)
                .build();
        boolean expResult = false;

        // When:
        boolean result = money.isNegative();

        // Then:
        assertEquals("Case 1: Value > 0. Should be " + expResult
                + ", but instead it's " + result, expResult, result);

        // Case 2: value = 0
        // Given:
        money = new Money.Builder().build();
        expResult = false;

        // When:
        result = money.isNegative();

        // Then:
        assertEquals("Case 2: Value = 0. Should be " + expResult
                + ", but instead it's " + result, expResult, result);

        // Case 3: value < 0
        // Given:
        money = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(12)
                .decimalUnit(80)
                .build();
        expResult = true;

        // When:
        result = money.isNegative();

        // Then:
        assertEquals("Case 3: Value < 0. Should be " + expResult
                + ", but instead it's " + result, expResult, result);
    }

    /**
     * Test of isZero method, of class Money.
     */
    @Test
    public void testIsZero() {
        // Case 1: value > 0
        // Given:
        Money money = new Money.Builder()
                .wholeUnit(32)
                .build();
        boolean expResult = false;

        // When:
        boolean result = money.isZero();

        // Then:
        assertEquals("Case 1: Value > 0. Should be " + expResult
                + ", but instead it's " + result, expResult, result);

        // Case 2: value = 0
        // Given:
        money = new Money.Builder().build();
        expResult = true;

        // When:
        result = money.isZero();

        // Then:
        assertEquals("Case 2: Value = 0. Should be " + expResult
                + ", but instead it's " + result, expResult, result);

        // Case 3: value < 0
        // Given:
        money = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(12)
                .decimalUnit(80)
                .build();
        expResult = false;

        // When:
        result = money.isZero();

        // Then:
        assertEquals("Case 3: Value < 0. Should be " + expResult
                + ", but instead it's " + result, expResult, result);
    }

    /**
     * Test of isNotZero method, of class Money.
     */
    @Test
    public void testIsNotZero() {
        // Case 1: value > 0
        // Given:
        Money money = new Money.Builder()
                .wholeUnit(32)
                .build();
        boolean expResult = true;

        // When:
        boolean result = money.isNotZero();

        // Then:
        assertEquals("Case 1: Value > 0. Should be " + expResult
                + ", but instead it's " + result, expResult, result);

        // Case 2: value = 0
        // Given:
        money = new Money.Builder().build();
        expResult = false;

        // When:
        result = money.isNotZero();

        // Then:
        assertEquals("Case 2: Value = 0. Should be " + expResult
                + ", but instead it's " + result, expResult, result);

        // Case 3: value < 0
        // Given:
        money = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(12)
                .decimalUnit(80)
                .build();
        expResult = true;

        // When:
        result = money.isNotZero();

        // Then:
        assertEquals("Case 3: Value < 0. Should be " + expResult
                + ", but instead it's " + result, expResult, result);
    }

    /**
     * Test of isLessThan method, of class Money.
     */
    @Test
    public void testIsLessThan() {
        // Case 1: (+)(+) & absolute (money1 < money2)
        // Given:
        Money money1 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(6)
                .decimalUnit(50)
                .build();
        Money money2 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(6)
                .decimalUnit(83)
                .build();

        boolean expResult = true;

        // When:
        boolean result = money1.isLessThan(money2);

        // Then:
        assertEquals("Case 1:  Where absolute (Money1 < Money2). Should be "
                + expResult + ", but instead it's " + result, expResult, result);

        // Case 2: (+)(+) & absolute (money1 > money2)
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build();

        money2 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(183)
                .decimalUnit(299)
                .build();

        expResult = false;

        // When:
        result = money1.isLessThan(money2);

        // Then:
        assertEquals("Case 2: Where absolute (Money1 > Money2). Should be "
                + expResult + ", but instead it's " + result, expResult, result);

        // Case 3: (+)(+) & absolute (money1 = money2)
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build();

        money2 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build();

        expResult = false;

        // When:
        result = money1.isLessThan(money2);

        // Then:
        assertEquals("Case 3: Where absolute (Money1 = Money2). Should be "
                + expResult + ", but instead it's " + result, expResult, result);

        // Case 4: (+)(-) & absolute (money1 < money2)
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(6)
                .decimalUnit(50)
                .build();

        money2 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(183)
                .decimalUnit(299)
                .build();

        expResult = false;

        // When:
        result = money1.isLessThan(money2);

        // Then:
        assertEquals("Case 4:  Where absolute (Money1 < Money2). Should be "
                + expResult + ", but instead it's " + result, expResult, result);

        // Case 5: (+)(-) & absolute (money1 > money2)
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build();

        money2 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(183)
                .decimalUnit(299)
                .build();

        expResult = false;

        // When:
        result = money1.isLessThan(money2);

        // Then:
        assertEquals("Case 5: Where absolute (Money1 > Money2). Should be "
                + expResult + ", but instead it's " + result, expResult, result);

        // Case 6: (+)(-) & absolute (money1 = money2)
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build();

        money2 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build();

        expResult = false;

        // When:
        result = money1.isLessThan(money2);

        // Then:
        assertEquals("Case 6: Where absolute (Money1 = Money2). Should be "
                + expResult + ", but instead it's " + result, expResult, result);

        // Case 7: (-)(+) & absolute (money1 < money2)
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(15)
                .decimalUnit(50)
                .build();

        money2 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(15)
                .decimalUnit(299)
                .build();

        expResult = true;

        // When:
        result = money1.isLessThan(money2);

        // Then:
        assertEquals("Case 7:  Where absolute (Money1 < Money2). Should be "
                + expResult + ", but instead it's " + result, expResult, result);

        // Case 8: (-)(+) & absolute (money1 > money2)
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build();

        money2 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(2963)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build();

        expResult = true;

        // When:
        result = money1.isLessThan(money2);

        // Then:
        assertEquals("Case 8: Where absolute (Money1 > Money2). Should be "
                + expResult + ", but instead it's " + result, expResult, result);

        // Case 9: (-)(+) & absolute (money1 = money2)
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build();

        money2 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build();

        expResult = true;

        // When:
        result = money1.isLessThan(money2);

        // Then:
        assertEquals("Case 9: Where absolute (Money1 = Money2). Should be "
                + expResult + ", but instead it's " + result, expResult, result);

        // Case 10: (-)(-) & absolute (money1 < money2)
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(15)
                .decimalUnit(50)
                .build();

        money2 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(15)
                .decimalUnit(299)
                .build();

        expResult = true;

        // When:
        result = money1.isLessThan(money2);

        // Then:
        assertEquals("Case 10:  Where absolute (Money1 < Money2). Should be "
                + expResult + ", but instead it's " + result, expResult, result);

        // Case 11: (-)(-) & absolute (money1 > money2)
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build();

        money2 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(2963)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build();

        expResult = true;

        // When:
        result = money1.isLessThan(money2);

        // Then:
        assertEquals("Case 11: Where absolute (Money1 > Money2). Should be "
                + expResult + ", but instead it's " + result, expResult, result);

        // Case 12: (-)(-) & absolute (money1 = money2)
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build();

        money2 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build();

        expResult = false;

        // When:
        result = money1.isLessThan(money2);

        // Then:
        assertEquals("Case 12: Where absolute (Money1 = Money2). Should be "
                + expResult + ", but instead it's " + result, expResult, result);
    }

    /**
     * Test of isSameCurrencyCodes method, of class Money.
     */
    @Test
    public void testIsSameCurrencyCodes() {

    }

    /**
     * Test of toString method, of class Money.
     */
    @Test
    public void testToString() {
        // Case 1: Value > 0
        // Given:
        Money instance = new Money.Builder()
                .currencyCode("PHP")
                .sign(SignValue.Positive)
                .wholeUnit(12569)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build();
        String expResult = "PHP12,569.05";

        // When:
        String result = instance.toString();

        // Then:
        assertEquals("Case 1: Value > 0. Should be " + expResult
                + ", but instead it's " + result, expResult, result);

        // Case 2: Value = 0
        // Given:
        Money instance2 = new Money.Builder().build();
        String currencyCode
                = Currency.getInstance(Locale.getDefault()).getSymbol();
        String expResult2 = currencyCode + "0.00";

        // When:
        String result2 = instance2.toString();

        // Then:
        assertEquals("Case 2: Value = 0.", expResult2, result2);

    }

    /**
     * Test of equals method, of class Money.
     */
    @Test
    public void testEquals() {
        // Given
        SignValue[] listOfSign = new SignValue[]{SignValue.Negative,
            SignValue.Positive,
            SignValue.Positive,
            SignValue.Positive,
            SignValue.Positive};
        long[] listOfWholeUnitsForFactory = new long[]{-25, 1256, 1589647859, 0, 0};
        long[] listOfWholeUnitsForBuilder = new long[]{25, 1256, 1589647859, 0, 0};
        long[] listOfDecimalUnits = new long[]{0, 5, 88596, 0, 856984478};
        long[] listOfLeadingZeroUnits = new long[]{0, 1, 7, 0, 2};

        Money[] expMonies = new Money[]{new Money.Builder()
            .currencyCode("USD")
            .sign(SignValue.Negative)
            .wholeUnit(25)
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
            .sign(SignValue.Positive)
            .decimalUnit(856984478)
            .leadingDecimalZeroes(2)
            .build()};

        MoneyFactory moneyFactory = new MoneyFactory("USD");

        for (int i = 0; i < listOfWholeUnitsForFactory.length; i++) {
            // When
            Money resultMoney1 = moneyFactory.valueOf(
                    listOfWholeUnitsForFactory[i],
                    listOfDecimalUnits[i],
                    listOfLeadingZeroUnits[i]);

            Money resultMoney2 = new Money.Builder()
                    .currencyCode("USD")
                    .sign(listOfSign[i])
                    .wholeUnit(listOfWholeUnitsForBuilder[i])
                    .decimalUnit(listOfDecimalUnits[i])
                    .leadingDecimalZeroes(listOfLeadingZeroUnits[i])
                    .build();
            
            // Then
            assertEquals(expMonies[i], resultMoney1);
            assertEquals(expMonies[i], resultMoney2);
        }
    }

    /**
     * Test of hashCode method, of class Money.
     */
    @Test
    public void testHashCode() {
        // Given
        Money moneyA = new Money.Builder()
                .wholeUnit(1)
                .build();
        
        Money moneyB = new Money.Builder()
                .wholeUnit(1)
                .build();
        
        // When and Then
        assertTrue(moneyA.equals(moneyB) && moneyB.equals(moneyA));
        assertTrue(moneyA.hashCode() == moneyB.hashCode());
    }

    /**
     * Test of compareTo method, of class Money.
     */
    @Test
    public void testCompareTo() {
        // Case 1: (+)(+) & absolute (money1 < money2)
        // Given:
        Money money1 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(6)
                .decimalUnit(50)
                .build();
        Money money2 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(6)
                .decimalUnit(83)
                .build();

        int expResult = -1;

        // When:
        int result = money1.compareTo(money2);

        // Then:
        assertEquals("Case 1:  Where absolute (Money1 < Money2). Should be "
                + expResult + ", but instead it's " + result, expResult, result);

        // Case 2: (+)(+) & absolute (money1 > money2)
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build();
        money2 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(183)
                .decimalUnit(299)
                .build();

        expResult = 1;

        // When:
        result = money1.compareTo(money2);

        // Then:
        assertEquals("Case 2: Where absolute (Money1 > Money2). Should be "
                + expResult + ", but instead it's " + result, expResult, result);

        // Case 3: (+)(+) & absolute (money1 = money2)
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build();
        
        money2 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build();
                
        expResult = 0;

        // When:
        result = money1.compareTo(money2);

        // Then:
        assertEquals("Case 3: Where absolute (Money1 = Money2). Should be "
                + expResult + ", but instead it's " + result, expResult, result);

        // Case 4: (+)(-) & absolute (money1 < money2)
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(6)
                .decimalUnit(50)
                .build();
                
        money2 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(183)
                .decimalUnit(299)
                .build();
                
        expResult = 1;

        // When:
        result = money1.compareTo(money2);

        // Then:
        assertEquals("Case 4:  Where absolute (Money1 < Money2). Should be "
                + expResult + ", but instead it's " + result, expResult, result);

        // Case 5: (+)(-) & absolute (money1 > money2)
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build();
                
        money2 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(183)
                .decimalUnit(299)
                .build();

        expResult = 1;

        // When:
        result = money1.compareTo(money2);

        // Then:
        assertEquals("Case 5: Where absolute (Money1 > Money2). Should be "
                + expResult + ", but instead it's " + result, expResult, result);

        // Case 6: (+)(-) & absolute (money1 = money2)
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build();
                
        money2 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build();

        expResult = 1;

        // When:
        result = money1.compareTo(money2);

        // Then:
        assertEquals("Case 6: Where absolute (Money1 = Money2). Should be "
                + expResult + ", but instead it's " + result, expResult, result);

        // Case 7: (-)(+) & absolute (money1 < money2)
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(15)
                .decimalUnit(50)
                .build();
        
        money2 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(15)
                .decimalUnit(299)
                .build();
                
        expResult = -1;

        // When:
        result = money1.compareTo(money2);

        // Then:
        assertEquals("Case 7:  Where absolute (Money1 < Money2). Should be "
                + expResult + ", but instead it's " + result, expResult, result);

        // Case 8: (-)(+) & absolute (money1 > money2)
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build();
        
        money2 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(2963)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build();

        expResult = -1;

        // When:
        result = money1.compareTo(money2);

        // Then:
        assertEquals("Case 8: Where absolute (Money1 > Money2). Should be "
                + expResult + ", but instead it's " + result, expResult, result);

        // Case 9: (-)(+) & absolute (money1 = money2)
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build();
                
        money2 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build();
        
        expResult = -1;

        // When:
        result = money1.compareTo(money2);

        // Then:
        assertEquals("Case 9: Where absolute (Money1 = Money2). Should be "
                + expResult + ", but instead it's " + result, expResult, result);

        // Case 10: (-)(-) & absolute (money1 < money2)
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(15)
                .decimalUnit(50)
                .build();
        
        money2 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(15)
                .decimalUnit(299)
                .build();

        expResult = -1;

        // When:
        result = money1.compareTo(money2);

        // Then:
        assertEquals("Case 10:  Where absolute (Money1 < Money2). Should be "
                + expResult + ", but instead it's " + result, expResult, result);

        // Case 11: (-)(-) & absolute (money1 > money2)
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build();
        
        money2 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(2963)
                .decimalUnit(05)
                .leadingDecimalZeroes(1)
                .build();

        expResult = -1;

        // When:
        result = money1.compareTo(money2);

        // Then:
        assertEquals("Case 11: Where absolute (Money1 > Money2). Should be "
                + expResult + ", but instead it's " + result, expResult, result);

        // Case 12: (-)(-) & absolute (money1 = money2)
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build();
        
        money2 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build();

        expResult = 0;

        // When:
        result = money1.compareTo(money2);

        // Then:
        assertEquals("Case 12: Where absolute (Money1 = Money2). Should be "
                + expResult + ", but instead it's " + result, expResult, result);
    }

    /**
     * Test of isGreaterThan method, of class Money.
     */
    @Test
    public void testIsGreaterThan() {
        // Case 1: (+)(+) & absolute (money1 < money2)
        // Given:
        Money money1 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(6)
                .decimalUnit(50)
                .build();
                
        Money money2 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(6)
                .decimalUnit(83)
                .build();

        boolean expResult = false;

        // When:
        boolean result = money1.isGreaterThan(money2);

        // Then:
        assertEquals("Case 1:  Where absolute (Money1 < Money2). Should be "
                + expResult + ", but instead it's " + result, expResult, result);

        // Case 2: (+)(+) & absolute (money1 > money2)
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build();
                
        money2 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(183)
                .decimalUnit(299)
                .build();

        expResult = true;

        // When:
        result = money1.isGreaterThan(money2);

        // Then:
        assertEquals("Case 2: Where absolute (Money1 > Money2). Should be "
                + expResult + ", but instead it's " + result, expResult, result);

        // Case 3: (+)(+) & absolute (money1 = money2)
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build();
        
        money2 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build();

        expResult = false;

        // When:
        result = money1.isGreaterThan(money2);

        // Then:
        assertEquals("Case 3: Where absolute (Money1 = Money2). Should be "
                + expResult + ", but instead it's " + result, expResult, result);

        // Case 4: (+)(-) & absolute (money1 < money2)
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(6)
                .decimalUnit(50)
                .build();
        
        money2 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(183)
                .decimalUnit(299)
                .build();

        expResult = true;

        // When:
        result = money1.isGreaterThan(money2);

        // Then:
        assertEquals("Case 4:  Where absolute (Money1 < Money2). Should be "
                + expResult + ", but instead it's " + result, expResult, result);

        // Case 5: (+)(-) & absolute (money1 > money2)
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build();
                
        money2 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(183)
                .decimalUnit(299)
                .build();

        expResult = true;

        // When:
        result = money1.isGreaterThan(money2);

        // Then:
        assertEquals("Case 5: Where absolute (Money1 > Money2). Should be "
                + expResult + ", but instead it's " + result, expResult, result);

        // Case 6: (+)(-) & absolute (money1 = money2)
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build();
        
        money2 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build();

        expResult = true;

        // When:
        result = money1.isGreaterThan(money2);

        // Then:
        assertEquals("Case 6: Where absolute (Money1 = Money2). Should be "
                + expResult + ", but instead it's " + result, expResult, result);

        // Case 7: (-)(+) & absolute (money1 < money2)
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(15)
                .decimalUnit(50)
                .build();
        
        money2 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(15)
                .decimalUnit(299)
                .build();

        expResult = false;

        // When:
        result = money1.isGreaterThan(money2);

        // Then:
        assertEquals("Case 7:  Where absolute (Money1 < Money2). Should be "
                + expResult + ", but instead it's " + result, expResult, result);

        // Case 8: (-)(+) & absolute (money1 > money2)
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build();
                
        money2 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(2963)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build();

        expResult = false;

        // When:
        result = money1.isGreaterThan(money2);

        // Then:
        assertEquals("Case 8: Where absolute (Money1 > Money2). Should be "
                + expResult + ", but instead it's " + result, expResult, result);

        // Case 9: (-)(+) & absolute (money1 = money2)
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build();
                
        money2 = new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build();

        expResult = false;

        // When:
        result = money1.isGreaterThan(money2);

        // Then:
        assertEquals("Case 9: Where absolute (Money1 = Money2). Should be "
                + expResult + ", but instead it's " + result, expResult, result);

        // Case 10: (-)(-) & absolute (money1 < money2)
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(15)
                .decimalUnit(50)
                .build();
        
        money2 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(15)
                .decimalUnit(299)
                .build();

        expResult = false;

        // When:
        result = money1.isGreaterThan(money2);

        // Then:
        assertEquals("Case 10:  Where absolute (Money1 < Money2). Should be "
                + expResult + ", but instead it's " + result, expResult, result);

        // Case 11: (-)(-) & absolute (money1 > money2)
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build();
                
        money2 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(2963)
                .decimalUnit(05)
                .leadingDecimalZeroes(1)
                .build();

        expResult = false;

        // When:
        result = money1.isGreaterThan(money2);

        // Then:
        assertEquals("Case 11: Where absolute (Money1 > Money2). Should be "
                + expResult + ", but instead it's " + result, expResult, result);

        // Case 12: (-)(-) & absolute (money1 = money2)
        // Given:
        money1 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build();
        
        money2 = new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build();
        
        expResult = false;

        // When:
        result = money1.isGreaterThan(money2);

        // Then:
        assertEquals("Case 12: Where absolute (Money1 = Money2). Should be "
                + expResult + ", but instead it's " + result, expResult, result);
    }

    /**
     * Test of toStringDecimal method, of class Money.
     */
    @Test
    public void testToStringDecimal() {
        // Case 1: Value > 0
        // Given:
        Money instance = new Money.Builder()
                .currencyCode("PHP")
                .sign(SignValue.Positive)
                .wholeUnit(12569)
                .decimalUnit(5)
                .leadingDecimalZeroes(5)
                .build();
                
        String expResult = "12569.000005";

        // When:
        String result = instance.toStringDecimal();

        // Then:
        assertEquals("Case 1: Value > 0. Should be " + expResult
                + ", but instead it's " + result, expResult, result);

        // Case 2: Value = 0
        // Given:
        instance = new Money.Builder().build();
        expResult = "0.0";

        // When:
        result = instance.toStringDecimal();

        // Then:
        assertEquals("Case 2: Value = 0. Should be " + expResult
                + ", but instead it's " + result, expResult, result);

        // Case 3: Value < 0
        // Given:
        instance = new Money.Builder()
                .currencyCode("PHP")
                .sign(SignValue.Negative)
                .wholeUnit(1246580)
                .decimalUnit(9)
                .leadingDecimalZeroes(1)
                .build();
        
        expResult = "-1246580.09";

        // When:
        result = instance.toStringDecimal();

        // Then:
        assertEquals("Case 3: Value < 0. Should be " + expResult
                + ", but instead it's " + result, expResult, result);
    }

    /**
     * Test of multiply method, of class Money.
     */
    @Test
    public void testMultiply_Money() {
        // Given
        Money[] listOfInput1 = new Money[]{
            new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(6)
                .decimalUnit(50)
                .build(),
            new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(2963)
                .decimalUnit(5)
                .leadingDecimalZeroes(5)
                .build(),
            new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build(),
            new Money.Builder().build(),
            new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(2963)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build(),
            new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(1)
                .build(),
            new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(15)
                .decimalUnit(50)
                .build(),
            new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build(),
            new Money.Builder().build(),
            new Money.Builder()
                .sign(SignValue.Positive)
                .decimalUnit(55960)
                .build()
        };
        
        Money[] listOfInput2 = new Money[]{
        new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(6)
                .decimalUnit(83)
                .build(),
            new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(183)
                .decimalUnit(299)
                .build(),
            new Money.Builder().build(),
            new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build(),
            new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(183)
                .decimalUnit(299)
                .build(),
            new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build(),
            new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(15)
                .decimalUnit(299)
                .build(),
            new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(6)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build(),
            new Money.Builder().build(),
            new Money.Builder()
                .sign(SignValue.Positive)
                .decimalUnit(2)
                .leadingDecimalZeroes(1)
                .build()
        };
        
        Money[] listOfExpResults = new Money[]
        {
            new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(44)
                .decimalUnit(395)
                .build(),
            new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(543114)
                .decimalUnit(937916495)
                .build(),
            new Money.Builder().build(),
            new Money.Builder().build(),
            new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(543124)
                .decimalUnit(10195)
                .build(),
            new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build(),
            new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(237)
                .decimalUnit(1345)
                .build(),
            new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(17929)
                .decimalUnit(175)
                .build(),
            new Money.Builder().build(),
            new Money.Builder()
                .sign(SignValue.Positive)
                .decimalUnit(11192)
                .leadingDecimalZeroes(1)
                .build()
        };
        
        for (int i = 0; i < listOfExpResults.length; i++) {
            // When
            Money result = listOfInput1[i].multiply(listOfInput2[i]);
            
            // Then
            assertEquals(result, listOfExpResults[i]);
        }
    }

    /**
     * Test of multiply method, of class Money.
     */
    @Test
        public void testMultiply_long() {
        // Given
        Money[] listOfInput1 = new Money[]
        {
            new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(6)
                .decimalUnit(50)
                .build(),
            new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(2963)
                .decimalUnit(5)
                .leadingDecimalZeroes(5)
                .build(),
            new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build(),
            new Money.Builder().build(),
            new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(2963)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build(),
            new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(1)
                .build(),
            new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(15)
                .decimalUnit(50)
                .build(),
            new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build(),
            new Money.Builder().build(),
            new Money.Builder()
                .sign(SignValue.Positive)
                .decimalUnit(55960)
                .build()
            
        };
        
        long[] listOfInput2 = new long[]
        {
            6,
            -183,
            0,
            123412,
            1
        };
        
        Money[] listOfExpResults = new Money[]
        {
            new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(39)
                .build(),
            new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(542229)
                .decimalUnit(915)
                .leadingDecimalZeroes(3)
                .build(),
            new Money.Builder().build(),
            new Money.Builder().build(),
            new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(2963)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build()
        };
        
        for (int i = 0; i < listOfExpResults.length; i++) {
            // When
            Money result = listOfInput1[i].multiply(listOfInput2[i]);
            
            // Then
            assertEquals(result, listOfExpResults[i]);
        }
    }

    /**
     * Test of multiply method, of class Money.
     */
    @Test
        public void testMultiply_int() {
        // Given
        Money[] listOfInput1 = new Money[]
        {
            new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(6)
                .decimalUnit(50)
                .build(),
            new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(2963)
                .decimalUnit(5)
                .leadingDecimalZeroes(5)
                .build(),
            new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build(),
            new Money.Builder().build(),
            new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(2963)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build(),
            new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(1)
                .build(),
            new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(15)
                .decimalUnit(50)
                .build(),
            new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build(),
            new Money.Builder().build(),
            new Money.Builder()
                .sign(SignValue.Positive)
                .decimalUnit(55960)
                .build()
        };
        
        int[] listOfInput2 = new int[]
        {
            6,
            -183,
            0,
            123412,
            1
        };
        
        Money[] listOfExpResults = new Money[]
        {
            new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(39)
                .build(),
            new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(542229)
                .decimalUnit(915)
                .leadingDecimalZeroes(3)
                .build(),
            new Money.Builder().build(),
            new Money.Builder().build(),
            new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(2963)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build()
        };
        
        for (int i = 0; i < listOfExpResults.length; i++) {
            // When
            Money result = listOfInput1[i].multiply(listOfInput2[i]);
            
            // Then
            assertEquals(result, listOfExpResults[i]);
        }
    }

    /**
     * Test of divide method, of class Money.
     */
    @Test
        public void testDivide() {
        MoneyFactory m = new MoneyFactory();
        // Given
        Money[] listOfInput1 = new Money[]
        {
            new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(6)
                .decimalUnit(50)
                .build(),
            new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(2963)
                .decimalUnit(5)
                .leadingDecimalZeroes(5)
                .build(),
            new Money.Builder().build(),
            new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(2963)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build(),
            new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(1)
                .build(),
            new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(15)
                .decimalUnit(50)
                .build(),
            new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build(),
            new Money.Builder()
                .sign(SignValue.Positive)
                .decimalUnit(55960)
                .build()
        };
        
        Money[] listOfInput2 = new Money[]
        {
            new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(6)
                .decimalUnit(83)
                .build(),
            new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(183)
                .decimalUnit(299)
                .build(),
            new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build(),
            new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(183)
                .decimalUnit(299)
                .build(),
            new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(2963)
                .decimalUnit(50)
                .build(),
            new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(15)
                .decimalUnit(299)
                .build(),
            new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(6)
                .decimalUnit(5)
                .leadingDecimalZeroes(1)
                .build(),
            new Money.Builder()
                .sign(SignValue.Positive)
                .decimalUnit(2)
                .leadingDecimalZeroes(1)
                .build()
        };
        
        Money[] listOfExpResults = new Money[]
        {
            m.valueOf("0.9516837481698389"),
            m.valueOf("16.16484544378310848"),
            new Money.Builder().build(),
            m.valueOf("-16.16511819486194687"),
            m.valueOf("-0.000337438839210393"),
            m.valueOf("-1.013138113602196222"),
            m.valueOf("-489.8347107438016529"),
            new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(27)
                .decimalUnit(98)
                .build()
        };
        
        for (int i = 0; i < listOfExpResults.length; i++) {
            // When
            Money result = listOfInput1[i].divide(listOfInput2[i]);
            
            // Then
            assertEquals(result, listOfExpResults[i]);
        }
    }
    
    /**
     * Test of power method, of class Money.
     */
    @Test
        public void testPower_long() {
        MoneyFactory mf = new MoneyFactory();
        // Given
        Money[] listOfInput1 = new Money[]
        {
            new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(5000)
                .build(),
            new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(2963)
                .build(),
            new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(8)
                .build(),
            new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(5000)
                .build(),
            new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(2000)
                .build(),
            new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(8)
                .build(),
            new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(5000)
                .build(),
            new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(2963)
                .build(),
            new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(8)
                .build(),
            new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(10)
                .build(),
            new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(800)
                .build(),
            new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(80)
                .build()
        };
        
        long[] listOfInput2 = new long[]
        {
            4,
            5,
            15,
            
            -1,
            -2,
            -3,
            
            4,
            3,
            7,
            
            -3,
            -2,
            -4
        };
        
        String[] exptResults = new String[] {
            "625000000000000","228380099273071043","35184372088832",
            
            "0.0002","0.00000025","0.001953125",
            
            "625000000000000","-26013270347","-2097152",
            
            "-0.001","0.0000015625","0.0000000244140625"
        };
        
        for (int i = 0; i < exptResults.length; i++) {
            //Given
            Money expectedResult = mf.valueOf(exptResults[i]);            
            
            // When
            Money result = listOfInput1[i].power(listOfInput2[i]);
            
            // Then
            assertEquals(result, expectedResult);
        }
    } 

    /**
     * Test of power method, of class Money.
     */
    @Test
        public void testPower_int() {
        MoneyFactory mf = new MoneyFactory();
        // Given
        Money[] listOfInput1 = new Money[]
        {
            new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(5000)
                .build(),
            new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(2963)
                .build(),
            new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(8)
                .build(),
            new Money.Builder()
                    .sign(SignValue.Positive)
                .wholeUnit(5000)
                .build(),
            new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(2000)
                .build(),
            new Money.Builder()
                .sign(SignValue.Positive)
                .wholeUnit(8)
                .build(),
            new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(5000)
                .build(),
            new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(2963)
                .build(),
            new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(8)
                .build(),
            new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(10)
                .build(),
            new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(800)
                .build(),
            new Money.Builder()
                .sign(SignValue.Negative)
                .wholeUnit(80)
                .build(),
        };
        
        int[] listOfInput2 = new int[]
        {
            4,
            5,
            15,
            
            -1,
            -2,
            -3,
            
            4,
            3,
            7,
            
            -3,
            -2,
            -4        
        };
        
        String[] exptResults = new String[] {
            "625000000000000","228380099273071043","35184372088832",
            
            "0.0002","0.00000025","0.001953125",
            
            "625000000000000","-26013270347","-2097152",
            
            "-0.001","0.0000015625","0.0000000244140625"
        };
        
        for (int i = 0; i < exptResults.length; i++) {
            //Given
            Money expectedResult = mf.valueOf(exptResults[i]);            
            
            // When
            Money result = listOfInput1[i].power(listOfInput2[i]);
            
            // Then
            assertEquals(result, expectedResult);
        }
    }
}
