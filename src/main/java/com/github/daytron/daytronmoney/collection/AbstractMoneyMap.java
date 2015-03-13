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
 *
 * @author Ryan Gilera
 * @param <K>
 * @param <V>
 */
public abstract  class  AbstractMoneyMap<K, V extends Money> 
        extends HashMap<K,Money> implements MoneyCollection{
    private MoneyFactory moneyFactory;

    public AbstractMoneyMap(int initialCapacity) {
        super(initialCapacity);
        this.moneyFactory = new MoneyFactory();
    }

    public AbstractMoneyMap() {
        super();
        this.moneyFactory = new MoneyFactory();
    }

    public AbstractMoneyMap(Map<? extends K, ? extends Money> m) {
        super(m);
        this.moneyFactory = new MoneyFactory();
    }

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

    public abstract MoneyMap<K,V> retrieveAllPositiveValues();
    public abstract MoneyMap<K,V> retrieveAllNegativeValues();
    
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
