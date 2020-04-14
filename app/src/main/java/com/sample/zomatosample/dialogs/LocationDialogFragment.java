package com.sample.zomatosample.dialogs;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.sample.zomatosample.R;

public class LocationDialogFragment extends DialogFragment {

    private LocationDialogListener listener;

    public interface LocationDialogListener {
        void onGoToSettingsClick(DialogFragment dialog);
    }


    // Override the Fragment.onAttach() method to instantiate the LocationDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (LocationDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException("Class must implement LocationDialogListener");
        }
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Build the dialog and set up the button click handlers
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
        builder.setMessage(R.string.location_settings_navigation_description)
                .setPositiveButton(R.string.go_to_settings, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onGoToSettingsClick(LocationDialogFragment.this);
                    }
                });
        return builder.create();
    }
}
