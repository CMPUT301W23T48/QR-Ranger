package com.example.qrranger;

import static android.content.ContentValues.TAG;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * Generates QR Codes from their scan data.
 * Also responsible for saving QRCodes to the database and linking them to the user profile.
 */
public class QRGenerator {
    private QRCollection qrCollection;
    private PlayerCollection playerCollection;
    private gemID gemRepresentation;

    private QRCode qr;

    public QRGenerator() {
        qrCollection = new QRCollection(null);
        playerCollection = new PlayerCollection(null);
        gemRepresentation = new gemID();
        qr = new QRCode("None", "None", "None");
    }

    /**
     * Checks if the QR exists in the database:
     * - If it does, pull it and populate the QR.
     * - If it does not, create a new QR and add it to the database.
     *
     * @param qrData
     *      The string of bytes retrieved from the QR scan.
     *
     * @return
     *      The generated or retrieved QRCode instance.
     *
     */
    public QRCode generateQR(String qrData) {
        qrCollection = new QRCollection(null);

        // Generate a new QR if it doesn't already exist or pull the existing one from the db.
        CompletableFuture<Boolean> future = qrCollection.checkQRExists(qrData);
        future.thenAccept(qrExists -> {
            if (qrExists) {
                // Pull existing QR from the DB.
                qrCollection.read(qrData, data -> {
                    String qrId = Objects.requireNonNull(data.get("qr_id").toString());
                    String name = Objects.requireNonNull(data.get("name").toString());
                    String url = Objects.requireNonNull(data.get("url").toString());
                    Integer points = (Integer) data.get("points");

                    qr = new QRCode(qrId, name, url);
                    qr.setPoints(points);
                }, error -> {
                    // Send error message.
                    Log.e(TAG, "Error loading QR database entry.");
                });
            }
            else {
                // QR doesn't exist, so generate a new one!
                String hash = SHA256Hash(qrData);
                Integer points = calculateScore(hash);
                gemRepresentation = new gemID();
                String name = "Placeholder!";

                // Create the QRCode object.
                qr = new QRCode(hash, name, qrData);
                qr.setPoints(points);

                // Add the new QR to the database:
                Map values = qrCollection.createValues(hash, name, qrData, points);
                qrCollection.create(values);
            }
        });

        return qr;
    }

    /**
     *
     * @param qrId
     *      The ID of the QR Code to be added to the player's account.
     *
     * @throws IllegalArgumentException
     *      Thrown when QR being added is already linked to the account.
     */
    public void addQRToAccount(String qrId) throws IllegalArgumentException {

        playerCollection = new PlayerCollection(null);
        UserState state = UserState.getInstance();
        String userId = state.getUserID();

        playerCollection.read(userId, data -> {
            //Check if the qr is already added to the player's profile.
            ArrayList<String> codeList = Objects.requireNonNull((ArrayList<String>) data.get("qr_code_ids"));
            if(codeList.contains(qrId)) {
                throw new IllegalArgumentException("QR Code is already in account!");
            }
            else {
                // Add the QR to the player database.
                playerCollection.add_QR_to_players(userId, qrId);
            }
        }, error -> {
            Log.e(TAG, "Error when reading Player from database.");
        });
    }

    /**
     * SHA256 Hashing algorithm.
     *
     * @See SHA256Hash
     *      Written by Jefferson Fong and refactored here.
     *
     * @param input
     *      The input string to be hashed.
     *
     * @return
     *      The SHA256 hash value of the input string.
     */
    private static String SHA256Hash(String input) {
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

    /**
     * Calculates the score of a QR code given it's ID (hashcode).
     *
     * @See ScoreCalculator
     *      Written by Jefferson Fong and refactored here.
     *
     * @param hash
     *      The SHA256 hashcode from which the score is based.
     *
     * @return
     *      The score value calculated for the given hash.
     */
    private static Integer calculateScore(String hash) {
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
