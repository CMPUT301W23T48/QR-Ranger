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

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import com.example.qrranger.R;

public class ProfileFragment extends Fragment {
    Player myUser = new Player();
    private TextView playerName;
    private TextView playerEmail;
    private Map<String, Object> value = new HashMap<>();
    private TextView playerPhoneNumb;
    private TextView playerTotalScore;
    private TextView playerTotalQRCodes;
    private TextView profileRank;
    private ImageView myAvatar;
    private ImageButton mySettButton;
    private Intent data;

    private ActivityResultLauncher<Intent> startSettingsForResult =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null) {
                                boolean dataChanged = data.getBooleanExtra("dataChanged", false);
                                if (dataChanged) {
                                    System.out.println("Data changed");
                                    myUser = (Player) data.getSerializableExtra("myUser");
                                    setViews();
                                }
                            }
                        }
                    });

    PlayerCollection myPlayerCollection = new PlayerCollection(Database.getInstance());

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        playerEmail = view.findViewById(R.id.ProfileUserEmail);
        playerName = view.findViewById(R.id.ProfileUserName);
        playerPhoneNumb = view.findViewById(R.id.ProfileUserPhoneNumber);
        playerTotalScore = view.findViewById(R.id.ProfileTS);
        playerTotalQRCodes = view.findViewById(R.id.ProfileQRNum);
        mySettButton = view.findViewById(R.id.ProfileSettingButton);
        profileRank = view.findViewById(R.id.ProfileRank);

        UserState us = UserState.getInstance();
        String userID = us.getUserID();

        CompletableFuture<Boolean> future = myPlayerCollection.checkUserExists(userID);
        future.thenAccept(userExists -> {
                    if (userExists) {
                        // User exists
                        System.out.println("User exists");
                        setValues(userID);
                    } else {
                        // User does not exist, handle errors
                    }

                });

        mySettButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSettingsActivity();
            }
        });

        return view;
    }
    public void setValues(String userID){
        // get data from userID
        myPlayerCollection.read(userID, data -> {
            // player found so handle code using data here
            final Map values = data;
            System.out.println("Data for user: " + values);
            myUser.setUserName(Objects.requireNonNull(data.get("username")).toString());
            myUser.setEmail(Objects.requireNonNull(data.get("email")).toString());
            myUser.setPhoneNumber(Objects.requireNonNull(data.get("phoneNumber")).toString());
            myUser.setTotalScore(((Long) data.get("totalScore")));
            myUser.setTotalQRCode(((Long) data.get("totalQRCode")));
            myUser.setGeoLocationSett((Boolean) data.get("geolocation_setting"));
            myUser.setPlayerId(userID);
            myUser.setQrCodeCollection((ArrayList<QRCode>) data.get("qr_code_ids"));
            getAndSetRank(userID);
            System.out.println("Setting views");
            setViews();
            }, error -> {
                // Player not found, cannot set values
                // perhaps change to default values for less chance of error during demo
                System.out.println("Error getting player data: " + error);
        });

    }

    public void setViews(){
        playerName.setText(myUser.getUserName());
        playerEmail.setText(myUser.getEmail());
        playerPhoneNumb.setText(myUser.getPhoneNumber());
        playerTotalScore.setText(myUser.getTotalScore().toString());
        playerTotalQRCodes.setText(myUser.getTotalQRCode().toString());
    }

    private void startSettingsActivity() {
        Intent intent = new Intent(getActivity(), SettingActivity.class);
        intent.putExtra("myUser", myUser); // pass the user data to the settings activity
        startSettingsForResult.launch(intent);
    }

    public void getAndSetRank(String userID){
        CompletableFuture<Integer> rankFuture = myPlayerCollection.getPlayerRank(userID);
        rankFuture.thenAccept(rank -> {
            System.out.println("Player rank: " + rank);
            profileRank.setText(rank.toString());
        }).exceptionally(e -> {
            System.err.println("Failed to get player rank: " + e.getMessage());
            return null;
        });
    }
}