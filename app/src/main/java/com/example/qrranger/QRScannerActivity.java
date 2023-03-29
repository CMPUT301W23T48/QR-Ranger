package com.example.qrranger;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
    private ImageView gemShape;
    private ImageView backgroundColor;
    private ImageView gemBorder;
    private ImageView gemLustre;
    private Bitmap locationImage;
    private QRGenerator generator;
    private QRCode qrCode;
    private String scanResult;
    private ActivityResultLauncher<Intent> pictureResultLauncher;
    private int CAMERA_PERMISSIONS_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle SavedInstanceBundle) {
        super.onCreate(SavedInstanceBundle);
        setContentView(R.layout.activity_qr_scanner);

        // Populate view properties.
        rejectButton = findViewById(R.id.button_reject);
        confirmButton = findViewById(R.id.button_confirm);
        qrTitle = findViewById(R.id.gemName);
        qrScore = findViewById(R.id.gemValue);
        gemShape = findViewById(R.id.gem);
        backgroundColor = findViewById(R.id.backgroundColor);
        gemBorder = findViewById(R.id.borderType);
        gemLustre = findViewById(R.id.lusterLevel);

        generator = new QRGenerator();
        qrCode = new QRCode("Placeholder", "Placeholder");

        scanQR();

        /*
         * Similar to onActivityResult() seen below, but the
         * non-deprecated version. Essentially sets up a launcher for
         * the camera activity. One of the parameters is an ActivityResultCallback,
         * which can set an override for onActivityResult() to execute once the activity
         * launched by the launcher completes.
         *
         * When the camera activity completes, it gets and stores the image taken before
         * exiting the QRScannerActivity back to the MainActivity.
         */
        pictureResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            if (result.getData() != null) {
                                locationImage = (Bitmap) result.getData().getExtras().get("data");

                                // Return to the main activity with the image data.
                                Intent returnIntent = new Intent(getBaseContext(), MainActivity.class);
                                startActivity(returnIntent);
                            }
                        }
                    }
                });

        // If user rejects the QR code, go back to MainActivity.
        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent exitScannerIntent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(exitScannerIntent);
            }
        });

        // If user accepts the QR code, move on to take location photo.
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(generator.addQRToAccount(qrCode.getId())) {
                    // QR is successfully added to account.
                    takePhoto();
                }
                else {
                    // QR is already in account.
                    Toast.makeText(getBaseContext(), "QR is already in account!", Toast.LENGTH_LONG).show();
                    Intent qrAlreadyIn = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(qrAlreadyIn);
                }
            }
        });
    }

    private void updateUi() {
        gemShape.setImageResource(qrCode.getGemID().getGemType());
        gemLustre.setImageResource(qrCode.getGemID().getLusterLevel());
        gemBorder.setImageResource(qrCode.getGemID().getBoarder());
        backgroundColor.setImageResource(qrCode.getGemID().getBgColor());
        qrTitle.setText(qrCode.getName());
        qrScore.setText(((Integer)qrCode.getPoints()).toString());
    }

    /**
     *  Takes a photograph using MediaStore and stores the result as a bitmap.
     * @See MediaStore.ACTION_IMAGE_CAPTURE
     */
    public void takePhoto() {
        Toast.makeText(getApplicationContext(), "Take a photo near the QR!", Toast.LENGTH_SHORT).show();
        // Make the intent.
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ask for camera permissions, or the camera will not open.
        if (ContextCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(QRScannerActivity.this, new String[]{android.Manifest.permission.CAMERA}, CAMERA_PERMISSIONS_REQUEST_CODE);
        }

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
     * Activates once the QRScan activity has been completed. Retrieves the data collected when
     * the QR code is scanned and stores it.
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
            // Get the result.
            IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (intentResult != null && intentResult.getContents() != null) {
                scanResult = intentResult.getContents();
                generator.generateQR(scanResult);
                while(generator.getQr().getId() == "123456789") {
                    qrCode = generator.getQr();
                }
                updateUi();
            } else {
                // An error occurs and the scan returns no results.
                Log.e(TAG, "Error during QR scan.");

                // Return to the main activity rather than the scan page.
                Intent returnToMain = new Intent(getBaseContext(), MainActivity.class);
                startActivity(returnToMain);
            }
        }
        else if (requestCode == IntentIntegrator.REQUEST_CODE && resultCode == RESULT_CANCELED) {
            // Activates on back-button press out of the scanner.

            // Small popup notifying the user that the scan was cancelled, if so.
            Toast.makeText(getBaseContext(), "Scan Cancelled", Toast.LENGTH_SHORT).show();

            // Return to the main activity rather than the scan page.
            Intent returnToMain = new Intent(getBaseContext(), MainActivity.class);
            startActivity(returnToMain);
        }
    }
}
