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
import java.util.Iterator;

/**
 *
 * @author Ryan Gilera
 */
public class MoneyArrayList extends AbstractMoneyList {
    
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
    
    @Override
    public MoneyArrayList retrieveAllPositiveValues() {
        MoneyArrayList listOfPositives = new MoneyArrayList();
        
        for (Money money : this) {
            if (money.isPositive()) {
                listOfPositives.add(money);
            }
        }
        
        return listOfPositives;
    }
    
    @Override
    public MoneyArrayList retrieveAllNegativeValues() {
        MoneyArrayList listOfNegatives = new MoneyArrayList();
        
        this.stream().filter((money) -> 
                (money.isNegative())).forEach((money) -> {
            listOfNegatives.add(money);
        });
        
        return listOfNegatives;
    }
    
    public void removeAnyZeroValues() {
        for (Iterator<Money> iterator = this.iterator(); iterator.hasNext();) {
            Money nextMoney = iterator.next();
            if (nextMoney.isZero()) {
                iterator.remove();
            } 
        } 
    }
    
    public void removeAnyNegativeValues() {
        for (Iterator<Money> iterator = this.iterator(); iterator.hasNext();) {
            Money nextMoney = iterator.next();
            if (nextMoney.isNegative()) {
                iterator.remove();
            } 
        } 
    }
    
    public void removeAnyPositiveValues() {
        for (Iterator<Money> iterator = this.iterator(); iterator.hasNext();) {
            Money nextMoney = iterator.next();
            if (nextMoney.isPositive()) {
                iterator.remove();
            } 
        } 
    }

    @Override
    public Money sum() {
        Money sumMoney = new Money();
        for (Money money : this) {
            sumMoney = sumMoney.add(money);
        }
        
        return sumMoney;
    }

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
            
            return differenceMoney;
        } else {
            return get(0);
        }
    }

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
            
            return productMoney;
        } else {
            return get(0);
        }
        
    }

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
            
            return quotientMoney;
        } else {
            return get(0);
        }
    }
    
    @Override
    public void addEachWith(Money money) {
        for (int i = 0; i < this.size(); i++) {
            set(i, get(i).add(money));
        }
    }

    @Override
    public void subtractEachWith(Money money) {
        for (int i = 0; i < this.size(); i++) {
            set(i, get(i).subtract(money));
        }
    }

    @Override
    public void multiplyEachWith(Money money) {
        for (int i = 0; i < this.size(); i++) {
            set(i, get(i).multiply(money));
        }
    }

    @Override
    public void divideEachWith(Money money) {
        for (int i = 0; i < this.size(); i++) {
            set(i, get(i).divide(money));
        }
    }
}
