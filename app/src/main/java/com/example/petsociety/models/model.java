package com.example.petsociety.models;
public class model {
    private String gambar;
    private String   title;
    private int total,price;

    public model() {
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
