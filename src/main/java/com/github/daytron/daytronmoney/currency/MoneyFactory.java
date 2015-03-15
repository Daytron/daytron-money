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
 *
 * @author Ryan Gilera
 */
public class MoneyFactory {

    private static final String DEFAULT_CURRENCY_CODE
            = Currency.getInstance(Locale.getDefault()).getCurrencyCode();
    private String currencyCode;

    public MoneyFactory() {
        this.currencyCode = DEFAULT_CURRENCY_CODE;
    }

    public MoneyFactory(String currencyCode) {
        currencyCode = currencyCode.trim();
        this.currencyCode = (Currency.getInstance(
                currencyCode.toUpperCase())).getCurrencyCode();
    }

    public MoneyFactory(Locale locale) {
        this.currencyCode = Currency.getInstance(locale).getCurrencyCode();
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        currencyCode = currencyCode.trim();
        this.currencyCode = (Currency.getInstance(
                currencyCode.toUpperCase())).getCurrencyCode();
    }
    
    

    public Money valueOf() {
        return new Money(currencyCode, SignValue.Positive, 0, 0, 0);
    }

    public Money valueOf(long wholeUnit, long decimalUnit, 
            long leadingDecimalZeros) {
        return new Money(this.currencyCode, SignValue.Positive, 
                wholeUnit, decimalUnit, leadingDecimalZeros);
    }

    public Money valueOf(String valueString) {
        valueString = valueString.trim();
        if (valueString == null) {
            throw new NullPointerException("Null argument passed.");
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
        if (parsedData[0] == 1) {
            sign = SignValue.Positive;
        } else {
            sign = SignValue.Negative;
        }

        wholeUnit = parsedData[1];
        decimalUnit = parsedData[2];
        leadingDecimalZeros = parsedData[3];

        return new Money(newCurrencyCode, sign, wholeUnit, decimalUnit, 
        leadingDecimalZeros);
    }

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
         -?  = Optional negative symbol
         \d{1,} = 1 or more digits
         \.? = An optional dot (escaped, because in regex, . is a special character)
         \d* = 0 or more digits (the decimal part);
         $   = End of the line.
         */
        if (valueString.matches("^[-+]?\\d{1,19}\\.?\\d{1,19}$")) {

            /*
             This is more forgiving with ending dot input.
             If the last character given is a dot, it actually accepts it as 
             "£ [digit/s]"
             Chops off dot at the end.
             */
            if (valueString.charAt(valueString.length() - 1) == '.') {
                valueString = valueString.substring(0, valueString.length() - 1);
            }
            
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
             is detected in the argument input.
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
