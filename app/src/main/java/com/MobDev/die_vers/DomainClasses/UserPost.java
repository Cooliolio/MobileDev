package com.MobDev.die_vers.DomainClasses;

import java.util.Date;

public class UserPost {

    private int post_id;
    private boolean priced; //true == prijs | false == prijs overeenkomen
    private double price;
    private double[] bets;
    //private location;
    private int user_id; // Naam van poster and rating
    private Date post_date;
    private String post_description;
    private String[] imageUrls;

    public UserPost(int post_id, boolean priced, double price, double[] bets, int user_id, Date post_date, String post_description, String[] imageUrls) {
        this.post_id = post_id;
        this.priced = priced;
        this.price = price;
        this.bets = bets;
        this.user_id = user_id;
        this.post_date = post_date;
        this.post_description = post_description;
        this.imageUrls = imageUrls;
    }

    public UserPost() {
    }

    public double[] getBets() {
        return bets;
    }

    public void setBets(double[] bets) {
        this.bets = bets;
    }

    public String[] getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(String[] imageUrls) {
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
