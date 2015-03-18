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
package com.github.daytron.daytronmoney.utility;

import com.github.daytron.daytronmoney.currency.Money;
import com.github.daytron.daytronmoney.currency.MoneyFactory;
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
public class StringUtilTest {
    
    public StringUtilTest() {
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
     * Test of parseAndRemoveCurrencyCode method, of class StringUtil.
     */
    @Test
    public void testParseAndRemoveCurrencyCode() {
        // Given
        MoneyFactory moneyFactory = new MoneyFactory("JPY");
        String input = "JPY12.34";
        String[] expectedString = new String[] {
          "JPY", "12.34"  
        };
        
        // When
        String[] result = StringUtil.parseAndRemoveCurrencyCode(input);
        
        // Then
        assertArrayEquals(expectedString, result);
    }

    /**
     * Test of removeCommas method, of class StringUtil.
     */
    @Test
    public void testRemoveCommas() {
        // Given
        String input = "12,456.89";
        String expResult = "12456.89";
        
        // When
        String result = StringUtil.removeCommas(input);
        
        // Then
        assertEquals(expResult, result);
    }

    /**
     * Test of analyzeDecimalValue method, of class StringUtil.
     */
    @Test
    public void testAnalyzeDecimalValue() {
        // Given 
        String input = "0069";
        long[] expResult = new long[] {
          2,69  
        };
        
        // When
        long[] result = StringUtil.analyzeDecimalValue(input);
        
        // Then
        assertArrayEquals(expResult, result);
        
    }

    /**
     * Test of countLeadingZeros method, of class StringUtil.
     */
    @Test
    public void testCountLeadingZeros() {
        // Given
        String input = "000001";
        long expResult = 5;
        
        // When
        long result = StringUtil.countLeadingZeros(input);
        
        // Then
        assertEquals(expResult, result);
    }

    /**
     * Test of removeAnyLeadingZeroes method, of class StringUtil.
     */
    @Test
    public void testRemoveAnyLeadingZeroes() {
        // Given
        String input  = "000523";
        String expResult = "523";
        
        // When
        String output = StringUtil.removeAnyLeadingZeroes(input);
        
        // Then
        assertEquals(expResult, output);
        
    }

    /**
     * Test of removeAnyTrailingZeroes method, of class StringUtil.
     */
    @Test
    public void testRemoveAnyTrailingZeroes() {
        // Given
        String input  = "000523000";
        String expResult = "000523";
        
        // When
        String output = StringUtil.removeAnyTrailingZeroes(input);
        
        // Then
        assertEquals(expResult, output);
    }

    /**
     * Test of removeAnyLeadingAndTrailingZeroes method, of class StringUtil.
     */
    @Test
    public void testRemoveAnyLeadingAndTrailingZeroes() {
        // Given
        String input  = "000523000";
        String expResult = "523";
        
        // When
        String output = StringUtil.removeAnyLeadingAndTrailingZeroes(input);
        
        // Then
        assertEquals(expResult, output);
    }

    /**
     * Test of combineValueIntoString method, of class StringUtil.
     */
    @Test
    public void testCombineValueIntoString() {
        // Given
        long whole = 5, dec = 8, numLeadingZero =3;
        String expValue = "50008";
        
        // When
        String result = StringUtil.combineValueIntoString(whole, dec, numLeadingZero);
        
        //Then
        assertEquals(expValue,result);
    }
    
}
