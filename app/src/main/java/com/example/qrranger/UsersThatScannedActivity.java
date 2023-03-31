package com.example.qrranger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UsersThatScannedActivity extends AppCompatActivity {
    private Button backButton;
    private ListView userList;
    private String qr_id;
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_that_scanned);

        Intent intent = getIntent();
        qr_id = intent.getStringExtra("qr_id");

        LinearLayout ll = findViewById(R.id.users_that_scanned_ll);
        backButton = ll.findViewById(R.id.users_that_scanned_back_button);
        userList = ll.findViewById(R.id.users_that_scanned_list);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        userList.setAdapter(adapter);

        PlayerCollection playerCollection = new PlayerCollection(null);

        playerCollection.getUsersWithQrId(qr_id)
                .thenAccept(usernames -> {
                    runOnUiThread(() -> populateUserList(usernames));
                })
                .exceptionally(error -> {
                    runOnUiThread(() -> {
                        // Handle error, for example, by showing a Toast message
                        Toast.makeText(UsersThatScannedActivity.this, "Error getting users: " + error, Toast.LENGTH_SHORT).show();
                    });
                    return null;
                });



        backButton.setOnClickListener(view -> finish());
    }



    /**
     * Populate the userList ListView with the names of the users.
     *
     * @param usernames List of usernames.
     */
    private void populateUserList(List<String> usernames) {
        adapter.clear();
        adapter.addAll(usernames);
        adapter.notifyDataSetChanged();
    }



}