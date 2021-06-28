package com.example.retrofitpractice.modelandentity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class Users {
    @PrimaryKey
    @ColumnInfo(name = "email_id")
    @NonNull
    public String emailID;

    @ColumnInfo(name = "user_name")
    public String userName;

    @ColumnInfo(name = "user_password")
    public String userPassword;

    public Users(@NonNull String emailID, String userName, String userPassword) {
        this.emailID = emailID;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    @NonNull
    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(@NonNull String emailID) {
        this.emailID = emailID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
