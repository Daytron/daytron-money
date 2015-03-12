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
package com.github.daytron.daytronmoney.operation;

import com.github.daytron.daytronmoney.currency.Money;
import com.github.daytron.daytronmoney.currency.SignValue;
import com.github.daytron.daytronmoney.utility.ConversionTypeUtil;
import com.github.daytron.daytronmoney.utility.StringUtil;

/**
 *
 * @author Ryan Gilera
 */
public class Addition extends MoneyOperation {

    public Addition(Money thisMoney, Money thatMoney) {
        super(thisMoney, thatMoney);
    }

    @Override
    public Money execute() {
        Money sumMoney;
        
        if (getThatMoney() == null) {
            return null;
        }

        long newWholeUnit;
        long newDecimalUnit;
        long newLeadingZeroes;
        SignValue newSign;
        
        SignValue thisSign = getThisMoney().getSign();
        SignValue thatSign = getThatMoney().getSign();
        
        
        final long[] resultArray = ConversionTypeUtil
                    .concatWholeAndDecThenConvertToLong(getThisMoney(), getThatMoney());
        final long cutOffDecimalPlace = resultArray[0];
        final long concatThisMoney = resultArray[1];
        final long concatThatMoney = resultArray[2];

        long sumOfConcats;
        
        if ((thisSign == SignValue.Positive && thatSign == SignValue.Positive)
                || (thisSign == SignValue.Negative && thatSign == SignValue.Negative)) {

            
            sumOfConcats = concatThisMoney + concatThatMoney;
            
            // Determine sign
            if (thisSign == SignValue.Positive) {
                newSign = SignValue.Positive;
            } else {
                newSign = SignValue.Negative;
            }

        } else {

            long greaterConcatMoney, lesserConcatMoney;
            // Determine sign
            // Determine the larger amount and assign them respectively.
            if (concatThisMoney < concatThatMoney) {
                // Choose the sign of the greater money
                newSign = thatSign;

                greaterConcatMoney = concatThatMoney;
                lesserConcatMoney = concatThisMoney;
            } else if (concatThisMoney > concatThatMoney) {
                // Choose the sign of the greater money
                newSign = thisSign;
                
                greaterConcatMoney = concatThisMoney;
                lesserConcatMoney = concatThatMoney;
            } else {
                // Otherwise they are both equal and will result to zero.
                // For simplicity sake, zero is assume positive
                // Note: In the toString method, zero value money is filtered to 
                // show no sign at all
                newSign = SignValue.Positive;
                // Doesn't matter which one is which since they are equal
                greaterConcatMoney = concatThisMoney;
                lesserConcatMoney = concatThatMoney;
            }

            System.out.println("");
            System.out.println("GreaterVal: " + greaterConcatMoney);
            System.out.println("LesserVal: " + lesserConcatMoney);
            
            sumOfConcats = greaterConcatMoney - lesserConcatMoney;

        }
        
        System.out.println("sum concats: " + sumOfConcats);
        // Return to String and separate whole from decimal values
        String sumValueStr = Long.toString(sumOfConcats);
        long lastIndexOfWhole = ((sumValueStr.length()-1) - cutOffDecimalPlace);
        System.out.println("");
        System.out.println("sumValueStr.length()-1: " + (sumValueStr.length()-1));
        System.out.println("cutOffDecimalPlace: " + cutOffDecimalPlace);
        System.out.println("lastIndexOfWhole: " + lastIndexOfWhole);
        String newWholeStr ="", newDecimalStr="";
        for (int i = 0; i < sumValueStr.length(); i++) {
            char character = sumValueStr.charAt(i);

            if (i <= lastIndexOfWhole) {
                newWholeStr += Character.toString(character);
            } else {
                newDecimalStr += Character.toString(character);
            }
        }
        
        System.out.println("");
        System.out.println("Whole: " + newWholeStr);

        // Then determine the number of leading zeroes for newDecimalStr
        newLeadingZeroes = StringUtil.countLeadingZeros(newDecimalStr);
        System.out.println("lead zeros; " + newLeadingZeroes);
        // Remove any leading zeroes
        newDecimalStr = StringUtil.removeAnyLeadingZeroes(newDecimalStr);
        System.out.println("new decimal str: " + newDecimalStr);

        // Get the long value
        newWholeUnit = Long.valueOf(newWholeStr);
        newDecimalUnit = Long.valueOf(newDecimalStr);


        sumMoney = new Money(
                getThisMoney().getCurrencyCode(),newSign, 
                newWholeUnit, newDecimalUnit, newLeadingZeroes);
        
        return sumMoney;
    }


    
    
}
