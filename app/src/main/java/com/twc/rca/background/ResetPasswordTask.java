package com.twc.rca.background;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.twc.rca.utils.ApiUtils;
import com.twc.rca.utils.ILog;
import com.twc.rca.volley.utils.NetworkErrorHelper;
import com.twc.rca.volley.utils.VolleySingleTon;

import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Sushil on 10-05-2018.
 */

public class ResetPasswordTask extends ApiUtils {

    protected static final String TAG = ResetPasswordTask.class.getSimpleName();

    Context context;

    public static String OTP_NUMBER = "otp_number";

    public static String METHOD_NAME = "reset_pwd";

    String emailId, otpNumber, pswd;

    public ResetPasswordTask(Context context, String emailId, String otpNumber, String pswd) {
        this.context = context;
        this.emailId = emailId;
        this.otpNumber = otpNumber;
        this.pswd = pswd;
    }

    public void userResetPswd(final ResetPswdResponseCallback resetPswdResponseCallback) {

        JsonObjectRequest resetPswdRequest = new JsonObjectRequest(Request.Method.POST, BASE_APP_URL, new JSONObject(getParams(context)), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                ILog.d(TAG, response.toString());
                if (isValidResponse(response.toString()))
                    resetPswdResponseCallback.onSuccessResetPswdResponse(response.toString());
                else
                    resetPswdResponseCallback.onFailureResetPswdResponse(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ILog.d(TAG, error.toString());
                resetPswdResponseCallback.onFailureResetPswdResponse(NetworkErrorHelper.getErrorStatus(error).getErrorMessage());
            }
        });
        VolleySingleTon.getInstance(context).addToRequestQueue(resetPswdRequest);
    }

    protected Map<String, Object> getParams(Context context) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(EMAIL_ID, emailId);
        map.put(OTP_NUMBER, otpNumber);
        map.put(PASSWORD, pswd);
        map.put(ApiUtils.METHOD, METHOD_NAME);
        return map;
    }

    public interface ResetPswdResponseCallback {
        void onSuccessResetPswdResponse(String response);

        void onFailureResetPswdResponse(String response);
    }
}
