package com.example.androidmapsh.database;

import android.os.Handler;
import android.os.Message;

import java.util.List;

public class DatabaseManager {


    private static RoomDB database;
    private static List<Location> locations;


    public Runnable loadLocationList(String symbol, Handler handler) {
        return () -> {
            OHLC[] ohlcList = runLoadOHLC(symbol);
            Message message = new Message();
            message.what = MainActivity.DB_OHLC_LOAD;
            message.arg1 = 1;
            message.obj = ohlcList;
            handler.sendMessage(message);
        };
    }

    public Runnable updateLocation List(String symbol, List<OHLC> ohlcList, Handler handler) {
        return () -> {
            runUpdateOHLC(symbol, (OHLC[]) ohlcList.toArray());
            Message message = new Message();
            message.what = MainActivity.DB_OHLC_UPDATE;
            message.arg1 = 1;
            handler.sendMessage(message);
        };
    }

    //public Runnable insertLoacation()



}
