package com.twc.rca.product.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.twc.rca.R;
import com.twc.rca.activities.BaseActivity;
import com.twc.rca.product.fragments.AllApplicantFragment;
import com.twc.rca.product.fragments.CurrentApplicantFragment;
import com.twc.rca.utils.PreferenceUtils;

/**
 * Created by Sushil on 15-03-2018.
 */

public class OrderApplicantListActivity extends BaseActivity {

    ViewPager mViewPager;

    TextView tv_actionbar_title;

    ImageButton img_button_back;

    String orderId;

    public static final int CURRENT_APPLICANT = 0, ALL_APPLICANT = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_applicant_list);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            View viewActionBar = getLayoutInflater().inflate(R.layout.layout_actionbar, null);
            ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                    ActionBar.LayoutParams.MATCH_PARENT,
                    ActionBar.LayoutParams.WRAP_CONTENT,
                    Gravity.CENTER);
            tv_actionbar_title = (TextView) viewActionBar.findViewById(R.id.tv_actionbar_title);
            img_button_back = (ImageButton) viewActionBar.findViewById(R.id.img_btn_back);
            img_button_back.setVisibility(View.VISIBLE);
            actionBar.setCustomView(viewActionBar, params);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            Toolbar toolbar = (Toolbar) actionBar.getCustomView().getParent();
            toolbar.setContentInsetsAbsolute(0, 0);
            toolbar.getContentInsetEnd();
            toolbar.setPadding(0, 0, 0, 0);

            img_button_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
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

        orderId = getIntent().getStringExtra("orderId");
        initView();
    }

    void initView() {
        mViewPager = (ViewPager) findViewById(R.id.my_account_pager);
        FragmentStatePagerAdapter manager = new MyAccountPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(manager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.my_account_tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tv_actionbar_title.setText(getString(R.string.your_applicants));
    }

    class MyAccountPagerAdapter extends FragmentStatePagerAdapter {

        public MyAccountPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case CURRENT_APPLICANT:
                    Bundle bundle = new Bundle();
                    bundle.putString("orderId", orderId);
                    fragment = CurrentApplicantFragment.getInstance();
                    fragment.setArguments(bundle);
                    break;

                case ALL_APPLICANT:
                    fragment = AllApplicantFragment.getInstance();
                    break;

                default:
                    fragment = null;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {

                case CURRENT_APPLICANT:
                    return getString(R.string.current);

                case ALL_APPLICANT:
                    return getString(R.string.all);

                default:
                    return null;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (PreferenceUtils.isApplicantionSubmitted(this)) {
            PreferenceUtils.setIsApplicantionSubmitted(this, false);
            finish();
            startActivity(getIntent());
        }
    }

    @Override
    protected boolean isHomeAsUpEnabled() {
        return false;
    }
}
