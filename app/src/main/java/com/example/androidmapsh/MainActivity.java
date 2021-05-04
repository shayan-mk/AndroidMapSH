package com.example.androidmapsh;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.androidmapsh.database.DatabaseManager;
import com.example.androidmapsh.database.Location;
import com.example.androidmapsh.ui.bookmarks.BookmarksViewModel;
import com.example.androidmapsh.ui.map.MapViewModel;
import com.example.androidmapsh.ui.settings.SettingsViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mapbox.mapboxsdk.Mapbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getName();

    private ExecutorService threadPool;
    private Handler handler;

    //View Models
    private BookmarksViewModel bookmarksVM;
    private MapViewModel mapVM;
    private SettingsViewModel settingsVM;

    //Defining Message Codes
    private static final int BASE_MESSAGE_CODE = 1000;
    public static final int DB_LOCATION_LOAD = BASE_MESSAGE_CODE + 1;
    public static final int DB_LOCATION_INSERT = BASE_MESSAGE_CODE + 2;
    public static final int DB_LOCATION_DELETE = BASE_MESSAGE_CODE + 3;
    public static final int NET_SEARCH_RESULT = BASE_MESSAGE_CODE + 4;
    public static final int DB_LOCATION_TRUNCATE = BASE_MESSAGE_CODE + 5;

    //Defining MapBox API Key
    public static String MAPBOX_API_KEY = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        threadPool = Executors.newFixedThreadPool(5);
        MAPBOX_API_KEY = getString(R.string.access_token);
        handler = new Handler(getMainLooper());

        DatabaseManager.initDatabaseManager(this);

        bookmarksVM = new ViewModelProvider(MainActivity.this).get(BookmarksViewModel.class);
        mapVM = new ViewModelProvider(MainActivity.this).get(MapViewModel.class);
        settingsVM = new ViewModelProvider(MainActivity.this).get(SettingsViewModel.class);
        initApp();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_bookmarks, R.id.navigation_map, R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                Log.d(TAG, "handleMessage: " + msg.what);
                switch (msg.what) {
                    case DB_LOCATION_LOAD:
                        Log.d(TAG, "handleMessage: " + msg.obj.getClass());
                        bookmarksVM.setBookmarks((Location[]) msg.obj);
                        break;
                    case DB_LOCATION_INSERT:
                        // TODO
                        break;
                    case DB_LOCATION_DELETE:
                        bookmarksVM.deleteBookmark((Location)msg.obj);
                        break;
                    case DB_LOCATION_TRUNCATE:
                        bookmarksVM.truncateCache();
                        break;
                    case NET_SEARCH_RESULT:
                        mapVM.updateRecommendations((Location[])msg.obj);
                        break;
                }
            }
        };
    }

    public void execute(Runnable runnable){
        threadPool.execute(runnable);
    }

    public Handler getHandler(){
        return handler;
    }

    public BookmarksViewModel getBookmarksVM() {
        return bookmarksVM;
    }

    public MapViewModel getMapVM() {
        return mapVM;
    }

    public SettingsViewModel getSettingsVM(){
        return settingsVM;
    }

    private void initApp(){
        SharedPreferences appSettingsPref = getSharedPreferences("AppSettingsPrefs", 0);
        settingsVM.setSharedPreferences(appSettingsPref);

        SharedPreferences.Editor editor = appSettingsPref.edit();
        Boolean isNightMode = appSettingsPref.getBoolean("NightMode", false);

        if(isNightMode){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

    }
}