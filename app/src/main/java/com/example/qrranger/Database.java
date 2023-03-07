package com.example.qrranger;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Database {

    private static Database single_instance = null;

    private FirebaseFirestore db;

    private Database(){
        db = FirebaseFirestore.getInstance();
    }

    public CollectionReference getCollection(String collection_name){
        return db.collection(collection_name);
    }

    public static synchronized Database getInstance()
    {
        if (single_instance == null)
        {
            single_instance = new Database();
        }
        return single_instance;
    }
}
