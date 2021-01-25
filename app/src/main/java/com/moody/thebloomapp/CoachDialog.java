package com.moody.thebloomapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class CoachDialog extends AppCompatDialogFragment {
    private EditText coachKeyET;



    private CoachDialogListener listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.layout_dialog, null);

        builder.setView(view).setTitle("Coach Login").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String key = coachKeyET.getText().toString().trim();
                listener.checkKey(key);
            }
        });
        coachKeyET = view.findViewById(R.id.enter_key);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (CoachDialogListener) context;
        } catch (Exception e) {
            throw new ClassCastException(context.toString() + "Must Implement CoachDialogListener");
        }
    }

    public interface CoachDialogListener {
        void checkKey(String key);
    }
}
