package com.example.geartrader;

public class ListingModel {
    // Private //
    private int id;
    private String title;
    private int price;
    private double lat;
    private double lng;


    // Public //

    // Default Constructor
    public ListingModel() {
    }

    // Constructor for displaying the DbHelper method "getAllListings"
    public ListingModel(int id, String title, int price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }

    public ListingModel(int id, String title, int price, double lat, double lng) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.lat = lat;
        this.lng = lng;
    }

    // Generated toString method
    @Override
    public String toString() {
        return "ID: " + id +
                ", Title: '" + title + '\'' +
                ", Price: £" + price
                ;
    }

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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
