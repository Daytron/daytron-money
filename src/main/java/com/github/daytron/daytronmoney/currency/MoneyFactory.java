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
import java.util.Arrays;
import java.util.Currency;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Ryan Gilera
 */
public class MoneyFactory {

    private static final String DEFAULT_CURRENCY_SYMBOL
            = Currency.getInstance(Locale.getDefault()).getSymbol(Locale.getDefault());
    private final String currencySymbol;

    public MoneyFactory() {
        this.currencySymbol = DEFAULT_CURRENCY_SYMBOL;
    }

    public MoneyFactory(String currencySymbol) {
        currencySymbol = currencySymbol.trim();
        Pattern p = Pattern.compile("\\d");
        Matcher m = p.matcher(currencySymbol);

        if (currencySymbol == null) {
            throw new IllegalArgumentException("Error! Symbol argument is null.");
        } else if (currencySymbol.isEmpty()) {
            throw new IllegalArgumentException("Error! Symbol argument is empty.");
        } else if (m.find()) {
            throw new IllegalArgumentException("Error! Symbol argument contains number.");
        }

        this.currencySymbol = currencySymbol;
    }

    public MoneyFactory(Locale locale) {
        this.currencySymbol = Currency.getInstance(locale).getSymbol(locale);
    }

    public MoneyFactory(Currency currency) {
        this.currencySymbol = currency.getSymbol();
    }

    public Money valueOf() {
        return new Money(currencySymbol, SignValue.Positive, 0, 0, 0);
    }

    public Money valueOf(long wholeUnit, long decimalUnit, 
            long leadingDecimalZeros) {
        return new Money(this.currencySymbol, SignValue.Positive, 
                wholeUnit, decimalUnit, leadingDecimalZeros);
    }

    public Money valueOf(String valueString) {
        valueString = valueString.trim();
        if (valueString == null || valueString.isEmpty()) {
            return null;
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

        return new Money(this.currencySymbol, sign, wholeUnit, decimalUnit, 
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
        
        valueString = StringUtil.removeSymbol(valueString);

        /*
         Regex conditions:
         ^   = Beginning of the line
         -?  = Optional negative symbol
         \d{1,} = 1 or more digits
         \.? = An optional dot (escaped, because in regex, . is a special character)
         \d* = 0 or more digits (the decimal part);
         $   = End of the line.
         */
        if (valueString.matches("^[-+]?\\d{1,12}\\.?\\d*$")) {

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

            
            System.out.println("value: " + valueString);
            String[] splitString = valueString.split("\\.");
            System.out.println("splitString: " + Arrays.toString(splitString));
            
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

        } else if (valueString.matches("^[-+]?\\d{1,}\\.?\\d*$")) {
            /*
             Throws an illegal argument exception if the whole numbers exceeds
             999 Billion (999,999,999,999.00).
             Long data type can hold upto 9,223,372,036,854,775,807 (19 digits).
             Limits input up to 12 whole numbers (999 Billion max), excluding 
             decimal digits if given.
             */
            throw new IllegalArgumentException("Invalid input. "
                    + "Value is too big! Can only accept up to "
                    + "999,999,999,999.99");
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
