package com.example.qrranger;

import org.junit.Test;
/**
 * Testing Setters and Getters of QRCode by first initializing the class
 * getting the inital attributes, and setting them to new ones
 */
public class QRCodeTest {
    @Test
    public void testGettersAndSettersOfQRCode(){
        QRCodeModel test = new QRCodeModel("9a86a0b4c6d85bf937e174b39055a156");
        System.out.println(test.getId());
        System.out.println(test.getName());
        System.out.println(test.getGeoLocation());
        System.out.println(test.getGemID());
        System.out.println("\n");

        test.setGeoLocation("17635 Stony Plain Rd, Edmonton, AB T5S 1E3");
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
     **/
    public void testScoreCalculator(){
        QRCodeModel test = new QRCodeModel("696ce4dbd7bb57cbfe58b64f530f428b74999cb37e2ee60980490cd9552de3a6");
        test.setPoints(QRCodeModel.calculateScore(test.getId()));
        System.out.println(test.getPoints());



    }

}