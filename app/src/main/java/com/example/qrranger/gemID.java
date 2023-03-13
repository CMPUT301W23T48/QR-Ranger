package com.example.qrranger;

import java.util.Random;

public class gemID {
    private String bgColor;
    private String boarder;
    private String gemType;
    private int lusterLevel;
    private String gemName;

    public gemID() {
        //Creates gem ID
        Random generator = new Random();
        //Randomly assign background color to Gem
        String[] bgColor = new String[]{"bgred", "bgblue", "bgpurple", "bgpink", "bgwhite"};
        int randomIndex = generator.nextInt(bgColor.length);
        setBgColor(bgColor[randomIndex]);

        //Randomly assign boarder to Gem
        String[] boarder = new String[]{"gemborder", "blackborder", "bronzeborder", "silverborder", "goldborder", "bookborder"};
        randomIndex = generator.nextInt(boarder.length);
        setBoarder(boarder[randomIndex]);

        //Randomly assign gem type to Gem
        String[] gemType = new String[]{"bluegem1", "bluegem2", "bluegem3", "greengem1", "greengem2", "greengem3", "purplegem1", "purplegem2", "purplegem3", "yellowgem1", "yellowgem2", "yellowgem3"};
        randomIndex = generator.nextInt(gemType.length);
        setGemType(gemType[randomIndex]);

        //Randomly assign luster level to Gem
        randomIndex = generator.nextInt(4)+1;
        setLusterLevel(randomIndex);
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
    public String getBgColor() {
        return bgColor;
    }
    public String getBoarder() {
        return boarder;
    }
    public String getGemType() {
        return gemType;
    }
    public int getLusterLevel() {
        return lusterLevel;
    }

    //Setters for gem representation
    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }
    public void setBoarder(String boarder) {
        this.boarder = boarder;
    }
    public void setGemType(String gemType) { this.gemType = gemType;}
    public void setLusterLevel(int lusterLevel) {
        this.lusterLevel = lusterLevel;
    }
}
