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

import com.github.daytron.daytronmoney.utility.ConversionTypeUtil;
import com.github.daytron.daytronmoney.utility.StringUtil;
import java.math.BigInteger;

/**
 * A <code>MoneyOperation</code> subclass implementing addition operation
 * for <code>Money</code> objects.
 * 
 * @author Ryan Gilera
 */
class Addition extends MoneyOperation {

    /**
     * Constructor for accepting two <code>Money</code> objects for addition.
     * 
     * @param thisMoney A <code>Money</code> object
     * @param thatMoney A <code>Money</code> object
     */
    protected Addition(Money thisMoney, Money thatMoney) {
        super(thisMoney, thatMoney);
    }

    /**
     * Implements execute method from it's super class. Addition operation of 
     * two <code>Money</code> objects.
     * 
     * @return A <code>Money</code> object representing the sum. 
     */
    @Override
    public Money execute() {
        if (getThatMoney() == null) {
            throw new NullPointerException("Cannot add Null value.");
        }

        long newWholeUnit, newDecimalUnit, newLeadingZeroes;
        SignValue newSign;
        
        SignValue thisSign = getThisMoney().getSign();
        SignValue thatSign = getThatMoney().getSign();
        
        final BigInteger[] resultArray = ConversionTypeUtil
                    .concatWholeAndDecThenConvertBigInteger(getThisMoney(), getThatMoney());
        final BigInteger cutOffDecimalPlace = resultArray[0];
        final BigInteger concatThisBigInt = resultArray[1];
        final BigInteger concatThatBigInt = resultArray[2];

        BigInteger sumBigInt;
        
        if ((thisSign == SignValue.Positive && thatSign == SignValue.Positive)
           || (thisSign == SignValue.Negative && thatSign == SignValue.Negative)) {

            sumBigInt = concatThisBigInt.add(concatThatBigInt);
            
            // Determine sign
            newSign = ((thisSign == SignValue.Positive)?SignValue.Positive:SignValue.Negative);
        } else {
            BigInteger greaterBigInt, lesserBigInt;
            // Determine sign
            // Determine the larger amount and assign them respectively.
            if (concatThisBigInt.compareTo(concatThatBigInt) < 0) {
                // Choose the sign of the greater money
                newSign = thatSign;

                greaterBigInt = concatThatBigInt;
                lesserBigInt = concatThisBigInt;
            } else if (concatThisBigInt.compareTo(concatThatBigInt) > 0) {
                // Choose the sign of the greater money
                newSign = thisSign;
                
                greaterBigInt = concatThisBigInt;
                lesserBigInt = concatThatBigInt;
            } else {
                // Otherwise they are both equal and will result to zero.
                // For simplicity sake, zero is assume positive
                // Note: In the toString method, zero value money is filtered to 
                // show no sign at all
                newSign = SignValue.Positive;
                // Doesn't matter which one is which since they are equal
                greaterBigInt = concatThisBigInt;
                lesserBigInt = concatThatBigInt;
            }

            sumBigInt = greaterBigInt.subtract(lesserBigInt);
        }
        
        // Return to String and separate whole from decimal values
        final String sumValueStr = sumBigInt.toString();
        // Determine the index of the last character for whole unit
        // to determine the position of decimal point.
        final long lastIndexOfWhole = ((sumValueStr.length()-1) - 
                cutOffDecimalPlace.intValue());

        String newWholeStr ="", newDecimalStr="";
        for (int i = 0; i < sumValueStr.length(); i++) {
            char character = sumValueStr.charAt(i);

            if (i <= lastIndexOfWhole) {
                newWholeStr += Character.toString(character);
            } else {
                newDecimalStr += Character.toString(character);
            }
        }

        // Then determine the number of leading zeroes for newDecimalStr
        newLeadingZeroes = StringUtil.countLeadingZeros(newDecimalStr);
        
        // Remove any leading zeroes
        newDecimalStr = StringUtil.removeAnyLeadingZeroes(newDecimalStr);

        // Get the long value
        // If string is empty, it means zero
        newWholeUnit = ((newWholeStr.isEmpty()) ? 0:Long.valueOf(newWholeStr));
        newDecimalUnit = ((newDecimalStr.isEmpty()) ? 0:Long.valueOf(newDecimalStr));
        
        
        return new Money.Builder()
                .currencyCode(getThisMoney().getCurrencyCode())
                .sign(newSign)
                .wholeUnit(newWholeUnit)
                .decimalUnit(newDecimalUnit)
                .leadingDecimalZeroes(newLeadingZeroes)
                .build();
    }


    
    
}
