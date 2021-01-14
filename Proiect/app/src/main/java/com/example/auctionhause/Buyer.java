package com.example.auctionhause;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Buyer implements Serializable {

    private List<Item> sItems;
    private  String name;
    public static int contorIdBuy = 0;
    private int buyId;
    private String username,password,email;
    private boolean adult;


    public Buyer(String name, String email, String username, String password, boolean adult) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.adult = adult;
    }

    public Buyer(List<Item> sItems, String name, String username, String password, String email, boolean Major) {
        this.sItems = sItems;
        this.name = name;
        this.buyId = contorIdBuy++;
        this.username = username;
        this.password = password;
        this.email = email;
        this.adult = Major;
    }

    public List<Item> getsItems() {
        return sItems;
    }

    public void setsItems(List<Item> sItems) {
        this.sItems = sItems;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getAdult(){return adult;}

    public int getBuyId() {
        return buyId;
    }

    public void setBuyId(int buyId) {
        this.buyId = buyId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Buyer{" +
                "sItems=" + sItems +
                ", name='" + name + '\'' +
                ", buyId=" + buyId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", adult=" + adult +
                '}';
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }



}
