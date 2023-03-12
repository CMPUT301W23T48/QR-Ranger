package com.example.qrranger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class SearchFragment extends Fragment {
    EditText searchInput;
    ImageButton confirmButton;
    PlayerCollection pc = new PlayerCollection(null);



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        confirmButton = view.findViewById(R.id.SearchSubmitButton);
        searchInput = view.findViewById(R.id.SearchInput);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("button clicked");

                String user_search = searchInput.getText().toString();
                // launch next activity to display that user's profile if they exist
                pc.searchUser(user_search, data -> {
                    // player found so handle code using data here
                    if (data != null){
                        System.out.println("Search User ID: " + data.get("userID"));
                        // use data to send as intent to next activity
                        startOtherUserProfile(data);
                    }
                    else
                    {
                        // handle error
                        System.out.println("Error User Does Not Exist");
                    }

                }, error -> {
                    // Player not found, cannot display user
                    // put a toast here saying user doesn't exist
                    System.out.println("Error getting player data: " + error);
                });

            }
        });

        return view;
    }

    private void startOtherUserProfile(Map<String,Object> data) {
        // send the info in data as intent for the new activity
        Intent intent = new Intent(getActivity(), OtherUserProfileActivity.class);
        intent.putExtra("username", data.get("username").toString());
        intent.putExtra("email", data.get("email").toString());
        intent.putExtra("phoneNumber", data.get("phoneNumber").toString());
        intent.putExtra("userID", data.get("userID").toString());
        intent.putExtra("totalQRCode", data.get("totalQRCode").toString());
        intent.putExtra("totalScore", data.get("totalScore").toString());
//        intent.putExtra("qr_code_ids", data.get("qr_code_ids"));
        startActivity(intent);
    }


}