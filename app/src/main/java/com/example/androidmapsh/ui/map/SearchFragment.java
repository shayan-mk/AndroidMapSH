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

import com.example.androidmapsh.MainActivity;
import com.example.androidmapsh.R;
import com.example.androidmapsh.map.NetworkManager;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    MainActivity mainActivity;
    MapViewModel mapViewModel;


    public View onCreate(@NonNull LayoutInflater inflater,
                         ViewGroup container, Bundle savedInstanceState){

        //Assign variable
        View root = inflater.inflate(R.layout.fragment_search_map, container, false);
        final EditText editText = root.findViewById(R.id.edit_text);
        //TODO: search using voice
        final Button micButton = root.findViewById(R.id.button);
        final ListView listView = root.findViewById(R.id.list_view);
        recommendations = new ArrayList<>();
        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, recommendations);
        listView.setAdapter(itemsAdapter);



        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                itemsAdapter.clear();
                if(count != 0){
                    NetworkManager.getInstance().loadSearchResults(s.toString(), );
                    recommendations = NetworkManager.getRecommendations(s.toString());
                    itemsAdapter.addAll(recommendations);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = itemsAdapter.getItem(position);
                //TODO: search the location
            }
        });

        return root;
    }

}

