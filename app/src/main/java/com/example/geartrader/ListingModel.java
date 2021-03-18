package com.example.geartrader;

public class ListingModel {
    // Private //
    private int id;
    private String title;
    private int price;


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

    // Generated toString method
    @Override
    public String toString() {
        return "ID: " + id +
                ", Title: '" + title + '\'' +
                ", Price: £" + price
                ;
    }
}
