package com.example.qrranger;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    private QRScanController scanController;

    @Override
    protected void onCreate(Bundle SavedInstanceBundle) {
        super.onCreate(SavedInstanceBundle);
        setContentView(R.layout.activity_qr_scanner);

        rejectButton = findViewById(R.id.button_reject);
        confirmButton = findViewById(R.id.button_confirm);
        qrTitle = findViewById(R.id.tv_title);
        qrScore = findViewById(R.id.tv_score);

        scanController.scanQR();

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == scanController.getRequestCode() && resultCode == RESULT_OK) {
            scanController.parseScanResult(requestCode, resultCode, data);
        }

    }
}
