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

/**
 * QRCollection is a class that extends the Database_Controls abstract class.
 * It provides methods to interact with the 'qr_codes' collection in the Firestore database.
 * The class allows you to create, read, update, and delete QR codes, as well as check if a QR code exists.
 */
public class QRCollection extends Database_Controls{
    CollectionReference collection;

    /**
     * Constructor for QRCollection class, initializes the Firestore collection reference.
     *
     * @param db The instance of the Database class.
     */
    public QRCollection(Database db) {
        if (db == null)
        {
            db = Database.getInstance();
        }
        collection = db.getCollection("qr_codes");
    }


    /**
     * Adds a new QR code to the database with the given values.
     *
     * @param values A map containing field names as keys and their corresponding values.
     */
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

    /**
     * Reads the data for a QR code with the given QR_ID.
     *
     * @param QR_ID The QR code ID to be read.
     * @param onSuccess A consumer to handle the data on a successful read operation.
     * @param onError A consumer to handle exceptions on a failed read operation.
     */
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

    /**
     * Updates the data for a QR code with the given QR_ID.
     *
     * @param QR_ID The QR code ID to be updated.
     * @param values A map containing field names as keys and their corresponding new values.
     * @return A CompletableFuture that completes when the update operation finishes.
     */
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

    /**
     * Deletes the QR code with the given QR_ID from the database.
     *
     * @param QR_ID The QR code ID to be deleted.
     */
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

    /**
     * Creates a HashMap representing the fields in the QR code collection.
     *
     * @param QR_ID The unique identifier for the QR code.
     * @param name The name of the QR code.
     * @param points The points earned when scanning the QR code.
     * @param gem The gem ID associated with the QR code.
     * @return A HashMap containing the key-value pairs for the QR code fields.
     */
    public Map createValues(String QR_ID, String name, Integer points, gemID gem)
    {
        // This represents the fields in the player collection
        // can add or remove fields here
        Map <String, Object> values = new HashMap<>();
        values.put("qr_id", QR_ID);
        values.put("name", name);
        values.put("points", points);
        values.put("gem_id", gem);
        return values;
    }

    /**
     * Checks if a QR code with the specified QR_ID exists in the database.
     *
     * @param QR_ID The unique identifier for the QR code.
     * @return A CompletableFuture that completes with true if the QR code exists, false otherwise.
     */
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
