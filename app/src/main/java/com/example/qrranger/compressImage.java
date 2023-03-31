package com.example.qrranger;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class compressImage {
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        int options = 100;
        while (bytes.toByteArray().length / 1024 > 100) {
            bytes.reset();
            image.compress(Bitmap.CompressFormat.JPEG, options, bytes);
            options -= 10;
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(
                bytes.toByteArray());
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }
}