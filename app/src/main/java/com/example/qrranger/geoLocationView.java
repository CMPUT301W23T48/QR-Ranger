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

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.InputStream;

/**
 * An activity that displays a geo-tagged image based on the QR code id.
 */
public class geoLocationView extends AppCompatActivity {
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private String qr_id;
    private String image_id;

    /**
     * Initializes the activity, sets up UI components, and loads the
     * geo-tagged image from Firebase Storage.
     *
     * @param savedInstanceState Saved instance state bundle.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.geolocation_view);
        Button back = (Button) findViewById(R.id.back);
        ImageView imageView = (ImageView) findViewById(R.id.geoImageView); // Replace with your ImageView id
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        Intent intent = getIntent();
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

    /**
     * Loads an image from Firebase Storage using the provided image name
     * and sets it to the specified ImageView.
     *
     * @param imageName The name of the image to load.
     * @param imageView The ImageView to display the loaded image.
     */
    private void loadImage(String imageName, ImageView imageView) {
        StorageReference imageRef = storageReference.child("geoImages/" + imageName);
        imageRef.getStream().addOnSuccessListener(taskSnapshot -> {
            InputStream inputStream = taskSnapshot.getStream();
            new DownloadImageTask(imageView).execute(inputStream);
        }).addOnFailureListener(e -> {
            System.out.println("Error loading image: " + e);
        });
    }

    /**
     * An AsyncTask to download an image from an InputStream and set it to an ImageView.
     */
    private static class DownloadImageTask extends AsyncTask<InputStream, Void, Bitmap> {
        ImageView imageView;

        /**
         * Constructor for the DownloadImageTask class.
         *
         * @param imageView The ImageView to display the downloaded image.
         */
        public DownloadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        /**
         * Downloads the image from the InputStream in the background.
         *
         * @param inputStreams The InputStream array containing the image data.
         * @return The Bitmap representation of the downloaded image.
         */
        @Override
        protected Bitmap doInBackground(InputStream... inputStreams) {
            return BitmapFactory.decodeStream(inputStreams[0]);
        }

        /**
         * Sets the downloaded image to the ImageView.
         *
         * @param bitmap The Bitmap representation of the downloaded image.
         */
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }

}
