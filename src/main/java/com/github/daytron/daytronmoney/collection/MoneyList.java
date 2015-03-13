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
import com.github.daytron.daytronmoney.currency.SignValue;

/**
 *
 * @author Ryan Gilera
 */
public class MoneyList extends AbstractMoneyList {
    
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
    public MoneyList retrieveAllPositiveValues() {
        MoneyList listOfPositives = new MoneyList();
        
        for (Money money : this) {
            if (money.isPositive()) {
                listOfPositives.add(money);
            }
        }
        
        return listOfPositives;
    }
    
    @Override
    public MoneyList retrieveAllNegativeValues() {
        MoneyList listOfNegatives = new MoneyList();
        
        this.stream().filter((money) -> 
                (money.isNegative())).forEach((money) -> {
            listOfNegatives.add(money);
        });
        
        return listOfNegatives;
    }
    
    public void removeAnyZeroValues() {
        this.stream().filter((money) -> 
                (money.isZero())).forEach((money) -> {
            remove(money);
        });
    }
    
    public void removeAnyNegativeValues() {
        this.stream().filter((money) -> 
                (money.isNegative())).forEach((money) -> {
            remove(money);
        });
    }
    
    public void removeAnyPositiveValues() {
        this.stream().filter((money) -> 
                (money.isPositive())).forEach((money) -> {
            remove(money);
        });
    }

    @Override
    public void sortByValue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sortByCurrency() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Money sum() {
        
        Money sumMoney = new Money();
        for (Money money : this) {
            sumMoney = sumMoney.plus(money);
        }
        
        return sumMoney;
    }

    @Override
    public void addEachWith(Money money) {
        for (int i = 0; i < this.size(); i++) {
            set(i, get(i).plus(money));
        }
    }
}
