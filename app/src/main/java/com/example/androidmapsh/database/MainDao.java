package com.example.androidmapsh.database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MainDao {
    //Insert query
    @Insert(onConflict = REPLACE)
    void insert(Location location);

    //Delete query
    @Delete
    void delete(Location location);

    //Delete all queries
    @Query("DELETE FROM Location")
    void deleteAll();

//    //Update query
//    @Query("UPDATE Location SET name = :sName WHERE ID = :sID")
//    void update(int sID, String sName);

    //Get all data query
    @Query("SELECT * FROM Location")
    List<Location> getAll();

}
