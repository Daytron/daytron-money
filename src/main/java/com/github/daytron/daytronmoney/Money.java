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
package com.github.daytron.daytronmoney;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Ryan Gilera
 */
public abstract class Money {
    private final String currencySymbol;
    private final long wholeUnit;
    private final long decimalUnit;
    private final Sign signUnit;
    
    public Money(String symbol) {
        symbol = symbol.trim();
        Pattern p = Pattern.compile("\\d");
        Matcher m = p.matcher(symbol);
        
        if (symbol == null) {
            throw new IllegalArgumentException("Error! Symbol argument is null.");
        } else if (symbol.isEmpty()) {
            throw new IllegalArgumentException("Error! Symbol argument is empty.");
        } else if (m.find()) {
            throw new IllegalArgumentException("Error! Symbol argument contains number.");
        }
        
        
        this.currencySymbol = symbol;
        this.wholeUnit = 0;
        this.decimalUnit = 0;
        this.signUnit = Sign.Positive;
    }
    
    
}
