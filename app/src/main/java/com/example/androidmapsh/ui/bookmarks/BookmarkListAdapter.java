package com.example.androidmapsh.ui.bookmarks;

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
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidmapsh.MainActivity;
import com.example.androidmapsh.R;
import com.example.androidmapsh.database.DatabaseManager;
import com.example.androidmapsh.database.Location;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class BookmarkListAdapter extends RecyclerView.Adapter<BookmarkListAdapter.ViewHolder> {
    public static final String TAG = BookmarkListAdapter.class.getName();
    private final List<Location> bookmarkList;
    private final Context context;
    private final OnItemClickListener listener;
    private final Spannable.Factory spannableFactory;
    private final MainActivity mainActivity;

    public BookmarkListAdapter(Context context, OnItemClickListener listener, MainActivity mainActivity) {
        this.context = context;
        this.listener = listener;
        this.bookmarkList = new ArrayList<>();
        this.mainActivity = mainActivity;

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
        BookmarkListAdapter.ViewHolder viewHolder = new ViewHolder(cryptoView);

        viewHolder.name.setSpannableFactory(spannableFactory);
        viewHolder.lat.setSpannableFactory(spannableFactory);
        viewHolder.lng.setSpannableFactory(spannableFactory);
        viewHolder.delete_button.setSpannableFactory(spannableFactory);
        // Return a new holder instance
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Location location = bookmarkList.get(position);

        holder.name.setText(location.getName());
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(5);

        Spannable spannable = new SpannableString(df.format(location.getLat()));
//        spannable.setSpan(new ForegroundColorSpan(location.getPercentChange1h() > 0 ? Color.GREEN : Color.RED), 4, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.lat.setText(spannable, TextView.BufferType.SPANNABLE);

        spannable = new SpannableString(df.format(location.getLng()));
//        spannable.setSpan(new ForegroundColorSpan(location.getPercentChange24h() > 0 ? Color.GREEN : Color.RED), 4, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.lng.setText(spannable, TextView.BufferType.SPANNABLE);

        holder.delete_button.setOnClickListener(deleteObject -> mainActivity.execute(DatabaseManager.getInstance().
                deleteLocation(location, mainActivity.getHandler())));
        holder.itemView.setOnClickListener(view -> listener.onItemClick(location.getLat(), location.getLng()));
    }

    @Override
    public int getItemCount() {
        return bookmarkList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView name;
        public TextView lat;
        public TextView lng;
        public Button delete_button;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            name = itemView.findViewById(R.id.bookmarkName);
            lat = itemView.findViewById(R.id.bookmarkLat);
            lng = itemView.findViewById(R.id.bookmarkLng);
            delete_button = itemView.findViewById(R.id.delete_button);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(double lat, double lng);
    }

    public void deleteFromList(Location location){
        bookmarkList.remove(location);
        notifyDataSetChanged();
    }

    public void setBookmarkList(List<Location> locations){
        Log.d(TAG, "setBookmarkList: inserting data to recycler view " );
        bookmarkList.clear();
        bookmarkList.addAll(locations);
        notifyDataSetChanged();
    }
}
