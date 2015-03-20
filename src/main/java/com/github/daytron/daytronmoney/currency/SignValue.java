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

/**
 * Constant values that represents <code>Money</code> object's sign.
 * 
 * @author Ryan Gilera
 */
public enum SignValue {

    /**
     * Positive value 
     */
    Positive("+"),
    
    /**
     * Negative value
     */
    Negative("-");

    private final String text;

    private SignValue(String sign) {
        this.text = sign;
    }

    /**
     * Returns String representation from it's argument.
     * 
     * @return A <code>String</code> object 
     */
    public String getText() {
        return text;
    }

    /**
     * Reverse sign value.
     * 
     * @return Reversed <code>SignValue</code> 
     */
    public SignValue oppositeOf() {
        if (this == SignValue.Positive) {
            return SignValue.Negative;
        } else {
            return SignValue.Positive;
        }
    }
}
