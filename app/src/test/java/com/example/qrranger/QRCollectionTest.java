package com.example.qrranger;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class QRCollectionTest extends TestCase {
    private static final int TIMEOUT_SECONDS = 5;
    @Test
    public void testCreate() throws InterruptedException{
        DatabaseModel db = DatabaseModel.getInstance();
        QRCollectionController qrCollection = new QRCollectionController(db);

        // Create a Map of field-value pairs for the new document
        Map<String, Object> values = new HashMap<>();
        values.put("qr_id", "123");
        values.put("name", "John");
        values.put("url", "mjkkkkkkkk");
        values.put("points", 10);

        // create a countdown latch to wait for completion of the create operation
        CountDownLatch latch = new CountDownLatch(1);

        // Call the create() method and verify that a new document is added to the collection
        boolean completed = latch.await(TIMEOUT_SECONDS, TimeUnit.SECONDS);
        qrCollection.create(values);
        CompletableFuture<Boolean> check = qrCollection.checkQRExists("123");

        assertTrue(completed);
        assertTrue(check == CompletableFuture.completedFuture(true));
    }

}