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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for Subtraction
 * @author Ryan Gilera
 */
public class SubtractionTest {
    
    public SubtractionTest() {
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
     * Test of execute method, of class Subtraction.
     */
    @Test
    public void testExecute() {
        long[] valueA = new long[] {
          12,2698,-96,0,485697854  
        };
        long[] valueB = new long[] {
          78596,-78,0,78569844,7856983  
        };
        long[] exptResults = new long[] {
          -78584,2776,-96,-78569844,477840871  
        };
        
        for (int i = 0; i< valueA.length; i++) {
            // Given
            Money aMoney = new Money(valueA[i]);
            Money bMoney = new Money(valueB[i]);
            
            MoneyOperation additionOperation = new Subtraction(aMoney, bMoney);
            Money expectedResult = new Money(exptResults[i]);
            
            // When 
            Money sumMoney = additionOperation.execute();
            
            // Then
            assertEquals(expectedResult, sumMoney);
        }
    }
    
}
