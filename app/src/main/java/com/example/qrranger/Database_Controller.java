package com.example.qrranger;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
/**
 * An abstract class providing basic CRUD (Create, Read, Update, Delete) operations
 * for Firestore collections.
 */
public abstract class Database_Controller {

    /**
     * Creates a new document in the Firestore collection with the provided values.
     *
     * @param values A Map containing the key-value pairs to store in the new document.
     */
    abstract void create(Map values);

    /**
     * Reads the document with the specified ID from the Firestore collection, invoking
     * onSuccess or onError accordingly.
     *
     * @param ID       The ID of the document to read.
     * @param onSuccess A Consumer to handle the retrieved data in case of a successful read.
     * @param onError   A Consumer to handle any exception that occurs during the read operation.
     */
    abstract void read(String ID, Consumer<Map<String, Object>> onSuccess, Consumer<Exception> onError);

    /**
     * Updates the specified document in the Firestore collection with the provided values.
     *
     * @param ID     The ID of the document to update.
     * @param values A Map containing the key-value pairs to update in the document.
     * @return A CompletableFuture that completes when the update operation finishes.
     */
    abstract CompletableFuture<Void> update(String ID, Map<String, Object> values);

    /**
     * Deletes the specified document from the Firestore collection.
     *
     * @param ID The ID of the document to delete.
     */
    abstract void delete(String ID);
}
