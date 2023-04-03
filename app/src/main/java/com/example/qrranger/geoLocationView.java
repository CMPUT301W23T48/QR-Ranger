package com.example.qrranger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class geoLocationView extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore;
    private String qr_id;
    private String name;

    private PlayerCollection playerCollection;
    private QRCollection qrCollection;
    private UserState state;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.geolocation_view);
        Button back = (Button) findViewById(R.id.back);


        Intent intent = getIntent();
        firebaseFirestore = FirebaseFirestore.getInstance();
        qr_id = intent.getStringExtra("qr_id");
        QRCollection qrc = new QRCollection(null);
        qrc.read(qr_id, data -> {
            name = data.get("qr_id").toString();

        }, error -> {
            // qr not found, cannot set values
            System.out.println("Error getting player data: " + error);
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {finish();}
        });
    }
}
