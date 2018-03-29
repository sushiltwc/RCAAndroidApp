package com.twc.rca.product.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import com.twc.rca.R;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Sushil on 05-03-2018.
 */

public class DatePickerDialogFragment extends DialogFragment {

    private DatePicker datePicker;

    public interface DateDialogListener {
        void onFinishDialog(Date date,int id);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.layout_date_picker, null);
        datePicker = (DatePicker) v.findViewById(R.id.dialog_date_picker);
        Bundle bundle = this.getArguments();
        final int id = bundle.getInt("id");

        return new android.support.v7.app.AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Select Date")
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int year = datePicker.getYear();
                                int mon = datePicker.getMonth();
                                int day = datePicker.getDayOfMonth();
                                Date date = new GregorianCalendar(year, mon, day).getTime();
                                DateDialogListener activity = (DateDialogListener) getActivity();
                                activity.onFinishDialog(date,id);
                                dismiss();
                            }
                        })
                .create();
    }
}