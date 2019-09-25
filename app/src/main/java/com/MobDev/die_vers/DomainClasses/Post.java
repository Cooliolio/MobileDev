package com.MobDev.die_vers.DomainClasses;

import android.location.Location;

import java.util.ArrayList;
import java.util.Date;

public class Post {

    private int post_id;
    private String post_title;
    private boolean priced; //true == prijs | false == prijs overeenkomen
    private double price;
    private double[] bets;
    private Location location;
    private int user_id; // Naam van poster and rating
    private Date post_date;
    private String post_description;
    private ArrayList<String> imageUrls; // KAN vervangen worden door STRING[]

    public Post(int post_id, String post_title, boolean priced, double price, double[] bets, Location location, int user_id, Date post_date, String post_description, ArrayList<String> imageUrls) {
        this.post_id = post_id;
        this.post_title = post_title;
        this.priced = priced;
        this.price = price;
        this.bets = bets;
        this.location = location;
        this.user_id = user_id;
        this.post_date = post_date;
        this.post_description = post_description;
        this.imageUrls = imageUrls;
    }

    public Post(String post_title, double price, ArrayList<String> imageUrls) {
        this.post_title = post_title;
        this.price = price;
        this.imageUrls = imageUrls;
    }

    public Post() {
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public double[] getBets() {
        return bets;
    }

    public void setBets(double[] bets) {
        this.bets = bets;
    }

    public ArrayList<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(ArrayList<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Date getPost_date() {
        return post_date;
    }

    public void setPost_date(Date post_date) {
        this.post_date = post_date;
    }

    public String getPost_description() {
        return post_description;
    }

    public void setPost_description(String post_description) {
        this.post_description = post_description;
    }
}
