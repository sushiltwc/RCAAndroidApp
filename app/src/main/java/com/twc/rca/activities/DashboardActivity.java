package com.twc.rca.activities;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.twc.rca.R;
import com.twc.rca.product.fragments.DubaiVisaFragment;
import com.twc.rca.product.fragments.OrderFragment;
import com.twc.rca.utils.PreferenceUtils;

/**
 * Created by Sushil on 21-02-2018.
 */

public class DashboardActivity extends BaseActivity {

    // Behaviour for twice back to exit
    public static final int BACK_PRESS_INTERVAL = 3000;

    private long mBackPressedTime;

    AHBottomNavigation bottomNavigation;

    TextView tv_actionbar_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            View viewActionBar = getLayoutInflater().inflate(R.layout.layout_actionbar, null);
            ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                    ActionBar.LayoutParams.MATCH_PARENT,
                    ActionBar.LayoutParams.WRAP_CONTENT,
                    Gravity.CENTER);
            tv_actionbar_title = (TextView) viewActionBar.findViewById(R.id.tv_actionbar_title);
            actionBar.setCustomView(viewActionBar, params);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            Toolbar toolbar = (Toolbar) actionBar.getCustomView().getParent();
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

        initView();
        bottomNavigation.setCurrentItem(0);
    }

    void initView() {

        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);

        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Home", R.drawable.ic_home);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Track Visa", R.drawable.ic_track_visa);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("Notifications", R.drawable.ic_notifications);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("My Account", R.drawable.ic_my_account);

        // Add items
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);

        // Set background color
        bottomNavigation.setDefaultBackgroundColor(this.getResources().getColor(R.color.text_color));
        // Disable the translation inside the CoordinatorLayout
        bottomNavigation.setBehaviorTranslationEnabled(false);
        bottomNavigation.setAccentColor(this.getResources().getColor(R.color.colorPrimary));
        bottomNavigation.setInactiveColor(this.getResources().getColor(R.color.colorWhite));
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

        // Set current item programmatically
        bottomNavigation.setCurrentItem(0);
        bottomNavigation.setNotificationBackgroundColor(Color.parseColor("#F63D2B"));

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if (position == 0) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, new DubaiVisaFragment())
                            .commit();
                    tv_actionbar_title.setText(getString(R.string.dubai_visa));

                } else if (position == 3) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, new OrderFragment())
                            .commit();
                    tv_actionbar_title.setText(getString(R.string.your_orders));
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (PreferenceUtils.isPaymentDone(this)) {
            bottomNavigation.setCurrentItem(3);
            PreferenceUtils.setIsPaymentDone(this, false);
        }
    }

    @Override
    public void onBackPressed() {
        if (mBackPressedTime + BACK_PRESS_INTERVAL > System.currentTimeMillis()) {
            finish();
        } else {
            showSnack(getString(R.string.press_back_again_app_name));
            mBackPressedTime = System.currentTimeMillis();
        }
    }

    @Override
    protected boolean isHomeAsUpEnabled() {
        return false;
    }
}
