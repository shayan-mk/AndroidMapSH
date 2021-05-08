package com.example.androidmapsh.database;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.androidmapsh.MainActivity;

import java.util.List;

public class DatabaseManager {
    public static final String TAG = DatabaseManager.class.getName();

    private static RoomDB database;
    private static DatabaseManager databaseManager = null;

    public Runnable loadLocationList( Handler handler) {
        return () -> {
            List<Location> locationDataList = loadLocations();
            Message message = new Message();
            message.what = MainActivity.DB_LOCATION_LOAD;
            message.arg1 = 1;
            Location[] locations = new Location[locationDataList.size()];
            message.obj = locationDataList.toArray(locations);
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

    public Runnable truncateTable(Handler handler){
        return () -> {
            truncateTable();
            Message message = new Message();
            message.what = MainActivity.DB_LOCATION_TRUNCATE;
            message.arg1 = 1;
            handler.sendMessage(message);
        };
    }

    private synchronized void insertLocation(Location location){
        database.mainDao().insert(location);
    }

    private synchronized void deleteLocation(Location location){
        database.mainDao().delete(location);
    }

    private synchronized void truncateTable(){
        database.mainDao().deleteAll();
    }

    private List<Location> loadLocations(){
        Log.d(TAG, "loadLocations: database:" + database);
        Log.d(TAG, "loadLocations: dao:" + database.mainDao());
        return database.mainDao().getAll();
    }

    public static DatabaseManager getInstance(){
        if(databaseManager == null){
            databaseManager = new DatabaseManager();
        }
        return databaseManager;
    }

    public static void initDatabaseManager(Context context){
        databaseManager = new DatabaseManager();
        database = RoomDB.getInstance(context);
    }
}
