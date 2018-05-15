package com.twc.rca.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

/**
 * Created by TWC on 08-02-2018.
 */

public class BaseActivity extends AppCompatActivity {

    CoordinatorLayout mCoordinatorLayout;

    ProgressDialog mProgressDialog;

    private String TAG = BaseActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initActionBar();
    }

    /**
     * This function is for assigning back button on actionbar.
     * Should be overriden as false for those not wanting back functionality
     *
     * @return
     */
    protected boolean isHomeAsUpEnabled() {
        return true;
    }

    private void initActionBar() {
        if (isHomeAsUpEnabled()) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    // Progress dialog common code
    public void showProgressDialog() {
        showProgressDialog(null);
    }

    public ProgressDialog showProgressDialog(String message) {
        initProgressDialog();
        if (!mProgressDialog.isShowing())
            mProgressDialog.show();
        if (message != null) mProgressDialog.setMessage(message);

        return mProgressDialog;
    }

    public void initProgressDialog() {
        if (mProgressDialog != null) return;
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setInverseBackgroundForced(true);
        mProgressDialog.setCancelable(false);
        //       mProgressDialog.setIndeterminate(true);
//        mProgressDialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progress_dialog_icon_drawable_animation));
    }

    public void dismissProgressDialog() {
        if (mProgressDialog == null) return;
        if (mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public boolean showSnack(int stringResId) {
        return showSnack(getString(stringResId));
    }

    public boolean showSnack(String string) {
        if (mCoordinatorLayout != null) {
            Snackbar.make(mCoordinatorLayout, string, Snackbar.LENGTH_LONG).show();
            return true;
        } else {
            showToast(string);
            return false;
        }
    }

    public void showSnack(String content, String actionContent, View.OnClickListener actionOnClick) {
        View view = mCoordinatorLayout != null ? mCoordinatorLayout : findViewById(android.R.id.content);
        if (view != null)
            Snackbar.make(view, content, Snackbar.LENGTH_LONG)
                    .setAction(actionContent, actionOnClick)
                    .show();
    }


    //Toast Msg
    public void showToast(int stringResId) {
        showToast(getString(stringResId));
    }

    public void showToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }
}
