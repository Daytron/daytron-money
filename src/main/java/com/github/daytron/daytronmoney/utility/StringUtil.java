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
 *
 * @author Ryan Gilera
 */
public class StringUtil {
    private StringUtil(){
    }
    
    /**
     * Reformats the text by removing any characters prior to value (including
     * the sign).
     * @param valueString
     * @return 
     */
    public static String removeSymbol(String valueString) {
        
        // Removes any symbol/characters with a space afterwards
        String[] removedSymbol = valueString.split("\\s");
        if (removedSymbol.length > 1) {
            valueString = removedSymbol[1];
        } else {
            valueString = removedSymbol[0];
        }

        // Removes any characters(ex. symbol or currency name) 
        // prior to sign or number
        StringBuilder filteredString = new StringBuilder();
        boolean beginAppend = false;
        for (int i = 0; i < valueString.length(); i++) {
            char character = valueString.charAt(i);

            if (!beginAppend) {
                if (character == '+' || character == '-'
                    || (Character.toString(character)).matches("[0-9]")) {
                        beginAppend = true;
                        // Include this character
                        filteredString.append(character); 
                }
            } else {
                filteredString.append(character);   
            }
        }
        
        return filteredString.toString();
    }
    
    /**
     * Reformats the text by removing all commas.
     * @param valueString A <code>String</code> input.
     * @return The newly formatted <code>String</code> value.
     */
    public static String removeCommas(String valueString){
        if (valueString.contains(",")) {
            valueString = valueString.replace(",", "");
        }
        
        return valueString;
    }
    
    public static long [] analyzeDecimalValue(String valueString) {
        final long[] result = new long[2];
        
        result[0] = countLeadingZeros(valueString);
        
        if (result[0] > 0) {
            valueString = valueString.substring((int)result[0]);
            valueString = normalizeTrailingZeros(valueString);
            result[1] = Long.valueOf(valueString);
        } else {
            valueString = normalizeTrailingZeros(valueString);
            if (valueString.length() == 1 && 
                    valueString.charAt(0) != '0') {
                valueString += "0";
            }
            result[1] = Long.valueOf(valueString);
        }
        
        return result;
    }
    
    private static String normalizeTrailingZeros(String valueString) {
        // default trim limit index 
        int indexOfCharacterTrimLimit = 0;
        for (int i = (valueString.length() - 1); i >= 0; i--) {
            char character = valueString.charAt(i);
            if (character != '0') {
                indexOfCharacterTrimLimit = i + 1;
                break;
            } 
        }
        
        valueString = valueString.substring(0, indexOfCharacterTrimLimit);
        
        if (valueString.isEmpty()) {
            valueString = "0";
        } 
        
        return valueString;
        
    }
    
    private static long countLeadingZeros(String valueString) {
        long count = 0;
        
        for (int i = 0; i < valueString.length(); i++) {
            char character = valueString.charAt(i);
            if (character == '0') {
                count += 1;
            } else {
                break;
            }
        }
        
        return count;
    }
    
    
}
