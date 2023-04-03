package com.example.qrranger;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.qrranger.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * The main activity for the QR Ranger application.
 * This activity initializes Firebase, handles user authentication,
 * and manages the bottom navigation menu for switching between app features.
 */
public class MainActivityController extends AppCompatActivity {

    private ActivityMainBinding binding;

    private FirebaseAuth mAuth;


    /**
     * Initializes the activity, sets up UI components, Firebase authentication,
     * and handles user sign-in and account creation.
     *
     * @param savedInstanceState Saved instance state bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        replaceFragment(new MapFragmentView());
        binding.navigator.setBackground(null);

        binding.navigator.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.map:
                    replaceFragment(new MapFragmentView());
                    break;
                case R.id.search:
                    replaceFragment(new SearchFragmentView());
                    break;
                case R.id.add:
                    Intent launchScanner = new Intent(getBaseContext(), QRScannerActivity.class);
                    startActivity(launchScanner);
                    break;
                case R.id.stat:
                    replaceFragment(new LeaderboardFragmentView());
                    break;
                case R.id.profile:
                    replaceFragment(new ProfileFragmentView());
                    break;
            }
            return true;
        });

        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInAnonymously:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                            // create user
                            PlayerCollection pc = new PlayerCollection(null);
                            CompletableFuture<Boolean> future = pc.checkUserExists(user.getUid());
                            future.thenAccept(userExists -> {
                                if (userExists) {
                                    System.out.println("User exists");
                                    // User exists so continue to launch profile screen
                                    UserStateModel us = UserStateModel.getInstance();
                                    us.setUserID(user.getUid());
                                } else {
                                    // FIRST TIME LOGIN
                                    System.out.println("User does not exist");
                                    // generate unique username and add default values to database
                                    CompletableFuture<String> usernameFuture = pc.generateUniqueUsername();
                                    usernameFuture.thenAccept(uniqueUsername -> {
                                        System.out.println("Unique Username Generated.");
                                        // Create the new player with a unique default username
                                        Map<String, Object> values = pc.createValues(user.getUid(), uniqueUsername, "Not Set", "Not Set", false, 0, 0);
                                        pc.create(values);
                                        UserStateModel us = UserStateModel.getInstance();
                                        us.setUserID(user.getUid());
                                    }).exceptionally(ex -> {
                                        // Handle the error
                                        System.out.println("Error Generating Unique Username.");
                                        return null;
                                    });
                                }
                            }).exceptionally(throwable -> {
                                System.out.println("Error checking user exists: " + throwable.getMessage());
                                return null;
                            });
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInAnonymously:failure", task.getException());
                            Toast.makeText(MainActivityController.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }

    /**
     * Called when the activity is starting.
     * Checks if the user is signed in and updates the UI accordingly.
     */
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    /**
     * Replaces the current fragment with the specified fragment.
     *
     * @param fragment The fragment to replace the current fragment with.
     */
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}