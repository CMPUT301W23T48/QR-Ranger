package com.example.qrranger;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Class used for scanning QR Codes and processing the data received by them.
 */
public class QRScannerActivity extends AppCompatActivity{
    private Button rejectButton;
    private Button confirmButton;
    private TextView qrTitle;
    private TextView qrScore;
    private QRScanController scanController;
    private Bitmap locationImage;

    @Override
    protected void onCreate(Bundle SavedInstanceBundle) {
        super.onCreate(SavedInstanceBundle);
        setContentView(R.layout.activity_qr_scanner);


        rejectButton = findViewById(R.id.button_reject);
        confirmButton = findViewById(R.id.button_confirm);
        qrTitle = findViewById(R.id.tv_title);
        qrScore = findViewById(R.id.tv_score);
        scanController = new QRScanController();

        scanController.scanQR();

        // If user rejects the QR code, go back to profile.
        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // If user accepts the QR code, move on to take location photo.
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Take a picture of QR's location!", Toast.LENGTH_LONG);
                takePhoto();
            }
        });
    }

    /**
     *  Takes a photograph using MediaStore and stores the result as a bitmap.
     */
    public void takePhoto() {
        // Launch the camera activity.
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Set the ActivityResultLauncher so that the result of the activity is stored.
        ActivityResultLauncher<Intent> pictureResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            if (result.getData() != null) {
                                locationImage = (Bitmap) result.getData().getExtras().get("data");
                            }
                        }
                    }
                });

        // Launch the photo activity with the launcher.
        pictureResultLauncher.launch(takePhotoIntent);
    }

    /**
     * Overridden to retrieve the result of the QR Code Scan.
     *
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode The integer result code returned by the child activity
     *                   through its setResult().
     * @param data An Intent, which can return result data to the caller
     *               (various data can be attached to Intent "extras").
     *
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == scanController.getRequestCode() && resultCode == RESULT_OK) {
            scanController.parseScanResult(requestCode, resultCode, data);
        }
    }
}
