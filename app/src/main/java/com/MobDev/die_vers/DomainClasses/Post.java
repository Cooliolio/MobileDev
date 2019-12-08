package com.MobDev.die_vers.DomainClasses;

import android.location.Location;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Date;

public class Post {

    private String id;
    private String title;
    private String price;
    private Date date;
    private String description;
    private ArrayList<String> imageUrls;
    private String category;


    private String userId; // Naam van poster and rating
    //From user
    private String postcode;


    public Post(String title, String price, Date date, String description, ArrayList<String> imageUrls, String userId, String postcode, String category) {
        this.title = title;
        this.price = price;
        this.date = date;
        this.description = description;
        this.imageUrls = imageUrls;
        this.userId = userId;
        this.postcode = postcode;
        this.category = category;
    }

    public Post(String post_title, String price, ArrayList<String> imageUrls) {
        this.title = post_title;
        this.price = price;
        this.imageUrls = imageUrls;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}