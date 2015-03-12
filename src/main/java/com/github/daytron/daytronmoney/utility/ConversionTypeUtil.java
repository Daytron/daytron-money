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

/**
 *
 * @author Ryan Gilera
 */
public class ConversionTypeUtil {
    private ConversionTypeUtil(){
    }
    
    public static long [] concatWholeAndDecThenConvertToLong(Money thisMoney, 
            Money thatMoney) {
        long[] resultArray = new long[3];
        
        long thisWholeUnit = thisMoney.getWholeUnit();
        long thisDecimalUnit = thisMoney.getDecimalUnit();
        long thisLeadingZeroes = thisMoney.getLeadingDecimalZeros();
        long thatWholeUnit = thatMoney.getWholeUnit();
        long thatDecimalUnit = thatMoney.getDecimalUnit();
        long thatLeadingZeroes = thatMoney.getLeadingDecimalZeros();
        
        System.out.println("this decimal unit: " + thisDecimalUnit);
            System.out.println("this leading unit: " + thisLeadingZeroes);
        
        int lengthThisDecimal = (Long.toString(thisDecimalUnit)).length();
        lengthThisDecimal += thisLeadingZeroes;

        int lengthThatDecimal = (Long.toString(thatDecimalUnit)).length();
        lengthThatDecimal += thatLeadingZeroes;

        System.out.println("lengthThis: " + lengthThisDecimal);
            System.out.println("");
            
            System.out.println("that decimal unit: " + thatDecimalUnit);
            System.out.println("that leading unit: " + thatLeadingZeroes);
             System.out.println("lengthThat: " + lengthThatDecimal);
            System.out.println("");

        String thisStrValue = StringUtil.combineValueIntoString(thisWholeUnit, 
                thisDecimalUnit, thisLeadingZeroes);
        String thatStrValue = StringUtil.combineValueIntoString(thatWholeUnit, 
                thatDecimalUnit, thatLeadingZeroes);

        int cutOffDecimalPlace;
        if (lengthThatDecimal > lengthThisDecimal) {
            cutOffDecimalPlace = lengthThatDecimal;

            int diff = lengthThatDecimal - lengthThisDecimal;
            for (int i = 0; i < diff; i++) {
                thisStrValue += "0";
            }

        } else if (lengthThatDecimal < lengthThisDecimal) {
            cutOffDecimalPlace = lengthThisDecimal;

            int diff = lengthThisDecimal - lengthThatDecimal;
            for (int i = 0; i < diff; i++) {
                thatStrValue += "0";
            }
        } else {
            // any of the units will do
            cutOffDecimalPlace = lengthThatDecimal;
        }
        
        resultArray[0] = cutOffDecimalPlace;
        
        System.out.println("thisStrValeu: " + thisStrValue);
            System.out.println("thatStrValeu: " + thatStrValue);
            System.out.println("");

        // Get The long value
        resultArray[1] = Long.valueOf(thisStrValue);
        resultArray[2] = Long.valueOf(thatStrValue);
        
        return resultArray;
    }
}
