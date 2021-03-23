package com.example.geartrader;

public class ListingModel {
    // Private //
    private int id;
    private String title;
    private float price;
    private String summary;
    private String date;
    private byte[] image;
    private double lat;
    private double lng;


    // Public //

    // Default Constructor
    public ListingModel() {
    }

    // Constructor for displaying the DbHelper method "getAllListings"
    public ListingModel(int id, String title, float price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }

    public ListingModel(int id, String title, float price, String summary, String date, byte[] image, double lat, double lng) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.summary = summary;
        this.date = date;
        this.image = image;
        this.lat = lat;
        this.lng = lng;
    }

    // Generated toString method
    @Override
    public String toString() {
        return  title + '\'' +
                ", Price: £" + price
                ;
    }

    public byte[] getImage() { return image; }

    public void setImage(byte[] image) { this.image = image; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
