package com.example.androidmapsh.ui.bookmarks;

import android.os.Bundle;
import android.provider.ContactsContract;
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
import com.example.androidmapsh.database.Location;
import com.example.androidmapsh.map.NetworkManager;

import java.util.ArrayList;
import java.util.List;

public class BookmarksFragment extends Fragment implements BookmarkListAdaptor.OnItemClickListener{

    BookmarksViewModel bookmarksViewModel;
    MainActivity mainActivity;


    public View onCreateView(@NonNull LayoutInflater inflater,
                         ViewGroup container, Bundle savedInstanceState){
        bookmarksViewModel =
                new ViewModelProvider(this).get(BookmarksViewModel.class);
        View root = inflater.inflate(R.layout.fragment_bookmarks, container, false);
        //bookmarksViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        //Assign variable
        final EditText editText = root.findViewById(R.id.edit_text);
        //TODO: search using voice
        final Button micButton = root.findViewById(R.id.button);
        final RecyclerView listView = root.findViewById(R.id.list_view);
        mainActivity = (MainActivity) getActivity();

        //Loading bookmarks
        mainActivity.execute(DatabaseManager.getInstance()
                .loadLocationList(mainActivity.getHandler()));

        BookmarkListAdaptor bookmarkListAdaptor = new BookmarkListAdaptor(getActivity(), this, bookmarksViewModel.getBookmarks());
        listView.setAdapter(bookmarkListAdaptor);


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
}