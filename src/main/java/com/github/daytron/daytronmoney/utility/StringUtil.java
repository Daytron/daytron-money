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
package com.github.daytron.daytronmoney.utility;

/**
 * Utility class for <code>String</code> formatting.
 * 
 * @author Ryan Gilera
 */
public class StringUtil {

    private StringUtil() {
    }

    /**
     * <p>Extracts any characters prior to value (including
     * the sign) and its value.
     * 
     * <p>Returns a <code>String</code> array as the result. The 
     * first element is the extracted currency code (could be an 
     * empty <code>String</code> if no extra characters found) while the
     * second element is the value.
     * 
     * @param valueString Input <code>String</code> to parse
     * @return <code>String</code> array
     */
    public static String[] parseAndRemoveCurrencyCode(String valueString) {
        String[] result = new String[2];
        valueString = valueString.trim();
        
        // Removes any symbol/characters with a space afterwards
        String[] removedSymbol = valueString.split("\\s");
        if (removedSymbol.length > 1) {
            result[0] = removedSymbol[0].toUpperCase();
            result[1] = removedSymbol[1];
        } else {
            // Removes any characters(ex. symbol or currency name) 
            // prior to sign or number
            StringBuilder filteredValue = new StringBuilder();
            StringBuilder filteredCode = new StringBuilder();
            boolean beginAppendValue = false;
            for (int i = 0; i < valueString.length(); i++) {
                char character = valueString.charAt(i);

                if (!beginAppendValue) {
                    if (character == '+' || character == '-'
                            || (Character.toString(character)).matches("[0-9]")) {
                        beginAppendValue = true;
                        // Include this character
                        filteredValue.append(character);
                    } else {
                        filteredCode.append(character);
                    }
                } else {
                    filteredValue.append(character);
                }
            }
            // Optional code parsed data
            // Possible empty string if no code detected in the input
            result[0] = filteredCode.toString().toUpperCase();
            
            result[1] = filteredValue.toString();
        }
        
        return result;
    }

    /**
     * Reformats the text by removing all commas.
     *
     * @param valueString A <code>String</code> input.
     * @return The newly formatted <code>String</code> without the commas.
     */
    public static String removeCommas(String valueString) {
        if (valueString.contains(",")) {
            valueString = valueString.replace(",", "");
        }

        return valueString;
    }

    /**
     * Parse and extracts leading zeros and the newly formatted 
     * <code>String</code>. Any leading and trailing zeros (excluding 
     * value is less than 10 can retain a single trailing zero) are analyzed and removed.
     * 
     * @param valueString Input <code>String</code> to parse
     * @return <code>long</code> array object
     */
    public static long[] analyzeDecimalValue(String valueString) {
        final long[] result = new long[2];

        result[0] = countLeadingZeros(valueString);

        valueString = removeAnyLeadingAndTrailingZeroes(valueString);
        result[1] = Long.valueOf(valueString);
        
        return result;
    }

    /**
     * Counts the number of leading zeros.
     * 
     * @param valueString Input <code>String</code> to parse
     * @return long value that represents the number of leading zeroes.
     */
    public static long countLeadingZeros(String valueString) {
        long count = 0;
        for (int i = 0; i < valueString.length(); i++) {
            char character = valueString.charAt(i);
            if (character == '0') {
                count += 1;
                
                // if it is all zeros until the last digit
                // then there's no leading zeroes
                if (i == (valueString.length() - 1)) {
                    count = 0;
                }
            } else {
                break;
            }
        }

        return count;
    }

    /**
     * Removes any leading zeroes. A single digit can retain one leading zero
     * if the value it represents is less than 10.
     * 
     * @param valueString Input <code>String</code> to format
     * @return <code>String</code> formatted text
     */
    public static String removeAnyLeadingZeroes(String valueString) {
        String strHelper = "";
        boolean thereIsStillLeadingZeroes = true;

        if (countLeadingZeros(valueString) > 0) {
            for (int i = 0; i < valueString.length(); i++) {
                char character = valueString.charAt(i);
                if (character == '0' && thereIsStillLeadingZeroes) {
                    continue;
                } else {
                    thereIsStillLeadingZeroes = false;
                }
                
                strHelper += Character.toString(character);
            }
            
            valueString = strHelper;
        }

        return valueString;
    }
    
    /**
     * Removes any leading zeroes. A single digit can retain one trailing zero
     * if the value it represents is in tens.
     * 
     * @param valueString Input <code>String</code> to format
     * @return <code>String</code> formatted text
     */
    public static String removeAnyTrailingZeroes(String valueString) {
        boolean itsAllZeroes = false;
        int lastZeroIndex = 0;
        
        for (int i = (valueString.length() -1); i >= 0; i--) {
            char character = valueString.charAt(i);
            if (character != '0') {
                // if i == 0 for example 0.50000
                // Then keep extra zero 
                // ex. 0.50000 == 0.50 not 0.5 which will result to 0.05 later on
                // in Money constructor
                if (i == 0) {
                    lastZeroIndex = i + 2;
                } else {
                    lastZeroIndex = i + 1;
                }
                
                break;
            }
            
            if (i == 0) {
                itsAllZeroes = true;
            }

        }
        
        if (itsAllZeroes) {
            return  "0";
        } else {
            valueString = valueString.substring(0, lastZeroIndex);
        }
        
        return valueString;
    }
    
    /**
     * Combines removing leading trailing methods in order. It is ideal to
     * remove trailing zeroes first before removing any leading zeroes.
     * 
     * @param value Input <code>String</code> to format
     * @return <code>String</code> formatted text
     */
    public static String removeAnyLeadingAndTrailingZeroes(String value) {
        String newString = removeAnyTrailingZeroes(value);
        return removeAnyLeadingZeroes(newString);
    }
    
    /**
     * Combines Money units into a <code>String</code> formate without the 
     * decimal point.
     * 
     * @param whole <code>long</code> value for whole units
     * @param decimal <code>long</code> value for decimal units
     * @param leadingZeroes <code>long</code> value for any leading zeroes
     * @return <code>String</code> formatted text
     */
    public static String combineValueIntoString(long whole, long decimal, long leadingZeroes) {
        String thisValueStr = Long.toString(whole);
        for (int j = 0; j < leadingZeroes; j++) {
            thisValueStr += "0";
        }
        thisValueStr += Long.toString(decimal);
        
        return thisValueStr;
    }

}
