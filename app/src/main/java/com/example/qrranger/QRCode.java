package com.example.qrranger;


public class QRCode {
    private String id;
    private String name;
    private String url;
    private Integer points;
    private String geoLocation;

    private gemID gemID;

    //Initialization of the QRCode Class
    public QRCode(String id, String name, String url, gemID gemID) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.points = 0;
        this.geoLocation = "Unknown";
        this.gemID = gemID;
    }

    //Getters for the QR code Class
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public Integer getPoints() {
        return points;
    }

    public String getGeoLocation(){
        return geoLocation;
    }

    //Setters for the QR code class
    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public void setGeoLocation(String GeoLocation){
        this.geoLocation = GeoLocation;
    }

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
