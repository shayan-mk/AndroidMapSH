package com.example.androidmapsh.ui.map;


import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidmapsh.MainActivity;
import com.example.androidmapsh.R;
import com.example.androidmapsh.database.Location;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RecommendationListAdapter extends RecyclerView.Adapter<RecommendationListAdapter.ViewHolder> {
    private static final String TAG = RecommendationListAdapter.class.getName();
    private final List<Location> recommendationList;
    private final Context context;
    private final OnItemClickListener listener;
    private final Spannable.Factory spannableFactory;

    public RecommendationListAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        recommendationList = new ArrayList<>();

        spannableFactory = new Spannable.Factory() {
            @Override
            public Spannable newSpannable(CharSequence source) {
                return (Spannable) source;
            }
        };
    }

    public void loadRecommendations(List<Location> newData) {
        Log.d(TAG, "loadRecommendations: " + newData);
        recommendationList.clear();
        recommendationList.addAll(newData);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View bookmarkView = inflater.inflate(R.layout.bookmark_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(bookmarkView);

//        viewHolder.name.setSpannableFactory(spannableFactory);
//        viewHolder.latitude.setSpannableFactory(spannableFactory);
//        viewHolder.longitude.setSpannableFactory(spannableFactory);
        // Return a new holder instance
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.androidmapsh.ui.map.RecommendationListAdapter.ViewHolder holder, int position) {
        Location location = recommendationList.get(position);

        holder.name.setText(location.getName());
        holder.name.setTextSize(20);
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(5);

//        Spannable spannable = new SpannableString(df.format(location.getLatitude()));
//        spannable.setSpan(new ForegroundColorSpan(location.getPercentChange1h() > 0 ? Color.GREEN : Color.RED), 4, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        holder.latitude.setText(spannable, TextView.BufferType.SPANNABLE);
        holder.latitude.setVisibility(View.GONE);

//        spannable = new SpannableString(df.format(location.getLongitude()));
//        spannable.setSpan(new ForegroundColorSpan(location.getPercentChange24h() > 0 ? Color.GREEN : Color.RED), 4, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        holder.longitude.setText(spannable, TextView.BufferType.SPANNABLE);
        holder.longitude.setVisibility(View.GONE);

        holder.deleteButton.setVisibility(View.GONE);

        //TODO: move map to the chosen point
        holder.itemView.setOnClickListener(view -> listener.onItemClick(location.getName()));
    }

    @Override
    public int getItemCount() {
        return recommendationList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView name;
        public TextView latitude;
        public TextView longitude;
        public Button deleteButton;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.bookmarkName);
            latitude = (TextView) itemView.findViewById(R.id.bookmarkLatitude);
            longitude = (TextView) itemView.findViewById(R.id.bookmarkLongitude);
            deleteButton = (Button) itemView.findViewById(R.id.delete_button);
        }
    }

    //TODO: what to do after selecting a recommended place
    //      Open the map fragment from the selected place
    public interface OnItemClickListener {
        void onItemClick(String symbol);
    }
}
