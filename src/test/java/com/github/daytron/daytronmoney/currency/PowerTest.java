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

import com.github.daytron.daytronmoney.exception.BaseNotAWholeNumber;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * Test class for Powers
 * 
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
            "0","1","1","-1","-1","90",
        };
        String[] valueB = new String[] {
            "3","-5","7","3","-4","3"
        };
        String[] exptResults = new String[] {
            "0","1","1","-1","1","729000",
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
    
    /**
     * Test of execute method with null money.
     */
    @Test(expected = NullPointerException.class)
    public void testExecuteNullMoney() {
        // Given
        Money nullMoney = null;
        
        // When
        Money result = nullMoney.power(2);
    }
    
    /**
     * Test of execute method with decimal money.
     */
    @Test(expected = BaseNotAWholeNumber.class)
    public void testExecuteNotWholeNumber() {
        // Given
        Money notWholeMoney = new Money.Builder()
                .wholeUnit(2)
                .decimalUnit(12)
                .build();
        
        // When
        Money result = notWholeMoney.power(2);
    }
    
    /**
     * Test of execute method with zero power.
     */
    @Test
    public void testExecuteZeroPower() {
        // Given
        Money notWholeMoney = new Money.Builder()
                .wholeUnit(2)
                .build();
        Money expMoney = new Money.Builder()
                    .sign(SignValue.Positive)
                    .wholeUnit(1)
                    .build();
        
        // When
        Money result = notWholeMoney.power(0);
        
        // Then
        assertEquals(expMoney, result);
    }
    
    /**
     * Test of execute method with zero money and negative power.
     */
    @Test(expected = ArithmeticException.class)
    public void testExecuteZeroMoneyWithNegativePower() {
        // Given
        Money zeroMoney = new Money.Builder()
                .build();
        
        // When
        Money result = zeroMoney.power(-2);
    }
}
