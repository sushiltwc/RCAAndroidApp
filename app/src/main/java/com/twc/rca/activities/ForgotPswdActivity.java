package com.twc.rca.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.view.View;

import com.twc.rca.R;
import com.twc.rca.background.ForgotPswdTask;
import com.twc.rca.utils.ApiUtils;
import com.twc.rca.utils.ILog;
import com.twc.rca.utils.PreferenceUtils;

import org.json.JSONObject;

/**
 * Created by Sushil on 13-03-2018.
 */

public class ForgotPswdActivity extends BaseActivity {

    AppCompatButton btn_fp_next;
    AppCompatTextView tv_login_link;
    AppCompatEditText et_fp_email;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initview();
    }

    void initview() {
        et_fp_email = (AppCompatEditText) findViewById(R.id.et_forgot_pswd_email_id);
        tv_login_link = (AppCompatTextView) findViewById(R.id.tv_forgot_pswd_login_link);
        btn_fp_next = (AppCompatButton) findViewById(R.id.btn_forgot_pswd_next);

        tv_login_link.setText(Html.fromHtml("Go back to <font color='#66A1E7'>LOG IN</font>"));

        tv_login_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_fp_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ApiUtils.isValidMail(et_fp_email.getText().toString())) {
                    showProgressDialog(getString(R.string.please_wait));
                    new ForgotPswdTask(getApplicationContext(), et_fp_email.getText().toString()).userForgotPswd(forgotPswdResponseCallback);
                }
                else{
                    showToast(getApplicationContext().getResources().getString(R.string.email_validation_msg));
                }
            }
        });
    }

    ForgotPswdTask.ForgotPswdResponseCallback forgotPswdResponseCallback=new ForgotPswdTask.ForgotPswdResponseCallback() {
        @Override
        public void onSuccessForgotPswdResponse(String response) {
            dismissProgressDialog();
            try {
                JSONObject jsonObject = new JSONObject(response);

                JSONObject contentObject = (JSONObject) jsonObject.get(ApiUtils.CONTENT);

                finish();

                Intent intent = new Intent(ForgotPswdActivity.this, ResetPswdActivity.class);
                intent.putExtra(ApiUtils.MESSAGE,contentObject.getString(ApiUtils.MESSAGE));
                intent.putExtra(PreferenceUtils.EMAILID,et_fp_email.getText().toString());
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailureForgotPswdResponse(String response) {
            dismissProgressDialog();
        }
    };
}
