package com.twc.rca.activities;

/**
 * Created by TWC on 15-02-2018.
 */

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import com.twc.rca.R;
import com.twc.rca.background.UserExistTask;
import com.twc.rca.model.UserExist;
import com.twc.rca.permissions.PermissionDialogUtil;
import com.twc.rca.permissions.RunTimePermissionWrapper;
import com.twc.rca.utils.ApiUtils;
import com.twc.rca.utils.MailUtils;
import com.twc.rca.utils.PreferenceUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class SplashScreenActivity extends BaseActivity {

    private int timeoutMillis = 5000;

    ArrayList<UserExist> userExistArrayList;

    ArrayList<String> userExistMailIds;

    /**
     * The time when this {@link Activity} was created.
     */
    private long startTimeMillis = 0;

    private static final Random random = new Random();

    public int getTimeoutMillis() {
        return timeoutMillis;
    }

    @TargetApi(23)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /** Default creation code. */
        super.onCreate(savedInstanceState);

        /** Set the mainLayout as the content view */
        setContentView(R.layout.activity_splash);

        userExistArrayList = new ArrayList<>();

        userExistMailIds = new ArrayList<>();

        /**
         * Save the start time of this Activity, which will be used to determine
         * when the splash screen should timeout.
         */
        startTimeMillis = System.currentTimeMillis();

        /**
         * On a post-Android 6.0 devices, check if the required permissions have
         * been granted.
         */
        if (Build.VERSION.SDK_INT >= 23) {
            checkPermissions();
        } else {
            userExistCheck();
        }
    }

    /**
     * See if we now have all of the required dangerous permissions. Otherwise,
     * tell the user that they cannot continue without granting the permissions,
     * and then request the permissions again.
     */
    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (RunTimePermissionWrapper.isAllPermissionGranted(this, RunTimePermissionWrapper.PERMISSION_LIST.REGISTRATION)) {
            checkPermissions();
        }
        else {
            userExistCheck();;
        }
    }

    void userExistCheck(){
        if(PreferenceUtils.isRegistered(this)){
            startNextActivity();
        }
        else {
            try {
                new UserExistTask(this, MailUtils.getGoogleAccountMail(this)).isUserExist(this, loginResponseCallback);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    UserExistTask.UserExistResponseCallback loginResponseCallback = new UserExistTask.UserExistResponseCallback() {

        @Override
        public void onSuccessUserExistResponse(String response) {
            try {
                JSONObject jsonObject = new JSONObject(response);

                JSONObject contentObject=(JSONObject)jsonObject.get(ApiUtils.CONTENT);
                UserExist userExist = new UserExist();

                JSONArray data = (JSONArray) contentObject.get(ApiUtils.RESULT_SET);
                if (data.length() > 0) {
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject jsonobject = data.getJSONObject(i);
                            userExist.userId = jsonobject.getString(PreferenceUtils.USERID);
                            userExist.email_id = jsonobject.getString(PreferenceUtils.EMAILID);
                            userExist.mobile_no = jsonobject.getString(PreferenceUtils.MOBILE_NO);
                            userExist.device_id = jsonobject.getString(PreferenceUtils.DEVICE_ID);

                            userExistArrayList.add(userExist);

                            userExistMailIds.add(userExist.email_id);
                    }
                }
                startNextActivity();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailureUserExistResponse(String response) {
            try {
                JSONObject jsonObject = new JSONObject(response);

                JSONObject contentObject = (JSONObject) jsonObject.get(ApiUtils.CONTENT);

                String message = (String) contentObject.get(ApiUtils.MESSAGE);

                showSnack(message);

                startNextActivity();

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    private void startNextActivity() {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
            }
        });
        long delayMillis = getTimeoutMillis() - (System.currentTimeMillis() - startTimeMillis);
        if (delayMillis < 0) {
            delayMillis = 0;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent launchIntent = null;
                if (PreferenceUtils.isRegistered(getApplicationContext())) {
                    launchIntent = new Intent(SplashScreenActivity.this, DashboardActivity.class);
                    startActivity(launchIntent);
                } else {
                        if(userExistArrayList.size()>0) {
                            launchIntent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                            launchIntent.putStringArrayListExtra(PreferenceUtils.USEREXIST_MAILID, userExistMailIds);
                        }
                        else {
                            launchIntent =new Intent(SplashScreenActivity.this,SignupActivity.class);
                            launchIntent.putStringArrayListExtra(PreferenceUtils.USEREXIST_MAILID,userExistMailIds);
                        }
                    }
                if (launchIntent != null) {
                    startActivity(launchIntent);
                    finish();
                }
            }
        }, delayMillis);
    }

    /**
     * Check if the required permissions have been granted, and
     * {@link #startNextActivity()} if they have. Otherwise
     * {@link #requestPermissions(String[], int)}.
     */
    private void checkPermissions() {
        if (RunTimePermissionWrapper.isAllPermissionGranted(this, RunTimePermissionWrapper.PERMISSION_LIST.REGISTRATION)) {
            userExistCheck();
        } else {
            PermissionDialogUtil.handlePermissionRequest(this, RunTimePermissionWrapper.REQUEST_CODE.MULTIPLE_REGISTRATION_PROFILE, RunTimePermissionWrapper.PERMISSION_LIST.REGISTRATION);
        }
    }
}