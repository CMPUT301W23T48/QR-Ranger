package com.example.qrranger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class SettingActivity extends AppCompatActivity {
    Button backButton;
    Button confirmButton, confirmButton2;
    EditText editUserName ;
    EditText editContactInfo ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        LinearLayout linearLayout = findViewById(R.id.SettingLL3);
        confirmButton = linearLayout.findViewById(R.id.SettingUsernameSubmitButton);
        LinearLayout linearLayout1 = findViewById(R.id.SettingLL5);
        confirmButton2 = linearLayout1.findViewById(R.id.SettingContactSubmitButton);

        editContactInfo = findViewById(R.id.SettingContactInput);
        editUserName = findViewById(R.id.SettingUsernameInput);

        Intent intent = getIntent();
        Player myUser = (Player) intent.getSerializableExtra("myUser");

        editUserName.setText(myUser.getUserName());
        editContactInfo.setText(myUser.getPhoneNumber());

        String phoneNumb = editContactInfo.getText().toString();
        String userName = editUserName.getText().toString();

        confirmButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumb = editContactInfo.getText().toString();
                myUser.setPhoneNumber(phoneNumb);
                Snackbar snackbar = Snackbar.make(view, "Saved", Snackbar.LENGTH_LONG);
                // Add an action to the Snackbar
                snackbar.setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // This is called when the "OK" button on the Snackbar is clicked
                    }
                });

                // Show the Snackbar
                snackbar.show();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = editUserName.getText().toString();
                myUser.setUserName(userName);
                Snackbar snackbar = Snackbar.make(view, "Saved", Snackbar.LENGTH_LONG);

                // Add an action to the Snackbar
                snackbar.setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // This is called when the "OK" button on the Snackbar is clicked
                    }
                });

                // Show the Snackbar
                snackbar.show();
            }
        });


        backButton = findViewById(R.id.SettingBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("myUser", myUser); // Pass the modified object back to the first activity
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });

    }
}