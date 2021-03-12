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

    public ListingModel(int id, String title, int price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }

    // Generated toString method
    @Override
    public String toString() {
        return "ListingModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                '}';
    }
}
