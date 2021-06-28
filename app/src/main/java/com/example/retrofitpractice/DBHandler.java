package com.example.retrofitpractice;

import android.annotation.SuppressLint;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.retrofitpractice.modelandentity.EntityResult;
import com.example.retrofitpractice.modelandentity.Users;

@Database(entities = {EntityResult.class, Users.class},
        version = 1
)
public abstract class DBHandler extends RoomDatabase {
    public abstract EntityResultDAO entityResultDAO();

    public abstract UsersDAO usersDAO();

}
