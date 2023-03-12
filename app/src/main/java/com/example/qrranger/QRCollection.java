package com.example.qrranger;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
        collection = db.getCollection("qr_codes");
    }


    @Override
    void create(Map values) {
        // adds the values in the Map to the database, key = field
        collection.add(values)
                .addOnSuccessListener(documentReference -> {
                    Log.d(TAG, "QR code added with ID: " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "Error adding QR code", e);
                });
    }


    @Override
    void read(String QR_ID, Consumer<Map<String, Object>> onSuccess, Consumer<Exception> onError) {
        // returns the data for a QR code with the given QR_ID
        Query query = collection.whereEqualTo("qr_id", QR_ID);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    onSuccess.accept(document.getData());
                }
            } else {
                onError.accept(task.getException());
            }
        });
    }

    @Override
    CompletableFuture<Void> update(String QR_ID, Map<String, Object> values) {
        // updates the data for the QR code with the given QR_ID
        CompletableFuture<Void> future = new CompletableFuture<>();
        collection
                .whereEqualTo("qr_id", QR_ID)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.isEmpty()) {
                        future.completeExceptionally(new Exception("No QR code found with qr_id: " + QR_ID));
                    } else {
                        DocumentReference documentReference = queryDocumentSnapshots.getDocuments().get(0).getReference();
                        values.put("qr_id", QR_ID); // Add the username to the HashMap
                        documentReference.update(values)
                                .addOnSuccessListener(aVoid -> future.complete(null))
                                .addOnFailureListener(e -> future.completeExceptionally(e));
                    }
                })
                .addOnFailureListener(e -> future.completeExceptionally(e));
        return future;
    }

    @Override
    void delete(String QR_ID) {
        // deletes the QR code, with QR_ID, from the database.
        Query query = collection.whereEqualTo("qr_id", QR_ID);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    collection.document(document.getId()).delete();
                }
            } else {
                System.out.println("Error deleting player: " + task.getException());
            }
        });
    }

    public Map createValues(String QR_ID, String name, String url, Integer points)
    {
        // This represents the fields in the player collection
        // can add or remove fields here
        Map <String, Object> values = new HashMap<>();
        values.put("qr_id", QR_ID);
        values.put("name", name);
        values.put("url", url);
        values.put("points", points);
        return values;
    }

    public CompletableFuture<Boolean> checkQRExists(String QR_ID) {
        // returns true if a QR code exists in the database, false otherwise
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Query query = collection.whereEqualTo("qr_id", QR_ID);
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    future.complete(true);
                } else {
                    future.complete(false);
                }
            } else {
                future.completeExceptionally(task.getException());
            }
        });
        return future;
    }
}
