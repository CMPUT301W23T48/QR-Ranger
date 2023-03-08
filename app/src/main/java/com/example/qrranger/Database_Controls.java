package com.example.qrranger;

import android.content.Context;
import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public abstract class Database_Controls {

    // add
    abstract void create(Map values);

    // get
    abstract void read(String username, Consumer<Map<String, Object>> onSuccess, Consumer<Exception> onError);

    abstract CompletableFuture<Void> update(String username, Map<String, Object> values);

    abstract void delete(String username);
}
