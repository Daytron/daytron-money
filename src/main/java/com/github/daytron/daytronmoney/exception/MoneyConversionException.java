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
package com.github.daytron.daytronmoney.exception;

/**
 * Exception class used when the base used in a power operation is not a whole number
 * 
 * @author Shaun Plummer
 */
public class MoneyConversionException extends Exception {
    
    /**
     * Default Exception constructor.
     */
    public MoneyConversionException() {
        super();
    }
    
    
    /**
     * Exception constructor with message as its parameter.
     * 
     * @param message A <code>String</code> message
     */
    public MoneyConversionException(String message) {
        super(message);
    }
    
    
    /** 
     * AException constructor that customises the Throwable object with a custom  
     * message. 
     *  
     * @param msg the custom message  
     * @param t Throwable object 
     */ 
    public MoneyConversionException(String msg, Throwable t) {
        super(msg, t);
    }
}
