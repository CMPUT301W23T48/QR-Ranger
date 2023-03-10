package com.example.qrranger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256Hash {
    public static String hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashInBytes = md.digest(input.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashInBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        String input = "BFG5DGW54";
        System.out.println(hash(input + "\n"));
    }

}
