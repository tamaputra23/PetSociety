package com.example.petsociety.models;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;
public class NeedsCheckout {
    public String uid;
    public String author;
    public String yourorder;
    public String jumlah;
    public String total;
    public String price_est;
    public String menu;
    public NeedsCheckout(){}

    public NeedsCheckout(String uid, String author, String yourorder, String jumlah, String total, String price_est, String menu) {
        this.uid = uid;
        this.author = author;
        this.yourorder = yourorder;
        this.jumlah = jumlah;
        this.total = total;
        this.price_est = price_est;
        this.menu = menu;
    }
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("yourorder", yourorder);
        result.put("jumlah", jumlah);
        result.put("total", total);
        result.put("price_est", price_est);
        result.put("menu", menu);
        return result;
    }
}