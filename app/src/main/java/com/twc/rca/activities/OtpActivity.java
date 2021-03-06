package com.twc.rca.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.WindowManager;

import com.twc.rca.R;
import com.twc.rca.background.OTPVerificationTask;
import com.twc.rca.background.ResendOtpTask;
import com.twc.rca.utils.ApiUtils;
import com.twc.rca.utils.OtpTextWatcher;
import com.twc.rca.utils.PreferenceUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TWC on 10-03-2018.
 */

public class OtpActivity extends BaseActivity implements View.OnClickListener {

    AppCompatTextView tv_otp_mailId, tv_resend_otp;
    AppCompatEditText et_otp_1, et_otp_2, et_otp_3, et_otp_4;
    List<AppCompatEditText> list_otp_view;
    String emailId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        emailId = getIntent().getStringExtra(PreferenceUtils.EMAILID);

        initView();
    }

    void initView() {
        tv_otp_mailId = (AppCompatTextView) findViewById(R.id.tv_otp_mailId);
        tv_resend_otp = (AppCompatTextView) findViewById(R.id.tv_resend_otp);
        et_otp_1 = (AppCompatEditText) findViewById(R.id.et_otp_digit_1);
        et_otp_2 = (AppCompatEditText) findViewById(R.id.et_otp_digit_2);
        et_otp_3 = (AppCompatEditText) findViewById(R.id.et_otp_digit_3);
        et_otp_4 = (AppCompatEditText) findViewById(R.id.et_otp_digit_4);

        tv_otp_mailId.setText(emailId);

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
    }

    OtpTextWatcher.OTPListener otpListener = new OtpTextWatcher.OTPListener() {
        @Override
        public void onOTPSuccess(int otpNumber) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            showProgressDialog(getString(R.string.please_wait));
            new OTPVerificationTask(getApplicationContext(), emailId, otpNumber).otpVerification(otpVerificationResponseCallback);
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
        }
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

    OTPVerificationTask.OTPVerificationResponseCallback otpVerificationResponseCallback = new OTPVerificationTask.OTPVerificationResponseCallback() {
        @Override
        public void onSucceseOTPVerificationResponse(String response) {
            dismissProgressDialog();
            try {
                JSONObject jsonObject = new JSONObject(response);

                JSONObject contentObject = (JSONObject) jsonObject.get(ApiUtils.CONTENT);

                JSONArray data = (JSONArray) contentObject.get(ApiUtils.RESULT_SET);

                JSONObject jsonobject = data.getJSONObject(0);

                String user_Id = jsonobject.getString(PreferenceUtils.USERID);
                String email_Id = jsonobject.getString(PreferenceUtils.EMAILID);
                String mobile_No = jsonobject.getString(PreferenceUtils.MOBILE_NO);
                String access_token = jsonobject.getString(ApiUtils.ACCESS_TOKEN);

                PreferenceUtils.saveProfileInfo(getApplicationContext(), user_Id, email_Id, mobile_No, access_token);
                Intent intent = new Intent(OtpActivity.this, VerificationActivity.class);
                intent.putExtra(PreferenceUtils.EMAILID, emailId);
                startActivity(intent);
                finish();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailureOTPVerificationResponse(String response) {
            dismissProgressDialog();
            try {
                org.json.JSONObject jsonObject = new org.json.JSONObject(response);

                org.json.JSONObject contentObject = (org.json.JSONObject) jsonObject.get(ApiUtils.CONTENT);

                String message = (String) contentObject.get(ApiUtils.MESSAGE);

                showSnack(message);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}
