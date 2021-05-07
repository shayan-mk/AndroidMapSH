package com.example.androidmapsh.ui.map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidmapsh.MainActivity;
import com.example.androidmapsh.R;
import com.example.androidmapsh.database.Location;
import com.example.androidmapsh.map.NetworkManager;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.android.gestures.AndroidGesturesManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.LocationComponentOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.maps.UiSettings;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapFragment extends Fragment implements OnMapReadyCallback, PermissionsListener, RecommendationListAdapter.OnItemClickListener {
    public static final String TAG = MapFragment.class.getName();
    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;
    private static final int RESULT_OK = -1;

    private MapViewModel mapViewModel;
    private MainActivity mainActivity;
    private PermissionsManager permissionsManager;
    private MapboxMap mapboxMap;
    private MapView mapView;
    private EditText editText;
    private ImageView location;
    private LatLng lastClickedPoint;
    private long lastClickedTime;
    private View root;
    private boolean isTyping;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: map is creating" + mapView);
        mainActivity = (MainActivity) getActivity();
        mapViewModel = mainActivity.getMapVM();
        Mapbox.getInstance(mainActivity, getString(R.string.access_token));
        root = inflater.inflate(R.layout.fragment_map, container, false);

        mapView = root.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        //-------------------------------------------------------------------------------------

        isTyping = true;
        editText = root.findViewById(R.id.edit_text);
        //TODO: search using voice
        Button micButton = root.findViewById(R.id.button);
        RecyclerView rcView = root.findViewById(R.id.list_view);
        location = (ImageView)  root.findViewById(R.id.image_view);
        location.setImageResource(R.drawable.ic_location_black);

        RecommendationListAdapter recommendationListAdapter = new RecommendationListAdapter(mainActivity, this);
        rcView.setAdapter(recommendationListAdapter);
        rcView.setLayoutManager(new LinearLayoutManager(mainActivity));
        mapViewModel.setRla(recommendationListAdapter);

        Button currentLocButton = root.findViewById(R.id.current_loc_button);
        currentLocButton.setOnClickListener(v -> goToCurrentLoc());
        micButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "onTextChanged: " + s);
                if (isTyping) {
                    mainActivity.execute(NetworkManager.getInstance()
                            .loadSearchResults(s.toString(), mainActivity.getHandler()));
                }
                isTyping = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "afterTextChanged: " + s);
            }
        });

//        root.setOnCapturedPointerListener(new View.OnCapturedPointerListener() {
//            @Override
//            public boolean onCapturedPointer (View view, MotionEvent motionEvent) {
//                // Get the coordinates required by your app
//                float horizontalOffset = motionEvent.getX();
//                float verticalOffset = motionEvent.getY();
//                Log.d(TAG, "onCapturedPointer: " + horizontalOffset + " " + verticalOffset);
//                location.setX(horizontalOffset);
//                location.setY(verticalOffset);
//
//                return true;
//            }
//        });
//
//        root.setOnTouchListener(new View.OnTouchListener() {
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_DOWN){
//                    // Get the coordinates required by your app
//
//                }
//                float horizontalOffset = event.getX();
//                float verticalOffset = event.getY();
//                Log.d(TAG, "onCapturedPointer: " + horizontalOffset + " " + verticalOffset);
//                location.setX(horizontalOffset);
//                location.setY(verticalOffset);
//                return true;
//            }
//        });

        root.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    // Get the coordinates required by your app

                }
                float horizontalOffset = event.getX();
                float verticalOffset = event.getY();
                Log.d(TAG, "onCapturedPointer: " + horizontalOffset + " " + verticalOffset);
                location.setX(horizontalOffset);
                location.setY(verticalOffset);
                return true;
            }
        });

        return root;
    }

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        MapFragment.this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(Style.MAPBOX_STREETS, this::enableLocationComponent);
        mapboxMap.addOnMapLongClickListener(point -> {
            Log.d(TAG, "onMapReady: hold" + point.getLatitude() + point.getLongitude());
            //TODO drop red pin
            SaveLocationDialog saveBookmark = new SaveLocationDialog(point.getLatitude(), point.getLongitude());
            saveBookmark.show(mainActivity.getSupportFragmentManager(), "SaveLocation");
            return true;
        });


        UiSettings uiSettings = mapboxMap.getUiSettings();
        uiSettings.setDoubleTapGesturesEnabled(false);
    }

    @SuppressWarnings({"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(mainActivity)) {

            // Enable the most basic pulsing styling by ONLY using
            // the `.pulseEnabled()` method
            LocationComponentOptions customLocationComponentOptions = LocationComponentOptions.builder(mainActivity)
                    .pulseEnabled(true)
                    .build();

            // Get an instance of the component
            LocationComponent locationComponent = mapboxMap.getLocationComponent();

            // Activate with options
            locationComponent.activateLocationComponent(
                    LocationComponentActivationOptions.builder(mainActivity, loadedMapStyle)
                            .locationComponentOptions(customLocationComponentOptions)
                            .build());

            // Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);

            // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);

            // Set the component's render mode
            locationComponent.setRenderMode(RenderMode.NORMAL);
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(mainActivity);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(mainActivity, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapboxMap.getStyle(this::enableLocationComponent);
        } else {
            Toast.makeText(mainActivity, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            mainActivity.finish();
        }
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart: " + mapView);
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop: " + mapView);
        super.onStop();
        mapView.onStop();
    }


    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: " + mapView);
        super.onDestroy();
        if (mapView != null)
            mapView.onDestroy();
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume: " + mapView);
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause: " + mapView);
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(@NotNull Bundle outState) {
        Log.d(TAG, "onSaveInstanceState: " + mapView);
        super.onSaveInstanceState(outState);
        if (mapView != null)
            mapView.onSaveInstanceState(outState);
    }


    @Override
    public void onItemClick(String name, double lat, double lng) {
        goToLocation(lat, lng);
        isTyping = false;
        editText.setText(name);
        mapViewModel.updateRecommendations(null);
        mainActivity.hideSoftKeyboard();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: " + mapView);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: " + mapView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: " + mapView);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d(TAG, "onViewStateRestored: " + mapView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: " + mapView);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: " + mapView);
    }

    public void goToLocation(double lat, double lng) {
        CameraPosition old = mapboxMap.getCameraPosition();
        CameraPosition pos = new CameraPosition.Builder()
                .target(new LatLng(lat,lng))
                .zoom(old.zoom)
                .tilt(old.tilt)
                .build();
        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(pos),1000);
    }

    public void goToCurrentLoc() {
        android.location.Location lastKnownLocation = mapboxMap.getLocationComponent().getLastKnownLocation();
        double lat = lastKnownLocation.getLatitude();
        double lng = lastKnownLocation.getLongitude();
        goToLocation(lat, lng);

    }

    private void speak(){
        //intent to show speech
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say the name of the location");

        //start intent
        try {
            //show dialog
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
            Log.d(TAG, "speak: ");
        }
        catch (Exception e){
            //if there was some error
            //get message of error and show
            Toast.makeText(mainActivity, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: " + data);
        switch (requestCode){
            case REQUEST_CODE_SPEECH_INPUT:{
                if (resultCode == RESULT_OK && null!=data){
                    //get text array from voice intent
                    ArrayList<String> result = data.
                            getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    editText.setText(result.get(0));
                }
                break;
            }
        }
    }
}




