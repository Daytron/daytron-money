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
import java.util.Collection;
import java.util.Iterator;

/**
 * Custom data structure for handling Monies that extends the capabilities of 
 * a <code>ArrayList</code> class. Implements custom methods from 
 * <code>MoneyCollection</code> interface and <code>AbstractMoneyList</code> abstract 
 * class.
 * 
 * @author Ryan Gilera
 */
public class MoneyArrayList extends AbstractMoneyList {

    /**
     * Creates <code>MoneyArrayList</code> object with no arguments
     */
    public MoneyArrayList() {
        super();
    }

    /**
     * Creates <code>MoneyArrayList</code> object that takes an <code>Collection</code>
     * object as its argument.
     * 
     * @param c <code>Collection</code> object
     */
    public MoneyArrayList(Collection<? extends Money> c) {
        super(c);
    }

    /**
     * Creates <code>MoneyArrayList</code> object that takes an <code>integer</code>
     * value as its initial size.
     * 
     * @param initialCapacity <code>integer</code> value
     */
    public MoneyArrayList(int initialCapacity) {
        super(initialCapacity);
    }
    
    /**
     * Checks of this object has negative <code>Money</code> objects.
     * 
     * @return <code>boolean</code> value
     */
    @Override
    public boolean hasNegativeValue() {
        boolean hasNegative = false;
        for (Money money : this) {
            if (money.isNegative()) {
                hasNegative = true;
            }
        }
        
        return hasNegative;
    }
    
     /**
     * Checks of this object has positive <code>Money</code> objects.
     * 
     * @return <code>boolean</code> value
     */
    @Override
    public boolean hasPositiveValue() {
        boolean hasPositive = false;
        for (Money money : this) {
            if (money.isPositive()) {
                hasPositive = true;
            }
        }
        
        return hasPositive;
    }
    
    /**
     * Retrieves all positive values.
     * 
     * @return <code>MoneyArrayList</code> object with all positive values.
     */
    @Override
    public MoneyArrayList retrieveAllPositiveValues() {
        MoneyArrayList listOfPositives = new MoneyArrayList();
        
        this.stream().filter((money) -> {
            return money.isPositive();
        }).forEach((money) -> {
            listOfPositives.add(money);
        });
        
        return listOfPositives;
    }
    
    /**
     * Retrieves all negative values.
     * 
     * @return <code>MoneyArrayList</code> object with all negative values.
     */
    @Override
    public MoneyArrayList retrieveAllNegativeValues() {
        MoneyArrayList listOfNegatives = new MoneyArrayList();
        
        this.stream().filter((money) -> 
                (money.isNegative())).forEach((money) -> {
            listOfNegatives.add(money);
        });
        
        return listOfNegatives;
    }
    
    /**
     * Removes any zero values.
     */
    public void removeAnyZeroValues() {
        for (Iterator<Money> iterator = this.iterator(); iterator.hasNext();) {
            Money nextMoney = iterator.next();
            if (nextMoney.isZero()) {
                iterator.remove();
            } 
        } 
    }
    
    /**
     * Removes any negative values.
     */
    public void removeAnyNegativeValues() {
        for (Iterator<Money> iterator = this.iterator(); iterator.hasNext();) {
            Money nextMoney = iterator.next();
            if (nextMoney.isNegative()) {
                iterator.remove();
            } 
        } 
    }
    
    /**
     * Removes any positive values.
     */
    public void removeAnyPositiveValues() {
        for (Iterator<Money> iterator = this.iterator(); iterator.hasNext();) {
            Money nextMoney = iterator.next();
            if (nextMoney.isPositive()) {
                iterator.remove();
            } 
        } 
    }

    /**
     * Calculates the sum of all values stored.
     * 
     * @return <code>Money</code> as sum
     */
    @Override
    public Money sum() {
        Money sumMoney = new Money.Builder().build();
        for (Money money : this) {
            sumMoney = sumMoney.add(money);
        }
        
        return sumMoney;
    }

    /**
     * Calculates the difference of all values stored.
     * 
     * @return <code>Money</code> as difference
     */
    @Override
    public Money difference() {
        if (isEmpty()) {
            throw new ArithmeticException("Empty list!");
        }
        
        Money differenceMoney = get(0);
        
        if (size() > 1) {
            for (int i = 1; i <= (size()-1);i++) {
                differenceMoney = differenceMoney.subtract(get(i));
            }   
        }
        
        return differenceMoney;
    }

    /**
     * Calculates the product of all values stored.
     * 
     * @return <code>Money</code> as product
     */
    @Override
    public Money product() {
        if (isEmpty()) {
            throw new ArithmeticException("Empty list!");
        }
        
        Money productMoney = get(0);
        
        if (size() > 1) {
            for (int i = 1; i <= (size()-1);i++) {
                productMoney = productMoney.multiply(get(i));
            }  
        } 
        
        return productMoney;
    }

    /**
     * Calculates the quotient of all values stored.
     * 
     * @return <code>Money</code> object as quotient
     */
    @Override
    public Money quotient() {
        if (isEmpty()) {
            throw new ArithmeticException("Empty list!");
        }
        
        Money quotientMoney = get(0);
        
        if (size() > 1) {
            for (int i = 1; i <= (size()-1);i++) {
                quotientMoney = quotientMoney.divide(get(i));
            }
        }
        
        return quotientMoney;
    }
    
    /**
     * Calculates the sum for each values stored with the given value.
     * 
     * @param money <code>Money</code> object
     */
    @Override
    public void addEachWith(Money money) {
        for (int i = 0; i < this.size(); i++) {
            set(i, get(i).add(money));
        }
    }

    /**
     * Calculates the difference for each values stored with the given value.
     * 
     * @param money <code>Money</code> object
     */
    @Override
    public void subtractEachWith(Money money) {
        for (int i = 0; i < this.size(); i++) {
            set(i, get(i).subtract(money));
        }
    }

    /**
     * Calculates the product for each values stored with the given value.
     * 
     * @param money <code>Money</code> object
     */
    @Override
    public void multiplyEachWith(Money money) {
        for (int i = 0; i < this.size(); i++) {
            set(i, get(i).multiply(money));
        }
    }

    /**
     * Calculates the quotient for each values stored with the given value.
     * 
     * @param money <code>Money</code> object
     */
    @Override
    public void divideEachWith(Money money) {
        for (int i = 0; i < this.size(); i++) {
            set(i, get(i).divide(money));
        }
    }
}
