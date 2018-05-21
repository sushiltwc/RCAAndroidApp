package com.twc.rca.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.view.View;
import android.widget.ArrayAdapter;

import com.twc.rca.R;
import com.twc.rca.background.LoginTask;
import com.twc.rca.utils.ApiUtils;
import com.twc.rca.utils.MailUtils;
import com.twc.rca.utils.PreferenceUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Sushil on 13-03-2018.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    AppCompatAutoCompleteTextView auto_login_id;

    AppCompatEditText et_login_pswd;

    AppCompatTextView tv_forgot_pswd, tv_sign_up;

    AppCompatButton btn_login;

    ArrayList<String> list_mail,list_userExist,list_loginId;

    String str_login_id, str_login_pswd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    void initView() {
        auto_login_id = (AppCompatAutoCompleteTextView) findViewById(R.id.auto_login_id);
        et_login_pswd = (AppCompatEditText) findViewById(R.id.et_login_pswd);
        tv_forgot_pswd = (AppCompatTextView) findViewById(R.id.tv_login_forgot_pswd);
        tv_sign_up = (AppCompatTextView) findViewById(R.id.tv_signup);
        btn_login = (AppCompatButton) findViewById(R.id.btn_login);

        tv_sign_up.setText(Html.fromHtml("New User? Go to <font color='#66A1E7'>SIGN UP</font>"));
        tv_forgot_pswd.setOnClickListener(this);
        tv_sign_up.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        list_mail = new ArrayList<>();
        list_userExist=new ArrayList<>();
        list_loginId=new ArrayList<>();

       /* list_userExist=getIntent().getStringArrayListExtra(PreferenceUtils.USEREXIST_MAILID);
        list_mail = MailUtils.getAllGoogleAccountMailId(this);

        if(list_userExist!=null){
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(LoginActivity.this, android.R.layout.simple_dropdown_item_1line, list_userExist);
            auto_login_id.setAdapter(adapter);
            list_loginId=list_userExist;
            auto_login_id.setOnClickListener(this);
        }
        else if(list_mail != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(LoginActivity.this, android.R.layout.simple_dropdown_item_1line, list_mail);
            auto_login_id.setAdapter(adapter);
            list_loginId=list_mail;
            auto_login_id.setOnClickListener(this);
        }*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.auto_login_id:
                popupDialog("",list_loginId,view.getId());
                break;

            case R.id.tv_login_forgot_pswd:
                Intent intent_forgot_pswd=new Intent(this,ForgotPswdActivity.class);
                startActivity(intent_forgot_pswd);
                break;

            case R.id.tv_signup:
                Intent intent_signup=new Intent(this,SignupActivity.class);
                startActivity(intent_signup);
                finish();
                break;

            case R.id.btn_login:
                if (isValidLoginData()) {
                    showProgressDialog(getString(R.string.please_wait));
                    new LoginTask(this, str_login_id, str_login_pswd).userLogin(loginResponseCallback);
                }
        }
    }

    void popupDialog(String title, final ArrayList<String> list, final int id) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setItems(list.toArray(new String[list.size()]), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (id == R.id.auto_login_id)
                    auto_login_id.setText(list.get(which));
                    auto_login_id.setSelection(auto_login_id.getText().length());
            }
        });
        builder.show();
    }

    boolean isValidLoginData() {
        str_login_id = auto_login_id.getText().toString();
        str_login_pswd = et_login_pswd.getText().toString();

        if (!ApiUtils.isValidMail(str_login_id)) {
            showSnack(R.string.email_validation_msg);
            return false;
        }

        if (str_login_pswd == null || str_login_pswd == "") {
            showSnack(R.string.empty_password_msg);
            return false;
        }
        return true;
    }

    LoginTask.LoginResponseCallback loginResponseCallback = new LoginTask.LoginResponseCallback() {
        @Override
        public void onSuccessLoginResponse(String response) {
            dismissProgressDialog();
            int otpNumber = 0;
            try {
                JSONObject jsonObject = new JSONObject(response);

                JSONObject contentObject = (JSONObject) jsonObject.get(ApiUtils.CONTENT);

                JSONArray data = (JSONArray) contentObject.get(ApiUtils.RESULT_SET);
                if (data.length() > 0) {
                    JSONObject jsonobject = data.getJSONObject(0);
                    otpNumber = jsonobject.getInt(PreferenceUtils.OTP_NO);
                }
                Intent intent = new Intent(LoginActivity.this, OtpActivity.class);
                intent.putExtra(PreferenceUtils.EMAILID, str_login_id);
                intent.putExtra(PreferenceUtils.OTP_NO, otpNumber);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onFailureLoginResponse(String response) {
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
