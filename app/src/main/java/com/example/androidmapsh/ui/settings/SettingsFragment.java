package com.example.androidmapsh.ui.settings;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidmapsh.MainActivity;
import com.example.androidmapsh.R;
import com.example.androidmapsh.database.DatabaseManager;
import com.example.androidmapsh.database.Location;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;
    private MainActivity mainActivity;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //Linking with MainActivity
        mainActivity = (MainActivity) getActivity();
        settingsViewModel = mainActivity.getSettingsVM();

        //Creating View
        View root = inflater.inflate(R.layout.fragment_settings, container, false);



        //Initializing truncate button
        Button truncateButton = (Button) root.findViewById(R.id.truncate_button);
        //Setting listener for truncate button
        truncateButton.setOnClickListener(view1 -> {

            //Pass the truncate action to the handler
            mainActivity.execute(DatabaseManager.getInstance().
                    truncateTable(mainActivity.getHandler()));

        });

        SharedPreferences appSettingsPref = settingsViewModel.getSharedPreferences();
        SharedPreferences.Editor editor = appSettingsPref.edit();
        Boolean isNightMode = appSettingsPref.getBoolean("NightMode", false);

        //Initializing switch button
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch darModeSwitch = (Switch) root.findViewById(R.id.dark_mode_switch);
        darModeSwitch.setChecked(isNightMode);

        //Setting listener for dark mode switch
        darModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //Dark mode
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor.putBoolean("NightMode", true);
                } else {
                    //Light mode
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor.putBoolean("NightMode", false);
                }
                editor.apply();
            }
        });

        return root;
    }


}