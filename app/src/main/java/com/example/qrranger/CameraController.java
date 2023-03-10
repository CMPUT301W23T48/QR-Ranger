package com.example.qrranger;

import static androidx.core.app.ActivityCompat.startActivityForResult;
import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.provider.MediaStore;

public class CameraController {
    public final static int IMAGE_CAPTURE_REQUEST_CODE = 1034;
    public CameraController() {

    }

    public void takePhoto() {
        Intent intent = new Intent(getApplicationContext(), MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityforResult(intent, IMAGE_CAPTURE_REQUEST_CODE);
    }
}
