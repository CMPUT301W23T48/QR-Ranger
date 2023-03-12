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

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

/**
 * Class used for scanning QR Codes and processing the data received by them.
 */
public class QRScannerActivity extends AppCompatActivity{
    private Button rejectButton;
    private Button confirmButton;
    private TextView qrTitle;
    private TextView qrScore;
    private Bitmap locationImage;
    private byte[] scanResult;

    @Override
    protected void onCreate(Bundle SavedInstanceBundle) {
        super.onCreate(SavedInstanceBundle);
        setContentView(R.layout.activity_qr_scanner);

        // Populate view properties.
        rejectButton = findViewById(R.id.button_reject);
        confirmButton = findViewById(R.id.button_confirm);
        qrTitle = findViewById(R.id.tv_title);
        qrScore = findViewById(R.id.tv_score);

        scanQR();

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
     * Initiates the scan of a QR code using the ZXing Open Source API.
     */
    public void scanQR() {
        // Set up Intent Integrator Settings.
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setBeepEnabled(false);
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.setPrompt("Scan a QR Code!");

        // Set the default format to QR Codes.
        ArrayList<String> supportedCodeFormats = new ArrayList<>();
        supportedCodeFormats.add("QR_CODE");
        intentIntegrator.setDesiredBarcodeFormats(supportedCodeFormats);

        intentIntegrator.initiateScan();
    }

    /**
     * Retrieves the result of the QR code scan once the scan has completed.
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

        if (requestCode == IntentIntegrator.REQUEST_CODE && resultCode == RESULT_OK) {
            IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (intentResult != null) {
                if (intentResult.getContents() != null) {
                    scanResult = intentResult.getRawBytes();
                } else {
                    //Toast.makeText(getApplicationContext(), "Scan Cancelled", Toast.LENGTH_SHORT);
                }
            }
        }
    }

}
