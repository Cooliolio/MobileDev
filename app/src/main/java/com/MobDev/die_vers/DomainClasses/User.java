package com.MobDev.die_vers.DomainClasses;

public class User {
    private int id;
    private String postcode;

    public User(int id, String postcode) {
        this.id = id;
        this.postcode = postcode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
