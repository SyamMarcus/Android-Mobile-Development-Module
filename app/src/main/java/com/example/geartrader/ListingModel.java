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
}
