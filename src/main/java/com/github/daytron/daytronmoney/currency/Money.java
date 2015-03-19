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

import com.github.daytron.daytronmoney.exception.CurrencyDidNotMatchException;
import com.github.daytron.daytronmoney.utility.ConversionTypeUtil;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.Objects;

/**
 *
 * @author Ryan Gilera
 */
public final class Money {

    private static final String DEFAULT_CURRENCY_CODE
            = Currency.getInstance(Locale.getDefault()).getCurrencyCode();
    private static final String ZERO_STRING = "0";
    private static final String DECIMAL_POINT = ".";

    private final String currencyCode;
    private final long wholeUnit;
    private final long decimalUnit;
    private final long leadingDecimalZeros;
    private final SignValue sign;

    public Money(String currencyCode, SignValue sign, long wholeUnit,
            long decimalUnit, long leadingDecimalZeros) {

        // Check if leadingZeroInput is negative, then throw exception
        if (leadingDecimalZeros < 0) {
            throw new IllegalArgumentException("Negative leading zero input.");
        }

        this.currencyCode = Currency.getInstance(
                currencyCode.toUpperCase()).getCurrencyCode();

        // Prevent negative zero money
        // For simplicity zero is considered positive
        // but when represented in String format,
        // it is unsign.
        if (wholeUnit == 0 && decimalUnit == 0) {
            this.sign = SignValue.Positive;
        } else {
            // Check also if user's long type input is negative
            // ex new Money("USD",SignValue.Positive, -12,7,1);
            // this should change the sign to negative, regardless of sign input
            if (wholeUnit < 0 || decimalUnit < 0) {
                this.sign = SignValue.Negative;
            } else {
                this.sign = sign;
            }
        }

        // If decimal input single digit, non-zero and no leading zero
        // Add a leading zero
        // Single digit decimal means it has a 1 leading zero
        // ex. 1 means 0.01 not 0.10
        if (Long.toString(decimalUnit).length() == 1 && decimalUnit != 0
                && leadingDecimalZeros == 0) {
            this.leadingDecimalZeros = 1;
        } else {
            // Also check if decimal is zero but leadingzero > 0 
            // then normalize it. ex. 23.000000 to 23.0
            // But in toString output is 23.00
            if (decimalUnit == 0) {
                this.leadingDecimalZeros = 0;
            } else {
                this.leadingDecimalZeros = leadingDecimalZeros;
            }
        }

        // Check also if user's long type input is negative
        // ex new Money("USD",SignValue.Positive, -12,7,1);
        // after the sign is change (see above code), remove negative
        if (wholeUnit < 0 || decimalUnit < 0) {
            this.wholeUnit = Math.abs(wholeUnit);
            this.decimalUnit = Math.abs(decimalUnit);
        } else {
            this.wholeUnit = wholeUnit;
            this.decimalUnit = decimalUnit;
        }
    }

    public Money(SignValue sign, long wholeUnit,
            long decimalUnit, long leadingDecimalZeros) {
        this(DEFAULT_CURRENCY_CODE, sign, wholeUnit, decimalUnit,
                leadingDecimalZeros);
    }

    public Money(SignValue sign, long wholeUnit, long decimalUnit) {
        this(DEFAULT_CURRENCY_CODE, sign, wholeUnit, decimalUnit, 0);
    }

    public Money(long wholeUnit, long decimalUnit) {
        this(DEFAULT_CURRENCY_CODE, SignValue.Positive, wholeUnit, decimalUnit, 0);
    }

    public Money(long wholeUnit) {
        this(DEFAULT_CURRENCY_CODE, SignValue.Positive, wholeUnit, 0, 0);
    }

    public Money() {
        this(DEFAULT_CURRENCY_CODE, SignValue.Positive, 0, 0, 0);
    }

    public SignValue getSign() {
        return sign;
    }

    public long getWholeUnit() {
        return wholeUnit;
    }

    public long getDecimalUnit() {
        return decimalUnit;
    }

