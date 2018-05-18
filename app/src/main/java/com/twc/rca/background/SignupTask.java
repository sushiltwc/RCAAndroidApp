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
 * Created by Sushil on 08-03-2018.
 */

public class SignupTask extends ApiUtils {

    protected static final String TAG = SignupTask.class.getSimpleName();

    Context context;

    public static String METHOD_NAME = "signup";

    String emailId, phoneNo, userName, password;

    public SignupTask(String emailId, String phoneNo, String userName, String password) {
        this.emailId = emailId;
        this.phoneNo = phoneNo;
        this.userName = userName;
        this.password = password;
    }

    public void userSignUp(final SignUpResponseCallback signUpResponseCallback) {

        JsonObjectRequest signUpRequest = new JsonObjectRequest(Request.Method.POST, BASE_APP_URL, new JSONObject(getParams(context)), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                ILog.d(TAG, response.toString());
                if (isValidResponse(response.toString()))
                    signUpResponseCallback.onSuccessSignUpResponse(response.toString());
                else
                    signUpResponseCallback.onFailureSignUpResponse(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ILog.d(TAG, error.toString());
                signUpResponseCallback.onFailureSignUpResponse(NetworkErrorHelper.getErrorStatus(error).getErrorMessage());
            }
        });
        VolleySingleTon.getInstance(context).addToRequestQueue(signUpRequest);
        signUpRequest.setRetryPolicy(new DefaultRetryPolicy(15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    protected Map<String, Object> getParams(Context context) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(EMAIL_ID, emailId);
        map.put(PHONE_NO, phoneNo);
        map.put(USER_NAME, userName);
        map.put(PASSWORD, password);
        map.put(METHOD, METHOD_NAME);
        return map;
    }

    public interface SignUpResponseCallback {
        void onSuccessSignUpResponse(String response);

        void onFailureSignUpResponse(String response);
    }
}
