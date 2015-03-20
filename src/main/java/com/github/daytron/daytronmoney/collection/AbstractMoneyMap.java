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
import java.util.HashMap;
import java.util.Map;

/**
 * An abstract class for all structures that extends <code>HashMap</code> class.
 * 
 * @author Ryan Gilera
 * @param <K> A generic data type that extends Object class
 * @param <V> A generic data type that extends <code>Money</code> class
 */
public abstract class  AbstractMoneyMap<K, V extends Money> 
        extends HashMap<K,Money> implements MoneyCollection{
    private MoneyFactory moneyFactory;

    /**
     * Abstract constructor that takes an <code>integer</code>
     * as its initial size.
     * 
     * @param initialCapacity <code>integer</code> value
     */
    public AbstractMoneyMap(int initialCapacity) {
        super(initialCapacity);
        this.moneyFactory = new MoneyFactory();
    }

    /**
     * Abstract constructor with no arguments.
     */
    public AbstractMoneyMap() {
        super();
        this.moneyFactory = new MoneyFactory();
    }

    /**
     * Abstract constructor that takes an <code>integer</code>
     * value for initial capacity and <code>float</code> for load factor 
     * as its argument.
     * 
     * @param m <code>Map</code> object
     */
    public AbstractMoneyMap(Map<? extends K, ? extends Money> m) {
        super(m);
        this.moneyFactory = new MoneyFactory();
    }

    /**
     * Abstract constructor that takes an <code>integer</code>
     * value for initial capacity and <code>float</code> for load factor 
     * as its argument.
     * 
     * @param initialCapacity <code>integer</code> value
     * @param loadFactor <code>float</code> value
     */
    public AbstractMoneyMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
        this.moneyFactory = new MoneyFactory();
    }
    

    @Override
    public void setMoneyFactory(MoneyFactory moneyFactory) {
        this.moneyFactory = moneyFactory;
    }

    @Override
    public MoneyFactory getMoneyFactory() {
        return moneyFactory;
    }

    /**
     * Retrieves all positive values.
     * 
     * @return <code>MoneyHashMap</code> object with all positive values.
     */
    public abstract MoneyHashMap<K,V> retrieveAllPositiveValues();

    /**
     * Retrieves all negative values.
     * 
     * @return <code>MoneyHashMap</code> object with all negative values.
     */
    public abstract MoneyHashMap<K,V> retrieveAllNegativeValues();
    
    /**
     * Overloads put method to accept <code>String</code> String values.
     * 
     * @param key A generic type object
     * @param amount A <code>String</code> object
     */
    public void put(K key, String amount) {
        Money money = moneyFactory.valueOf(amount);
        put(key, money);
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
