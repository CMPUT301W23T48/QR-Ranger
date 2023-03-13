package com.example.qrranger;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


public class GemActivity extends AppCompatActivity {

    private TextView nameView;
    private TextView qrScore;
    private ImageView gemShape;
    private ImageView backgroundColor;
    private ImageView gemBorder;
    private ImageView gemLustre;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_gemview);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        // get the gem by the name


        ConstraintLayout cl = findViewById(R.id.gem_layout);
        nameView = cl.findViewById(R.id.gemName);
        nameView.setText(name);



    }


}
