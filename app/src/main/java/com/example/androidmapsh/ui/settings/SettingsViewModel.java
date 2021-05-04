package com.example.androidmapsh.ui.settings;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidmapsh.ui.map.MapFragment;

public class SettingsViewModel extends ViewModel {
    public static final String TAG = SettingsViewModel.class.getName();
    private SharedPreferences sharedPreferences;

    public SettingsViewModel() {

    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        Log.d(TAG, "setSharedPreferences: " + sharedPreferences);
        this.sharedPreferences = sharedPreferences;
    }

    public SharedPreferences getSharedPreferences() {
        Log.d(TAG, "getSharedPreferences: " + sharedPreferences);
        return sharedPreferences;
    }
}