package com.twc.rca.walkthrough;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Sushil on 08-02-2018.
 */

public class DissmissLastViewPager extends ViewPager {

    static final float THRESHOLD_DIFFERENCE = 20;

    float mStartDragX;

    OnSwipeOutListener mSwipeOutListener;

    boolean mSwipeOutDone = false;

    public DissmissLastViewPager(Context context) {
        super(context);
    }

    public DissmissLastViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnSwipeOutListener(OnSwipeOutListener listener) {
        mSwipeOutListener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        float x = ev.getX();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartDragX = x;
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                if (!mSwipeOutDone) {
                    if ((mStartDragX - x) >= THRESHOLD_DIFFERENCE && getCurrentItem() == getAdapter()
                            .getCount() - 1) {
                        if (mSwipeOutListener != null) {
                            mSwipeOutListener.onSwipeOutAtEnd();
                            mSwipeOutDone = true;
                        }
                    }
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    public interface OnSwipeOutListener {
        void onSwipeOutAtEnd();
    }
}

