package com.example.qrranger;

import org.junit.Test;

public class GemIDGenerationTest {
    @Test
    public void testGemGeneratingIDArray(){

        gemID ID = new gemID();
        System.out.println(ID.getBgColor());
        System.out.println(ID.getBoarder());
        System.out.println(ID.getGemColor());
        System.out.println(ID.getGemShape());
        System.out.println(ID.getLusterLevel());

    }


}
