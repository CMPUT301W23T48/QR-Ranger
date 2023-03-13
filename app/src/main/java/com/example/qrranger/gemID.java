package com.example.qrranger;

import java.util.Random;

public class gemID {
    private int bgColor;
    private int boarder;
    private int gemType;
    private int lusterLevel;
    private String gemName;

    public gemID() {
        //Creates gem ID
        Random generator = new Random();
        //Randomly assign background color to Gem
        int[] bgColor = new int[]{R.drawable.bgred, R.drawable.bgblue, R.drawable.bgpurple, R.drawable.bgpink, R.drawable.bgwhite};
        int randomIndex = generator.nextInt(bgColor.length);
        setBgColor(bgColor[randomIndex]);

        //Randomly assign boarder to Gem
        int[] boarder = new int[]{R.drawable.gemborder, R.drawable.blackborder, R.drawable.bronzeborder, R.drawable.silverborder, R.drawable.goldborder, R.drawable.bookborder};
        randomIndex = generator.nextInt(boarder.length);
        setBoarder(boarder[randomIndex]);

        //Randomly assign gem type to Gem
        int[] gemType = new int[]{R.drawable.bluegem1, R.drawable.bluegem2, R.drawable.bluegem3, R.drawable.greengem1, R.drawable.greengem2, R.drawable.greengem3, R.drawable.purplegem1, R.drawable.purplegem2, R.drawable.purplegem3, R.drawable.yellowgem1, R.drawable.yellowgem2, R.drawable.yellowgem3};
        randomIndex = generator.nextInt(gemType.length);
        setGemType(gemType[randomIndex]);

        //Randomly assign luster level to Gem
        int[] gemLustre = new int[]{R.drawable.lusterlevel0, R.drawable.lusterlevel1, R.drawable.lusterlevel2, R.drawable.lusterlevel3, R.drawable.lusterlevel4};
        randomIndex = generator.nextInt(4);
        setLusterLevel(gemLustre[randomIndex]);
    }
    public String gemName(String hash) {
        //Takes hash and turns it into a binary array
        StringBuilder result = new StringBuilder();
        char[] hashChars = hash.toCharArray();
        for (char aChar: hashChars){
            result.append(
                    String.format("%8s", Integer.toBinaryString(aChar))
                            .replaceAll(" ", "0")
            );
        }
        result.toString();
        String name = "";
        if (hashChars[0] == '0') {
            name += "Clear ";
        } else {
            name += "Smokey ";
        }
        if (hashChars[1] == '0') {
            name += "Shiny ";
        } else {
            name += "Dull ";
        }
        if (hashChars[2] == '0') {
            name += "Rare ";
        } else {
            name += "Common ";
        }
        if (hashChars[3] == '0') {
            name += "Flawless ";
        } else {
            name += "Spotless ";
        }
        if (hashChars[4] == '0') {
            name += "BO";
        } else {
            name += "Lo";
        }
        if (hashChars[5] == '0') {
            name += "Sho ";
        } else {
            name += "Kou ";
        }
        if (hashChars[6] == '0') {
            name += "Quartz";
        } else {
            name += "Diamond";
        }
        return name;
    }

    //Getters for gem representation
    public int getBgColor() {
        return bgColor;
    }
    public int getBoarder() {
        return boarder;
    }
    public int getGemType() {
        return gemType;
    }
    public int getLusterLevel() {
        return lusterLevel;
    }

    //Setters for gem representation
    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }
    public void setBoarder(int boarder) {
        this.boarder = boarder;
    }
    public void setGemType(int gemType) { this.gemType = gemType;}
    public void setLusterLevel(int lusterLevel) {
        this.lusterLevel = lusterLevel;
    }
}
