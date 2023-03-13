package com.example.qrranger;

import android.location.Location;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
    /**
     * Sets Player class with attributes
     */
    //player profile
    private String playerId;
    private String email;
    private String userName;
    private String phoneNumber;
    private Long totalScore;
    private Long totalQRCode;



    /**
     * Provides attribute for allowing geolocation
     */
    private boolean geoLocationSett;

    /**
     * Allows each player to have their own qrCollection
     * holds a list of qr ids
     */
    private ArrayList<String> qrCodeCollection;

    //player stats
//    private QRCode leastScoring = qrCodeCollection.getLeast();
//    private QRCode highestScoring = qrCodeCollection.getHighest();
//    private int scoreTotal = qrCodeCollection.getTotal();

    /**
     * Allows location to be stored where the player scans a QR code
     */
    private Location location;
    public Player(){
    }

    public Player(String userName, String phoneNumber, String email){
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
    /**
     * Allows for the changing of Username, Phonenumber and GeoSetting
     */
    public void changeUsername(String userName){
        this.userName = userName;
    }
    public void changePhoneNumb(String phoneNumb){
        this.phoneNumber = phoneNumb;
    }
    public void changeGeoSett(){
        if (this.geoLocationSett == true){
            this.geoLocationSett = false;
        }else{
            this.geoLocationSett = true;
        }
    }
    /**
     * Setters for userEmail, PlayerId, UserName, and Geolocation Setting
     */
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPlayerId(String playerId){
        this.playerId = playerId;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setGeoLocationSett(boolean geoLocationSett) {
        this.geoLocationSett = geoLocationSett;
    }
    /**
     * Setters for phoneNumber, totalScore, totalQRCodes and QRCodeCollection
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    // set totalScore
    public void setTotalScore(Long totalScore) { this.totalScore = totalScore; }
    // set totalQRCode (the number of QR codes the player owns)
    public void setTotalQRCode(Long totalQRCode){ this.totalQRCode = totalQRCode; }
    // set the QR code collection
    public void setQrCodeCollection(ArrayList<String> QRList) { this.qrCodeCollection = QRList; }


    /**
     * Getters for playerId, email, userName, phoneNumber, comment, totalScore, totalQRCodes, and geoLocationSett
     */
    public String getPlayerId() {
        return playerId;
    }
    public String getEmail() {
        return email;
    }
    public String getUserName() {
        return userName;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String comment(String comment){
        return comment;
    }
    public Long getTotalScore() { return totalScore; }
    public Long getTotalQRCode() {
        return totalQRCode;
    }
    public Boolean getGeoLocationFlag() { return geoLocationSett; }
    public ArrayList<String> getQrCodeCollection() { return qrCodeCollection; }


    /**
     * Adds QR code to a player's collection
     */
    public void addQRCode(QRCode newQR){
        qrCodeCollection.add(newQR.getId());
    }

    //remove QR Code
//    public void removeQRCode(QRCode deleteQR){
//        for(QRCode myQr : qrCodeCollection){
//            if(myQr.getID() == deleteQR.getID()){
//                qrCodeCollection.remove(myQr);
//            }
//        }
//    }
    /**
     * When prompted, provides user location
     */
    public Location getLocation(){
        return this.location;
    }
    //give information about geolocation setting
    public boolean isGeoLocationSett() {
        return geoLocationSett;
    }


    //give least score qr code
//    public QRCode getLeastScoring() {
//        return leastScoring;
//    }
//    //give highest score qr code
//    public QRCode getHighestScoring() {
//        return highestScoring;
//    }

}
