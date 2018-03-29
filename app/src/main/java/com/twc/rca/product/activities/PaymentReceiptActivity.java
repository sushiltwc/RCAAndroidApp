package com.twc.rca.product.activities;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.twc.rca.R;
import com.twc.rca.activities.BaseActivity;
import com.twc.rca.activities.DashboardActivity;
import com.twc.rca.utils.PreferenceUtils;

/**
 * Created by Sushil on 07-03-2018.
 */

public class PaymentReceiptActivity extends BaseActivity {

    AppCompatButton btn_lets_start;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_receipt);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();

            // clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            // finally change the color

            window.setStatusBarColor(this.getResources().getColor(R.color.text_color));
        }

        btn_lets_start=(AppCompatButton)findViewById(R.id.btn_lets_start);

        btn_lets_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(PaymentReceiptActivity.this, DashboardActivity.class);
               // i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                ComponentName cn = i.getComponent();
                Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
                startActivity(mainIntent);
                PreferenceUtils.setIsPaymentDone(PaymentReceiptActivity.this,true);
            }
        });
    }

    @Override
    protected boolean isHomeAsUpEnabled() {
        return false;
    }
}
