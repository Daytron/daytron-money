/*
 * The MIT License
 *
 * Copyright 2015 Ryan Gilera, Shaun Plummer.
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

/**
 * A <code>MoneyOperation</code> subclass implementing power operation
 * for <code>Money</code> objects.
 * 
 * @author Shaun Plummer
 */
class Power extends MoneyOperation {
    
    protected Power(Money thisMoney, Money thatMoney) {
        super(thisMoney, thatMoney);
    }
    
    /**
     * Implements execute method from it's super class. Performing the power operation of 
     * two <code>Money</code> objects using the second as the exponent.
     * 
     * @return A <code>Money</code> object representing the product. 
     */
    @Override
    public Money execute() {
        Money thatMoney = getThatMoney();
        Money thisMoney = getThisMoney();
        
        if (thatMoney == null) {
            throw new NullPointerException("Exponent cannot be null");
        }
        
        //Check base is a whole number
        if (!thisMoney.isWholeNumber()) {
            throw new BaseNotAWholeNumber("Power operation can only be applied to whole numbers.");
        }
        
        
        //Check exponent is a whole number
        if(!thatMoney.isWholeNumber()) {
            throw new IllegalArgumentException("Exponent must be a whole number");
        }
        //Ensure any number to the power of zero is 1
        if(thatMoney.isZero()) {
            return new Money.Builder()
                    .sign(SignValue.Positive)
                    .wholeUnit(1)
                    .build();
        }
        //Calculation not possible
        if(thisMoney.isZero() && thatMoney.isLessThanZero()) {
            throw new ArithmeticException("Zero to a negative exponent is an undefined operation");
        }
        
        long newWholeUnit = thatMoney.getWholeUnit();
        Money result = new Money.Builder()
                .sign(thisMoney.getSign())
                .wholeUnit(thisMoney.getWholeUnit())
                .leadingDecimalZeroes(thisMoney.getLeadingDecimalZeros())
                .build();
        
        //Multiply by the exponent
        for(int i = 1; i < newWholeUnit; i++) {
            result = result.multiply(thisMoney);
        }

        //if the exponent was a negative number take the reciprocal
        if( thatMoney.getSign() == SignValue.Negative) {
            result = new Money.Builder()
                    .sign(SignValue.Positive)
                    .wholeUnit(1)
                    .build()
                    .divide(result);
        }

        return result;
    }
}
