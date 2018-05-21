package com.twc.rca.product.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.twc.rca.R;
import com.twc.rca.activities.BaseActivity;
import com.twc.rca.applicant.model.ApplicantModel;
import com.twc.rca.product.fragments.ApplicationFormFragment;
import com.twc.rca.product.fragments.DocumentFragment;
import com.twc.rca.utils.ApiUtils;

/**
 * Created by Sushil on 06-03-2018.
 */

public class ApplicantActivity extends BaseActivity {

    CustomViewPager mViewPager;

    TextView tv_actionbar_title;

    ImageButton img_button_back;

    ApplicantModel applicantModel;

    public static final int VISA_DOCUMENT = 0, APPLICANT = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicant);
        applicantModel = getIntent().getParcelableExtra("applicant");

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            View viewActionBar = getLayoutInflater().inflate(R.layout.layout_applicant_actionbar, null);
            ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                    ActionBar.LayoutParams.MATCH_PARENT,
                    ActionBar.LayoutParams.WRAP_CONTENT,
                    Gravity.CENTER);
            tv_actionbar_title = (TextView) viewActionBar.findViewById(R.id.tv_applicant_actionbar_title);
            img_button_back = (ImageButton) viewActionBar.findViewById(R.id.img_btn_back_arrow);
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
        if (applicantModel.getApplicantSurname().equalsIgnoreCase("null"))
            tv_actionbar_title.setText("Application for " + ApiUtils.getFormattedString(applicantModel.getApplicantGivenName()));
        else
            tv_actionbar_title.setText("Application for " + ApiUtils.getFormattedString(applicantModel.getApplicantGivenName()) + ApiUtils.getFormattedString(applicantModel.getApplicantSurname()));
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

    @Override
    protected boolean isHomeAsUpEnabled() {
        return false;
    }
}
