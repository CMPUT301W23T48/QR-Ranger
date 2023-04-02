package com.example.qrranger;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class PlayerCollection extends Database_Controls {

    CollectionReference collection;

    /**
     * Constructor for PlayerCollection.
     * @param db Database instance, pass null to use the default instance.
     */
    public PlayerCollection(Database db) {
        if (db == null) {
            db = Database.getInstance();
        }
        collection = db.getCollection("players");
    }

    /**
     * Create a new player document in the Firestore collection.
     * @param values Map containing the field values for the new player document.
     */
    @Override
    void create(Map values) {
        // adds the given values into the database
        collection.add(values).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "onComplete: Finished");
                } else {
                    Log.d(TAG, "UnSuccessful");
                }
            }
        });
        // can be run with:
        // Map<String, Object> values;
        // PlayerCollection pc = new PlayerCollection(null)
        // values = createValues(...) can put null values
        // pc.create(values)
    }

    /**
     * Read a player document by userID.
     * @param userID The userID to search for.
     * @param onSuccess Callback to be executed on successful read.
     * @param onError Callback to be executed on error.
     */
    @Override
    public void read(String userID, Consumer<Map<String, Object>> onSuccess, Consumer<Exception> onError) {
        // returns the data for a user with the given userID
        Query query = collection.whereEqualTo("userID", userID);

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
        // PlayerCollection pc = new PlayerCollection(null);
        // pc.read(userID, data -> {
        //      // on success so code with data here
        //      System.out.println("Data for user1: " + data); },
        //      error -> {
        //      System.out.println("Error getting player data: " + error);});
        // can change system.out.println to assign to a variable
    }

    /**
     * Update an existing player document in the Firestore collection.
     * @param userID The userID of the document to update.
     * @param newData Map containing the updated field values.
     * @return CompletableFuture that resolves to null on completion, or exception on error.
     */
    @Override
    public CompletableFuture<Void> update(String userID, Map<String, Object> newData) {
        // returns null on completion of update, exception otherwise
        CompletableFuture<Void> future = new CompletableFuture<>();
        collection.whereEqualTo("userID", userID)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.isEmpty()) {
                        future.completeExceptionally(new Exception("No player found with username: " + userID));
                    } else {
                        DocumentReference documentReference = queryDocumentSnapshots.getDocuments().get(0).getReference();
                        newData.put("userID", userID); // Add the username to the HashMap
                        newData.remove("qr_code_ids");
                        documentReference.update(newData)
                                .addOnSuccessListener(aVoid -> future.complete(null))
                                .addOnFailureListener(e -> future.completeExceptionally(e));
                    }
                })
                .addOnFailureListener(e -> future.completeExceptionally(e));
        return future;
        // can be run with:
        // PlayerCollection pc = new PlayerCollection(null);
        // Map<String, Object> values;
        // values = createValues(...) // can be null
        // pc.update(userID, values, task -> {
        //    if (task.isSuccessful()) {
        //        System.out.println("Player data updated");
        //    } else {
        //        System.out.println("Error updating player data: " + task.getException());
        //    }
        //});
    }

    /**
     * Delete a player document from the Firestore collection by userID.
     * @param userID The userID of the document to delete.
     */
    @Override
    public void delete(String userID) {
        // deletes the document in the player collection that has the given userID
        Query query = collection.whereEqualTo("userID", userID);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    collection.document(document.getId()).delete();
                }
            } else {
                System.out.println("Error deleting player: " + task.getException());
            }
        });
        // can be run with:
        // PlayerCollection pc = new PlayerCollection();
        // pc.delete(userID);
    }

    /**
     * Create a map of field values for a new player document.
     * @param userID The userID for the new player.
     * @param username The username for the new player.
     * @param phoneNumber The phone number for the new player.
     * @param email The email for the new player.
     * @param geolocation_setting The geolocation setting for the new player.
     * @param totalScore The total score for the new player.
     * @param totalQRCode The total number of QR codes for the new player.
     * @return Map of field values for the new player document.
     */
    public Map createValues(String userID, String username, String phoneNumber, String email, Boolean geolocation_setting, Integer totalScore, Integer totalQRCode) {
        // This represents the fields in the player collection
        // can add or remove fields here
        Map<String, Object> values = new HashMap<>();
        values.put("userID", userID);
        values.put("username", username);
        values.put("phoneNumber", phoneNumber);
        values.put("email", email);
        values.put("geolocation_setting", geolocation_setting);
        values.put("totalScore", totalScore);
        values.put("totalQRCode", totalQRCode);
        values.put("qr_code_ids", new ArrayList<String>());
        return values;
    }

    /**
     * Check if a user exists by userID.
     * @param userID The userID to search for.
     * @return CompletableFuture that resolves to true if the user exists, false otherwise.
     */
    public CompletableFuture<Boolean> checkUserExists(String userID) {
        // returns true if a user exists, false otherwise
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Query query = collection.whereEqualTo("userID", userID);
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

    /**
     * Check if a username is unique.
     * @param username The username to check for uniqueness.
     * @return CompletableFuture that resolves to true if the username is unique, false otherwise.
     */
    public CompletableFuture<Boolean> checkUsernameUnique(String username) {
        // returns true if a user exists, false otherwise
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Query query = collection.whereEqualTo("username", username);
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    future.complete(false);
                } else {
                    future.complete(true);
                }
            } else {
                future.completeExceptionally(task.getException());
            }
        });
        return future;
    }

    /**
     * Generate a unique username for a new user.
     * @return CompletableFuture that resolves to a unique username.
     */
    public CompletableFuture<String> generateUniqueUsername() {
        // generates a unique username to use a default for a new user
        // asynchronous call as it counts the number of players in the database
        CompletableFuture<String> future = new CompletableFuture<>();
        collection.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot snapshot = task.getResult();
                int numPlayers = snapshot.size();
                String uniqueUsername = "User" + (numPlayers + 1);
                future.complete(uniqueUsername);
            } else {
                future.completeExceptionally(task.getException());
            }
        });
        return future;
    }


    /**
     * Add a QR code ID to a player's document in the player collection.
     * @param userID The userID of the player to add the QR code to.
     * @param QR_ID The QR code ID to add.
     */
    public void add_QR_to_players(String userID, String QR_ID) {
        // Use a query to find the player document with the matching userID field
        Query query = collection.whereEqualTo("userID", userID);
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // There should be only one document with the given userID field
                for (DocumentSnapshot document : task.getResult()) {
                    // Update the qr_code_ids field of the player document
                    ArrayList<String> qrCodeIds = (ArrayList<String>) document.get("qr_code_ids");
                    qrCodeIds.add(QR_ID);
                    document.getReference().update("qr_code_ids", qrCodeIds);
                }
            } else {
                // Handle errors here
            }
        });
    }
    // run with:
