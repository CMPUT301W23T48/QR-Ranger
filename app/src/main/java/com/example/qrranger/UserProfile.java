package com.example.qrranger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class UserProfile extends AppCompatActivity {
    Player myUser = new Player("hluong1", "12345678910", "hluong1@ualberta.ca");
    TextView playerName;
    TextView playerPhoneNumb;
    TextView playerTotalScore;
    ImageView myAvatar;
    ImageButton mySettButton;

    ImageButton QRCodes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        playerName = findViewById(R.id.textView);
        playerPhoneNumb = findViewById(R.id.textView2);
        playerTotalScore = findViewById(R.id.textView3);

        playerName.setText(myUser.getUserName());
        playerPhoneNumb.setText(myUser.getPhoneNumber());
        playerTotalScore.setText("Your total score now is 0");

        myAvatar = findViewById(R.id.imageView);
        mySettButton = findViewById(R.id.imageButton);
        mySettButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfile.this, SettingActivity.class);
                intent.putExtra("Player", myUser);
                startActivityForResult(intent, 2);

            }
        });

        myAvatar.setImageDrawable(getResources().getDrawable(R.drawable.avatarpic));
        mySettButton.setImageDrawable(getResources().getDrawable(R.drawable.setting_pic));

        LinearLayout linearLayout = findViewById(R.id.linear_layout);
        QRCodes = linearLayout.findViewById(R.id.imageButton3);
        QRCodes.setImageDrawable(getResources().getDrawable(R.drawable.images));
        QRCodes = linearLayout.findViewById(R.id.imageButton4);
        QRCodes.setImageDrawable(getResources().getDrawable(R.drawable.images1));;
        QRCodes = linearLayout.findViewById(R.id.imageButton6);
        QRCodes.setImageDrawable(getResources().getDrawable(R.drawable.images2));

        linearLayout = findViewById(R.id.linear_layout2);
        QRCodes = linearLayout.findViewById(R.id.imageButton5);
        QRCodes.setImageDrawable(getResources().getDrawable(R.drawable.img_avatar2));
        QRCodes = linearLayout.findViewById(R.id.imageButton7);
        QRCodes.setImageDrawable(getResources().getDrawable(R.drawable.images));
        QRCodes = linearLayout.findViewById(R.id.imageButton8);
        QRCodes.setImageDrawable(getResources().getDrawable(R.drawable.images2));

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            // Retrieve the updated Player object from the returned intent
            Player updatedUser = (Player) data.getSerializableExtra("myUser");

            playerName.setText(updatedUser.getUserName());
            playerPhoneNumb.setText(updatedUser.getPhoneNumber());

            myUser.setUserName(updatedUser.getUserName()) ;
            myUser.setPhoneNumber( updatedUser.getPhoneNumber());
        }
    }
}