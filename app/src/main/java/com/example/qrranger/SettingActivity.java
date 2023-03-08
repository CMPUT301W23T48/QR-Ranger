package com.example.qrranger;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class SettingActivity extends AppCompatActivity {
    Button backButton;
    Button confirmButton;
    EditText editUserName ;
    EditText editContactInfo ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        LinearLayout linearLayout = findViewById(R.id.my_layout1);
        confirmButton = linearLayout.findViewById(R.id.button7);

        editContactInfo = findViewById(R.id.edit_text2);
        editUserName = findViewById(R.id.edit_text1);

        Intent intent = getIntent();
        Player myUser = (Player) intent.getSerializableExtra("Player");

        editUserName.setText(myUser.getUserName());
        editContactInfo.setText(myUser.getPhoneNumber());

        String phoneNumb = editContactInfo.getText().toString();
        String userName = editUserName.getText().toString();

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumb = editContactInfo.getText().toString();
                String userName = editUserName.getText().toString();
                myUser.setPhoneNumber(phoneNumb);
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


        backButton = findViewById(R.id.button3);
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