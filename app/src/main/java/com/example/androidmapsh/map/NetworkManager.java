package com.example.androidmapsh.map;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.androidmapsh.MainActivity;
import com.example.androidmapsh.database.Location;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkManager {
    private static NetworkManager instance = null;
    private static final String TAG = "NetworkManager";
    private static final String API_URL = "https://api.mapbox.com/geocoding/v5/mapbox.places/";

    private NetworkManager() {
    }

    public static NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }

    public Runnable loadSearchResults(String name, Handler handler) {
        return () -> runLoadSearchResults(name, handler);
    }

    private void runLoadSearchResults(String name, Handler handler) {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("access_token", MainActivity.MAPBOX_API_KEY);
        String correctedName = name.replaceAll(" ", "%20") + ".json";
        String url = buildURL(API_URL + correctedName, parameters);

        final Request request = new Request.Builder().url(url).build();

        OkHttpClient okHttpClient = new OkHttpClient();
        Log.d(TAG, "request: " + request.toString());
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.v("TAG", e.getMessage());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                if (!response.isSuccessful()) {
                    Message message = new Message();
                    message.what = MainActivity.NET_SEARCH_RESULT;
                    message.arg1 = 0;
                    handler.sendMessage(message);
                    throw new IOException("Unexpected code " + response);
                } else {
                    String responseJson = response.body().string();
                    Location[] locations = parseResponse(responseJson);
                    Message message = new Message();
                    message.what = MainActivity.NET_SEARCH_RESULT;
                    message.arg1 = 1;
                    message.obj = locations;
                    handler.sendMessage(message);
                }
            }
        });
    }

    private String buildURL(String string, HashMap<String, String> queryParameters) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(string).newBuilder();

        for (String param : queryParameters.keySet()) {
            urlBuilder.addQueryParameter(param, queryParameters.get(param));
        }
        String url = urlBuilder.build().toString();
        Log.d(TAG, "buildURL: " + url);

        return url;

    }

    private Location[] parseResponse (String json) {
        List<Location> locations = new ArrayList<>();
        JsonObject root = JsonParser.parseString(json).getAsJsonObject();
        JsonArray locationsArray = root.getAsJsonArray("features");
        for (JsonElement element : locationsArray) {
            JsonObject object = element.getAsJsonObject();
            String name = object.get("place_name").getAsString();
            JsonArray coordinates = object.getAsJsonArray("center");
            float longitude = coordinates.get(0).getAsFloat();
            float latitude = coordinates.get(1).getAsFloat();
            locations.add(new Location(name, latitude, longitude));
        }

        Location[] result = new Location[locations.size()];
        locations.toArray(result);
        return result;

    }
}





