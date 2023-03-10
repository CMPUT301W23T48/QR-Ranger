package com.example.qrranger;

import android.content.Intent;

import androidx.annotation.Nullable;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class QRScanController {
    private IntentIntegrator intentIntegrator;
    private IntentResult intentResult;
    private ArrayList<String> supportedCodeFormats;
    private byte[] scanResult;

    public QRScanController() {
        // Set up Intent Integrator Settings.
        intentIntegrator.setBeepEnabled(false);
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.setPrompt("Scan a QR Code!");

        // Set the default format to QR Codes.
        supportedCodeFormats = new ArrayList<>();
        supportedCodeFormats.add("QR_CODE");
        intentIntegrator.setDesiredBarcodeFormats(supportedCodeFormats);
    }

    /**
     * For testing purposes.
     * @return
     */
    public QRScanController(ArrayList<String> supportedCodeFormats) {
        this.supportedCodeFormats = supportedCodeFormats;
    }

    public byte[] getScanResult(){
        return scanResult;
    }

    public int getRequestCode() {
        return IntentIntegrator.REQUEST_CODE;
    }

    public void scanQR() {
        intentIntegrator.initiateScan();
    }

    public void parseScanResult(int requestCode, int resultCode, @Nullable Intent data) {
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
