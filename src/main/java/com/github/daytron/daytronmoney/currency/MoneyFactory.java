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

import com.github.daytron.daytronmoney.utility.StringUtil;
import java.util.Currency;
import java.util.Locale;

/**
 * A factory class for instantiating <code>Money</code> objects. Parses other
 * data types into a <code>Money</code> object.
 * 
 * @author Ryan Gilera
 */
public class MoneyFactory {

    private static final String DEFAULT_CURRENCY_CODE
            = Currency.getInstance(Locale.getDefault()).getCurrencyCode();
    private String currencyCode;

    /**
     * Default constructor. Applies default local currency code.
     */
    public MoneyFactory() {
        this(DEFAULT_CURRENCY_CODE);
    }

    /**
     * Optional constructor with specific currency code as an input.
     * 
     * @param currencyCode A <code>String</code> object that represents a
     * currency code (Three letter currency code)
     */
    public MoneyFactory(String currencyCode) {
        currencyCode = currencyCode.trim();
        this.currencyCode = (Currency.getInstance(
                currencyCode.toUpperCase())).getCurrencyCode();
    }

    /**
     * Optional constructor with Locale constant as its input. Please note that
     * only Locale country constants are allowed. A language-based Local constants
     * throws an exception.
     * 
     * @param locale A <code>Locale</code> country constant
     */
    public MoneyFactory(Locale locale) {
        this.currencyCode = Currency.getInstance(locale).getCurrencyCode();
    }

    /**
     * Returns the currency code.
     * 
     * @return A <code>String</code> object 
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * Sets the currency code.
     * 
     * @param currencyCode A <code>String</code> object that represents a
     * currency code (Three letter currency code)
     */
    public void setCurrencyCode(String currencyCode) {
        currencyCode = currencyCode.trim();
        this.currencyCode = (Currency.getInstance(
                currencyCode.toUpperCase())).getCurrencyCode();
    }

    /**
     * Creates a zero Money for no-argument input.
     * 
     * @return Resulting <code>Money</code> object 
     */
    public Money valueOf() {
        return new Money(currencyCode, SignValue.Positive, 0, 0, 0);
    }
    
    /**
     * Creates a <code>Money</code> object from parsing it's 
     * <code>long</code> argument. Default sign is positive if 
     * value is greater than 0.
     * 
     * @param value A <code>long</code> value to be parsed
     * @return Resulting <code>Money</code> object 
     */
    public Money valueOf(long value) {
        return new Money(currencyCode, 
                SignValue.Positive, value, 0, 0);
    }
    
    /**
     * Creates a <code>Money</code> object from parsing its 
     * <code>int</code> argument. Default sign is positive if 
     * value is greater than 0.
     * 
     * @param value An <code>integer</code> value to be parsed
     * @return Resulting <code>Money</code> object 
     */
    public Money valueOf(int value) {
        return new Money(currencyCode, 
                SignValue.Positive, (long)value, 0, 0);
    }

    /**
     * Creates a <code>Money</code> object from parsing 
     * its <code>long</code> arguments. First argument represents the whole unit
     * , the second for its decimal unit and last argument for its leading zeroes.
     * Default sign is positive if value is greater than 0.
     * 
     * @param wholeUnit A <code>long</code> value to be parsed as whole unit
     * @param decimalUnit A <code>long</code> value to be parsed as decimal unit
     * @param leadingDecimalZeros A <code>long</code> value to be parsed as 
     * leading zeroes
     * @return Resulting <code>Money</code> object 
     */
    public Money valueOf(long wholeUnit, long decimalUnit, 
            long leadingDecimalZeros) {
        return new Money(this.currencyCode, SignValue.Positive, 
                wholeUnit, decimalUnit, leadingDecimalZeros);
    }

