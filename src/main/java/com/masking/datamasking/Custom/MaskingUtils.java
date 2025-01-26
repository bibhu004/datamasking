package com.masking.datamasking.Custom;

import java.util.Random;

public class MaskingUtils {

    private static final String[] nums = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    
    // Array of alphabetic characters (a-z, A-Z)
    private static final String[] characters = {
        "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", 
        "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", 
        "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };
    
    // Array of special characters
    private static final String[] splChar = {
        "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "_", "+", "=", "{", "}", "[", "]", "|", 
        ":", ";", "\"", "'", "<", ">", ",", ".", "?", "/"
    };

    private static Random random = new Random();

    // Method to generate a random index
    public static int getRandomIndex(int arrayLength) {
        return random.nextInt(arrayLength);
    }

    public static String maskSensitiveData(String data, boolean isAdmin) {

        if (isAdmin) {
            return data;  // If the user is an admin, return the data as-is
        } else {
            StringBuilder maskedData = new StringBuilder();
            
            for (char ch : data.toCharArray()) {
                if (Character.isDigit(ch)) {
                    maskedData.append(nums[getRandomIndex(nums.length)]);  // Mask digits with asterisks
                } else if (Character.isLetter(ch)) {
                    // Mask letters with asterisks, preserving case
                    maskedData.append(characters[getRandomIndex(characters.length)]);
                } else {
                    // Mask special characters with asterisks
                    maskedData.append(splChar[getRandomIndex(splChar.length)]);
                }
            }
            
            return maskedData.toString();
        }
    }
    
}
