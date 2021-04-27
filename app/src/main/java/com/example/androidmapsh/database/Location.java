package com.example.androidmapsh.database;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

//Define table name
@Entity(tableName = "Location")
public class Location implements Serializable {
    //Create id column
    @PrimaryKey(autoGenerate = true)
    private int ID;

    //Create name column
    @ColumnInfo(name = "name")
    private final String name;

    //Create latitude column
    @ColumnInfo(name = "latitude")
    private final float latitude;

    //Create longitude column
    @ColumnInfo(name = "longitude")
    private final float longitude;

    public Location(String name, float latitude, float longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }
}
