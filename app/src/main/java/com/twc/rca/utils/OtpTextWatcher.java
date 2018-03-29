package com.twc.rca.utils;

import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import java.util.List;

/**
 * Created by Sushil on 10-03-2018.
 */

public class OtpTextWatcher implements TextWatcher {

    private AppCompatEditText view;
    private List<AppCompatEditText> otpDigitViews;
    private OTPListener otpListener;

    public OtpTextWatcher(AppCompatEditText otpView, List<AppCompatEditText> otpDigitViews, OTPListener listener) {
        view = otpView;
        this.otpDigitViews = otpDigitViews;
        this.otpListener = listener;
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

        String digit1 = otpDigitViews.get(0).getText().toString();
        String digit2 = otpDigitViews.get(1).getText().toString();
        String digit3 = otpDigitViews.get(2).getText().toString();
        String digit4 = otpDigitViews.get(3).getText().toString();
        String currentDigit = editable.toString();
        final String inputValue = digit1 + digit2 + digit3 + digit4;

        if (inputValue.length() == 4) {
            otpListener.onOTPSuccess(Integer.parseInt(inputValue));
          /*  if (inputValue.equals("1234")) {
                otpListener.onOTPSuccess();
            } else {
                otpListener.onOTPError();
            }*/
        } else {
            if (currentDigit.length() >= 1
                    && view != otpDigitViews.get(3)) {
                if (view != null)
                    view.focusSearch(View.FOCUS_RIGHT).requestFocus();
            } else {
                if (currentDigit.length() <= 0 && view.getSelectionStart() <= 0) {
                    try {
                        view.focusSearch(View.FOCUS_LEFT).requestFocus();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public interface OTPListener {
        void onOTPSuccess(int otpNumber);

        void onOTPError();
    }}