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
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 *
 * @author Ryan Gilera
 */
class Division extends MoneyOperation {
    
    protected Division(Money thisMoney, Money thatMoney) {
        super(thisMoney, thatMoney);
    }

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

        Money quotientMoney;
        long newWholeUnit, newDecimalUnit, newLeadingZeroes;
        SignValue newSign;
        
        SignValue thisSign = getThisMoney().getSign();
        SignValue thatSign = getThatMoney().getSign();
        
        
        final BigInteger[] resultArray = ConversionTypeUtil
                    .concatWholeAndDecThenConvertToLong(getThisMoney(), getThatMoney());
        // Cutoff decimal is not needed here
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
        
        BigInteger[] resultContainer = concatThisMoney.divideAndRemainder(concatThatMoney);
        String newWholeStr, newDecimalStr;
        newWholeStr = resultContainer[0].toString();
        
        // Get decimal part
        long dividend = resultContainer[1].longValue();
        long divisor = concatThatMoney.longValue();
        double newDecimal = dividend / (divisor * 1.0);
        
        newDecimalStr = String.format("%.19f", newDecimal);
        newDecimalStr = newDecimalStr.substring(newDecimalStr.indexOf(".") + 1, 
                newDecimalStr.length()-1);
        
        newLeadingZeroes = StringUtil.countLeadingZeros(newDecimalStr);
        
        newDecimalStr = StringUtil.removeAnyLeadingZeroes(newDecimalStr);
        newDecimalStr = StringUtil.removeAnyTrailingZeroes(newDecimalStr);
        
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

        quotientMoney = new Money(
                getThisMoney().getCurrencyCode(),newSign, 
                newWholeUnit, newDecimalUnit, newLeadingZeroes);
        
        return quotientMoney;
    }
    
}
