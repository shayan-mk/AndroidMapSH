package com.example.androidmapsh.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
        //Setting listeners of truncate buttons
        truncateButton.setOnClickListener(view1 -> {
            mainActivity.execute(DatabaseManager.);


        });


        return root;
    }
}