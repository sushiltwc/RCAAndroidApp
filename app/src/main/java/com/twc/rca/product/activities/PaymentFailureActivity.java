package com.twc.rca.product.activities;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.twc.rca.R;
import com.twc.rca.activities.BaseActivity;
import com.twc.rca.activities.DashboardActivity;
import com.twc.rca.utils.PreferenceUtils;

/**
 * Created by Sushil on 11-05-2018.
 */

public class PaymentFailureActivity extends BaseActivity {

    AppCompatButton btn_go_back;
    TextView tv_actionbar_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_failure_receipt);

        final ActionBar actionBar = getSupportActionBar();
        if(actionBar !=null)
        {
            View viewActionBar = getLayoutInflater().inflate(R.layout.layout_actionbar, null);
            ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                    ActionBar.LayoutParams.MATCH_PARENT,
                    ActionBar.LayoutParams.WRAP_CONTENT,
                    Gravity.CENTER);
            tv_actionbar_title = (TextView) viewActionBar.findViewById(R.id.tv_actionbar_title);
            tv_actionbar_title.setText(getString(R.string.payment_receipt));
            actionBar.setCustomView(viewActionBar, params);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            Toolbar toolbar=(Toolbar)actionBar.getCustomView().getParent();
            toolbar.setContentInsetsAbsolute(0, 0);
            toolbar.getContentInsetEnd();
            toolbar.setPadding(0, 0, 0, 0);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();

            // clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            // finally change the color

            window.setStatusBarColor(this.getResources().getColor(R.color.text_color));
        }

        btn_go_back=(AppCompatButton)findViewById(R.id.btn_go_back);

        btn_go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(PaymentFailureActivity.this, DashboardActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
                PreferenceUtils.setIsPaymentDone(PaymentFailureActivity.this,false);
            }
        });
    }

    @Override
    protected boolean isHomeAsUpEnabled() {
        return false;
    }
}
