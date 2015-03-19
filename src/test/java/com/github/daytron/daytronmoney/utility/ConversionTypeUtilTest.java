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
import java.math.BigInteger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ryan Gilera
 */
public class ConversionTypeUtilTest {
    
    public ConversionTypeUtilTest() {
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
     * Test of concatWholeAndDecThenConvertToLong method, of class ConversionTypeUtil.
     */
    @Test
    public void testConcatWholeAndDecThenConvertToLong() {
        // Given
        Money moneyA = new Money(10, 2634);
        Money moneyB = new Money(30, 12);
        
        BigInteger[] expectedResult = new BigInteger[] {
            new BigInteger("4"),
            new BigInteger("102634"),
            new BigInteger("301200")
        };
        
        // When 
        BigInteger[] result = ConversionTypeUtil
                .concatWholeAndDecThenConvertToLong(moneyA, moneyB);
        
        // Then
        Assert.assertArrayEquals(expectedResult, result);
    }
    
}