package com.example.retrofitpractice;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.retrofitpractice.modelandentity.EntityResult;

import java.util.List;

@Dao
public interface EntityResultDAO {

    @Query("SELECT * FROM entity_result")
    List<EntityResult> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EntityResult entityResult);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<EntityResult> entityResults);

    @Query("DELETE FROM entity_result")
    void deleteAll();
}