    public long getLeadingDecimalZeros() {
        return leadingDecimalZeros;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public Money negate() {        
        return new Money(currencyCode, sign.oppositeOf(),
                wholeUnit, decimalUnit, leadingDecimalZeros);
    }

    /**
     *
     * @param money
     * @return
     */
    public Money add(Money money) {
        verifyInput(money);

        MoneyOperation additionOperation = new Addition(this, money);
        return additionOperation.execute();
    }

    public Money subtract(Money money) {
        verifyInput(money);

        MoneyOperation subtractionOperation = new Subtraction(this, money);
        return subtractionOperation.execute();
    }
    
    public Money multiply(Money money) {
        verifyInput(money);
        
        MoneyOperation multiplicationOperation = new Multiplication(this, money);
        return multiplicationOperation.execute();
    }
    
    public Money multiply(long value) {
        Money convertedTypeMoney = new Money(value);
        return multiply(convertedTypeMoney);    
    }
    
    public Money multiply(int value) {
        Money convertedTypeMoney = new Money((long)value);
        return multiply(convertedTypeMoney);    
    }
    
    public Money divide(Money money) {
        verifyInput(money);
        
        MoneyOperation divisionOperation = new Division(this, money);
        return divisionOperation.execute();
    }

    public boolean isPositive() {
        return getSign() == SignValue.Positive && isNotZero();
    }

    public boolean isNegative() {
        return getSign() == SignValue.Negative;
    }

    public boolean isZero() {
        return getWholeUnit() == 0 && getDecimalUnit() == 0;
    }

    public boolean isNotZero() {
        return !isZero();
    }

    @Override
    public boolean equals(Object money) {
        if (money == null) {
            return false;
        }
        if (this == money) {
            return true;
        }
        if (!(money instanceof Money)) {
            return false;
        }

        Money thatMoney = (Money) money;

        return getCurrencyCode().equals(thatMoney.getCurrencyCode())
                && getSign() == thatMoney.getSign()
                && getWholeUnit() == thatMoney.getWholeUnit()
                && getDecimalUnit() == thatMoney.getDecimalUnit()
                && getLeadingDecimalZeros() == thatMoney.getLeadingDecimalZeros();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.currencyCode);
        hash = 41 * hash + (int) (this.wholeUnit ^ (this.wholeUnit >>> 32));
        hash = 41 * hash + (int) (this.decimalUnit ^ (this.decimalUnit >>> 32));
        hash = 41 * hash + (int) (this.leadingDecimalZeros ^ (this.leadingDecimalZeros >>> 32));
        hash = 41 * hash + Objects.hashCode(this.sign);
        return hash;
    }

    public int compareTo(Money money) {
        verifyInput(money);

        BigInteger[] resultArray = ConversionTypeUtil
                .concatWholeAndDecThenConvertToLong(this, money);

        if (getSign() == SignValue.Negative
                && money.getSign() == SignValue.Positive) {
            return -1;
        } else if (getSign() == SignValue.Positive
                && money.getSign() == SignValue.Negative) {
            return 1;
        } else if (getSign() == SignValue.Negative
                && money.getSign() == SignValue.Negative) {

            // index 1 is ThisMoney and 2 is opposite
            // index 0 is for cutoff decimal index
            if (resultArray[1].compareTo(resultArray[2]) > 0) {
                return -1;
            } else if (resultArray[1].compareTo(resultArray[2]) < 0) {
                return 1;
            } else {
                return 0;
            }

        } else {
            return resultArray[1].compareTo(resultArray[2]);
        }

    }

    /**
     *
     * @param money
     * @return
     */
    public boolean isLessThan(Money money) {
        return compareTo(money) == -1;
    }
    
    public boolean isLessThanOrEqualTo(Money money) {
        return compareTo(money) == -1 || equals(money);
    }
    
    public boolean isLessThanZero() {
        return compareTo(new Money(currencyCode, sign, 0, 0, 0)) == -1; 
    }

    public boolean isGreaterThan(Money money) {
        return compareTo(money) == 1;
    }
    
    public boolean isGreaterThanOrEqualTo(Money money) {
        return compareTo(money) == 1 || equals(money);
    }
    
    public boolean isGreaterThanZero() {
        return compareTo(new Money(currencyCode, sign, 0, 0, 0)) == 1; 
    }

    /**
     * Checks where the input is null and has the same currency with this
     * object.
     *
     * @param money
     * @return
     */
    private void verifyInput(Money money) {
        if (money == null) {
            throw new NullPointerException("Cannot accept null input.");
        }

        if (!isSameCurrencyCodes(money)) {
            throw new CurrencyDidNotMatchException("Currency codes doesn't matched!");
        }
    }

    public boolean isSameCurrencyCodes(Money money) {
        if (money == null) return false;
        
        return getCurrencyCode().equalsIgnoreCase(money.getCurrencyCode());
    }
    
    public String toStringDecimal() {
        // Apply sign
        String signText;
        if (getWholeUnit() == 0 && getDecimalUnit() == 0) {
            signText = "";
        } else if (getSign() == SignValue.Positive) {
            signText = "";
        } else {
            signText = getSign().getText();
        }
        
        // Add any leading zeros
        String penceStr = "";
        for (int i = 0; i < getLeadingDecimalZeros(); i++) {
            penceStr += ZERO_STRING;
        }
        
        return signText + getWholeUnit() + DECIMAL_POINT + penceStr + getDecimalUnit();
    }

    @Override
    public String toString() {
        double doubleFormat = Double.valueOf(toStringDecimal());

        NumberFormat numberFormatter = 
        NumberFormat.getCurrencyInstance(Locale.getDefault());
        
        Currency currency = Currency.getInstance(this.currencyCode);
        numberFormatter.setCurrency(currency);
        numberFormatter.setRoundingMode(RoundingMode.DOWN);
        
        return numberFormatter.format(doubleFormat);
    }

}
