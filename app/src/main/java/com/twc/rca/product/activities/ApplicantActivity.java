package com.twc.rca.product.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.twc.rca.R;
import com.twc.rca.activities.BaseActivity;
import com.twc.rca.applicant.model.ApplicantModel;
import com.twc.rca.product.fragments.ApplicationFormFragment;
import com.twc.rca.product.fragments.DocumentFragment;

/**
 * Created by Sushil on 06-03-2018.
 */

public class ApplicantActivity extends BaseActivity {

    CustomViewPager mViewPager;

    ApplicantModel applicantModel;

    public static final int VISA_DOCUMENT = 0, APPLICANT = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicant);
        applicantModel = getIntent().getParcelableExtra("applicant");
        initView();
    }

    void initView() {
        mViewPager = (CustomViewPager) findViewById(R.id.applicant_pager);
        ApplicantPagerAdapter manager = new ApplicantPagerAdapter(this.getSupportFragmentManager());
        mViewPager.setAdapter(manager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.applicant_tabs);
        tabLayout.setupWithViewPager(mViewPager);
        if (applicantModel.getApplicantSubmited().equalsIgnoreCase("Y")) {
            mViewPager.setPagingEnabled(true);
            ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(1);
            vgTab.setEnabled(true);
        } else {
            mViewPager.setPagingEnabled(false);
            ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(1);
            vgTab.setEnabled(false);
        }
    }

    class ApplicantPagerAdapter extends FragmentStatePagerAdapter {

        public ApplicantPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            Bundle bundle = new Bundle();
            bundle.putParcelable("applicant", applicantModel);
            switch (position) {
                case VISA_DOCUMENT:
                    fragment = DocumentFragment.getInstance();
                    fragment.setArguments(bundle);
                    break;

                case APPLICANT:
                    fragment = ApplicationFormFragment.newInstance();
                    fragment.setArguments(bundle);
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
