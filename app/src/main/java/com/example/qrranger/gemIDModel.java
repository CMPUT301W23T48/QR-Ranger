package com.example.qrranger;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * Class representing a gem with various attributes, such as background color,
 * border, gem type, luster level, and gem name.
 */
public class gemIDModel {
    private int bgColor;
    private int boarder;
    private int gemType;
    private int lusterLevel;

    private myNameDictionaryModel myDict = new myNameDictionaryModel();

    /**
     * Constructor for the gemID class. Assigns background color,
     * border, gem type, and luster level to the gem.
     */
    public gemIDModel() {
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
        randomIndex = generator.nextInt(5);
        setLusterLevel(gemLustre[randomIndex]);
    }

    /**
     * Generates a gem name based on a hash of the input string.
     *
     * @param hash The input string to generate the hash and name from.
     * @return The generated gem name.
     */
    public String gemName(String hash) {
    try {
        // Generate SHA-256 hash from input string
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = messageDigest.digest(hash.getBytes());
        BigInteger hashValue = new BigInteger(1, hashBytes);

        // Use hash value to select different kind of name
        int adjectiveIndex = hashValue.mod(BigInteger.valueOf(myDict.adjectivesDict().length)).intValue();
        int nounIndex = hashValue.mod(BigInteger.valueOf(myDict.nounsDict().length)).intValue();

        // Combine adjective and noun to form funny name
        String adjective = myDict.adjectivesDict()[adjectiveIndex];
        String noun = myDict.nounsDict()[nounIndex];
        String name = adjective + " " + noun;

        return name;
    }catch (NoSuchAlgorithmException e) {
        // Handle exception if SHA-256 algorithm is not available
        e.printStackTrace();
        return null;
    }
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
