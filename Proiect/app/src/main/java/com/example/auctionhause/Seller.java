package com.example.auctionhause;

import android.widget.SectionIndexer;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Seller  implements Serializable {

    private List<Item> sItems;
    private  String name;
    public static int contorIdSel = 0;
    private int selId;
    private String username,password,email;

    public Seller(String name, String email, String username, String password ) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Seller(List<Item> sItems, String name, String username, String password, String email) {
        this.sItems = sItems;
        this.name = name;
        this.selId = contorIdSel++;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public void addItem(Item i )
    {
        this.sItems.add(i);
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

    public int getSelId() {
        return selId;
    }

    public void setSelId(int selId) {
        this.selId = selId;
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
        return "Seller{" +
                "sItems=" + sItems +
                ", name='" + name + '\'' +
                ", selId=" + selId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
