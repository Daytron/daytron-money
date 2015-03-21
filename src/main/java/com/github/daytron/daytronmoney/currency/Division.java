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
 * A <code>MoneyOperation</code> subclass implementing division operation
 * for <code>Money</code> objects.
 * 
 * @author Ryan Gilera
 */
class Division extends MoneyOperation {
    
    /**
     * Constructor for accepting two <code>Money</code> objects for division.
     * 
     * @param thisMoney A <code>Money</code> object
     * @param thatMoney A <code>Money</code> object
     */
    protected Division(Money thisMoney, Money thatMoney) {
        super(thisMoney, thatMoney);
    }

    /**
     * Implements execute method from it's super class. Division operation of 
     * two <code>Money</code> objects.
     * 
     * @return A <code>Money</code> object representing the quotient. 
     */
    @Override
    public Money execute() {
        if (getThatMoney() == null) {
            throw new NullPointerException("Cannot divide by Null value.");
        }
        
        if (getThatMoney().isZero()) {
            throw new ArithmeticException("Cannot divide by Zero.");
        }
        
        if (getThisMoney().isZero()) {
            return new Money(getThisMoney().getCurrencyCode(), 
                    SignValue.Positive, 0, 0, 0);
        }

        long newWholeUnit, newDecimalUnit, newLeadingZeroes;
        SignValue newSign;
        
        SignValue thisSign = getThisMoney().getSign();
        SignValue thatSign = getThatMoney().getSign();
        
        
        final BigInteger[] resultArray = ConversionTypeUtil
                    .concatWholeAndDecThenConvertBigInteger(getThisMoney(), getThatMoney());
        // Cutoff decimal is not needed here
        final BigInteger concatThisBigInt = resultArray[1];
        final BigInteger concatThatBigInt = resultArray[2];
        
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
        
        BigInteger[] resultBigInt = concatThisBigInt.divideAndRemainder(concatThatBigInt);
        String newWholeStr, newDecimalStr;
        newWholeStr = resultBigInt[0].toString();
        
        // Get decimal part
        long dividend = resultBigInt[1].longValue();
        long divisor = concatThatBigInt.longValue();
        double newDecimal = dividend / (divisor * 1.0);
        
        newDecimalStr = String.format("%.19f", newDecimal);
        newDecimalStr = newDecimalStr.substring(newDecimalStr.indexOf(".") + 1, 
                newDecimalStr.length()-1);
        
        newLeadingZeroes = StringUtil.countLeadingZeros(newDecimalStr);
        newDecimalStr = StringUtil.removeAnyLeadingAndTrailingZeroes(newDecimalStr);
        
        // Get the long value
        // If string is empty, it means zero
        newWholeUnit = ((newWholeStr.isEmpty())?0:Long.valueOf(newWholeStr));
        newDecimalUnit = ((newDecimalStr.isEmpty())?0:Long.valueOf(newDecimalStr));
        
        return new Money(
                getThisMoney().getCurrencyCode(),newSign, 
                newWholeUnit, newDecimalUnit, newLeadingZeroes);
    }
    
}
