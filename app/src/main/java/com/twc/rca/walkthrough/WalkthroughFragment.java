package com.twc.rca.walkthrough;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.twc.rca.R;
import com.twc.rca.utils.InitUtils;

/**
 * Created by Sushil on 08-02-2018.
 */

public class WalkthroughFragment extends Fragment {

    public static final String PAGE = "page";

    int mPage;

    public static WalkthroughFragment getInstance(int page) {
        WalkthroughFragment fragment = new WalkthroughFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(PAGE, page);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(PAGE, InitUtils.TUTORIAL_1);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_walkthrough, container, false);
        initView(view);
        return view;
    }

    void initView(View view) {
        ImageView iconView = (ImageView) view.findViewById(R.id.icon);
        iconView.setImageResource(InitUtils.getTutorialIconId(mPage));

        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(InitUtils.getTitleId(mPage));
    }
}
