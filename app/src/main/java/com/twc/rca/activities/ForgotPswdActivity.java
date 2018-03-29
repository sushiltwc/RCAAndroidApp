package com.twc.rca.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.twc.rca.R;

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

        btn_fp_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotPswdActivity.this, ResetPswdActivity.class);
                startActivity(intent);
            }
        });
    }
}
