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
 * A <code>MoneyOperation</code> subclass implementing multiplication operation
 * for <code>Money</code> objects.
 * 
 * @author Ryan Gilera
 */
class Multiplication extends MoneyOperation {

    /**
     * Constructor for accepting two <code>Money</code> objects for multiplication.
     * 
     * @param thisMoney A <code>Money</code> object
     * @param thatMoney A <code>Money</code> object
     */
    protected Multiplication(Money thisMoney, Money thatMoney) {
        super(thisMoney, thatMoney);
    }
    
    /**
     * Implements execute method from it's super class. Multiplication operation of 
     * two <code>Money</code> objects.
     * 
     * @return A <code>Money</code> object representing the product. 
     */
    @Override
    public Money execute() {
        if (getThatMoney() == null) {
            throw new NullPointerException("Cannot multiply by Null value.");
        }
        
        if (getThisMoney().isZero() || getThatMoney().isZero()) {
            return new Money(getThisMoney().getCurrencyCode(), 
                    SignValue.Positive, 0, 0, 0);
        }

        Money productMoney;
        long newWholeUnit, newDecimalUnit, newLeadingZeroes;
        SignValue newSign;
        
        SignValue thisSign = getThisMoney().getSign();
        SignValue thatSign = getThatMoney().getSign();
        
        
        final BigInteger[] resultArray = ConversionTypeUtil
                    .concatWholeAndDecThenConvertBigInteger(getThisMoney(), getThatMoney());
        // For multiplication the cutoff decimal is total length of all values' decimal
        // places.
        // Note: resultArray[0] length is normalize for each value thus have the same
        // length, hence cutOffDecimal multiply by 2
        final BigInteger cutOffDecimalPlace = resultArray[0].multiply(new BigInteger("2"));
        final BigInteger concatThisMoney = resultArray[1];
        final BigInteger concatThatMoney = resultArray[2];
        
        // Determine sign first
        if (thisSign == SignValue.Negative && thatSign == SignValue.Positive) {
            newSign = SignValue.Negative;
        } else if (thatSign == SignValue.Negative && thisSign == SignValue.Positive) {
            newSign = SignValue.Negative;
        } else if (thisSign == SignValue.Negative && thatSign == SignValue.Negative){
            newSign = SignValue.Positive;
        } else {
            // Either sign of the two values, since they are equal
            newSign = thisSign;
        }
        
        BigInteger productBigContainer = concatThisMoney.multiply(concatThatMoney);
        
        String productValueStr = productBigContainer.toString();
        
        String newWholeStr ="", newDecimalStr="";
        // if the cutoff decimal is within the range of product
        // it means whole unit is > 0 and can be extracted
        if ((productValueStr.length()-1) >= cutOffDecimalPlace.intValue()) {
            long lastIndexOfWhole = ((productValueStr.length()-1) - 
                cutOffDecimalPlace.intValue());
            
            for (int i = 0; i < productValueStr.length(); i++) {
                char character = productValueStr.charAt(i);

                if (i <= lastIndexOfWhole) {
                    newWholeStr += Character.toString(character);
                } else {
                    newDecimalStr += Character.toString(character);
                }
            }
        // Otherwise whole unit is zero and must calculate the leading zero
        // if there is any leading zeros.
        } else {
            int leadingZeroesToAdd = cutOffDecimalPlace.intValue() - 
                    productValueStr.length();
            
            for (int j = 1; j <= leadingZeroesToAdd; j++) {
                newDecimalStr += "0";
            }
            
            newDecimalStr += productValueStr;
        }
        
        newLeadingZeroes = StringUtil.countLeadingZeros(newDecimalStr);
        
        newDecimalStr = StringUtil.removeAnyLeadingAndTrailingZeroes(newDecimalStr);
        
        // Get the long value
        // If string is empty means zero
        if (newWholeStr.isEmpty()) {
            newWholeUnit = 0;
        } else {
            newWholeUnit = Long.valueOf(newWholeStr);
        }
        
        if (newDecimalStr.isEmpty()) {
            newDecimalUnit = 0;
        } else {
            newDecimalUnit = Long.valueOf(newDecimalStr);
        }

        productMoney = new Money(
                getThisMoney().getCurrencyCode(),newSign, 
                newWholeUnit, newDecimalUnit, newLeadingZeroes);
        
        return productMoney;
    }
    
}
