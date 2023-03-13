package com.example.qrranger;

import junit.framework.TestCase;

import org.junit.Test;

public class PlayerTest extends TestCase {
    @Test
    /**
     * Testing getters and setters of the player class, along with
     * the changeUserName, changeGeoSett, and changePhoneNumber
     */
    public void testPlayerChangeMethods(){
        Player player = new Player();
        boolean False = false;
        gemID gemID = new gemID();
        player.setPlayerId("2122101");
        player.setEmail("raymond6@ualberta.ca");
        player.setUserName("raymond");
        player.setPhoneNumber("780 780 7890");
        player.setGeoLocationSett(False);
        player.setTotalScore(11111111L);
        player.setTotalQRCode(100L);

        player.changeUsername("adam");
        player.changeGeoSett();
        player.changePhoneNumb("123 123 1234");

    }

}