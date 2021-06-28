package com.example.retrofitpractice.modelandentity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {
    public Result() {
    }

    public Result(String country, Object mfrCommonName, Integer mfrID, String mfrName, List<VehicleType> vehicleTypes) {
        this.country = country;
        this.mfrCommonName = mfrCommonName;
        this.mfrID = mfrID;
        this.mfrName = mfrName;
        this.vehicleTypes = vehicleTypes;
    }

    @SerializedName("Country")
    @Expose
    private String country;
    @SerializedName("Mfr_CommonName")
    @Expose
    private Object mfrCommonName;
    @SerializedName("Mfr_ID")
    @Expose
    private Integer mfrID;
    @SerializedName("Mfr_Name")
    @Expose
    private String mfrName;
    @SerializedName("VehicleTypes")
    @Expose
    private List<VehicleType> vehicleTypes = null;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Object getMfrCommonName() {
        return mfrCommonName;
    }

    public void setMfrCommonName(Object mfrCommonName) {
        this.mfrCommonName = mfrCommonName;
    }

    public Integer getMfrID() {
        return mfrID;
    }

    public void setMfrID(Integer mfrID) {
        this.mfrID = mfrID;
    }

    public String getMfrName() {
        return mfrName;
    }

    public void setMfrName(String mfrName) {
        this.mfrName = mfrName;
    }

    public List<VehicleType> getVehicleTypes() {
        return vehicleTypes;
    }

    public void setVehicleTypes(List<VehicleType> vehicleTypes) {
        this.vehicleTypes = vehicleTypes;
    }

}