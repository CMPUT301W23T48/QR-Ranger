package com.example.qrranger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.HashMap;
import java.util.Map;

import com.example.qrranger.R;

public class ProfileFragment extends Fragment {
    Player myUser = new Player();
    TextView playerName;
    Map<String, Object> value = new HashMap<>();
    TextView playerPhoneNumb;
    TextView playerTotalScore;
    ImageView myAvatar;
    ImageButton mySettButton;

    PlayerCollection myPlayerCollection = new PlayerCollection(Database.getInstance());
    ImageButton QRCodes;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        Context my_context = getContext();
        String deviceId = Settings.Secure.getString(my_context.getContentResolver(), Settings.Secure.ANDROID_ID);

        playerName = view.findViewById(R.id.textView);
        playerPhoneNumb = view.findViewById(R.id.textView2);
        playerTotalScore = view.findViewById(R.id.textView3);


        myPlayerCollection.read(deviceId, data -> {
                    myUser.setUserName((String)data.get("UserName"));
                    myUser.setEmail((String)data.get("Email"));
                    myUser.setPhoneNumber((String)data.get("PhoneNumber"));
                    myUser.setPlayerId((String)data.get("PlayerID"));
                    myUser.setGeoLocationSett(((Boolean)data.get("Geolocation Setting")));
                    playerName.setText(myUser.getUserName());
                    playerPhoneNumb.setText(myUser.getPhoneNumber());
                    System.out.println("Data for user1: " + data);},
                error -> {
                    myUser.setUserName((deviceId));
                    myUser.setEmail("Please change your email in setting");
                    myUser.setPhoneNumber("Please change your phone number in setting");
                    myUser.setPlayerId(deviceId);
                    myUser.setGeoLocationSett(false);
                    value = myPlayerCollection.createValues(myUser.getUserName(), myUser.getPhoneNumber(), myUser.getEmail(), myUser.isGeoLocationSett(), 0,0);
                    myPlayerCollection.create(value);
                    playerName.setText(myUser.getUserName());
                    playerPhoneNumb.setText(myUser.getPhoneNumber());
                    System.out.println("Error getting player data: " + error);
                });


        playerTotalScore.setText("Your total score now is 0");

        myAvatar = view.findViewById(R.id.imageView);
        mySettButton = view.findViewById(R.id.imageButton);
        mySettButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                intent.putExtra("myUser", myUser);
                startActivityForResult(intent, 2);
            }
        });

        myAvatar.setImageDrawable(getResources().getDrawable(R.drawable.avatarpic));
        mySettButton.setImageDrawable(getResources().getDrawable(R.drawable.setting_pic));

        QRCodes = view.findViewById(R.id.imageButton3);
        QRCodes.setImageDrawable(getResources().getDrawable(R.drawable.images));
        QRCodes = view.findViewById(R.id.imageButton4);
        QRCodes.setImageDrawable(getResources().getDrawable(R.drawable.images1));
        ;
        QRCodes = view.findViewById(R.id.imageButton6);
        QRCodes.setImageDrawable(getResources().getDrawable(R.drawable.images2));

        QRCodes = view.findViewById(R.id.imageButton5);
        QRCodes.setImageDrawable(getResources().getDrawable(R.drawable.img_avatar2));
        QRCodes = view.findViewById(R.id.imageButton7);
        QRCodes.setImageDrawable(getResources().getDrawable(R.drawable.images));
        QRCodes = view.findViewById(R.id.imageButton8);
        QRCodes.setImageDrawable(getResources().getDrawable(R.drawable.images2));

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            // Retrieve the updated Player object from the returned intent
            Player updatedUser = (Player) data.getSerializableExtra("myUser");

            playerName.setText(updatedUser.getUserName());
            playerPhoneNumb.setText(updatedUser.getPhoneNumber());

            myUser.setUserName(updatedUser.getUserName());
            myUser.setPhoneNumber(updatedUser.getPhoneNumber());
        }
    }
}