package com.example.retrofitpractice;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.retrofitpractice.modelandentity.Users;

import java.util.List;

@Dao
public interface UsersDAO {

    @Query("SELECT * from users WHERE email_id = :userEmail AND user_password= :userPassword")
    List<Users> getUser(String userEmail, String userPassword);

    @Insert
    void insertUser(Users users);
}
