package com.example.qrranger;

import android.location.Location;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * Sets Player class with attributes
 */
public class PlayerModel implements Serializable {
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


    /**
     * Allows location to be stored where the player scans a QR code
     */
    private Location location;
    public PlayerModel(){
    }

    public PlayerModel(String userName, String phoneNumber, String email){
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
}
