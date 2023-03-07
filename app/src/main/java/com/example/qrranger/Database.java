package com.example.qrranger;

import android.content.Context;
import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Database {
    private FirebaseFirestore db;


    public Database(Context context) {
        this.db = FirebaseFirestore.getInstance();

    }

    public CollectionReference getCollection(String subCollection) {
        return this.db.collection(subCollection);
    }

    public FirebaseFirestore getDB() {
        return this.db;
    }
}
