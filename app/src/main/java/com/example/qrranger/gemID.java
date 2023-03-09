package com.example.qrranger;

import java.util.Random;

public class gemID {
    private String bgColor;
    private String boarder;
    private String gemType;
    private int lusterLevel;

    public gemID() {
        //Creates gem ID
        Random generator = new Random();
        //Randomly assign background color to Gem
        String[] bgColor = new String[]{"Red", "Blue", "Purple", "Pink", "White"};
        int randomIndex = generator.nextInt(bgColor.length);
        setBgColor(bgColor[randomIndex]);

        //Randomly assign boarder to Gem
        String[] boarder = new String[]{"None", "Black", "Bronze", "Silver", "Gold"};
        randomIndex = generator.nextInt(boarder.length);
        setBoarder(boarder[randomIndex]);

        //Randomly assign gem type to Gem
        String[] gemType = new String[]{"Diamond", "Square", "Triangle", "Hexagon", "Circular"};
        randomIndex = generator.nextInt(gemType.length);
        setGemType(gemType[randomIndex]);

        //Randomly assign luster level to Gem
        randomIndex = generator.nextInt(3)+1;
        setLusterLevel(randomIndex);
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
