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
import java.util.Map;

/**
 *
 * @author Ryan Gilera
 * @param <K>
 * @param <V>
 */
public class MoneyHashMap<K,V extends Money> extends AbstractMoneyMap<K, Money>{
    
    
    public MoneyHashMap() {
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
    public MoneyHashMap<K, Money> retrieveAllPositiveValues() {
        MoneyHashMap<K,Money> listOfPositives = new MoneyHashMap();
        
        for (Entry<K,Money> entry : this.entrySet()) {
            if (entry.getValue().isPositive()) {
                listOfPositives.put(entry.getKey(), entry.getValue());
            }
        }
        
        return listOfPositives;
    }

    @Override
    public MoneyHashMap<K, Money> retrieveAllNegativeValues() {
        MoneyHashMap<K,Money> listOfNegatives = new MoneyHashMap();
        
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
        Money differenceMoney = null;
        for (Money money : this.values()) {
            if (differenceMoney == null) {
                differenceMoney = money;
            } else {
                differenceMoney = differenceMoney.subtract(money);
            }
            
        }
        
        return differenceMoney;
    }

    @Override
    public Money product() {
        Money productMoney = null;
        for (Money money : this.values()) {
            if (productMoney == null) {
                productMoney = money;
            } else {
                productMoney = productMoney.multiply(money);
            }
            
        }
        
        return productMoney;
    }

    @Override
    public Money quotient() {
        Money quotientMoney = null;
        for (Money money : this.values()) {
            if (quotientMoney == null) {
                quotientMoney = money;
            } else {
                quotientMoney = quotientMoney.divide(money);
            }
            
        }
        
            return quotientMoney;
    }

    @Override
    public void addEachWith(Money money) {
        for (Map.Entry<K,Money> anEntry : this.entrySet()){
            Money newMoney = anEntry.getValue().add(money);
            put(anEntry.getKey(), newMoney);
        }
    }

    @Override
    public void subtractEachWith(Money money) {
        for (Map.Entry<K,Money> anEntry : this.entrySet()){
            Money newMoney = anEntry.getValue().subtract(money);
            put(anEntry.getKey(), newMoney);
        }
    }

    @Override
    public void multiplyEachWith(Money money) {
       for (Map.Entry<K,Money> anEntry : this.entrySet()){
            Money newMoney = anEntry.getValue().multiply(money);
            put(anEntry.getKey(), newMoney);
        }
    }

    @Override
    public void divideEachWith(Money money) {
        for (Map.Entry<K,Money> anEntry : this.entrySet()){
            Money newMoney = anEntry.getValue().divide(money);
            put(anEntry.getKey(), newMoney);
        }
    }
    

}
