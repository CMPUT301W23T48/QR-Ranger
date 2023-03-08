package com.example.qrranger;

import android.location.Location;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Player implements Serializable {

    //player profile
    private int playerId;
    private String email;
    private String userName;
    private String phoneNumber;

    //player settings
    private boolean geoLocationSett = true;

    //Collection of QrCode
    private ArrayList<QRCode> qrCodeCollection = new ArrayList<QRCode>();

    //player stats
//    private QRCode leastScoring = qrCodeCollection.getLeast();
//    private QRCode highestScoring = qrCodeCollection.getHighest();
//    private int scoreTotal = qrCodeCollection.getTotal();
    private int totalQRCode = qrCodeCollection.size();

    //location of player
    private Location location;

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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
//    //give total score of player
//    public int getScoreTotal() {
//        return scoreTotal;
//    }
    //give total qr code scanned by player
    public int getTotalQRCode() {
        return totalQRCode;
    }

}
