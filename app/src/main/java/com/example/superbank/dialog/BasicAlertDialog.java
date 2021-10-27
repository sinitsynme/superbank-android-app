package com.example.superbank.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

public class BasicAlertDialog extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getArguments() != null ? getArguments().getString("header") : null)
                .setMessage(getArguments().getString("msg"))
                .setPositiveButton("ОК", (dialog, id) -> dialog.cancel());
        return builder.create();
    }

    public void onCancel(DialogInterface.OnCancelListener onCancelListener) {
    }
}
