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
 * A abstract class as the base for all money operations.
 * 
 * @author Ryan Gilera
 */
abstract class MoneyOperation {
    private final Money thisMoney;
    private final Money thatMoney;
    
    /**
     * The constructor accepts two <code>Money</code> objects for money 
     * operation.
     * 
     * @param thisMoney A <code>Money</code> object
     * @param thatMoney A <code>Money</code> object
     */
    protected MoneyOperation(Money thisMoney, Money thatMoney) {
        this.thisMoney = thisMoney;
        this.thatMoney = thatMoney;
    }

    /**
     * Returns the first <code>Money</code> object.
     * 
     * @return A <code>Money</code> object
     */
    public Money getThisMoney() {
        return thisMoney;
    }

    /**
     * Returns the second  <code>Money</code> object.
     * 
     * @return A <code>Money</code> object
     */
    public Money getThatMoney() {
        return thatMoney;
    }
    
    /**
     * An abstract method for the operation execution.
     * 
     * @return A <code>Money</code> object
     */
    public abstract Money execute();
    
}
