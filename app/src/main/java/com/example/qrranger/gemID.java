package com.example.qrranger;

import java.util.Random;

public class gemID {
    private String bgColor;
    private String boarder;
    private String gemShape;
    private String gemColor;
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

        //Randomly assign gem shape to Gem
        String[] gemShape = new String[]{"Diamond", "Square", "Triangle", "Hexagon", "Circular"};
        randomIndex = generator.nextInt(gemShape.length);
        setGemShape(gemShape[randomIndex]);

        //Randomly assign gem color to Gem
        String[] gemColor = new String[]{"Blue", "Green", "Red", "Purple", "Yellow"};
        randomIndex = generator.nextInt(gemColor.length);
        setGemColor(gemColor[randomIndex]);

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
    public String getGemShape() {
        return gemShape;
    }
    public String getGemColor() {
        return gemColor;
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
    public void setGemShape(String gemShape) {
        this.gemShape = gemShape;
    }
    public void setGemColor(String gemColor) {
        this.gemColor = gemColor;
    }
    public void setLusterLevel(int lusterLevel) {
        this.lusterLevel = lusterLevel;
    }
}
