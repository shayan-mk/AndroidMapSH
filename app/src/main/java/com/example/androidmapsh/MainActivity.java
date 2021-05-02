package com.example.androidmapsh;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mapbox.mapboxsdk.Mapbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private ExecutorService threadPool;

    private static final int BASE_MESSAGE_CODE = 1000;
    public static final int DB_LOCATION_LOAD = BASE_MESSAGE_CODE + 1;
    public static final int DB_LOCATION_INSERT = BASE_MESSAGE_CODE + 2;
    public static final int DB_LOCATION_DELETE = BASE_MESSAGE_CODE + 3;
    public static final int NET_SEARCH_RESULT = BASE_MESSAGE_CODE + 4;
    public static String MAPBOX_API_KEY = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        threadPool = Executors.newFixedThreadPool(5);
        MAPBOX_API_KEY = getString(R.string.access_token);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_bookmarks, R.id.navigation_map, R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

}