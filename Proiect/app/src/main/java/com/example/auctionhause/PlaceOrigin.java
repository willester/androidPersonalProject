package com.example.auctionhause;

import java.io.Serializable;

public class PlaceOrigin implements Serializable {

    private String Country;
    private String City;
    private String Continent;


    public PlaceOrigin(String country, String city, String continent) {
        Country = country;
        City = city;
        Continent = continent;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getContinent() {
        return Continent;
    }

    public void setContinent(String continent) {
        Continent = continent;
    }

    @Override
    public String toString() {
        return
                "Country:'" + Country + '\'' +
                ", City:'" + City + '\'' +
                ", Continent:'" + Continent + '\'';
    }




}
