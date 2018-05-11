package com.twc.rca.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.WindowManager;

import com.twc.rca.R;
import com.twc.rca.background.ForgotPswdTask;
import com.twc.rca.background.ResendOtpTask;
import com.twc.rca.background.ResetPasswordTask;
import com.twc.rca.utils.ApiUtils;
import com.twc.rca.utils.OtpTextWatcher;
import com.twc.rca.utils.PreferenceUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sushil on 26-03-2018.
 */

public class ResetPswdActivity extends BaseActivity implements View.OnClickListener {

    AppCompatEditText et_otp_1, et_otp_2, et_otp_3, et_otp_4;
    AppCompatTextView tv_resend_otp;
    AppCompatEditText et_new_pswd, et_confirm_pswd;
    AppCompatButton btn_next;
    OtpTextWatcher otpTextWatcher;

    public static String METHOD_NAME = "resend_otp_to_email";
    List<AppCompatEditText> list_otp_view;
    int otpNumber, otp1, otp2, otp3, otp4;
    String emailId, str_password, str_re_password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        emailId = getIntent().getStringExtra(PreferenceUtils.EMAILID);
        showToast(getIntent().getStringExtra(ApiUtils.MESSAGE));
        initView();
    }

    void initView() {
        et_otp_1 = (AppCompatEditText) findViewById(R.id.et_fp_otp_digit_1);
        et_otp_2 = (AppCompatEditText) findViewById(R.id.et_fp_otp_digit_2);
        et_otp_3 = (AppCompatEditText) findViewById(R.id.et_fp_otp_digit_3);
        et_otp_4 = (AppCompatEditText) findViewById(R.id.et_fp_otp_digit_4);
        tv_resend_otp = (AppCompatTextView) findViewById(R.id.tv_resend_otp);
        et_new_pswd = (AppCompatEditText) findViewById(R.id.et_new_pswd);
        et_confirm_pswd = (AppCompatEditText) findViewById(R.id.et_confirm_pswd);
        btn_next = (AppCompatButton) findViewById(R.id.btn_reset_pswd_next);

        list_otp_view = new ArrayList<>();
        list_otp_view.add(et_otp_1);
        list_otp_view.add(et_otp_2);
        list_otp_view.add(et_otp_3);
        list_otp_view.add(et_otp_4);

        et_otp_1.addTextChangedListener(new OtpTextWatcher(et_otp_1, list_otp_view, otpListener));
        et_otp_2.addTextChangedListener(new OtpTextWatcher(et_otp_2, list_otp_view, otpListener));
        et_otp_3.addTextChangedListener(new OtpTextWatcher(et_otp_3, list_otp_view, otpListener));
        et_otp_4.addTextChangedListener(new OtpTextWatcher(et_otp_4, list_otp_view, otpListener));


        tv_resend_otp.setOnClickListener(this);
        btn_next.setOnClickListener(this);
    }

    OtpTextWatcher.OTPListener otpListener = new OtpTextWatcher.OTPListener() {
        @Override
        public void onOTPSuccess(int number) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            otpNumber = number;
        }

        @Override
        public void onOTPError() {

        }
    };


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_resend_otp:
                showProgressDialog(getApplicationContext().getResources().getString(R.string.please_wait));
                new ResendOtpTask(getApplicationContext(), emailId).userResendOtp(resendOtpResponseCallback);
                break;

            case R.id.btn_reset_pswd_next:
                if (validateFields()) {
                    showProgressDialog(getApplicationContext().getResources().getString(R.string.please_wait));
                    new ResetPasswordTask(getApplicationContext(), emailId, String.valueOf(otpNumber), et_new_pswd.getText().toString()).userResetPswd(resetPswdResponseCallback);
                }
                break;
        }
    }

    boolean validateFields() {

        str_password = et_new_pswd.getText().toString();
        str_re_password = et_confirm_pswd.getText().toString();

        if (str_password == null || str_password.equals("")) {
            showSnack(R.string.empty_password_msg);
            return false;
        } else if (!str_password.equals(str_re_password)) {
            showSnack(R.string.password_mismatch_msg);
            return false;
        } else {
            if (!ApiUtils.isValidPassword(str_password)) {
                showSnack(R.string.password_validation_msg);
                return false;
            }
        }

        if (String.valueOf(otpNumber).length() != 4) {
            showSnack(R.string.invalid_otp);
            return false;
        }

        return true;
    }

    ResendOtpTask.ResendOtpResponseCallback resendOtpResponseCallback = new ResendOtpTask.ResendOtpResponseCallback() {
        @Override
        public void onSuccessResendOtpResponse(String response) {
            dismissProgressDialog();
            try {
                JSONObject jsonObject = new JSONObject(response);

                JSONObject contentObject = (JSONObject) jsonObject.get(ApiUtils.CONTENT);

                showToast(contentObject.getString(ApiUtils.MESSAGE));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailureResendOtpResponse(String response) {
            dismissProgressDialog();
        }
    };

    ResetPasswordTask.ResetPswdResponseCallback resetPswdResponseCallback = new ResetPasswordTask.ResetPswdResponseCallback() {
        @Override
        public void onSuccessResetPswdResponse(String response) {
            dismissProgressDialog();
            try {
                JSONObject jsonObject = new JSONObject(response);

                JSONObject contentObject = (JSONObject) jsonObject.get(ApiUtils.CONTENT);

                showToast(contentObject.getString(ApiUtils.MESSAGE));

                finish();
                Intent intent = new Intent(ResetPswdActivity.this, LoginActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailureResetPswdResponse(String response) {
            dismissProgressDialog();
        }
    };
}
