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
import java.util.ArrayList;
import java.util.Collection;

/**
 * An abstract class for all structures that extends <code>ArrayList</code> class.
 * 
 * @author Ryan Gilera
 */
public abstract class AbstractMoneyList
        extends ArrayList<Money> implements MoneyCollection{
    private MoneyFactory moneyFactory;

    /**
     * An abstract constructor that takes no arguments.
     */
    public AbstractMoneyList() {
        super();
        this.moneyFactory = new MoneyFactory();
    }

    /**
     * An abstract constructor that takes an <code>integer</code>
     * value as its initial size.
     * 
     * @param initialCapacity <code>integer</code> value
     */
    public AbstractMoneyList(int initialCapacity) {
        super(initialCapacity);
        this.moneyFactory = new MoneyFactory();
    }
    
    /**
     * An abstract constructor that takes an <code>Collection</code>
     * object as its argument.
     * 
     * @param c <code>Collection</code> object
     */
    public AbstractMoneyList(Collection<? extends Money> c) {
        super(c);
        this.moneyFactory = new MoneyFactory();
    }

    /**
     * Retrieves all positive values.
     * 
     * @return <code>MoneyArrayList</code> object with all positive values.
     */
    public abstract MoneyArrayList retrieveAllPositiveValues();

    /**
     * Retrieves all negative values.
     * 
     * @return <code>MoneyArrayList</code> object with all negative values.
     */
    public abstract MoneyArrayList retrieveAllNegativeValues();
    
    /**
     * Overloads add method to accept <code>String</code> values.
     * 
     * @param amount <code>String</code> object
     */
    public void add(String amount) {
        Money money = moneyFactory.valueOf(amount);
        add(money);
    }
    
    @Override
    public void setMoneyFactory(MoneyFactory moneyFactory) {
        this.moneyFactory = moneyFactory;
    }

    @Override
    public MoneyFactory getMoneyFactory() {
        return moneyFactory;
    }
    
    @Override
    public boolean isAllPositiveValues() {
        return !hasNegativeValue();
    }
    
    @Override
    public boolean isAllNegativeValues() {
        return !hasPositiveValue();
    }
    
}
