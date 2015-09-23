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
 * Class that represents a single monetary value. This is an immutable class,
 * its value cannot be changed. All operation output returns a new object of
 * this class.
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

    /**
     * A private constructor to delegate object creation to its static nested 
     * builder class. Accept Builder as an argument and retrieves all 
 collected information.
     * 
     * @param moneyBuilder Static nested builder class object
     */
    private Money(Builder moneyBuilder) {
        this.currencyCode = moneyBuilder.bCurrencyCode;
        this.decimalUnit = moneyBuilder.bDecimalUnit;
        this.wholeUnit = moneyBuilder.bWholeUnit;
        this.leadingDecimalZeros = moneyBuilder.bLeadingDecimalZeros;
        this.sign = moneyBuilder.bSign;
    }

    /***
     * The builder class for object creation purposes.
     */
    public static final class Builder {

        private String bCurrencyCode;
        private long bWholeUnit;
        private long bDecimalUnit;
        private long bLeadingDecimalZeros;
        private SignValue bSign;

        /**
         * Apply default values in case the user did not set a custom value.
         */
        public Builder() {
            // Default values
            this.bCurrencyCode = DEFAULT_CURRENCY_CODE;
            this.bSign = SignValue.Positive;
            this.bWholeUnit = 0;
            this.bDecimalUnit = 0;
            this.bLeadingDecimalZeros = 0;
        }
        
        /**
         * Sets the whole unit value.
         * 
         * @param aWholeUnit long value
         * @return this Builder object
         */
        public Builder wholeUnit(long aWholeUnit) {
            this.bWholeUnit = aWholeUnit;
            return this;
        }

        /**
         * Sets the sign value with SignValue as its argument.
         * 
         * @param aSignValue SignValue option
         * @return this Builder object
         */
        public Builder sign(SignValue aSignValue) {
            this.bSign = aSignValue;
            return this;
        }

        /**
         * Sets the decimal value.
         * 
         * @param aDecimalUnit long value
         * @return this Builder object
         */
        public Builder decimalUnit(long aDecimalUnit) {
            if (aDecimalUnit < 0) {
                this.bSign = SignValue.Negative;
            }
            
            this.bDecimalUnit = aDecimalUnit;
            return this;
        }

        /**
         * Sets the leading zeros before the decimal value.
         * 
         * @param aLeadingZeroes long value
         * @return this Builder object
         */
        public Builder leadingDecimalZeroes(long aLeadingZeroes) {
            this.bLeadingDecimalZeros = aLeadingZeroes;
            return this;
        }

        /**
         * Sets the currency code.
         * 
         * @param aCurrencyCode Accepted String value 
         * @return this Builder object
         */
        public Builder currencyCode(String aCurrencyCode) {
            this.bCurrencyCode = Currency.getInstance(
                aCurrencyCode.toUpperCase()).getCurrencyCode();
            return this;
        }

        /**
         * Build the Money object based on new values set. Perform checks before 
         * returning the Money object.
         * 
         * @return Money object
         */
        public Money build() {
            Money bMoney = new Money(this);

            // Check if leadingZeroInput is negative, then throw exception
            if (bMoney.getLeadingDecimalZeros() < 0) {
                throw new IllegalStateException("Negative leading zero input.");
            }

            // Prevent negative zero money
            if (bMoney.getWholeUnit() == 0 && bMoney.getDecimalUnit() == 0 && 
                    bMoney.isNegative()) {
                throw new IllegalStateException("Zero money cannot be negative.");
            } else {
                // Prevent negative values, force to use SignValue instead
                if (bMoney.getWholeUnit() < 0 || bMoney.getDecimalUnit() < 0) {
                    throw new IllegalStateException("Negative unit values. Use "
                            + "SignValue instead.");
                } 
            }

            if (bMoney.getDecimalUnit() == 0 && 
                    bMoney.getLeadingDecimalZeros() > 0) {
                    throw new IllegalStateException("Abnormal leading zeros with "
                            + "zero decimal value.");
            }

            return bMoney;
        }
    }

    /**
     * Returns the sign.
     *
     * @return <code>SignValue</code> constant
     */
    public SignValue getSign() {
        return sign;
    }

    /**
     * Returns the whole unit.
     *
     * @return <code>long</code> value
     */
    public long getWholeUnit() {
        return wholeUnit;
    }

    /**
     * Returns the decimal unit.
     *
     * @return <code>long</code> value
     */
    public long getDecimalUnit() {
        return decimalUnit;
    }

    /**
     * Returns any leading zeroes.
     *
     * @return <code>long</code> value
     */
    public long getLeadingDecimalZeros() {
        return leadingDecimalZeros;
    }

    /**
     * Returns currency code.
     *
     * @return <code>String</code> object
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * Negates this value sign.
     *
     * @return <code>Money</code> object
     */
    public Money negate() {
        if (wholeUnit == 0 && decimalUnit == 0 && sign == SignValue.Positive) {
            throw new IllegalStateException("Cannot negate zero.");
        }
        
        return new Money.Builder()
                .wholeUnit(wholeUnit)
                .currencyCode(currencyCode)
                .decimalUnit(decimalUnit)
                .leadingDecimalZeroes(leadingDecimalZeros)
                .sign(sign.oppositeOf())
                .build();
    }

    /**
     * Calculates the sum of two <code>Money</code> objects and returns a new
     * <code>Money</code>.
     *
     * @param money <code>Money</code> object to be added
     * @return <code>Money</code> object as sum
     */
    public Money add(Money money) {
        verifyInput(money);

        MoneyOperation additionOperation = new Addition(this, money);
        return additionOperation.execute();
    }

    /**
     * Calculates difference of two <code>Money</code> objects and returns a new
     * <code>Money</code>.
     *
     * @param money <code>Money</code> object to be subtracted to
     * @return <code>Money</code> object as difference
     */
    public Money subtract(Money money) {
        verifyInput(money);

        MoneyOperation subtractionOperation = new Subtraction(this, money);
        return subtractionOperation.execute();
    }

    /**
     * Calculates product of two <code>Money</code> objects and returns a new
     * <code>Money</code>.
     *
     * @param money exponent to be multiplied by
     * @return <code>Money</code> object as product
     */
    public Money multiply(Money money) {
        verifyInput(money);

        MoneyOperation multiplicationOperation = new Multiplication(this, money);
        return multiplicationOperation.execute();
    }

    /**
     * Calculates product of two <code>Money</code> objects and returns a new
     * <code>Money</code>.
     *
     * @param value <code>long</code> object to be multiplied to
     * @return <code>Money</code> object as product
     */
    public Money multiply(long value) {
        SignValue sign = SignValue.Positive;
        
        if (value < 0) {
            sign = SignValue.Negative;
            value = Math.abs(value);
        }
        
        Money convertedTypeMoney = new Money.Builder()
                .sign(sign)
                .wholeUnit(value)
                .build();
        return multiply(convertedTypeMoney);
    }

    /**
     * Calculates product of two <code>Money</code> objects and returns a new
     * <code>Money</code>.
     *
     * @param value <code>integer</code> object to be multiplied to
     * @return <code>Money</code> object as product
     */
    public Money multiply(int value) {
        return multiply((long)value);
    }

    /**
     * Calculates quotient of two <code>Money</code> objects and returns a new
     * <code>Money</code>.
     *
     * @param money <code>long</code> object as divisor
     * @return <code>Money</code> object as quotient
     */
    public Money divide(Money money) {
        verifyInput(money);

        MoneyOperation divisionOperation = new Division(this, money);
        return divisionOperation.execute();
    }

    /**
     * Calculates product of a <code>Money</code> object to the specified power
     * and returns a new <code>Money</code>. Both the exponent and base values
     * must whole numbers. But can be positive or negative.
     *
     * @param value <code>long</code> value used as the exponent
     * @return <code>Money</code> object as product
     */
    public Money power(long value) {
        SignValue sign = SignValue.Positive;
        
        if (value < 0) {
            sign = SignValue.Negative;
            value = Math.abs(value);
        }
        
        Money convertedTypeMoney = new Money.Builder()
                .sign(sign)
                .wholeUnit(value)
                .build();
        verifyInput(convertedTypeMoney);

        MoneyOperation powerOperation = new Power(this, convertedTypeMoney);
        return powerOperation.execute();
    }

    /**
     * Calculates product of a <code>Money</code> object to the specified power
     * and returns a new <code>Money</code>. Both the exponent and base values
     * must whole numbers. But can be positive or negative.
     *
     * @param value <code>integer</code> value used as the exponent
     * @return <code>Money</code> object as product
     */
    public Money power(int value) {
        return power((long)value);
    }

    /**
     * Checks if this object is positive.
     *
     * @return <code>boolean</code> value
     */
    public boolean isPositive() {
        return getSign() == SignValue.Positive && isNotZero();
    }

    /**
     * Checks if this object is negative.
     *
     * @return <code>boolean</code> value
     */
    public boolean isNegative() {
        return getSign() == SignValue.Negative;
    }

    /**
     * Checks if this object is zero.
     *
     * @return <code>boolean</code> value
     */
    public boolean isZero() {
        return getWholeUnit() == 0 && getDecimalUnit() == 0;
    }

    /**
     * Checks if the object is a whole number without fractional parts.
     *
     * @return <code>boolean</code> value
     */
    public boolean isWholeNumber() {
        return getDecimalUnit() == 0 && getLeadingDecimalZeros() == 0;
    }

    /**
     * Checks if this object is not zero.
     *
     * @return <code>boolean</code> value
     */
    public boolean isNotZero() {
        return !isZero();
    }

    /**
     * Override equals method to match if two <code>Money</code> have the same
     * value and currency.
     *
     * @param money <code>Money</code> object to compare with
     * @return <code>boolean</code> value
     */
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

    /**
     * Override hash code to match equal <code>Money</code> objects.
     *
     * @return <code>boolean</code> value
     */
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

    /**
     * Compares two <code>Money</code> objects. Returns 1 if this object is
     * greater than the <code>Money</code> object being compared with. Returns 0
     * if they are equal and -1 if they are equal.
     *
     * @param money <code>Money</code> object being compared with
     * @return <code>integer</code> value
     */
    public int compareTo(Money money) {
        verifyInput(money);

        BigInteger[] resultArray = ConversionTypeUtil
                .concatWholeAndDecThenConvertBigInteger(this, money);

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
     * Checks if this object is less than the argument <code>Money</code>
     * object.
     *
     * @param money <code>Money</code> object
     * @return <code>boolean</code> value
     */
    public boolean isLessThan(Money money) {
        return compareTo(money) == -1;
    }

    /**
     * Checks if this object is less than or equal to the argument
     * <code>Money</code> object.
     *
     * @param money <code>Money</code> object
     * @return <code>boolean</code> value
     */
    public boolean isLessThanOrEqualTo(Money money) {
        return compareTo(money) == -1 || equals(money);
    }

    /**
     * Checks if this object is less than zero
     *
     * @return <code>boolean</code> value
     */
    public boolean isLessThanZero() {
        Money zeroMoney = new Money.Builder()
                .currencyCode(currencyCode)
                .build();
        return compareTo(zeroMoney) == -1;
    }

    /**
     * Checks if this object is greater than the argument <code>Money</code>
     * object.
     *
     * @param money <code>Money</code> object
     * @return <code>boolean</code> value
     */
    public boolean isGreaterThan(Money money) {
        return compareTo(money) == 1;
    }

    /**
     * Checks if this object is greater than or equal to the argument
     * <code>Money</code> object.
     *
     * @param money <code>Money</code> object
     * @return <code>boolean</code> value
     */
    public boolean isGreaterThanOrEqualTo(Money money) {
        return compareTo(money) == 1 || equals(money);
    }

    /**
     * Checks if this object is greater than zero
     *
     * @return <code>boolean</code> value
     */
    public boolean isGreaterThanZero() {
        Money tempMoney = new Money.Builder()
                .currencyCode(currencyCode)
                .sign(sign).build();
        return compareTo(tempMoney) == 1;
    }

    /**
     * Checks where the input is null and has the same currency with this
     * object. Throws runtime exceptions if deem invalid.
     *
     * @param money <code>Money</code> object
     * @return void
     */
    private void verifyInput(Money money) {
        if (money == null) {
            throw new NullPointerException("Cannot accept null input.");
        }

        if (!isSameCurrencyCodes(money)) {
            throw new CurrencyDidNotMatchException("Currency codes doesn't matched!");
        }
    }

    /**
     * Checks if this object has the same currency with the argument
     * <code>Money</code> object.
     *
     * @param money <code>Money</code> object
     * @return <code>boolean</code> value
     */
    public boolean isSameCurrencyCodes(Money money) {
        if (money == null) {
            return false;
        }

        return getCurrencyCode().equalsIgnoreCase(money.getCurrencyCode());
    }

    /**
     * Returns a <code>String</code> representation of this object without its
     * currency code.
     *
     * @return <code>String</code> object
     */
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

    /**
     * Returns a <code>String</code> representation of this object including its
     * sign, currency and value. Shows only the first two decimal places without
     * rounding the value.
     *
     * @return <code>String</code> object
     */
    @Override
    public String toString() {
        double doubleFormat = Double.valueOf(toStringDecimal());

        NumberFormat numberFormatter
                = NumberFormat.getCurrencyInstance(Locale.getDefault());

        Currency currency = Currency.getInstance(this.currencyCode);
        numberFormatter.setCurrency(currency);
        numberFormatter.setRoundingMode(RoundingMode.DOWN);

        return numberFormatter.format(doubleFormat);
    }

}
