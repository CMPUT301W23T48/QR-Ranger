package com.example.qrranger;

public class QRCode {
    private String name;
    private String url;
    private Integer points;

    private String geoLocation;

    //Initialization of the QRCode Class
    public QRCode(String name, String url) {
        this.name = name;
        this.url = url;
        this.points = 0;
        this.geoLocation = "Unknown";
    }

    //Getters for the QR code Class
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
    
}
