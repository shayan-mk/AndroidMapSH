package com.example.androidmapsh.ui.map;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.androidmapsh.MainActivity;
import com.example.androidmapsh.R;
import com.example.androidmapsh.database.DatabaseManager;
import com.example.androidmapsh.database.Location;

public class SaveLocationDialog extends AppCompatDialogFragment {

    private float latitude;
    private float longitude;
    MainActivity mainActivity;

    public SaveLocationDialog(float latitude, float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }



    @NonNull
    @Override
    @SuppressLint("SetTextI18n")
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mainActivity = (MainActivity) mainActivity;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.save_location_dialog, null);
        TextView textView = (TextView) view.findViewById(R.id.text_view);
        textView.setText("Save location: " + latitude +", "+ longitude);
        final EditText editText = view.findViewById(R.id.edit_text);

        // setting listeners of save buttons
        Button saveButton = (Button) view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(view1 -> {
            String name = editText.getText().toString();
            if(!name.isEmpty()){
                mainActivity.execute(DatabaseManager.getInstance().
                        insertLocation(new Location(name, latitude, longitude)
                                      , mainActivity.getHandler()));
                Toast.makeText(mainActivity, "Saved successfully!", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(mainActivity, "Enter the name!", Toast.LENGTH_SHORT).show();
            }

            this.dismiss();
        });


        builder.setView(view);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        return builder.create();
    }

    public void dismissDialog(){
        this.dismiss();
    }
}
