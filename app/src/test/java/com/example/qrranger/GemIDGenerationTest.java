package com.example.qrranger;

import org.junit.Test;
/**
 * Testing the generation of the gemID class,
 * and the getters of the gemID class
 */
public class GemIDGenerationTest {
    @Test
    public void testGemGeneratingIDArray(){

        gemIDModel ID = new gemIDModel();
        System.out.println(ID.getBgColor());
        System.out.println(ID.getBoarder());
        System.out.println(ID.getGemType());
        System.out.println(ID.getLusterLevel());

    }


}
