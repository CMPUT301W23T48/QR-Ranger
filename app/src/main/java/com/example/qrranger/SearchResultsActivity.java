package com.example.qrranger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchResultsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        ListView listView = findViewById(R.id.search_results_list_view);
        Button backButton = findViewById(R.id.search_results_back_button);

        // Retrieve the similar usernames list passed from SearchFragment
        List<Map<String, Object>> similarUsernames = (List<Map<String, Object>>) getIntent().getSerializableExtra("similarUsernames");

        // Convert the list of maps to a list of usernames
        List<String> usernames = new ArrayList<>();
        for (Map<String, Object> user : similarUsernames) {
            usernames.add(user.get("username").toString());
        }

        // Set up the ListView to display the similar usernames
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, usernames);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String username = adapterView.getItemAtPosition(i).toString();
                PlayerCollection pc = new PlayerCollection(null);
                pc.searchUser(username, data -> {
                    if (data != null){
                        System.out.println("Clicked User ID: " + data.get("userID"));
                        // use data to send as intent to next activity
                        startOtherUserProfileActivity(data);
                    }
                }, error -> {
                    System.out.println("Error: User does not exist");
                });
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
/**

 This method starts the OtherUserProfileActivity with the details of the user whose data is passed as a parameter.
 @param data a Map containing the details of the user whose profile is being accessed. The keys for the Map are:
 **/
    private void startOtherUserProfileActivity(Map<String, Object> data){
        Intent intent = new Intent(SearchResultsActivity.this, OtherUserProfileActivity.class);
        intent.putExtra("username", data.get("username").toString());
        intent.putExtra("email", data.get("email").toString());
        intent.putExtra("phoneNumber", data.get("phoneNumber").toString());
        intent.putExtra("userID", data.get("userID").toString());
        intent.putExtra("totalQRCode", data.get("totalQRCode").toString());
        intent.putExtra("totalScore", data.get("totalScore").toString());
        intent.putExtra("qr_code_ids", (ArrayList<String>)data.get("qr_code_ids"));
        startActivity(intent);
    }
}

