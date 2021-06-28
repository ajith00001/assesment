package com.example.retrofitpractice.modelandentity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "entity_result")
public class EntityResult {

    public EntityResult() {
        this.mfrId = 1;
        this.country = "India";
        this.mfrName = "KIA";
        this.vehicleCount = 1;
    }

    public EntityResult(int mfrId, String country, String mfrName, int vehicleCount) {
        this.mfrId = mfrId;
        this.country = country;
        this.mfrName = mfrName;
        this.vehicleCount = vehicleCount;
    }

    public int getMfrId() {
        return mfrId;
    }

    public void setMfrId(int mfrId) {
        this.mfrId = mfrId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getMfrName() {
        return mfrName;
    }

    public void setMfrName(String mfrName) {
        this.mfrName = mfrName;
    }

    public int getVehicleCount() {
        return vehicleCount;
    }

    public void setVehicleCount(int vehicleCount) {
        this.vehicleCount = vehicleCount;
    }

    @PrimaryKey
    @ColumnInfo(name = "mfr_id")
    public int mfrId;

    @ColumnInfo(name = "country")
    public String country;

    @ColumnInfo(name = "mfr_name")
    public String mfrName;

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @ColumnInfo(name = "vehicle_count")
    public int vehicleCount;

    @ColumnInfo(name = "image",typeAffinity = ColumnInfo.BLOB)
    public byte[] image;

    @ColumnInfo(name = "image_status")
    public boolean imageStatus=false;

    public boolean isImageStatus() {
        return imageStatus;
    }

    public void setImageStatus(boolean imageStatus) {
        this.imageStatus = imageStatus;
    }

    public static class PostClass {

        @SerializedName("INPUT")
        private String input;

        public PostClass() {
            this.input = "TestIp";
        }

        public PostClass(String input) {
            this.input = input;
        }

        public String getInput() {
            return input;
        }

        public void setInput(String input) {
            this.input = input;
        }
    }
}
