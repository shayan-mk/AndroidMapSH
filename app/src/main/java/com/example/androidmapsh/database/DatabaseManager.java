package com.example.androidmapsh.database;

import android.os.Handler;
import android.os.Message;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

public class DatabaseManager {


    private static RoomDB database = RoomDB.getDatabase();
    private static List<Location> locations;




    //public Runnable insertLocation()

    public synchronized void insertLocation(Location location){
        database.mainDao().insert(location);
    }

    public synchronized void deleteLocation(Location location){
        database.mainDao().delete(location);
    }


    public synchronized void updateLocation(int sID, String sName){
        database.mainDao().update(sID, sName);
    }

    public List<Location> loadLocations(){
        return database.mainDao().getAll();
    }
}
