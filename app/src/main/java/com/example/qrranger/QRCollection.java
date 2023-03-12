package com.example.qrranger;

import com.google.firebase.firestore.CollectionReference;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class QRCollection extends Database_Controls{
    CollectionReference collection;
    // Instantiating Database class with variable db
    public QRCollection(Database db) {
        if (db == null)
        {
            db = Database.getInstance();
        }
        collection = db.getCollection("qrcodes");
    }
    @Override
    void create(Map values) {
    }

    @Override
    void read(String username, Consumer<Map<String, Object>> onSuccess, Consumer<Exception> onError) {

    }

    @Override
    CompletableFuture<Void> update(String username, Map<String, Object> values) {
        return null;
    }

    @Override
    void delete(String username) {

    }
    public Map createValues(String username, String phoneNumber, String email, Boolean geolocation_setting, Integer totalScore, Integer totalQRCode)
    {
        // This represents the fields in the player collection
        // can add or remove fields here
        Map <String, Object> values = new HashMap<>();
        values.put("username", username);
        values.put("phoneNumber", phoneNumber);
        values.put("email", email);
        values.put("geolocation_setting", geolocation_setting);
        values.put("totalScore", totalScore);
        values.put("totalQRCode", totalQRCode);
        return values;
    }
}
