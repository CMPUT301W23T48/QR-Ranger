package com.example.qrranger;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import com.google.firebase.firestore.FirebaseFirestore;

import java.io.InputStream;

public class geoLocationView extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private String qr_id;
    private String image_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.geolocation_view);
        Button back = (Button) findViewById(R.id.back);
        ImageView imageView = (ImageView) findViewById(R.id.geoImageView); // Replace with your ImageView id
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        Intent intent = getIntent();
        firebaseFirestore = FirebaseFirestore.getInstance();
        qr_id = intent.getStringExtra("qr_id");
        QRCollection qrc = new QRCollection(null);

        qrc.read(qr_id, data -> {
            image_id = data.get("qr_id").toString();
            loadImage(image_id, imageView);

        }, error -> {
            // qr not found, cannot set values
            System.out.println("Error getting player data: " + error);
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {finish();}
        });
    }

    private void loadImage(String imageName, ImageView imageView) {
        StorageReference imageRef = storageReference.child("geoImages/" + imageName);
        imageRef.getStream().addOnSuccessListener(taskSnapshot -> {
            InputStream inputStream = taskSnapshot.getStream();
            new DownloadImageTask(imageView).execute(inputStream);
        }).addOnFailureListener(e -> {
            System.out.println("Error loading image: " + e);
        });
    }

    private static class DownloadImageTask extends AsyncTask<InputStream, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(InputStream... inputStreams) {
            return BitmapFactory.decodeStream(inputStreams[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }

}
