package com.example.androidmapsh.database;

import android.os.Handler;
import android.os.Message;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.androidmapsh.MainActivity;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

public class DatabaseManager {


    private static RoomDB database = RoomDB.getDatabase();
    private static DatabaseManager databaseManager = null;

    public Runnable loadLocationList( Handler handler) {
        return () -> {
            List<Location> locationDataList = loadLocations();
            Message message = new Message();
            message.what = MainActivity.DB_LOCATION_LOAD;
            message.arg1 = 1;
            message.obj = locationDataList;
            handler.sendMessage(message);
        };
    }

    public Runnable insertLocation( Location location, Handler handler) {
        return () -> {
            insertLocation(location);
            Message message = new Message();
            message.what = MainActivity.DB_LOCATION_INSERT;
            message.arg1 = 1;
            handler.sendMessage(message);
        };
    }

    public Runnable deleteLocation( Location location, Handler handler) {
        return () -> {
            deleteLocation(location);
            Message message = new Message();
            message.what = MainActivity.DB_LOCATION_DELETE;
            message.arg1 = 1;
            message.obj = location;
            handler.sendMessage(message);
        };
    }

    private synchronized void insertLocation(Location location){
        database.mainDao().insert(location);
    }

    private synchronized void deleteLocation(Location location){
        database.mainDao().delete(location);
    }

    private List<Location> loadLocations(){
        return database.mainDao().getAll();
    }

    public static DatabaseManager getInstance(){
        if(databaseManager == null){
            databaseManager = new DatabaseManager();
        }
        return databaseManager;
    }
}
