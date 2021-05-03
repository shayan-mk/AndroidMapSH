package com.example.androidmapsh.database;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.androidmapsh.MainActivity;
import com.example.androidmapsh.ui.map.MapFragment;

import java.net.ContentHandler;
import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;
import static com.example.androidmapsh.ui.map.MapFragment.TAG;

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

    private synchronized void insertLocation(Location location){
        database.mainDao().insert(location);
    }

    private synchronized void deleteLocation(Location location){
        database.mainDao().delete(location);
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

    public static DatabaseManager initDatabaseManager(Context context){
        databaseManager = new DatabaseManager();
        database = RoomDB.getInstance(context);
        return databaseManager;
    }
}
