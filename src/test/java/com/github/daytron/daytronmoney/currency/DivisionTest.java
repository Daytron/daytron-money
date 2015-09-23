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
 * Test class for Division
 * 
 * @author Ryan Gilera
 */
public class DivisionTest {
    
    public DivisionTest() {
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
     * Test of execute method, of class Division.
     */
    @Test
    public void testExecute() {
        MoneyFactory mf = new MoneyFactory();
        
        String[] valueA = new String[] {
          "6935.7","0","-1.058","-12.006","0.002"  
        };
        String[] valueB = new String[] {
          "1.005","256","-12.5","96","0.003"  
        };
        String[] exptResults = new String[] {
          "6901.19402985074626866","0","0.08464","-0.1250625","0.6666666666666666"  
        };
        
        for (int i = 0; i< valueA.length; i++) {
            // Given
            Money aMoney = mf.valueOf(valueA[i]);
            Money bMoney = mf.valueOf(valueB[i]);
            
            MoneyOperation divideOperation = new Division(aMoney, bMoney);
            Money expectedResult = mf.valueOf(exptResults[i]);
            
            // When 
            Money quotientMoney = divideOperation.execute();
            
            // Then
            assertEquals(expectedResult, quotientMoney);
        }
    }
    
}
