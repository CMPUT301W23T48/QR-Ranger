package com.example.qrranger;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private ImageView image;
    private TextView text;
    private byte[] qr;
    private Bitmap pic;
    @Override
    protected void onCreate(Bundle SavedInstancesBundle) {
        super.onCreate(SavedInstancesBundle);
        setContentView(R.layout.activity_add_qr);

        button = findViewById(R.id.button);
        text = findViewById(R.id.textView);
        image = findViewById(R.id.imageView2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scanIntent = new Intent(getBaseContext(), QRScannerActivity.class);
                startActivity(scanIntent);

                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    qr = (byte[]) extras.get("qr_code");
                    pic = (Bitmap) extras.get("image");

                    if(qr != null) {
                        text.setText(qr.toString());
                    }

                    if(image != null) {
                        image.setImageBitmap(pic);
                    }

                }
            }
        });


    }
}