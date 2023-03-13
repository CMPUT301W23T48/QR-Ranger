package com.example.qrranger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * Provided a String, this class will return the hash
 * equivalent of that String
 */
public class SHA256Hash {
    public static String hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashInBytes = md.digest(input.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashInBytes) {
                sb.append(String.format("%02x", b));
            }
            //Returns the String representation of the hash 
            //** Make sure to add "\n" to the end of the returned string to make give the desired hash**
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
