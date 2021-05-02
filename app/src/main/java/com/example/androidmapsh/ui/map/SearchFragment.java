package com.example.androidmapsh.ui.map;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.androidmapsh.MainActivity;
import com.example.androidmapsh.R;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    //Initialize variables
    TextView textView;
    ArrayList<String> recommendations;
    Dialog dialog;

    public View onCreate(@NonNull LayoutInflater inflater,
                         ViewGroup container, Bundle savedInstanceState){

        //Assign variable
        View root = inflater.inflate(R.layout.search_box, container, false);
        final TextView textView = root.findViewById(R.id.text_view);

        //Initialize array list
        recommendations = new ArrayList<>();
        //TODO: fill the array list

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Initialize dialog
                //TODO: cannot use this fragment
                //dialog = new Dialog(MainActivity.this);

            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, recommendations);

        return root;
    }
}
