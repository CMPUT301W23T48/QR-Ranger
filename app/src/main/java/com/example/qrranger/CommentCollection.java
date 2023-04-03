package com.example.qrranger;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class CommentCollection extends Database_Controls{
    CollectionReference collection;

    public CommentCollection(Database db) {
        if (db == null) {
            db = Database.getInstance();
        }
        collection = db.getCollection("comments");
    }


    /**
     * Creates a new document in the Firestore collection with the provided values.
     *
     * @param values A Map containing the key-value pairs to store in the new document.
     */
    @Override
    void create(Map values) {
        collection.add(values).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "create(): Finished");
                } else {
                    Log.d(TAG, "create(): UnSuccessful");
                }
            }
        });
    }

    /**
     * Reads the document with the specified ID from the Firestore collection, invoking
     * onSuccess or onError accordingly.
     *
     * @param comment_ID The ID of the document to read.
     * @param onSuccess A Consumer to handle the retrieved data in case of a successful read.
     * @param onError   A Consumer to handle any exception that occurs during the read operation.
     */
    @Override
    void read(String comment_ID, Consumer<Map<String, Object>> onSuccess, Consumer<Exception> onError) {
        Query query = collection.whereEqualTo("comment_id", comment_ID);

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
     * Updates the specified document in the Firestore collection with the provided values.
     *
     * @param comment_ID     The ID of the document to update.
     * @param values A Map containing the key-value pairs to update in the document.
     * @return A CompletableFuture that completes when the update operation finishes.
     */
    @Override
    CompletableFuture<Void> update(String comment_ID, Map<String, Object> values) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        collection.whereEqualTo("comment_id", comment_ID)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.isEmpty()) {
                        future.completeExceptionally(new Exception("No comment found with username: " + comment_ID));
                    } else {
                        DocumentReference documentReference = queryDocumentSnapshots.getDocuments().get(0).getReference();
                        values.put("comment_id", comment_ID); // Add the username to the HashMap
                        documentReference.update(values)
                                .addOnSuccessListener(aVoid -> future.complete(null))
                                .addOnFailureListener(e -> future.completeExceptionally(e));
                    }
                })
                .addOnFailureListener(e -> future.completeExceptionally(e));
        return future;
    }

    /**
     * Deletes the specified document from the Firestore collection.
     *
     * @param docID The ID of the document to delete.
     */
    @Override
    void delete(String docID) {
        collection.document(docID).delete()
                .addOnSuccessListener(aVoid -> System.out.println("Document successfully deleted!"))
                .addOnFailureListener(e -> System.out.println("Error deleting document: " + e));
    }



    /**
     * Creates a HashMap representing the fields in the comments collection.
     *
     * @param QR_ID The unique identifier for the QR code that owns the comment.
     * @param author The name of the author who wrote the comment.
     * @param authorID The id of the author who wrote the comment.
     * @param comment The actual data of the comment.
     * @return A HashMap containing the key-value pairs for the QR code fields.
     */
    public Map createValues(String QR_ID, String author, String authorID, String comment)
    {
        // This represents the fields in the player collection
        // can add or remove fields here
        Map <String, Object> values = new HashMap<>();
        values.put("author_id", authorID);
        values.put("comment", comment);
        values.put("author", author);
        values.put("QR_ID", QR_ID);
        return values;
    }


    // might need:
    // add comment to qr_code
    // remove comment if username is the same
    // ^ might add id to check for id instead of username
}
