package com.example.androidmapsh.database;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

//Define table name
@Entity(tableName = "Location")
public class Location implements Serializable {
    private static List<Location> locations;

    //Create id column
    @PrimaryKey(autoGenerate = true)
    public int ID;

    //Create name column
    @ColumnInfo(name = "name")
    private final String name;

    //Create latitude column
    @ColumnInfo(name = "latitude")
    private final double lat;

    //Create longitude column
    @ColumnInfo(name = "longitude")
    private final double lng;

    public Location(String name, double lat, double lng) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public void addLocation(Location location){
        locations.add(location);
    }

    private Location getLocationById(int id){
        for (Location location : locations) {
            if(location.getID() == id)
                return location;
        }
        return null;
    }

    private int getNumberOfLocations(){
        return locations.size();
    }

    private List<Location> getAllLocations(){
        return locations;
    }

    private void updateLocations(List<Location> locationList){
        locations = locationList;
    }
}
