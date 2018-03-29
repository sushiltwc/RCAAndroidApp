package com.twc.rca.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.twc.rca.R;
import com.twc.rca.utils.PreferenceUtils;

/**
 * Created by Sushil on 13-03-2018.
 */

public class VerificationActivity extends BaseActivity {

    AppCompatTextView tv_verification_mailId;

    AppCompatButton btn_continue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        tv_verification_mailId=(AppCompatTextView)findViewById(R.id.tv_verification_mailId);
        tv_verification_mailId.setText(getIntent().getStringExtra(PreferenceUtils.EMAILID));

        btn_continue=(AppCompatButton)findViewById(R.id.btn_verification_continue);

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(VerificationActivity.this,DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
