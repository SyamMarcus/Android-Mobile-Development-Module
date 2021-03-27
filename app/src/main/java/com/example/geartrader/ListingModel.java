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
    private String category;


    // Public //

    // Default Constructor
    public ListingModel() {
    }

    // Constructor for displaying the DbHelper method "getAllListings"
    public ListingModel(int id, String title, float price, String category) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.category = category;
    }

    public ListingModel(int id, String title, float price, String summary, String date,
                        byte[] image, double lat, double lng, String category) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.summary = summary;
        this.date = date;
        this.image = image;
        this.lat = lat;
        this.lng = lng;
        this.category = category;
    }

    // Generated toString method
    @Override
    public String toString() {
        return  title +  ", £" + price + ", " + category;
    }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

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
