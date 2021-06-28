package com.example.retrofitpractice.modelandentity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VehicleType {

    @SerializedName("IsPrimary")
    @Expose
    private Boolean isPrimary;
    @SerializedName("Name")
    @Expose
    private String name;

    public Boolean getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(Boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}