package com.example.qrranger;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class gemID {
    private int bgColor;
    private int boarder;
    private int gemType;
    private int lusterLevel;
    private String gemName;

    private myNameDictionary myDict = new myNameDictionary();
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
        randomIndex = generator.nextInt(5);
        setLusterLevel(gemLustre[randomIndex]);
    }
    public String gemName(String hash) {
//        ArrayList<String> lines = new ArrayList<String>();
//        ArrayList<String> lines1 = new ArrayList<String>();
//       try {
//            BufferedReader reader = new BufferedReader(new FileReader("Noun.txt"));
//            BufferedReader reader1 = new BufferedReader(new FileReader("Adjectives.txt"));
//            if(reader == null){
//                System.out.println("yo;o");
//            }
//            String line = reader.readLine();
//            String line1 = reader1.readLine();
//            while (line != null) {
//                lines.add(line);
//                line = reader.readLine();
//                System.out.println(line);
//            }
//            reader.close();
//            while(line1 != null){
//                lines1.add(line1);
//                line1 = reader1.readLine();
//            }
//            reader1.close();
//        } catch (IOException e) {
//           System.out.println("yo;odf");
//            e.printStackTrace();
//            return null;
//        }
        //this.name1 = lines.toArray(new String[lines.size()]);
        //this.name2 = lines.toArray(new String[lines.size()]);
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
