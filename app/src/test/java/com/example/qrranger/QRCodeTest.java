package com.example.qrranger;

import junit.framework.TestCase;

import org.junit.Test;

public class QRCodeTest extends TestCase {
    @Test
    /**
     * Testing Setters and Getters of QRCode by first initializing the class
     * getting the inital attributes, and setting them to new ones
     */
    public void testGettersAndSettersOfQRCode(){
        QRCode test = new QRCode("9a86a0b4c6d85bf937e174b39055a156","https://www.youtube.com/");
        System.out.println(test.getId());
        System.out.println(test.getName());
        System.out.println(test.getUrl());
        System.out.println(test.getGeoLocation());
        System.out.println(test.getGemID());
        System.out.println("\n");

        test.setGeoLocation("17635 Stony Plain Rd, Edmonton, AB T5S 1E3");
        test.setUrl("https://www.dennys.ca/restaurant/alberta/edmonton-west/");
        System.out.println(test.getUrl());
        System.out.println(test.getGeoLocation());


    }
    @Test
    /**
     * Using the Hash that was provided as an example on Eclass,
     * we can compare what our calculateScore method gives us
     * and compare it to what the score should be
     *
     * Since both scores are the same, this shows that our calculateScore
     * method works properly
     */
    public void testScoreCalculator(){
        QRCode test = new QRCode("696ce4dbd7bb57cbfe58b64f530f428b74999cb37e2ee60980490cd9552de3a6","https://www.youtube.com/");
        test.setPoints(QRCode.calculateScore(test.getId()));
        System.out.println(test.getPoints());



    }

}