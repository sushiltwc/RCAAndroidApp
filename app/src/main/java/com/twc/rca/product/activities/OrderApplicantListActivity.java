package com.twc.rca.product.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

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

    String orderId;

    public static final int CURRENT_APPLICANT = 0, ALL_APPLICANT = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_applicant_list);
        orderId = getIntent().getStringExtra("orderId");
        initView();
    }

    void initView() {
        mViewPager = (ViewPager) findViewById(R.id.my_account_pager);
        FragmentStatePagerAdapter manager = new MyAccountPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(manager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.my_account_tabs);
        tabLayout.setupWithViewPager(mViewPager);
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

}
