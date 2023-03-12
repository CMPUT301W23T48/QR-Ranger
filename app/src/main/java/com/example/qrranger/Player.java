package com.example.qrranger;

import android.location.Location;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {

    //player profile
    private String playerId;
    private String email;
    private String userName;
    private String phoneNumber;
    private Long totalScore;
    private Long totalQRCode;


    //player settings
    private boolean geoLocationSett;

    //Collection of QrCode
    private ArrayList<QRCode> qrCodeCollection;

    //player stats
//    private QRCode leastScoring = qrCodeCollection.getLeast();
//    private QRCode highestScoring = qrCodeCollection.getHighest();
//    private int scoreTotal = qrCodeCollection.getTotal();

    //location of player
    private Location location;
    public Player(){
    }

    public Player(String userName, String phoneNumber, String email){
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    //change username
    public void changeUsername(String userName){
        this.userName = userName;
    }
    //change phone number
    public void changePhoneNumb(String phoneNumb){
        this.phoneNumber = phoneNumb;
    }
    //change geo setting
    public void changeGeoSett(){
        if (this.geoLocationSett == true){
            this.geoLocationSett = false;
        }else{
            this.geoLocationSett = true;
        }
    }
    // set user email
    public void setEmail(String email) {
        this.email = email;
    }
    //set User ID
    public void setPlayerId(String playerId){
        this.playerId = playerId;
    }
    //set user name
    public void setUserName(String userName) {
        this.userName = userName;
    }
    //set GeoLocation
    public void setGeoLocationSett(boolean geoLocationSett) {
        this.geoLocationSett = geoLocationSett;
    }
    //set phone number
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    // set totalScore
    public void setTotalScore(Long totalScore) { this.totalScore = totalScore; }
    // set totalQRCode (the number of QR codes the player owns)
    public void setTotalQRCode(Long totalQRCode){ this.totalQRCode = totalQRCode; }
    // set the QR code collection
    public void setQrCodeCollection(ArrayList<QRCode> QRList) { this.qrCodeCollection = QRList; }


    //get player ID
    public String getPlayerId() {
        return playerId;
    }
    //get email
    public String getEmail() {
        return email;
    }
    //get username
    public String getUserName() {
        return userName;
    }
    //get phone number
    public String getPhoneNumber() {
        return phoneNumber;
    }
    //comment
    public String comment(String comment){
        return comment;
    }
    // get totalScore
    public Long getTotalScore() { return totalScore; }
    // get total qr code scanned by player
    public Long getTotalQRCode() {
        return totalQRCode;
    }
    // get the geolocation settings boolean
    public Boolean getGeoLocationFlag() { return geoLocationSett; }


    //add QR Code
    public void addQRCode(QRCode newQR){
        qrCodeCollection.add(newQR);
    }

    //remove QR Code
//    public void removeQRCode(QRCode deleteQR){
//        for(QRCode myQr : qrCodeCollection){
//            if(myQr.getID() == deleteQR.getID()){
//                qrCodeCollection.remove(myQr);
//            }
//        }
//    }
    //give player current Location
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
