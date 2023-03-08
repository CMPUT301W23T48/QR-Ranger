package com.example.qrranger;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class PlayerCollection extends Database_Controls{

    CollectionReference collection;
    // Instantiating Database class with variable db
    public PlayerCollection(Database db) {
        if (db == null)
        {
            db = Database.getInstance();
        }
        collection = db.getCollection("players");
    }

    @Override
    void create(Map values) {
        // adds the given values into the database
        collection.add(values).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()){
                    Log.d(TAG, "onComplete: Finished");
                }
                else
                {
                    Log.d(TAG, "UnSuccessful");
                }
            }
        });
        // can be run with:
        // Map<String, Object> values;
        // values = createValues(...) can put null values
        // add(values)
    }

    @Override
    public void read(String username, Consumer<Map<String, Object>> onSuccess, Consumer<Exception> onError) {
        // returns
        Query query = collection.whereEqualTo("username", username);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    onSuccess.accept(document.getData());
                }
            } else {
                onError.accept(task.getException());
            }
        });
        // can be run with:
        // PlayerCollection pc = new PlayerCollection();
        // pc.read("user1", data -> {
        //      System.out.println("Data for user1: " + data); },
        //      error -> {
        //      System.out.println("Error getting player data: " + error);});
        // can change system.out.println to assign to a variable
    }

    @Override
    public CompletableFuture<Void> update(String username, Map<String, Object> newData) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        collection
                .whereEqualTo("username", username)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.isEmpty()) {
                        future.completeExceptionally(new Exception("No player found with username: " + username));
                    } else {
                        DocumentReference documentReference = queryDocumentSnapshots.getDocuments().get(0).getReference();
                        newData.put("username", username); // Add the username to the HashMap
                        documentReference.update(newData)
                                .addOnSuccessListener(aVoid -> future.complete(null))
                                .addOnFailureListener(e -> future.completeExceptionally(e));
                    }
                })
                .addOnFailureListener(e -> future.completeExceptionally(e));
        return future;
        // can be run with:
        // PlayerCollection pc = new PlayerCollection();
        // Map<String, Object> values;
        // values = createValues(...) // can be null
        // pc.update("user1", values, task -> {
        //    if (task.isSuccessful()) {
        //        System.out.println("Player data updated");
        //    } else {
        //        System.out.println("Error updating player data: " + task.getException());
        //    }
        //});
    }

    @Override
    public void delete(String username) {
        CollectionReference playerCollection = collection;

        Query query = playerCollection.whereEqualTo("username", username);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    playerCollection.document(document.getId()).delete();
                }
            } else {
                System.out.println("Error deleting player: " + task.getException());
            }
        });
        // can be run with:
        // PlayerCollection pc = new PlayerCollection();
        // pc.delete("user1");
    }

    // returns a map to be used for adding and updating
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
