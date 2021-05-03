package com.example.androidmapsh.ui.map;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidmapsh.database.Location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapViewModel extends ViewModel {

    private static final String TAG = MapViewModel.class.getName();
//    private MutableLiveData<List<Location>> recommendations;
    private SaveLocationDialog saveLocationDialog;
    private RecommendationListAdapter rla;

    public MapViewModel() {
//        recommendations = new MutableLiveData<>();
//        recommendations.setValue(new ArrayList<>());
    }

//    public LiveData<List<Location>> getRecommendation(){
//        return recommendations;
//    }


    public void setRla(RecommendationListAdapter rla) {
        this.rla = rla;
    }

    public void updateRecommendations(Location[] results){
//        Log.d(TAG, "updateRecommendations: " + results);
//        recommendations.getValue().clear();
//        recommendations.getValue().addAll(Arrays.asList(results));
//        Log.d(TAG, "updateRecommendations: " + recommendations.getValue().get(0).getName());
        rla.loadRecommendations(Arrays.asList(results));

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