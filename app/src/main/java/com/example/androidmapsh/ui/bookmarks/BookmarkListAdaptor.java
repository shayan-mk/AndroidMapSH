package com.example.androidmapsh.ui.bookmarks;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidmapsh.R;
import com.example.androidmapsh.database.Location;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class BookmarkListAdaptor extends RecyclerView.Adapter<BookmarkListAdaptor.ViewHolder> {
    private final LiveData<List<Location>> bookmarkList;
    private final Context context;
    private final OnItemClickListener listener;
    private final Spannable.Factory spannableFactory;

    public BookmarkListAdaptor(Context context, OnItemClickListener listener, LiveData<List<Location>> bookmarks) {
        this.context = context;
        this.listener = listener;
        this.bookmarkList = bookmarks;

        spannableFactory = new Spannable.Factory() {
            @Override
            public Spannable newSpannable(CharSequence source) {
                return (Spannable) source;
            }
        };
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View cryptoView = inflater.inflate(R.layout.bookmark_item, parent, false);
        BookmarkListAdaptor.ViewHolder viewHolder = new ViewHolder(cryptoView);

        viewHolder.name.setSpannableFactory(spannableFactory);
        viewHolder.latitude.setSpannableFactory(spannableFactory);
        viewHolder.longitude.setSpannableFactory(spannableFactory);
        // Return a new holder instance
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Location location = bookmarkList.getValue().get(position);

        holder.name.setText(location.getName());
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(5);

        Spannable spannable = new SpannableString(df.format(location.getLatitude()));
//        spannable.setSpan(new ForegroundColorSpan(location.getPercentChange1h() > 0 ? Color.GREEN : Color.RED), 4, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.latitude.setText(spannable, TextView.BufferType.SPANNABLE);

        spannable = new SpannableString(df.format(location.getLongitude()));
//        spannable.setSpan(new ForegroundColorSpan(location.getPercentChange24h() > 0 ? Color.GREEN : Color.RED), 4, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.longitude.setText(spannable, TextView.BufferType.SPANNABLE);

//        holder.itemView.setOnClickListener(view -> listener.onItemClick(location.getSymbol()));
    }

    @Override
    public int getItemCount() {
        return bookmarkList.getValue().size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView name;
        public TextView latitude;
        public TextView longitude;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.bookmarkName);
            latitude = (TextView) itemView.findViewById(R.id.bookmarkLatitude);
            longitude = (TextView) itemView.findViewById(R.id.bookmarkLongitude);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String symbol);
    }
}
