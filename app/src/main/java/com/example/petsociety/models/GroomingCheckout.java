package com.example.petsociety.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;
public class GroomingCheckout {
    public String uid;
    public String author;
    public String petname;
    public String tpservice;
    public String price_est;
    public String delivery;
    public String menu;
    public GroomingCheckout(){}

    public GroomingCheckout(String uid, String author, String petname, String tpservice, String price_est, String delivery, String menu) {
        this.uid = uid;
        this.author = author;
        this.petname = petname;
        this.tpservice = tpservice;
        this.price_est = price_est;
        this.delivery = delivery;
        this.menu = menu;
    }
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("petname", petname);
        result.put("tpservice", tpservice);
        result.put("price_est", price_est);
        result.put("delivery", delivery);
        result.put("menu", menu);
        return result;
    }
}
