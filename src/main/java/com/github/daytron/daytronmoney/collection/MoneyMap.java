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
 * @param <K>
 * @param <V>
 */
public class MoneyMap<K,V extends Money> extends AbstractMoneyMap<K, Money>{
    
    
    public MoneyMap() {
    }

    @Override
    public boolean hasNegativeValue() {
        boolean hasNegative = false;
        for (Money money : this.values()) {
            if (money.isNegative()) {
                hasNegative = true;
            }
        }
        
        return hasNegative;
    }

    @Override
    public boolean hasPositiveValue() {
        boolean hasPositive = false;
        for (Money money : this.values()) {
            if (money.isPositive()) {
                hasPositive = true;
            }
        }
        
        return hasPositive;
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
    public MoneyMap<K, Money> retrieveAllPositiveValues() {
        MoneyMap<K,Money> listOfPositives = new MoneyMap();
        
        for (Entry<K,Money> entry : this.entrySet()) {
            if (entry.getValue().isPositive()) {
                listOfPositives.put(entry.getKey(), entry.getValue());
            }
        }
        
        return listOfPositives;
    }

    @Override
    public MoneyMap<K, Money> retrieveAllNegativeValues() {
        MoneyMap<K,Money> listOfNegatives = new MoneyMap();
        
        for (Entry<K,Money> entry : this.entrySet()) {
            if (entry.getValue().isNegative()) {
                listOfNegatives.put(entry.getKey(), entry.getValue());
            }
        }
        
        return listOfNegatives;
    }

    @Override
    public Money sum() {
        Money sumMoney = new Money();
        for (Money money : this.values()) {
            sumMoney = sumMoney.add(money);
        }
        
        return sumMoney;
    }
    
    @Override
    public Money difference() {
        Money differenceMoney = new Money();
        for (Money money : this.values()) {
            differenceMoney = differenceMoney.subtract(money);
        }
        
        return differenceMoney;
    }

    @Override
    public Money product() {
        Money productMoney = new Money();
        for (Money money : this.values()) {
            productMoney = productMoney.multiply(money);
        }
        
        return productMoney;
    }

    @Override
    public Money quotient() {
        Money quotientMoney = new Money();
        for (Money money : this.values()) {
            quotientMoney = quotientMoney.divide(money);
        }
        
        return quotientMoney;
    }

    @Override
    public void addEachWith(Money money) {
        for (K k : keySet()){
            Money oldMoney = get(k);
            Money newMoney = oldMoney.add(money);
            put(k, newMoney);
        }
    }

    @Override
    public void subtractEachWith(Money money) {
        for (K k : keySet()){
            Money oldMoney = get(k);
            Money newMoney = oldMoney.subtract(money);
            put(k, newMoney);
        }
    }

    @Override
    public void multiplyEachWith(Money money) {
       for (K k : keySet()){
            Money oldMoney = get(k);
            Money newMoney = oldMoney.multiply(money);
            put(k, newMoney);
        }
    }

    @Override
    public void divideEachWith(Money money) {
        for (K k : keySet()){
            Money oldMoney = get(k);
            Money newMoney = oldMoney.divide(money);
            put(k, newMoney);
        }
    }
    

}