    /**
     * Creates a <code>Money</code> object from parsing a <code>String</code> 
     * value. Possible inputs include:
     * <ul>
     * <li>"GBP 12.5"</li>
     * <li>"GBP12.5"</li>
     * <li>"USD-12"</li>
     * <li>"USD -896,586,785,785,896.0025634589"</li>
     * <li>""</li>
     * <li>"+9.6212"</li>
     * <li>"-8.0000000"</li>
     * <li>"0.00"</li>
     * <li>"-0"</li>
     * <li>"-0.12"</li>
     * <li>"0"</li>
     * <li>"286"</li>
     * <li>"0.0000005"</li>
     * <li>"000000000000000.6"</li>
     * <li>"12,856,896.00963"</li>
     * </ul>
     * 
     * @param valueString A <code>String</code> object to be parsed
     * @return Resulting <code>Money</code> object 
     */
    public Money valueOf(String valueString) {
        valueString = valueString.trim();
        if (valueString == null) {
            throw new NullPointerException("Null input!");
        }
        
        if (valueString.isEmpty()) {
            return new Money(currencyCode, SignValue.Positive, 0, 0, 0);
        }

        String[] resultParsedValue = StringUtil.parseAndRemoveCurrencyCode(valueString);
        valueString = resultParsedValue[1];
        
        String newCurrencyCode;
        if (resultParsedValue[0].isEmpty()) {
            newCurrencyCode = this.currencyCode;
        } else {
            newCurrencyCode = (Currency.getInstance(
                    resultParsedValue[0])).getCurrencyCode();
        }
        
        long[] parsedData = parseValue(valueString);
        SignValue sign;
        long wholeUnit, decimalUnit, leadingDecimalZeros;

        // Parse sign
        sign = ((parsedData[0] == 1)?SignValue.Positive:SignValue.Negative);

        wholeUnit = parsedData[1];
        decimalUnit = parsedData[2];
        leadingDecimalZeros = parsedData[3];

        return new Money(newCurrencyCode, sign, wholeUnit, decimalUnit, 
        leadingDecimalZeros);
    }

    /**
     * Parse String to long values as long arrays. 
     * 
     * @param valueString A <code>String</code> object to be parsed
     * @return A <code>long</code> array
     */
    private static long[] parseValue(String valueString) {
        // First element = sign 1 for positive and 0 for negative
        // Second element for pounds
        // Third element for pence
        final long[] parsedData = new long[4];

        // Removes commas
        // Allows to accept number string with commas
        valueString = StringUtil.removeCommas(valueString);
        
        /*
         Regex conditions:
         ^   = Beginning of the line
         [+-]?  = Optional Sign 
         [0-9]{1,19} = 1 or 19 digits including zero
         \\. = Literal point symbol, 
             (escaped, because in regex, . is a special character)
         (\\.[0-9]{1,19})? = An optional decimal value must have at least
                             one digit after the dot
         $   = End of the line.
         */
        if (valueString.matches("^[-+]?[0-9]{1,19}(\\.[0-9]{1,19})?$")) {
            
            // Determines sign and then removes it
            // 1 for positive and 0 for negative
            if (valueString.contains("-")) {
                parsedData[0] = 0;

                // Removes sign
                valueString = valueString.substring(1);
            } else if (valueString.contains("+")) {
                parsedData[0] = 1;

                // Removes sign
                valueString = valueString.substring(1);
            } else {
                parsedData[0] = 1;
            }

            String[] splitString = valueString.split("\\.");
            
            if (splitString.length > 1) {
                parsedData[1] = Long.valueOf(splitString[0]);
                
                // Filters single digit decimal number as two digits
                // Ex. 1.2 is 1.20 and NOT 1.02
                if (splitString[1].length() == 1 && 
                        splitString[1].charAt(0) != '0') {
                    splitString[1] += "0";
                }
                
                // Filter if all decimal values are pure repeating zeroes
                // Otherwise process remaining zeroess
                if (splitString[1].matches("[0]{1,}")) {
                    parsedData[2] = 0;
                    parsedData[3] = 0;
                } else {
                    long decimalResult[] = 
                            StringUtil.analyzeDecimalValue(splitString[1]);
                    parsedData[2] = decimalResult[1];
                    parsedData[3] = decimalResult[0];
                }
            } else {
                parsedData[1] = Long.valueOf(splitString[0]);
                parsedData[2] = 0;
                parsedData[3] = 0;
            }
            
            return parsedData;

        } else if ((valueString.indexOf('.', valueString.indexOf('.') + 1) != -1)) {
            /*
             Throws an illegal argument exeption if multiple occurences of dot 
             are detected in the argument input.
             */
            throw new IllegalArgumentException("Invalid number entry. "
                    + "Multiple dots detected.");
        } else if (valueString.charAt(0) == '.' || valueString.contains("+.")
                || valueString.contains("-.")) {
            /*
             Throws an illegal argument exception if a dot is detected in
             the first character or any sign followed by a dot.
             */
            throw new IllegalArgumentException("Invalid number entry. "
                    + "Invalid use of decimal point.");
        } else if (valueString.matches("^[-+]{2,}\\d{1,}\\.?\\d*$")) {
            /*
             Throws an illegal argument exception if multiple characters of the 
             sign symbols are detected.
             */
            throw new IllegalArgumentException("Invalid number entry. "
                    + "Invalid use of sign symbols.");
        } else {
            // Throws a standard illegal argument exception
            throw new IllegalArgumentException("Invalid number entry.");
        }
    }
    
    
}
