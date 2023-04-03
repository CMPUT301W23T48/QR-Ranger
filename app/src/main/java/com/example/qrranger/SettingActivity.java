package com.example.qrranger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class SettingActivity extends AppCompatActivity {
    private Button backButton;
    private Button confirmButton;
    private EditText editUserName ;
    private EditText editPhoneNumber;
    private EditText editEmail;
    private Boolean settingsChanged = false;

    private PlayerCollection myPlayerCollection = new PlayerCollection(null);
    private Switch geoSwitch ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_view);

        confirmButton =findViewById(R.id.SettingSubmitButton);
        backButton = findViewById(R.id.SettingBackButton);
        geoSwitch = findViewById(R.id.SettingSwitch);

        editPhoneNumber = findViewById(R.id.SettingPhoneNumInput);
        editUserName = findViewById(R.id.SettingUsernameInput);
        editEmail = findViewById(R.id.SettingEmailInput);

        Intent intent = getIntent();
        Player myUser = (Player) intent.getSerializableExtra("myUser");

        editUserName.setText(myUser.getUserName());
        editPhoneNumber.setText(myUser.getPhoneNumber());
        editEmail.setText(myUser.getEmail());

        myPlayerCollection.read(myUser.getPlayerId(), data -> {
            // player found so handle code using data here
            settingsChanged = Boolean.valueOf(Objects.requireNonNull(data.get("geolocation_setting")).toString());
            myUser.setGeoLocationSett(settingsChanged);
        }, error -> {
            // Player not found, cannot set values
            // perhaps change to default values for less chance of error during demo
            System.out.println("Error getting player data: " + error);
        });

        geoSwitch.setChecked(settingsChanged);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editUserName.getText().toString();
                // check that username unique

                PlayerCollection pc = new PlayerCollection(null);
                UserState us = UserState.getInstance();
                //check that username unique
                CompletableFuture<Boolean> futureUnique = pc.checkUsernameUnique(username);

                String phoneNumb = editPhoneNumber.getText().toString();
                myUser.setPhoneNumber(phoneNumb);


                String email = editEmail.getText().toString();
                myUser.setEmail(email);

                futureUnique.thenAccept(usernameUnique -> {
                    if (usernameUnique) {
                        System.out.println("Username Unique");
                        // Username unique so update database
                        myUser.setUserName(username);

                        Map<String, Object> new_values = pc.createValues(us.getUserID(), myUser.getUserName(), myUser.getPhoneNumber(), myUser.getEmail(), myUser.getGeoLocationFlag(), Math.toIntExact(myUser.getTotalScore()), Math.toIntExact(myUser.getTotalQRCode()));
                        pc.update(us.getUserID(), new_values);

                        Snackbar snackbar = Snackbar.make(view, "Saved", Snackbar.LENGTH_LONG);
                        // Show the Snack bar
                        snackbar.show();
                    } else {
                        // not unique so use a snackbar to inform the user
                        System.out.println("Username Not Unique");

                        Map<String, Object> new_values = pc.createValues(us.getUserID(), myUser.getUserName(), myUser.getPhoneNumber(), myUser.getEmail(), myUser.getGeoLocationFlag(), Math.toIntExact(myUser.getTotalScore()), Math.toIntExact(myUser.getTotalQRCode()));
                        pc.update(us.getUserID(), new_values);

                        Snackbar snackbar2 = Snackbar.make(view, "Username Already Taken.", Snackbar.LENGTH_LONG);
                        snackbar2.show();
                    }
                }).exceptionally(throwable -> {
                    System.out.println("Error checking username unique: " + throwable.getMessage());
                    return null;
                });
            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("dataChanged", settingsChanged);
                returnIntent.putExtra("myUser", myUser); // Pass the modified object back to the first activity
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });

        geoSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Do something when the switch's state changes
                if (isChecked) {
                    // The switch is ON
                    myUser.setGeoLocationSett(true);
                    UserState us = UserState.getInstance();
                    Map<String, Object> new_values = myPlayerCollection.createValues(us.getUserID(), myUser.getUserName(), myUser.getPhoneNumber(), myUser.getEmail(), myUser.getGeoLocationFlag(), Math.toIntExact(myUser.getTotalScore()), Math.toIntExact(myUser.getTotalQRCode()));
                    myPlayerCollection.update(us.getUserID(), new_values);
                } else {
                    // The switch is OFF
                    myUser.setGeoLocationSett(false);
                    UserState us = UserState.getInstance();
                    Map<String, Object> new_values = myPlayerCollection.createValues(us.getUserID(), myUser.getUserName(), myUser.getPhoneNumber(), myUser.getEmail(), myUser.getGeoLocationFlag(), Math.toIntExact(myUser.getTotalScore()), Math.toIntExact(myUser.getTotalQRCode()));
                    myPlayerCollection.update(us.getUserID(), new_values);
                }
            }
        });
    }

}