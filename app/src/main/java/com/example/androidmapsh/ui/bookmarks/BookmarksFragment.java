package com.example.androidmapsh.ui.bookmarks;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidmapsh.MainActivity;
import com.example.androidmapsh.R;
import com.example.androidmapsh.database.DatabaseManager;
import com.example.androidmapsh.database.Location;

import java.util.ArrayList;
import java.util.List;

public class BookmarksFragment extends Fragment implements BookmarkListAdapter.OnItemClickListener{
    public static final String TAG = BookmarksFragment.class.getName();

    BookmarksViewModel bookmarksViewModel;
    MainActivity mainActivity;


    public View onCreateView(@NonNull LayoutInflater inflater,
                         ViewGroup container, Bundle savedInstanceState){
        mainActivity = (MainActivity) getActivity();
        bookmarksViewModel = mainActivity.getBookmarksVM();

        View root = inflater.inflate(R.layout.fragment_bookmarks, container, false);
        //bookmarksViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        //Assign variable
        final EditText editText = root.findViewById(R.id.edit_text);
        //TODO: search using voice
        final Button micButton = root.findViewById(R.id.button);
        Log.d(TAG, "onCreateView: Initializing Recycler view");
        final RecyclerView listView = root.findViewById(R.id.list_view_bookmarks);


        BookmarkListAdapter bookmarkListAdapter = new BookmarkListAdapter(getActivity(), this, mainActivity);
        listView.setAdapter(bookmarkListAdapter);
        listView.setLayoutManager(new LinearLayoutManager(mainActivity));
        bookmarksViewModel.setBla(bookmarkListAdapter);

        //Loading bookmarks
        mainActivity.execute(DatabaseManager.getInstance()
                .loadLocationList(mainActivity.getHandler()));



        //Test bookmark list
        bookmarksViewModel.setBookmarks(initLocations());

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bookmarksViewModel.filterBookmarks(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return root;
    }

    @Override
    public void onItemClick(String symbol) {

    }

    private Location[] initLocations(){
        Log.d(TAG, "initLocations: ");
        Location[] locations = new Location[10];
        for(int i =0; i < 10; i++){
            Location location = new Location("Name"+ i, i, 2*i);
            locations[i] = location;
        }
        return locations;
    }
}