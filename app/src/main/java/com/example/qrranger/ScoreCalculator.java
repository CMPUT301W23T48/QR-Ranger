package com.example.qrranger;

import static com.example.qrranger.SHA256Hash.hash;

public class ScoreCalculator {

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
