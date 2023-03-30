package com.example.qrranger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class SettingActivity extends AppCompatActivity {
    private Button backButton;
    private Button confirmButton;
    private EditText editUserName ;
    private EditText editPhoneNumber;
    private EditText editEmail;
    private Boolean settingsChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        confirmButton =findViewById(R.id.SettingSubmitButton);
        backButton = findViewById(R.id.SettingBackButton);

        editPhoneNumber = findViewById(R.id.SettingPhoneNumInput);
        editUserName = findViewById(R.id.SettingUsernameInput);
        editEmail = findViewById(R.id.SettingEmailInput);

        Intent intent = getIntent();
        Player myUser = (Player) intent.getSerializableExtra("myUser");

        editUserName.setText(myUser.getUserName());
        editPhoneNumber.setText(myUser.getPhoneNumber());
        editEmail.setText(myUser.getEmail());

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
                        settingsChanged = true;

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
    }

}