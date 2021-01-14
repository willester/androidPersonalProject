package com.example.auctionhause;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

public class Item implements Serializable {

    private String name;
    private String category;
    private long minPrice;
    private Date dueDate;
    public static int contorId = 0;
    private int prodId;
    private PlaceOrigin origin;



    public Item(String name, String category, long minPrice, Date dueDate,PlaceOrigin or) {
        this.name = name;
        this.category = category;
        this.minPrice = minPrice;
        this.dueDate = dueDate;
        this.prodId = contorId++;
        origin = or;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(long minPrice) {
        this.minPrice = minPrice;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public int getProdId() {
        return prodId;
    }

    public void setProdId(int prodId) {
        this.prodId = prodId;
    }

    public PlaceOrigin getOrigin() {
        return origin;
    }

    public void setOrigin(PlaceOrigin origin) {
        this.origin = origin;
    }

    @Override
    public String toString() {
        return
                "Name: " + name + '\'' +
                " Category: " + category + '\'' +
                " DueDate: " + dueDate +
                " " + origin ;
    }
}
