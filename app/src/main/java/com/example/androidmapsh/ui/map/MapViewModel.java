package com.example.androidmapsh.ui.map;

import androidx.lifecycle.ViewModel;

import com.example.androidmapsh.database.Location;

import java.util.ArrayList;
import java.util.Arrays;

public class MapViewModel extends ViewModel {

    private static final String TAG = MapViewModel.class.getName();
    private SaveLocationDialog saveLocationDialog;
    private RecommendationListAdapter rla;
    private boolean hasStart;
    private double startLat;
    private double startLng;

    public MapViewModel() {
        hasStart = false;
    }

    public boolean hasStart() {
        return hasStart;
    }

    public double getStartLat() {
        return startLat;
    }

    public double getStartLng() {
        return startLng;
    }

    public void startFromCurrentLoc() {
        hasStart = false;
    }

    public void setStart(double lat, double lng) {
        hasStart = true;
        startLat = lat;
        startLng = lng;
    }
    public void setRla(RecommendationListAdapter rla) {
        this.rla = rla;
    }

    public void updateRecommendations(Location[] results){
        if(results != null)
            rla.loadRecommendations(Arrays.asList(results));
        else
            rla.loadRecommendations(new ArrayList<>());
    }

    public SaveLocationDialog getSaveLocationDialog() {
        return saveLocationDialog;
    }

    public void setSaveLocationDialog(SaveLocationDialog saveLocationDialog) {
        this.saveLocationDialog = saveLocationDialog;
    }

    public void dismissSaveLocationDialog(){
        if(saveLocationDialog != null){
            saveLocationDialog.dismissDialog();
            saveLocationDialog = null;
        }
    }
}