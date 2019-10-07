package com.MobDev.die_vers.DomainClasses;

import android.location.Location;

import java.util.ArrayList;
import java.util.Date;

public class Post {

    private String id;
    private String title;
    private boolean priced; //true == prijs | false == prijs overeenkomen
    private double price;
    private double[] bets;
    private Location location;
    private int user_id; // Naam van poster and rating
    private Date date;
    private String description;
    private ArrayList<String> imageUrls; // KAN vervangen worden door STRING[]

    public Post(String post_id, String post_title, boolean priced, double price, double[] bets, Location location, int user_id, Date post_date, String post_description, ArrayList<String> imageUrls) {
        this.id = post_id;
        this.title = post_title;
        this.priced = priced;
        this.price = price;
        this.bets = bets;
        this.location = location;
        this.user_id = user_id;
        this.date = post_date;
        this.description = post_description;
        this.imageUrls = imageUrls;
    }
    public Post(String post_title, double price, ArrayList<String> imageUrls, Location location) {
        this.title = post_title;
        this.price = price;
        this.imageUrls = imageUrls;
        this.location = location;
    }
    public Post() {
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isPriced() {
        return priced;
    }

    public void setPriced(boolean priced) {
        this.priced = priced;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double[] getBets() {
        return bets;
    }

    public void setBets(double[] bets) {
        this.bets = bets;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(ArrayList<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

}