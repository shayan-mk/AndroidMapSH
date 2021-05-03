package com.example.androidmapsh.ui.map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class MapViewModel extends ViewModel {

    private MutableLiveData<List<String>> recommendations;

    public MapViewModel() {
        recommendations = new MutableLiveData<List<String>>();
    }

    public MutableLiveData<List<String>> getRecommendation(){
        return recommendations;
    }

    public void updateRecommendations(String text){

    }
}