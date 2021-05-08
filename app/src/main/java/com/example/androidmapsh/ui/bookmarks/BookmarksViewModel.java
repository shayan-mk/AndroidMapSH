package com.example.androidmapsh.ui.bookmarks;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.androidmapsh.database.Location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookmarksViewModel extends ViewModel {
    public static final String TAG = BookmarksViewModel.class.getName();
    private BookmarkListAdapter bla;
    private ArrayList<Location> cacheBookmarks;

    public BookmarksViewModel() {
    }


    public void setBookmarks(Location[] bookmarks) {
        Log.d(TAG, "setBookmarks: " + cacheBookmarks);
        cacheBookmarks = new ArrayList<>(Arrays.asList(bookmarks));
        readFromCache();
    }

    public void deleteBookmark(Location location){
        Log.d(TAG, "deleteBookmark: Location ID:" + location.getID());
        cacheBookmarks.remove(location);
        //cacheBookmarks.remove(location);
        Log.d(TAG, "deleteBookmark: " + cacheBookmarks);
        readFromCache();
    }

    public void truncateCache(){
        Log.d(TAG, "truncateCache: " + cacheBookmarks);
        cacheBookmarks = new ArrayList<>();
        readFromCache();
    }

    public void filterBookmarks(String text){
        List<Location> filteredList = new ArrayList<>();
        for (Location bookmark : cacheBookmarks) {
            if(bookmark.getName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(bookmark);
            }
        }
        bla.setBookmarkList(filteredList);
    }

    private void readFromCache(){
        bla.setBookmarkList(cacheBookmarks);
    }

    public void setBla(BookmarkListAdapter bla) {
        this.bla = bla;
    }
}