package com.twc.rca.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.Button;

import com.twc.rca.R;
import com.twc.rca.utils.InitUtils;
import com.twc.rca.walkthrough.DissmissLastViewPager;
import com.twc.rca.walkthrough.DotPagerIndicator;
import com.twc.rca.walkthrough.WalkthroughFragment;

/**
 * Created by Sushil on 08-02-2018.
 */

public class InitialActivity extends BaseActivity {
    DissmissLastViewPager mPager;

    DotPagerIndicator mPagerIndicator;

    CustomPagerAdapter mPagerAdapter;

    Button btn_skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        initView();
    }

    void initView() {
        mPager = (DissmissLastViewPager) findViewById(R.id.pager);
        mPager.setOnSwipeOutListener(mSwipeOutListener);
        mPagerIndicator = (DotPagerIndicator) findViewById(R.id.pager_indicator);
        mPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPagerIndicator.setViewPager(mPager);

        btn_skip = (Button) findViewById(R.id.btn_skip);
        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishTutorial();
            }
        });
    }

    private class CustomPagerAdapter extends FragmentPagerAdapter {

        public CustomPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return WalkthroughFragment.getInstance(InitUtils.PAGES[position]);
        }

        @Override
        public int getCount() {
            return InitUtils.PAGES.length;
        }
    }

    DissmissLastViewPager.OnSwipeOutListener mSwipeOutListener = new DissmissLastViewPager.OnSwipeOutListener() {
        @Override
        public void onSwipeOutAtEnd() {
            finishTutorial();
        }
    };

    void finishTutorial() {
      //  storeTutorialSeen();
        launchNextScreen();
        finish();
    }

    void launchNextScreen() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

 /*   void storeTutorialSeen() {
        PreferenceUtils.setTutorialShown(this, InitUtils.INITIAL);
    }*/


}
