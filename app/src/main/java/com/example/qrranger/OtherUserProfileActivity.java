package com.example.qrranger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.CompletableFuture;

public class OtherUserProfileActivity extends AppCompatActivity {

    Button backButton;
    TextView usernameView;
    TextView userEmailView;
    TextView userPhoneNumberView;
    TextView rankView;
    TextView totalScoreView;
    TextView totalQRNumView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otheruser);
        Intent intent = getIntent();

        LinearLayout linearLayout = findViewById(R.id.OtherUserLL1);
        backButton = linearLayout.findViewById(R.id.OUBackButton);
        usernameView = linearLayout.findViewById(R.id.OUUserName);
        userEmailView = linearLayout.findViewById(R.id.OUUserEmail);
        userPhoneNumberView = linearLayout.findViewById(R.id.OUUserPhoneNumber);
        rankView = linearLayout.findViewById(R.id.OURank);
        totalScoreView = linearLayout.findViewById(R.id.OUTS);
        totalQRNumView = linearLayout.findViewById(R.id.OUQRNum);

        setViews(intent);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void setViews(Intent intent) {
        usernameView.setText(intent.getStringExtra("username"));
        userEmailView.setText(intent.getStringExtra("email"));
        userPhoneNumberView.setText(intent.getStringExtra("phoneNumber"));
        totalScoreView.setText(intent.getStringExtra("totalScore"));
        totalQRNumView.setText(intent.getStringExtra("totalQRCode"));
        getAndSetRank(intent.getStringExtra("userID"));
    }

    public void getAndSetRank(String userID) {
        PlayerCollection pc = new PlayerCollection(null);
        CompletableFuture<Integer> rankFuture = pc.getPlayerRank(userID);
        rankFuture.thenAccept(rank -> {
            System.out.println("Player rank: " + rank);
            rankView.setText(rank.toString());
        }).exceptionally(e -> {
            System.err.println("Failed to get player rank: " + e.getMessage());
            return null;
        });
    }
}
