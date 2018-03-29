package com.twc.rca.permissions;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.twc.rca.activities.BaseActivity;
import com.twc.rca.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper for Permission Dialog.
 * <p/>
 * Created by sushil
 */
public class PermissionDialogUtil {

    private static final long DELAY = 400;

    public static void handlePermissionRequest(final Activity context, int requestCode, final String... grantResult) {

        if (isNeverAskAgainCheck(context, grantResult)) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.fromParts("package", context.getPackageName(), null));
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            context.startActivityForResult(intent, requestCode);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (context != null)
                        ((BaseActivity) context).showToast(context.getString(R.string.permission_toast_msg, TextUtils.join(",", getPermissionValues(grantResult))));
                }
            }, DELAY);

        } else {
            String deniedArray[] = RunTimePermissionWrapper.getDeniedPermissionArray(context, grantResult);
            if (deniedArray != null && deniedArray.length > 0)
                ActivityCompat.requestPermissions(context, deniedArray, requestCode);
        }

    }

    private static Object[] getPermissionValues(String[] grantResult) {

        List<String> list = new ArrayList<>();
        for (String result : grantResult) {
            switch (result) {
                case Manifest.permission.READ_CALL_LOG:
                    list.add(RunTimePermissionWrapper.PERMISSION_TYPE.TELEPHONE);
                    break;
                case Manifest.permission.READ_SMS:
                    list.add(RunTimePermissionWrapper.PERMISSION_TYPE.SMS);
                    break;
                case Manifest.permission.READ_CONTACTS:
                    list.add(RunTimePermissionWrapper.PERMISSION_TYPE.CONTACTS);
                    break;
                case Manifest.permission.READ_EXTERNAL_STORAGE:
                    list.add(RunTimePermissionWrapper.PERMISSION_TYPE.STORAGE);
                    break;
                case Manifest.permission.ACCESS_COARSE_LOCATION:
                    list.add(RunTimePermissionWrapper.PERMISSION_TYPE.LOCATION);
                    break;
                case Manifest.permission.CAMERA:
                    list.add(RunTimePermissionWrapper.PERMISSION_TYPE.CAMERA);
                    break;

            }
        }

        return list.toArray();
    }


    /**
     * If user select neverAskAgain then shouldShowRequestPermissionRationale return false on deny its returns true.
     *
     * @param activity
     * @param permission
     * @return
     */
    public static boolean isNeverAskAgainCheck(Activity activity, String... permission) {

        for (String result : permission) {
            if (PermissionPrefs.isNeverAskAgain(activity, result)) {
                return true;
            }
        }
        return false;
    }

    public static void changePersonalDataVisibility(TextView request_personal_data_tv, boolean isShowNotNow, int requestCode) {
        request_personal_data_tv.setVisibility((requestCode == RunTimePermissionWrapper.REQUEST_CODE.MULTIPLE_REGISTRATION) ? View.VISIBLE : View.INVISIBLE);
    }
}
