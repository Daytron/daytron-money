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
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * Test class for Powers
 * @author Shaun Plummer
 */
public class PowerTest {
    
    public PowerTest() {
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
     * Test of execute method, of class Power.
     */
    @Test
    public void testExecute() {
        MoneyFactory mf = new MoneyFactory();
        
        String[] valueA = new String[] {
          "693.7","0","-1.058","-12.6","0.5"
        };
        String[] valueB = new String[] {
          "2","1","2","10","3"
        };
        String[] exptResults = new String[] {
          "481219.69","0","1.119364","100856861888.6953829376","0.125"
        };
        
        for (int i = 0; i< valueA.length; i++) {
            // Given
            Money aMoney = mf.valueOf(valueA[i]);
            Money bMoney = mf.valueOf(valueB[i]);
            
            MoneyOperation powerOperation = new Power(aMoney, bMoney);
            Money expectedResult = mf.valueOf(exptResults[i]);
            
            // When 
            Money productMoney = powerOperation.execute();
                        
            // Then
            assertEquals(expectedResult, productMoney);
        }
    }
}
