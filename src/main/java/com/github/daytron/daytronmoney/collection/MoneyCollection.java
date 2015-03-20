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
package com.github.daytron.daytronmoney.collection;

import com.github.daytron.daytronmoney.currency.Money;
import com.github.daytron.daytronmoney.currency.MoneyFactory;

/**
 *
 * @author Ryan Gilera
 */
public interface MoneyCollection {
    
    /**
     * Template method for checking if the collection has a negative <code>Money</code> 
     * object.
     * 
     * @return <code>boolean</code> value
     */
    public boolean hasNegativeValue();

    /**
     * Template method for checking if the collection has a positive <code>Money</code> 
     * object.
     * 
     * @return <code>boolean</code> value
     */
    public boolean hasPositiveValue();
    
    /**
     * Sets a <code>MoneyFactory</code> object.
     * 
     * @param moneyFactory <code>MoneyFactory</code> object
     */
    public void setMoneyFactory(MoneyFactory moneyFactory);

    /**
     * Retrieves a <code>MoneyFactory</code> object.
     * 
     * @return <code>MoneyFactory</code> object
     */
    public MoneyFactory getMoneyFactory();

    /**
     * Template method for checking if the collection has only positive <code>Money</code> 
     * objects.
     * 
     * @return <code>boolean</code> value
     */
    public boolean isAllPositiveValues();

    /**
     * Template method for checking if the collection has only negative <code>Money</code> 
     * objects.
     * 
     * @return <code>boolean</code> value
     */
    public boolean isAllNegativeValues();
    
    /**
     * Returns the sum of all values.
     * 
     * @return <code>Money</code> object
     */
    public Money sum();

    /**
     * Returns the difference of all values.
     * 
     * @return <code>Money</code> object
     */
    public Money difference();

    /**
     * Returns the product of all values.
     * 
     * @return <code>Money</code> object
     */
    public Money product();

    /**
     * Returns the quotient of all values.
     * 
     * @return <code>Money</code> object
     */
    public Money quotient();
    
    /**
     * Calculates the sum for each values stored with the given value.
     * 
     * @param money <code>Money</code> object
     */
    public void addEachWith(Money money);

    /**
     * Calculates the difference for each values stored with the given value.
     * 
     * @param money <code>Money</code> object
     */
    public void subtractEachWith(Money money);

    /**
     * Calculates the product for each values stored with the given value.
     * 
     * @param money <code>Money</code> object
     */
    public void multiplyEachWith(Money money);

    /**
     * Calculates the quotient for each values stored with the given value.
     * 
     * @param money <code>Money</code> object
     */
    public void divideEachWith(Money money);
}
