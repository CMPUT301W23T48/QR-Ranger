package com.example.qrranger;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class PlayerCollectionTest {

    private static final int TIMEOUT_SECONDS = 5;

    @Test
    public void testCreate() throws InterruptedException {
        DatabaseModel db = DatabaseModel.getInstance();
        PlayerCollection pc = new PlayerCollection(db);

        // create a map with sample data
        Map<String, Object> values = new HashMap<>();
        values.put("userID", "minh");
        values.put("username", "minh123");
        values.put("phoneNumber", "1234567890");
        values.put("email", "minh@example.com");
        values.put("geolocation_setting", true);
        values.put("totalScore", 0);
        values.put("totalQRCode", 0);

        // create a countdown latch to wait for completion of the create operation
        CountDownLatch latch = new CountDownLatch(1);

        // create the document and wait for completion
        pc.create(values);

        boolean completed = latch.await(TIMEOUT_SECONDS, TimeUnit.SECONDS);

        // check that the create operation completed successfully
        assertTrue(completed);
    }

    public void read_test() throws InterruptedException{
        DatabaseModel db = DatabaseModel.getInstance();
        PlayerCollection pc = new PlayerCollection(db);

        Player myTestUser = new Player();
        UserState testUserState = UserState.getInstance();
        String testStringID = testUserState.getUserID();

        pc.read(testStringID, data -> {
            // player found so handle code using data here
            myTestUser.setUserName(Objects.requireNonNull(data.get("username")).toString());
            myTestUser.setEmail(Objects.requireNonNull(data.get("email")).toString());
            myTestUser.setPhoneNumber(Objects.requireNonNull(data.get("phoneNumber")).toString());
            myTestUser.setTotalScore(((Long) data.get("totalScore")));
            myTestUser.setTotalQRCode(((Long) data.get("totalQRCode")));
            myTestUser.setGeoLocationSett((Boolean) data.get("geolocation_setting"));
            myTestUser.setPlayerId(testStringID);
            myTestUser.setQrCodeCollection((ArrayList<String>) data.get("qr_code_ids"));
        }, error -> {

            System.out.println("Error getting player data: " + error);
        });

        // create a countdown latch to wait for completion of the read operation
        CountDownLatch latch = new CountDownLatch(1);

        // check if value read
        assertTrue(myTestUser.getUserName() != null );
        assertTrue(myTestUser.getEmail()!= null);
        assertTrue(myTestUser.getPlayerId() != null);
        assertTrue(myTestUser.getPhoneNumber()!= null);
        assertTrue(myTestUser.getTotalQRCode()!= null);

        boolean completed = latch.await(TIMEOUT_SECONDS, TimeUnit.SECONDS);
        System.out.println("completed");
        // check that the create operation completed successfully
        assertTrue(completed);
    }
}




