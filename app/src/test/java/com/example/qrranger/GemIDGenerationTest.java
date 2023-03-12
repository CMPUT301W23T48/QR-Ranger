package com.example.qrranger;

import org.junit.Test;

public class GemIDGenerationTest {
    @Test
    public void testGemGeneratingIDArray(){

        gemID ID = new gemID();
        System.out.println(ID.getBgColor());
        System.out.println(ID.getBoarder());
        System.out.println(ID.getGemType());
        System.out.println(ID.getLusterLevel());

    }
    @Test
    public void testGemGeneratingNameArray(){

        gemID ID = new gemID();
        String hash = "f3b355bbe2db35727ebcfa32732649dc";
        ID.gemName(hash);
        System.out.println(ID.getGemName());


    }


}
