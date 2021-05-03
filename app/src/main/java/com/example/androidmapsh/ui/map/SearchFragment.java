package com.example.androidmapsh.ui.map;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidmapsh.MainActivity;
import com.example.androidmapsh.R;
import com.example.androidmapsh.database.DatabaseManager;
import com.example.androidmapsh.map.NetworkManager;
import com.example.androidmapsh.ui.bookmarks.BookmarkListAdapter;
import com.example.androidmapsh.ui.bookmarks.BookmarksViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements RecommendationListAdapter.OnItemClickListener{

    MainActivity mainActivity;
    MapViewModel mapViewModel;


    public View onCreate(@NonNull LayoutInflater inflater,
                         ViewGroup container, Bundle savedInstanceState){

        mapViewModel =
                new ViewModelProvider(this).get(MapViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search_map, container, false);

        //Assign variable
        final EditText editText = root.findViewById(R.id.edit_text);
        //TODO: search using voice
        final Button micButton = root.findViewById(R.id.button);
        final RecyclerView listView = root.findViewById(R.id.list_view);
        mainActivity = (MainActivity) getActivity();



        RecommendationListAdapter recommendationListAdapter = new RecommendationListAdapter(getActivity(), this, mapViewModel.getRecommendation());
        listView.setAdapter(recommendationListAdapter);


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                NetworkManager.getInstance().loadSearchResults(s.toString(), mainActivity.getHandler());
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
}

