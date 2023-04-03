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

public class SettingActivityView extends AppCompatActivity {
    private Button backButton;
    private Button confirmButton;
    private EditText editUserName ;
    private EditText editPhoneNumber;
    private EditText editEmail;
    private Boolean settingsChanged = false;

    private PlayerCollection myPlayerCollection = new PlayerCollection(null);
    private Switch geoSwitch ;

    /**

     This is the activity class for the settings page. It allows the user to update their profile information,
     including their username, phone number, email, and geolocation settings. The activity reads the current user's data
     from the database and displays it in the input fields. The user can update their information and save it to the database
     by clicking the "Confirm" button. If the user tries to choose a username that is already taken, an error message is displayed.
     The user's geolocation settings can be toggled on or off using a switch. If the switch is toggled, the user's geolocation
     setting is updated in the database.
     */
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
        PlayerModel myUser = (PlayerModel) intent.getSerializableExtra("myUser");

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
                UserStateModel us = UserStateModel.getInstance();
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

/**

 Sets an OnClickListener for the backButton. When the button is clicked, the method is called which creates an Intent
 containing information on whether settings have been changed and the modified myUser object. The Intent is passed back
 to the first activity using the setResult method and then the current activity is finished.
 @param view the view associated with the backButton
 */
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
    /**

    Sets up a listener for changes to the state of the geoSwitch button.
    When the switch is toggled, updates the user's geolocation setting in the database.
    @param geoSwitch the switch to listen for changes on
    @param myUser the user whose geolocation setting will be updated
    @param myPlayerCollection the database collection to update the user's information in
    */
        geoSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Do something when the switch's state changes
                if (isChecked) {
                    // The switch is ON
                    myUser.setGeoLocationSett(true);
                    UserStateModel us = UserStateModel.getInstance();
                    Map<String, Object> new_values = myPlayerCollection.createValues(us.getUserID(), myUser.getUserName(), myUser.getPhoneNumber(), myUser.getEmail(), myUser.getGeoLocationFlag(), Math.toIntExact(myUser.getTotalScore()), Math.toIntExact(myUser.getTotalQRCode()));
                    myPlayerCollection.update(us.getUserID(), new_values);
                } else {
                    // The switch is OFF
                    myUser.setGeoLocationSett(false);
                    UserStateModel us = UserStateModel.getInstance();
                    Map<String, Object> new_values = myPlayerCollection.createValues(us.getUserID(), myUser.getUserName(), myUser.getPhoneNumber(), myUser.getEmail(), myUser.getGeoLocationFlag(), Math.toIntExact(myUser.getTotalScore()), Math.toIntExact(myUser.getTotalQRCode()));
                    myPlayerCollection.update(us.getUserID(), new_values);
                }
            }
        });
    }

}