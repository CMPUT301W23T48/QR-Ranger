package com.example.qrranger;

/**
 * The structure for the QR class
 * Also responsible for calculating the score of a QR code
 */
public class QRCodeModel {
    /**
     * Attributes of id, name, points,
     * geoLocation and gemID for QRCode cLass
     */
    private String id;
    private String name;
    private Integer points;
    private String geoLocation;
    private gemIDModel gemId;

    /**
     * Initializer for generating a QRCode from scratch
     * responsible for generating a new gem ID
     */
    public QRCodeModel(String id) {
        this.id = id;
        this.gemId = new gemIDModel();
        this.name = gemId.gemName(id);
        this.points = calculateScore(id);
        this.geoLocation = "Unknown";
    }

    /**
     * Initializer for getting a QRCode from the database
     * and repopulating it, no need to generate a new gemID as
     * it already exists
     */
    public QRCodeModel() {
    }

    /**
     * Getters for the QRCode id, name, points, geoLocation and gemID
     */
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Integer getPoints() {
        return points;
    }
    public String getGeoLocation(){
        return geoLocation;
    }
    public gemIDModel getGemID() {
        return this.gemId;
    }

    /**
     * Setters for the name, points and geoLocation
     */
    public void setName(String name) {
        this.name = name;
    }
    public void setPoints(Integer points) {
        this.points = points;
    }
    public void setGeoLocation(String GeoLocation){
        this.geoLocation = GeoLocation;
    }
    public void setID(String ID) { this.id = ID; }
    public void setGemId(gemIDModel gem) { this.gemId = gem; }


    /**
     * Takes the String hash of a QRCode and
     * calculates a score using it
     */
    public static Integer calculateScore(String hash) {
        // Calculate score
        /* From: geeksforgeeks.org
         * At: https://www.geeksforgeeks.org/java-program-for-hexadecimal-to-decimal-conversion/
         * Author: mayur_patil https://auth.geeksforgeeks.org/user/mayur_patil/articles
         */
        char[] hashChars = hash.toCharArray();
        char prevChar = hashChars[0];
        int duplicates = 0, totalScore = 0, base = 0;
        for (int i = 1; i < hash.length(); i++) {
            if (hashChars[i] == prevChar) {
                duplicates += 1;
            }
            else if (duplicates != 0) {
                if (prevChar == '0') {
                    base = 20;
                }
                else if (prevChar >= '1' && prevChar <= '9') { // 1-9
                    base = prevChar - 48; // 2-9
                }
                else if (prevChar >= 'a' && prevChar <= 'g') { // a-g
                    base = prevChar - 87; // 10-16
                }
                totalScore += Math.pow(base, duplicates); // base^duplicates
                duplicates = 0;
            }
            prevChar = hashChars[i];
        }

        return totalScore;
    }
    
}
