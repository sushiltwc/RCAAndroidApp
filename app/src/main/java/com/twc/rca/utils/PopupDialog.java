package com.twc.rca.utils;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import com.twc.rca.R;

/**
 * Created by Sushil on 14-05-2018.
 */

public class PopupDialog extends DialogFragment {

    public static final String TAG = PopupDialog.class.getSimpleName();

    public static int TRAVEL_DATE_CODE = 1;

    public static int TIME_DIFF_CODE = 2;

    static PopupDialogInterface popupDialogInterface;

    static int code;

    static String message;

    public static PopupDialog getInstance(PopupDialogInterface popupDialogInterface1, int c, String msg) {
        PopupDialog dialog = new PopupDialog();
        popupDialogInterface = popupDialogInterface1;
        code = c;
        message = msg;
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.alert);
        builder.setMessage(message);
        if (code == TIME_DIFF_CODE) {
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (popupDialogInterface != null) {
                        popupDialogInterface.onCallBack(code);

                    }
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
        } else if (code == TRAVEL_DATE_CODE) {
            builder.setPositiveButton(R.string.cont, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (popupDialogInterface != null) {
                        popupDialogInterface.onCallBack(code);
                    }
                }
            });
        }

        return builder.create();
    }

    public interface PopupDialogInterface {
        void onCallBack(int result);
    }

}
