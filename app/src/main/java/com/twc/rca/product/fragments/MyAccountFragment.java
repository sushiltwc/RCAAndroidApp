package com.twc.rca.product.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.twc.rca.R;

/**
 * Created by Sushil on 15-03-2018.
 */

public class MyAccountFragment extends Fragment {

    ViewPager mViewPager;

    CurrentApplicantFragment mCurrentApplicantFragment;

    MNAFragment mMnAFragment;

    public static final int CURRENT_APPLICANT = 0, ALL_APPLICANT = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);

        return view;
    }

    void initView(View view) {
        mViewPager = (ViewPager) view.findViewById(R.id.dashboard_pager);
        FragmentStatePagerAdapter manager = new MyAccountPagerAdapter(getFragmentManager());
        mViewPager.setAdapter(manager);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.dashboard_tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    class MyAccountPagerAdapter extends FragmentStatePagerAdapter {

        public MyAccountPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position) {
                case CURRENT_APPLICANT:
                    return mCurrentApplicantFragment = CurrentApplicantFragment.getInstance();

                case ALL_APPLICANT:
                    return mMnAFragment = MNAFragment.getInstance();

                default:
                    return null;
            }
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
}
