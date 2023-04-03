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
    private boolean qrAdded;
    private QRCode qr;

    public QRGenerator() {
        qrCollection = new QRCollection(null);
        playerCollection = new PlayerCollection(null);
        qr = new QRCode("12345689");
        qrAdded = false;
    }

    public QRCode getQr() {
        return this.qr;
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
    public CompletableFuture<Void> generateQR(String qrData) {
        qrCollection = new QRCollection(null);


        // Generate a new QR if it doesn't already exist or pull the existing one from the db.
        CompletableFuture<Boolean> future = qrCollection.checkQRExists(qrData);
        CompletableFuture<Void> secondFuture = future.thenAccept(qrExists -> {
            if (qrExists) {
                // Pull existing QR from the DB.
                qrCollection.read(qrData, data -> {
                    String qrId = Objects.requireNonNull(data.get("qr_id").toString());
                    String name = Objects.requireNonNull(data.get("name").toString());
                    Integer points = (Integer) data.get("points");
                    gemID gem = (gemID) data.get("gem_id");

//                    qr = new QRCode(qrId, gem);
                    qr.setID(qrId);
                    qr.setName(name);
                    qr.setPoints(points);
                    qr.setGemId(gem);
                    qr.setGeoLocation("");
                }, error -> {
                    // Send error message.
                    Log.e(TAG, "Error loading QR database entry.");
                });
            }
            else {
                // QR doesn't exist, so generate a new one!
                String hash = SHA256Hash(qrData);

                gemID gem = new gemID();

                // Create the QRCode object.
                // qr = new QRCode(hash, qrData);
                qr.setID(hash);
                qr.setName(gem.gemName(qrData));
                qr.setPoints(QRCode.calculateScore(qrData));
                qr.setGemId(gem);
                qr.setGeoLocation("");

                // Add the new QR to the database:
                Map values = qrCollection.createValues(hash, qr.getName(), qr.getPoints(), qr.getGemID(), qr.getGeoLocation());
                qrCollection.create(values);
            }
        });
        return secondFuture;
    }

    /**
     *
     * @param qrId
     *      The ID of the QR Code to be added to the player's account.
     *
     * @throws IllegalArgumentException
     *      Thrown when QR being added is already linked to the account.
     */
    public boolean addQRToAccount(String qrId) throws IllegalArgumentException {

        playerCollection = new PlayerCollection(null);
        UserState state = UserState.getInstance();
        String userId = state.getUserID();

        playerCollection.read(userId, data -> {
            //Check if the qr is already added to the player's profile.
            ArrayList<String> codeList = Objects.requireNonNull((ArrayList<String>) data.get("qr_code_ids"));
            if(codeList.contains(qrId)) {
                qrAdded = false;
            }
            else {
                // Add the QR to the player database.
                playerCollection.add_QR_to_players(userId, qrId);
                qrAdded = true;
            }
        }, error -> {
            Log.e(TAG, "Error when reading Player from database.");
        });

        return qrAdded;
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
}
