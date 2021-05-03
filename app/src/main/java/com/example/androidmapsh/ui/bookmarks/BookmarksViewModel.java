package com.example.androidmapsh.ui.bookmarks;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidmapsh.database.Location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BookmarksViewModel extends ViewModel {

    private MutableLiveData<List<Location>> bookmarks;
    private List<Location> cacheBookmarks;

    public BookmarksViewModel() {
        bookmarks = new MutableLiveData<>();
        bookmarks.setValue(new ArrayList<>());
    }

    public LiveData<List<Location>> getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(Location[] bookmarks) {
        cacheBookmarks = Arrays.asList(bookmarks);
        readFromCache();
    }

    public void deleteBookmark(Location location){
        cacheBookmarks.remove(location);
        readFromCache();
    }

    public void filterBookmarks(String text){
        List<Location> filteredList = new ArrayList<>();
        for (Location bookmark : cacheBookmarks) {
            if(bookmark.getName().startsWith(text)){
                filteredList.add(bookmark);
            }
        }
        bookmarks.setValue(filteredList);
    }

    private void readFromCache(){
        bookmarks.setValue(cacheBookmarks);
    }
}