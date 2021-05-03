package com.example.androidmapsh.ui.map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidmapsh.database.Location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapViewModel extends ViewModel {

    private MutableLiveData<List<Location>> recommendations;
    private SaveLocationDialog saveLocationDialog;

    public MapViewModel() {
        recommendations = new MutableLiveData<>();
    }

    public LiveData<List<Location>> getRecommendation(){
        return recommendations;
    }

    public void updateRecommendations(Location[] results){
        recommendations.setValue(Arrays.asList(results));
    }

    public SaveLocationDialog getSaveLocationDialog() {
        return saveLocationDialog;
    }

    public void dismissSaveLocationDialog(){
        if(saveLocationDialog != null){
            saveLocationDialog.dismissDialog();
            saveLocationDialog = null;
        }
    }
}