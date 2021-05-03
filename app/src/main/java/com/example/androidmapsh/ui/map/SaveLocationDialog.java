package com.example.androidmapsh.ui.map;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.ArrayList;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.androidmapsh.R;

public class SaveLocationDialog extends AppCompatDialogFragment {

    private float latitude;
    private float longitude;

    public SaveLocationDialog(float latitude, float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.save_location_dialog, null);

        // setting listeners of save buttons
        Button saveButton = (Button) view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(view1 -> {
            //TODO: save the location
        });

        //TextView symbolTextView = (TextView) view.findViewById(R.id.ohlcCryptoSymbol);
        //symbolTextView.setText(symbol);

        builder.setView(view);
        builder.setNegativeButton("Hide", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        return builder.create();
    }
}
