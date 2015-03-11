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

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

/**
 *
 * @author Ryan Gilera
 */
public final class Money {
    private static final String DEFAULT_CURRENCY_SYMBOL
            = Currency.getInstance(Locale.getDefault()).getSymbol(Locale.getDefault());
    private static final String ZERO_STRING = "0";
    private static final String DECIMAL_POINT = ".";
    
    private final String currencySymbol;
    private final long wholeUnit;
    private final long decimalUnit;
    private final long leadingDecimalZeros;
    private final SignValue sign;
    
    public Money(String symbol, SignValue sign, long wholeUnit, 
            long decimalUnit, long leadingDecimalZeros) {
        
        
        // Does not allow values greater than Long type can handle
        if (Long.toString(wholeUnit).length()
                == Long.toString(Long.MAX_VALUE).length() || 
            (Long.toString(decimalUnit).length() + leadingDecimalZeros)
                >= Long.toString(Long.MAX_VALUE).length()) {
            throw new IllegalArgumentException("Error! Value reached maximum limit");
        }
        
        // Prevent negative zero money
        // For simplicity zero is considered positive
        // but when represented in String format,
        // it is unsign.
        if (wholeUnit == 0 && decimalUnit == 0) {
            this.sign = SignValue.Positive;
        } else {
            this.sign = sign;
        }
        
        
        this.currencySymbol = symbol;
        this.wholeUnit = wholeUnit;
        this.decimalUnit = decimalUnit;
        this.leadingDecimalZeros = leadingDecimalZeros;
    }

    public Money() {
        this(DEFAULT_CURRENCY_SYMBOL, SignValue.Positive, 0, 0, 0);
    }

    public SignValue getSign() {
        return sign;
    }

    public long getWholeUnit() {
        return wholeUnit;
    }

    public long getDecimalUnit() {
        return decimalUnit;
    }

    public long getLeadingDecimalZeros() {
        return leadingDecimalZeros;
    }
    

    public String getCurrencySymbol() {
        return currencySymbol;
    }
    
    
    
    
    @Override
    public String toString() {
        // Apply sign
        String signText;
        if (getWholeUnit() == 0 && getDecimalUnit() == 0) {
            signText = "";
        } else if (getSign() == SignValue.Positive) {
            signText = "";
        } else {
            signText = getSign().getText();
        }

        // Reformat value to proper value format
        // Ex. 1267 becomes 1,267
        String poundsStr = NumberFormat.getInstance().format(getWholeUnit());

        // Regroup back any leading zeros
        String penceStr = "";
        for (int i = 0; i < getLeadingDecimalZeros(); i++) {
            penceStr += ZERO_STRING;
        }
        
        penceStr += Long.toString(getDecimalUnit());
        
        if (getLeadingDecimalZeros() == 0 && getDecimalUnit() < 10) {
            penceStr += ZERO_STRING;
        }
        
        return getCurrencySymbol() + " " + signText + poundsStr 
                + DECIMAL_POINT + penceStr;
    }
    
}
