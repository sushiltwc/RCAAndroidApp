package com.twc.rca.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.twc.rca.R;
import com.twc.rca.background.SignupTask;
import com.twc.rca.model.UserExist;
import com.twc.rca.utils.ApiUtils;
import com.twc.rca.utils.MailUtils;
import com.twc.rca.utils.PreferenceUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Sushil on 13-03-2018.
 */

public class SignupActivity extends BaseActivity implements View.OnClickListener {

    AutoCompleteTextView auto_signup_id;

    AppCompatEditText et_signup_phone_no, et_signup_user_name, et_signup_pswd, et_signup_repswd;

    AppCompatTextView tv_log_in,tv_need_to_verify;

    AppCompatButton btn_sign_up;

    ArrayList<String> list_mail;

    String str_email, str_phone_no, str_name, str_password, str_re_password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initView();
    }

    void initView() {
        auto_signup_id = (AutoCompleteTextView) findViewById(R.id.auto_signup_id);
        et_signup_phone_no = (AppCompatEditText) findViewById(R.id.et_signup_phone_no);
        et_signup_user_name = (AppCompatEditText) findViewById(R.id.et_signup_userName);
        et_signup_pswd = (AppCompatEditText) findViewById(R.id.et_signup_pswd);
        et_signup_repswd = (AppCompatEditText) findViewById(R.id.et_signup_repswd);
        tv_log_in = (AppCompatTextView) findViewById(R.id.tv_login);
        tv_need_to_verify=(AppCompatTextView)findViewById(R.id.tv_need_to_verify);
        btn_sign_up = (AppCompatButton) findViewById(R.id.btn_signup);

        tv_log_in.setText(Html.fromHtml("Already have an account? Go to <font color='#66A1E7'>LOG IN</font>"));

        tv_log_in.setOnClickListener(this);
        btn_sign_up.setOnClickListener(this);

        list_mail = new ArrayList<>();

        list_mail = MailUtils.getAllGoogleAccountMailId(this);

        if (list_mail != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(SignupActivity.this, android.R.layout.simple_spinner_dropdown_item, list_mail);
            auto_signup_id.setAdapter(adapter);

            auto_signup_id.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.auto_signup_id:
                popupDialog("", list_mail, view.getId());
                break;

            case R.id.tv_login:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.btn_signup:
                if (isValidSignUpData()) {
                    showProgressDialog(getString(R.string.please_wait));
                    new SignupTask(str_email, str_phone_no, str_name, str_password).userSignUp(signUpResponseCallback);
                }
                break;
        }
    }

    void popupDialog(String title, final ArrayList<String> list, final int id) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setItems(list.toArray(new String[list.size()]), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (id == R.id.auto_signup_id)
                    auto_signup_id.setText(list.get(which));
                    auto_signup_id.setSelection(auto_signup_id.getText().length());
            }
        });
        builder.show();
    }

    boolean isValidSignUpData() {

        str_email = auto_signup_id.getText().toString();
        str_phone_no = et_signup_phone_no.getText().toString();
        str_name = et_signup_user_name.getText().toString();
        str_password = et_signup_pswd.getText().toString();
        str_re_password = et_signup_repswd.getText().toString();

        if (!ApiUtils.isValidMail(str_email)) {
            showSnack(R.string.email_validation_msg);
            return false;
        }

        if (!ApiUtils.isValidMobileNumber(str_phone_no)) {
            showSnack(R.string.empty_mobile_msg);
            return false;
        }

        if (str_name == null || str_name.equals("")) {
            showSnack(getString(R.string.empty_name_msg));
            return false;
        }

        if (str_password == null || str_password.equals("")) {
            showSnack(R.string.empty_password_msg);
            return false;
        }
        else{
            if(!ApiUtils.isValidPassword(str_password)){
                showSnack(R.string.password_validation_msg);
            return false;
            }
        }

        if (!str_password.equals(str_re_password)) {
            showSnack(R.string.password_mismatch_msg);
            return false;
        }
        return true;
    }

    SignupTask.SignUpResponseCallback signUpResponseCallback = new SignupTask.SignUpResponseCallback() {

        @Override
        public void onSuccessSignUpResponse(String response) {
            dismissProgressDialog();
            int otpNumber = 0;
            try {
                JSONObject jsonObject = new JSONObject(response);

                JSONObject contentObject = (JSONObject) jsonObject.get(ApiUtils.CONTENT);
                //JSONArray content = (JSONArray) contentObject.get(ApiUtils.CONTENT);
                UserExist userExist = new UserExist();

                // JSONObject userDataObject = content.getJSONObject(0);
                JSONArray data = (JSONArray) contentObject.get(ApiUtils.RESULT_SET);
                if (data.length() > 0) {
                    JSONObject jsonobject = data.getJSONObject(0);
                    otpNumber = jsonobject.getInt("otp_number");
                }
                Intent intent = new Intent(SignupActivity.this, OtpActivity.class);
                intent.putExtra(PreferenceUtils.EMAILID, str_email);
                intent.putExtra(PreferenceUtils.OTP_NO, otpNumber);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailureSignUpResponse(String response) {
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
