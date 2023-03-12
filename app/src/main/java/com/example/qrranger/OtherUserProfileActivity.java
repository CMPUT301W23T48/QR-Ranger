package com.example.qrranger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OtherUserProfileActivity extends AppCompatActivity {

    private Button backButton;
    private TextView usernameView;
    private TextView userEmailView;
    private TextView userPhoneNumberView;
    private TextView rankView;
    private TextView totalScoreView;
    private TextView totalQRNumView;

    private Intent intent = getIntent();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otheruser);

        LinearLayout linearLayout = findViewById(R.id.OtherUserLL1);
        backButton = linearLayout.findViewById(R.id.OUBackButton);
        usernameView = linearLayout.findViewById(R.id.OUUserName);
        userEmailView = linearLayout.findViewById(R.id.OUEmail);
        userPhoneNumberView = linearLayout.findViewById(R.id.OUPhoneNumber);
        rankView = linearLayout.findViewById(R.id.OURank);
        totalScoreView = linearLayout.findViewById(R.id.OUTS);
        totalQRNumView = linearLayout.findViewById(R.id.OUQRNum);

        setViews();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void setViews(){
        
    }
}
