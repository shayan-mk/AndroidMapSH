package com.example.androidmapsh.map;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.androidmapsh.MainActivity;
import com.example.androidmapsh.R;
import com.example.androidmapsh.database.Location;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkManager {
    private static NetworkManager instance = null;
    private static final String TAG = "NetworkManager";

    private NetworkManager() {
    }

    public static NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }

    public Runnable loadSearchResults(String name, Handler handler) {
        return () -> runLoadCrypto(name, handler);
    }

    private void runLoadCrypto(String name, Handler handler) {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("access_token", MainActivity.MAPBOX_API_KEY);
        String rawUrl = "https://api.mapbox.com/geocoding/v5/mapbox.places/" + name + ".json";
        String url = buildURL(rawUrl, parameters);

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
                    String responseString = response.body().string();
                    Log.d(TAG, "onResponse: " + responseString);
                    JsonObject obj = JsonParser.parseString(responseString).getAsJsonObject();
                    Log.d(TAG, "onResponse: " + obj.get("data").toString());
                    Log.d(TAG, "onResponse: " + obj.toString());
                    String dataJson = obj.get("data").toString();
                    Gson gson = new Gson();
                    Location[] cryptoData = gson.fromJson(dataJson, Location[].class);
                    Message message = new Message();
                    message.what = MainActivity.NET_SEARCH_RESULT;
                    message.arg1 = 1;
                    message.obj = cryptoData;
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
}

