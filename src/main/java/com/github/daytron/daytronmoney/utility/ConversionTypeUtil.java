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

/**
 * Utility class for type conversions.
 * 
 * @author Ryan Gilera
 */
public class ConversionTypeUtil {
    
    private ConversionTypeUtil(){
    }
    
    /**
     * Combines each money units to <code>String</code> and convert it to 
     * <code>BigInteger</code>.
     * Each of <code>String</code> text is saved into a <code>BigInteger</code> array.
     * Analyse appropriate decimal places and save into <code>BigInteger</code> array.
     * 
     * @param thisMoney A <code>Money</code> object
     * @param thatMoney A <code>Money</code> object
     * @return <code>BigInteger</code> array
     */
    public static BigInteger [] concatWholeAndDecThenConvertBigInteger(
            Money thisMoney, Money thatMoney) {
        BigInteger[] resultArray = new BigInteger[3];
        
        long thisWholeUnit = thisMoney.getWholeUnit();
        long thisDecimalUnit = thisMoney.getDecimalUnit();
        long thisLeadingZeroes = thisMoney.getLeadingDecimalZeros();
        long thatWholeUnit = thatMoney.getWholeUnit();
        long thatDecimalUnit = thatMoney.getDecimalUnit();
        long thatLeadingZeroes = thatMoney.getLeadingDecimalZeros();
        
        // Compute digits length
        int lengthThisDecimal = (Long.toString(thisDecimalUnit)).length();
        lengthThisDecimal += thisLeadingZeroes;
        int lengthThatDecimal = (Long.toString(thatDecimalUnit)).length();
        lengthThatDecimal += thatLeadingZeroes;

        // Convert it to String
        String thisStrValue = StringUtil.combineValueIntoString(thisWholeUnit, 
                thisDecimalUnit, thisLeadingZeroes);
        String thatStrValue = StringUtil.combineValueIntoString(thatWholeUnit, 
                thatDecimalUnit, thatLeadingZeroes);

        // Normalize to match each other's digits length
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
        
        // Convert and returns as a BigInteger array
        resultArray[0] = BigInteger.valueOf(cutOffDecimalPlace);
        resultArray[1] = new BigInteger(thisStrValue);
        resultArray[2] = new BigInteger(thatStrValue);
        
        return resultArray;
    }
}
