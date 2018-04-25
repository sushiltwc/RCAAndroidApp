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
import com.twc.rca.product.fragments.ApplicantionFragment;
import com.twc.rca.product.fragments.DocumentFragment;

/**
 * Created by Sushil on 06-03-2018.
 */

public class ApplicantActivity extends BaseActivity {

    ViewPager mViewPager;

    DocumentFragment mDocumentFragment;

    ApplicantionFragment mAplicantFragment;

    String applicantType;

    public static final int VISA_DOCUMENT = 0, APPLICANT = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicant);
        applicantType = getIntent().getStringExtra("applicant_type");
        initView();
    }

    void initView() {
        mViewPager = (ViewPager) findViewById(R.id.applicant_pager);
        FragmentStatePagerAdapter manager = new ApplicantPagerAdapter(this.getSupportFragmentManager());
        mViewPager.setAdapter(manager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.applicant_tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    class ApplicantPagerAdapter extends FragmentStatePagerAdapter {

        public ApplicantPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            Bundle bundle = new Bundle();
            bundle.putString("applicant_type", applicantType);
            switch (position) {
                case VISA_DOCUMENT:
                    fragment = DocumentFragment.getInstance();
                    fragment.setArguments(bundle);
                    break;

                case APPLICANT:
                    fragment = ApplicantionFragment.newInstance();
                    break;

                default:
                    fragment = null;
                    break;
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

                case VISA_DOCUMENT:
                    return getString(R.string.visa_document);

                case APPLICANT:
                    return getString(R.string.applicantion);

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
}
