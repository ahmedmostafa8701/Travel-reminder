package com.example.travelreminder.model;

import com.google.gson.annotations.SerializedName;

public class City {
    @SerializedName("city")
    private String name;

    @SerializedName("countryCode")
    private String countryCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public String toString() {
        return name + ", " + countryCode;
    }
}
