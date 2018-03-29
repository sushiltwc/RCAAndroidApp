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
 * Created by TWC on 21-02-2018.
 */

public class HomeFragment extends Fragment {

    ViewPager mViewPager;

    DubaiVisaFragment mDubaiVisaFragment;

    MNAFragment mMnAFragment;

    public static final int DUBAI_VISA = 0, MEET_ASSIST = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);

        return view;
    }

    void initView(View view) {
        mViewPager = (ViewPager) view.findViewById(R.id.dashboard_pager);
        FragmentStatePagerAdapter manager = new DashboardPagerAdapter(getFragmentManager());
        mViewPager.setAdapter(manager);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.dashboard_tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    class DashboardPagerAdapter extends FragmentStatePagerAdapter {

        public DashboardPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case DUBAI_VISA:
                    return mDubaiVisaFragment = DubaiVisaFragment.getInstance();

                case MEET_ASSIST:
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

                case DUBAI_VISA:
                    return getString(R.string.dubai_visa);

                case MEET_ASSIST:
                    return getString(R.string.meet_assist);

                default:
                    return null;
            }
        }
    }
}
