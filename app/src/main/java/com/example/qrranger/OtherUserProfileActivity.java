package com.example.qrranger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class OtherUserProfileActivity extends AppCompatActivity {

    Button backButton;
    TextView usernameView;
    TextView userEmailView;
    TextView userPhoneNumberView;
    TextView rankView;
    TextView totalScoreView;
    TextView totalQRNumView;
    ListView qrList;
    Player myUser = new Player();


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
        qrList = linearLayout.findViewById(R.id.OUQR_list_view);

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
        myUser.setUserName(intent.getStringExtra("username"));

        userEmailView.setText(intent.getStringExtra("email"));
        myUser.setEmail(intent.getStringExtra("email"));

        userPhoneNumberView.setText(intent.getStringExtra("phoneNumber"));
        myUser.setPhoneNumber(intent.getStringExtra("phoneNumber"));

        totalScoreView.setText(intent.getStringExtra("totalScore"));
//        myUser.setTotalScore(intent.getStringExtra("totalScore"));

        totalQRNumView.setText(intent.getStringExtra("totalQRCode"));
//        myUser.setTotalQRCode(intent.getStringExtra("totalQRCode"));
        myUser.setPlayerId(intent.getStringExtra("userID"));
        myUser.setQrCodeCollection(intent.getStringArrayListExtra("qr_code_ids"));

//        getAndSetList(myUser);
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

//    public void getAndSetList(Player user){
//        OtherUserProfileActivity.this.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                // code that modifies the adapter
//                ArrayList<String> qrCodeCollection = user.getQrCodeCollection();
//                ArrayList<String> qrNames = new ArrayList<>();
//                QRCollection qrc = new QRCollection(null);
//                for (String qrCode : qrCodeCollection) {
//                    qrc.read(qrCode, data -> {
//                        // qr found
//                        qrNames.add(data.get("name").toString());
//                        if (qrNames.size() == qrCodeCollection.size()) {
//                            // All QR names retrieved, update list view
//                            ArrayAdapter<String> adapter = new ArrayAdapter<>(OtherUserProfileActivity.this,
//                                    android.R.layout.simple_list_item_1, qrNames);
//                            qrList.setAdapter(adapter);
//                        }
//                    }, error -> {
//                        // qr not found, cannot set values
//                        System.out.println("Error getting player data: " + error);
//                    });
//
//                }
//            }
//        });
//
//    }

}
