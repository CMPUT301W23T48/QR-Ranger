package com.example.qrranger;


/**
 * A utility class for calculating scores based on a given hash string.
 */
public class ScoreCalculatorController {
    /**
     * Calculates a score based on the input hash string.
     * The score is determined by counting consecutive duplicate characters
     * and using their hexadecimal values as the base in a power function.
     *
     * @param hash The input hash string used for score calculation.
     * @return The calculated score as an Integer.
     */
    public static Integer calculateScore(String hash) {
         // Calculate score
        /* From: geeksforgeeks.org
         * At: https://www.geeksforgeeks.org/java-program-for-hexadecimal-to-decimal-conversion/
         * Author: mayur_patil https://auth.geeksforgeeks.org/user/mayur_patil/articles
         */
        char[] hashChars = hash.toCharArray();
        char prevChar = hashChars[0];
        int duplicates = 0, totalScore = 0, base = 0;
        for (int i = 1; i < hash.length(); i++) {
            if (hashChars[i] == prevChar) {
                duplicates += 1;
            }
            else if (duplicates != 0) {
                if (prevChar == '0') {
                    base = 20;
                }
                else if (prevChar >= '1' && prevChar <= '9') { // 1-9
                    base = prevChar - 48; // 2-9
                }
                else if (prevChar >= 'a' && prevChar <= 'g') { // a-g
                    base = prevChar - 87; // 10-16
                }
                totalScore += Math.pow(base, duplicates); // base^duplicates
                duplicates = 0;
            }
            prevChar = hashChars[i];
        }

        return totalScore;
    }

}