//    PlayerCollection pc = new PlayerCollection(null);
//    UserState us = UserState.getInstance();
//    String ID = us.getUserID();
//    pc.add_QR_from_players(ID, "test");


    /**
     * Deletes a QR code ID from a player's document in the player collection.
     *
     * @param userID The user ID.
     * @param QR_ID  The QR code ID.
     */
    public void delete_QR_from_players(String userID, String QR_ID) {
        // Use a query to find the player document with the matching userID field
        Query query = collection.whereEqualTo("userID", userID);
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // There should be only one document with the given userID field
                for (DocumentSnapshot document : task.getResult()) {
                    // Update the qr_code_ids field of the player document
                    ArrayList<String> qrCodeIds = (ArrayList<String>) document.get("qr_code_ids");
                    qrCodeIds.remove(QR_ID);
                    document.getReference().update("qr_code_ids", qrCodeIds);
                }
            } else {
                // Handle errors here
            }
        });
    }

    /**
     * Retrieves the rank of a player based on their user ID.
     *
     * @param userID The user ID of the player.
     * @return A CompletableFuture that will provide the player's rank based on their highest scoring QR code when completed.
     */
    public CompletableFuture<Integer> getPlayerRank(String userID) {
        CompletableFuture<Integer> futureRank = new CompletableFuture<>();

        Query query = collection.whereEqualTo("userID", userID);
        Database db = Database.getInstance();
        CollectionReference qrCodesCollection = db.getCollection("qr_codes");

        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (queryDocumentSnapshots.size() == 0) {
                futureRank.completeExceptionally(new IllegalArgumentException("Player not found with userID " + userID));
            } else {
                DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                Object qrCodeIdsObj = documentSnapshot.get("qr_code_ids");

                if (qrCodeIdsObj instanceof List) {
                    List<String> qrCodeIds = (List<String>) qrCodeIdsObj;

                    if (qrCodeIds.isEmpty()) {
                        futureRank.complete(-1);
                    } else {
                        List<Integer> pointsList = new ArrayList<>();
                        int totalQrCodes = qrCodeIds.size();
                        AtomicInteger processedQrCodes = new AtomicInteger();

                        for (String qrCodeId : qrCodeIds) {
                            Query qrCodeQuery = qrCodesCollection.whereEqualTo("qr_id", qrCodeId);
                            qrCodeQuery.get().addOnSuccessListener(qrCodeQueryDocumentSnapshots -> {
                                if (!qrCodeQueryDocumentSnapshots.isEmpty()) {
                                    DocumentSnapshot qrCodeDocument = qrCodeQueryDocumentSnapshots.getDocuments().get(0);
                                    Long qrCodePoints = qrCodeDocument.getLong("points");
                                    if (qrCodePoints == null) {
                                        pointsList.add(0);
                                    } else {
                                        pointsList.add(qrCodePoints.intValue());
                                    }
                                }
                                processedQrCodes.getAndIncrement();

                                if (processedQrCodes.get() == totalQrCodes) {
                                    int highestPoints = Collections.max(pointsList);
                                    Query higherPointsQuery = qrCodesCollection.whereGreaterThan("points", highestPoints);
                                    higherPointsQuery.get().addOnSuccessListener(higherPointsQueryDocumentSnapshots -> {
                                        int rank = higherPointsQueryDocumentSnapshots.size() + 1;
                                        futureRank.complete(rank);
                                    }).addOnFailureListener(e -> {
                                        futureRank.completeExceptionally(e);
                                    });
                                }
                            }).addOnFailureListener(e -> {
                                futureRank.completeExceptionally(e);
                            });
                        }
                    }
                } else {
                    futureRank.complete(-1);
                }
            }
        }).addOnFailureListener(e -> {
            futureRank.completeExceptionally(e);
        });

        return futureRank;
    }






    /**
     * Searches for a user with the given username and provides their data using a callback.
     *
     * @param username The username to search for.
     * @param onSuccess A Consumer that will be called with the user's data when the search is successful.
     * @param onError A Consumer that will be called with an exception when the search fails.
     */
    public void searchUser(String username, Consumer<Map<String, Object>> onSuccess, Consumer<Exception> onError) {
        // returns the data for a user with the given userID
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
    }

    /**
     * Searches for similar usernames in the Firestore collection and returns the results as a list of maps.
     * Each map contains the data of a matching user document.
     *
     * @param searchInput   The input string to search for similar usernames.
     * @param onSuccess     Callback to handle the success case. Takes a list of maps with the user data.
     * @param onFailure     Callback to handle the failure case. Takes a Throwable representing the error.
     */
    public void searchSimilarUsernames(String searchInput, OnSuccessListener<List<Map<String, Object>>> onSuccess, OnFailureListener onFailure) {
        Query query = collection.orderBy("username").startAt(searchInput).endAt(searchInput + "\uf8ff");
        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
            List<Map<String, Object>> users = new ArrayList<>();
            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                users.add(documentSnapshot.getData());
            }
            onSuccess.onSuccess(users);
        }).addOnFailureListener(onFailure);
    }


    /**
     * Retrieves the top 3 players sorted by their total score in descending order.
     *
     * @param onSuccess A Consumer that will be called with the top 3 players' data when the query is successful.
     * @param onError A Consumer that will be called with an exception when the query fails.
     */
    public void getTop6Players(Consumer<List<Map<String, Object>>> onSuccess, Consumer<Exception> onError) {
        // retrieve the top 3 players sorted by their total score in descending order
        Query query = collection.orderBy("totalScore", Query.Direction.DESCENDING).limit(6);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Map<String, Object>> top3Players = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    top3Players.add(document.getData());
                }
                onSuccess.accept(top3Players);
            } else {
                onError.accept(task.getException());
            }
        });
    }

    /**
     * Counts the total number of QR codes for a user with the given userID in the Firestore collection.
     * Returns the count as a CompletableFuture.
     *
     * @param userID        The unique identifier of the user for which to count the QR codes.
     * @return CompletableFuture<Integer> A CompletableFuture that resolves to the total count of QR codes for the user.
     * @throws IllegalArgumentException if the player is not found with the given userID.
     */
    public CompletableFuture<Integer> countTotalQRCodes(String userID) {
        CompletableFuture<Integer> futureTotal = new CompletableFuture<>();

        // Query for the player document with the given userID field
        Query query = collection.whereEqualTo("userID", userID);

        // Get the player's qr_code_ids from the document
        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (queryDocumentSnapshots.size() == 0) {
                // Player not found
                futureTotal.completeExceptionally(new IllegalArgumentException("Player not found with userID " + userID));
            } else {
                DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                List<String> qrCodeIds = (List<String>) documentSnapshot.get("qr_code_ids");
                int numberOfQRCodes = qrCodeIds != null ? qrCodeIds.size() : 0;
                documentSnapshot.getReference().update("totalQRCode", numberOfQRCodes);
                System.out.println("Number of QR Codes: " + numberOfQRCodes);
                futureTotal.complete(numberOfQRCodes);
            }
        }).addOnFailureListener(e -> {
            futureTotal.completeExceptionally(e);
        });

        return futureTotal;
    }

    /**
     * Get a list of users that have the specified QR code ID in their 'qr_code_ids' field.
     *
     * @param qr_id The QR code ID to search for.
     * @return A CompletableFuture that resolves to a list of user data maps containing user fields on successful query,
     *         or completes exceptionally with an exception on error.
     */
    public CompletableFuture<List<String>> getUsersWithQrId(String qr_id) {
        CompletableFuture<List<String>> future = new CompletableFuture<>();
        Query query = collection.whereArrayContains("qr_code_ids", qr_id);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<String> usernames = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String username = document.getString("username");
                    usernames.add(username);
                }
                future.complete(usernames);
            } else {
                future.completeExceptionally(task.getException());
            }
        });

        return future;
    }


    public void calcScore(String userID, Consumer<Integer> onSuccess, Consumer<Exception> onError) {
        // Get the player document with the given userID
        Query playerQuery = collection.whereEqualTo("userID", userID);
        Database db = Database.getInstance();
        CollectionReference qrCodeCollection = db.getCollection("qr_codes");

        playerQuery.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Get the player's qr_code_ids
                List<String> qrCodeIds = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    qrCodeIds = (List<String>) document.get("qr_code_ids");
                }

                // Fetch the QR codes with the given ids
                AtomicInteger score = new AtomicInteger(0);
                AtomicInteger counter = new AtomicInteger(qrCodeIds.size());

                for (String qrCodeId : qrCodeIds) {
                    qrCodeCollection.document(qrCodeId).get().addOnCompleteListener(qrCodeTask -> {
                        if (qrCodeTask.isSuccessful()) {
                            DocumentSnapshot qrCodeDocument = qrCodeTask.getResult();
                            if (qrCodeDocument.exists()) {
                                int points = qrCodeDocument.getLong("points").intValue();
                                score.addAndGet(points);
                            }

                            if (counter.decrementAndGet() == 0) {
                                onSuccess.accept(score.get());
                            }
                        } else {
                            onError.accept(qrCodeTask.getException());
                        }
                    });
                }
            } else {
                onError.accept(task.getException());
            }
        });
    }



}