package com.twc.rca.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by Sushil on 24-04-2018.
 */

public class BaseFragment extends Fragment {
    ProgressDialog mProgressDialog;

    CoordinatorLayout mCoordinatorLayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    // Progress dialog common code
    protected void showProgressDialog() {
        showProgressDialog(null);
    }

    protected ProgressDialog showProgressDialog(String message) {
        initProgressDialog();
        if (!mProgressDialog.isShowing())
            mProgressDialog.show();
        if (message != null) mProgressDialog.setMessage(message);

        return mProgressDialog;
    }

    protected void initProgressDialog() {
        if (mProgressDialog != null) return;
        mProgressDialog = new ProgressDialog(getActivity());
        //      mProgressDialog.setIndeterminate(true);
        //      mProgressDialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progress_dialog_icon_drawable_animation));
    }

    protected void dismissProgressDialog() {
        if (mProgressDialog == null) return;
        if (mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    public void showToast(int stringResId) {
        showToast(getString(stringResId));
    }

    public void showToast(String string) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show();
    }

}
