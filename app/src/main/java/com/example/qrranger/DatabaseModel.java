package com.example.qrranger;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
/**
 * A singleton class for accessing the Firebase Firestore database.
 */
public class DatabaseModel {

    private static DatabaseModel single_instance = null;

    private FirebaseFirestore db;

    /**
     * Private constructor to ensure the singleton pattern is followed.
     */
    private DatabaseModel(){
        db = FirebaseFirestore.getInstance();
    }

    /**
     * Retrieves a Firestore collection reference by the provided name.
     *
     * @param collection_name The name of the collection to retrieve.
     * @return A CollectionReference object representing the specified Firestore collection.
     */
    public CollectionReference getCollection(String collection_name){
        return db.collection(collection_name);
    }

    /**
     * Provides access to the singleton instance of the Database class.
     *
     * @return The singleton instance of the Database class.
     */
    public static synchronized DatabaseModel getInstance()
    {
        if (single_instance == null)
        {
            single_instance = new DatabaseModel();
        }
        return single_instance;
    }
}
