package com.twc.rca.permissions;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.twc.rca.activities.BaseActivity;
import com.twc.rca.R;

/**
 * Dialog to be shown on asking of the permission.
 * <p/>
 * Created by sushil
 */
public class PermissionDialog extends Dialog implements View.OnClickListener {

    private final Activity context;
    private PermissionDialog mDialog;

    private String[] permission;
    private int requestCode;
    private boolean isShowNotNow;

    public PermissionDialog(Activity context) {
        this(context, 0);
    }

    public PermissionDialog(Activity context, int theme_black_noTitleBar_fullscreen) {
        super(context, theme_black_noTitleBar_fullscreen);
        this.context = context;
    }

    public PermissionDialog showPermission(int requestCode, String... permission) {
        return showPermission(requestCode, false, permission);
    }

    public PermissionDialog showPermission(int requestCode, boolean isShowNotNow, String... permission) {
        return createDialog(requestCode, isShowNotNow, permission);
    }

    private PermissionDialog createDialog(int requestCode, boolean isShowNotNow, String... permission) {

        this.permission = permission;
        this.requestCode = requestCode;
        this.isShowNotNow = isShowNotNow;

        mDialog = new PermissionDialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        View mView = LayoutInflater.from(context).inflate(R.layout.view_permissions_info, null);
        mDialog.setContentView(mView);
        mDialog.getWindow().setBackgroundDrawable(null);

        updatePermissionDescription(mView, requestCode);

        Button permission_continue = (Button) mView.findViewById(R.id.permission_continue);
        permission_continue.setOnClickListener(this);

        Button permission_not_now = (Button) mView.findViewById(R.id.permission_not_now);

        permission_not_now.setVisibility(isShowNotNow ? View.VISIBLE : View.INVISIBLE);
        permission_not_now.setOnClickListener(this);

        TextView request_personal_data_tv = (TextView) mView.findViewById(R.id.request_personal_data_tv);
        PermissionDialogUtil.changePersonalDataVisibility(request_personal_data_tv, isShowNotNow, requestCode);

        mDialog.setCancelable(isShowNotNow);

        return mDialog;
    }

    private void updatePermissionDescription(View mView, int requestCode) {
        TextView desc = (TextView) mView.findViewById(R.id.permission_dialog_desc_tv);

        switch (requestCode) {
            case RunTimePermissionWrapper.REQUEST_CODE.MULTIPLE_REGISTRATION:
                desc.setText(context.getString(R.string.permission_registration_dialog_desc));
                break;

            case RunTimePermissionWrapper.REQUEST_CODE.MULTIPLE_ACCOUNT_SCREENS:
                desc.setText(context.getString(R.string.permission_acccount_dialog_desc));
                break;

            case RunTimePermissionWrapper.REQUEST_CODE.LOCATION:
                desc.setText("");
                break;

            case RunTimePermissionWrapper.REQUEST_CODE.SMS_DEALS_VERIFY_OTP:
            case RunTimePermissionWrapper.REQUEST_CODE.SMS_DEALS_SEND_OTP:
                desc.setText("");
                break;
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.permission_not_now:
                dismissPermissionDialog();
                showPermissionSnackBar();
                break;

            case R.id.permission_continue:
                dismissPermissionDialog();
                handleContinuePermission();
                break;
        }
    }

    private void showPermissionSnackBar() {
        if (context == null)
            return;

        if (context instanceof BaseActivity) {
            //((BaseActivity) context).showSnack(R.string.permission_snack_msg, R.string.permission_snack_action_msg, actionOnClickListener);
        }

    }

    View.OnClickListener actionOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PermissionDialogUtil.handlePermissionRequest(context, requestCode, permission);
        }
    };

    private void handleContinuePermission() {
        PermissionDialogUtil.handlePermissionRequest(context, requestCode, permission);
    }

    private void dismissPermissionDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }
}
