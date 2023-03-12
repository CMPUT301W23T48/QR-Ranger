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
    TextView playerEmail;
    Map<String, Object> value = new HashMap<>();
    TextView playerPhoneNumb;
    TextView playerTotalScore;
    ImageView myAvatar;
    ImageButton mySettButton;

    PlayerCollection myPlayerCollection = new PlayerCollection(Database.getInstance());

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        Context my_context = getContext();
        String deviceId = Settings.Secure.getString(my_context.getContentResolver(), Settings.Secure.ANDROID_ID);

        playerEmail = view.findViewById(R.id.ProfileEmail);
        playerName = view.findViewById(R.id.ProfileUserName);
        //playerPhoneNumb = view.findViewById(R.id.textView2);
        playerTotalScore = view.findViewById(R.id.ProfileTS);

        myPlayerCollection.read("sdafdsfsad", data -> {
                    //value = data;
                    System.out.println("Data for user1: " + data);},
                error -> {
                    System.out.println("Error getting player data: " + error);
                });

    if(value != null) {
        myUser.setUserName((String) value.get("UserName"));
        myUser.setEmail((String) value.get("Email"));
        myUser.setPhoneNumber((String) value.get("PhoneNumber"));
        myUser.setPlayerId((String) value.get("PlayerID"));
        //myUser.setGeoLocationSett(((Boolean) value.get("Geolocation Setting")));
        playerName.setText(myUser.getUserName());
        //playerPhoneNumb.setText(myUser.getPhoneNumber());
    }else {
        myUser.setUserName((deviceId));
        myUser.setEmail("Please change your email in setting");
        myUser.setPhoneNumber("Please change your phone number in setting");
        myUser.setPlayerId(deviceId);
        myUser.setGeoLocationSett(false);
        value = myPlayerCollection.createValues(deviceId, myUser.getUserName(), myUser.getPhoneNumber(), myUser.getEmail(), myUser.isGeoLocationSett(), 0, 0);
        myPlayerCollection.create(value);
        playerName.setText(myUser.getUserName());
        //playerPhoneNumb.setText(myUser.getPhoneNumber());
    }
        playerTotalScore.setText("0");

        myAvatar = view.findViewById(R.id.ProfileImage);
        mySettButton = view.findViewById(R.id.ProfileSettingButton);
        mySettButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                intent.putExtra("myUser", myUser);
                startActivityForResult(intent, 2);
            }
        });

        myAvatar.setImageDrawable(getResources().getDrawable(R.drawable.ub_profile));
        mySettButton.setImageDrawable(getResources().getDrawable(R.drawable.setting));

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            // Retrieve the updated Player object from the returned intent
            Player updatedUser = (Player) data.getSerializableExtra("myUser");

            playerName.setText(updatedUser.getUserName());
            //playerPhoneNumb.setText(updatedUser.getPhoneNumber());

            myUser.setUserName(updatedUser.getUserName());
            myUser.setPhoneNumber(updatedUser.getPhoneNumber());
        }
    }
}