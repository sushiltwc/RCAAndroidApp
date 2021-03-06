package com.twc.rca.background;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
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

public class ResendOtpTask extends ApiUtils {

    protected static final String TAG = ResendOtpTask.class.getSimpleName();

    Context context;

    public static String METHOD_NAME = "resend_otp_to_email";

    String emailId;

    public ResendOtpTask(Context context, String emailId) {
        this.context = context;
        this.emailId = emailId;
    }

    public void userResendOtp(final ResendOtpResponseCallback resendOtpResponseCallback) {

        JsonObjectRequest resendOtpRequest = new JsonObjectRequest(Request.Method.POST, BASE_APP_URL, new JSONObject(getParams(context)), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                ILog.d(TAG, response.toString());
                if (isValidResponse(response.toString()))
                    resendOtpResponseCallback.onSuccessResendOtpResponse(response.toString());
                else
                    resendOtpResponseCallback.onFailureResendOtpResponse(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ILog.d(TAG, error.toString());
                resendOtpResponseCallback.onFailureResendOtpResponse(NetworkErrorHelper.getErrorStatus(error).getErrorMessage());
            }
        });
        VolleySingleTon.getInstance(context).addToRequestQueue(resendOtpRequest);
        resendOtpRequest.setRetryPolicy(new DefaultRetryPolicy(25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    protected Map<String, Object> getParams(Context context) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(EMAIL_ID, emailId);
        map.put(METHOD, METHOD_NAME);
        return map;
    }

    public interface ResendOtpResponseCallback {
        void onSuccessResendOtpResponse(String response);

        void onFailureResendOtpResponse(String response);
    }
}

