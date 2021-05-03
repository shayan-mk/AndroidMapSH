package com.example.androidmapsh.database;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

//Define table name
@Entity(tableName = "Location")
public class Location implements Serializable {
    private static List<Location> locations;

//    //Create id column
//    @PrimaryKey(autoGenerate = true)
//    private final int ID;
//    //TODO: setter?

    //Create name column
    @PrimaryKey
    @ColumnInfo(name = "name")
    @NotNull
    private final String name;

    //Create latitude column
    @ColumnInfo(name = "latitude")
    private final float latitude;

    //Create longitude column
    @ColumnInfo(name = "longitude")
    private final float longitude;

    public Location(@NotNull String name, float latitude, float longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

//    public int getID() {
//        return ID;
//    }

    public String getName() {
        return name;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

//    public void addLocation(Location location){
//        locations.add(location);
//    }
//
//    private Location getLocationById(int id){
//        for (Location location : locations) {
//            if(location.getID() == id)
//                return location;
//        }
//        return null;
//    }
//
//    private int getNumberOfLocations(){
//        return locations.size();
//    }
//
//    private List<Location> getAllLocations(){
//        return locations;
//    }
//
//    private void updateLocations(List<Location> locationList){
//        locations = locationList;
//    }
}
