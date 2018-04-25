package com.twc.rca.product.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import com.twc.rca.R;

import java.util.Calendar;

/**
 * Created by Sushil on 19-04-2018.
 */

public class TimePickerDialogFragment extends DialogFragment {

    private TimePicker timePicker;

    public interface TimeDialogListener {
        void onFinishDialog(int hour, int min, String period, int id);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.layout_time_picker, null);
        timePicker = (TimePicker) v.findViewById(R.id.dialog_time_picker);
        Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        Bundle bundle = this.getArguments();
        final int id = bundle.getInt("id");

        return new android.support.v7.app.AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Select Time")
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int hour = timePicker.getCurrentHour();
                                int min = timePicker.getCurrentMinute();
                                String AM_PM;
                                if (hour < 12)
                                    AM_PM = "AM";
                                else
                                    AM_PM = "PM";
                                TimePickerDialogFragment.TimeDialogListener activity = (TimePickerDialogFragment.TimeDialogListener) getActivity();
                                activity.onFinishDialog(hour, min, AM_PM, id);
                                dismiss();
                            }
                        })
                .create();
    }
}
