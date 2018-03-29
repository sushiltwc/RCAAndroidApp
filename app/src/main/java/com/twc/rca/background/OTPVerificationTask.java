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
 * Created by Sushil on 10-03-2018.
 */

public class OTPVerificationTask extends ApiUtils {

    protected static final String TAG = OTPVerificationTask.class.getSimpleName();

    Context context;

    public static String METHOD_NAME = "otp_verification";

    public static String EMAILID = "emailId", DEVICEID = "deviceId", OTP_NUMBER = "otpNumber";

    String emailId;

    int otpNumner;

    public OTPVerificationTask(String emailId, int otpNumber) {
        this.emailId = emailId;
        this.otpNumner = otpNumber;
    }

    public void otpVerification(final OTPVerificationResponseCallback otpVerificationResponseCallback) {

        JsonObjectRequest signUpRequest = new JsonObjectRequest(Request.Method.POST, BASE_APP_URL, new JSONObject(getParams(context)), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                ILog.d(TAG, response.toString());
                if (isValidResponse(response.toString()))
                    otpVerificationResponseCallback.onSucceseOTPVerificationResponse(response.toString());
                else
                    otpVerificationResponseCallback.onFailureOTPVerificationResponse(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ILog.d(TAG, error.toString());
                otpVerificationResponseCallback.onFailureOTPVerificationResponse(NetworkErrorHelper.getErrorStatus(error).getErrorMessage());
            }
        });
        VolleySingleTon.getInstance(context).addToRequestQueue(signUpRequest);
        signUpRequest.setRetryPolicy(new DefaultRetryPolicy(15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    protected Map<String, Object> getParams(Context context) {
        String ts = Context.TELEPHONY_SERVICE;
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(EMAILID, emailId);
        map.put(OTP_NUMBER, otpNumner);
        map.put(DEVICEID, "90");
        map.put(ApiUtils.ACCESS_TOKEN, "Ramukaka");
        map.put(ApiUtils.METHOD, METHOD_NAME);
        return map;
    }

    public interface OTPVerificationResponseCallback {
        void onSucceseOTPVerificationResponse(String response);

        void onFailureOTPVerificationResponse(String response);
    }
}
