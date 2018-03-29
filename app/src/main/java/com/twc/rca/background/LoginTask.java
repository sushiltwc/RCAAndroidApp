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
 * Created by TWC on 10-03-2018.
 */

public class LoginTask extends ApiUtils {

    protected static final String TAG = LoginTask.class.getSimpleName();

    Context context;

    public static String METHOD_NAME = "login";

    public static String EMAILID = "emailId", PASSWORD = "password";

    String emailId, password;

    public LoginTask(Context context,String emailId, String password) {
        this.context=context;
        this.emailId = emailId;
        this.password = password;
    }

    public void userLogin(final LoginResponseCallback loginResponseCallback) {

        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, BASE_APP_URL, new JSONObject(getParams(context)), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                ILog.d(TAG, response.toString());
                if (isValidResponse(response.toString()))
                    loginResponseCallback.onSuccessLoginResponse(response.toString());
                else
                    loginResponseCallback.onFailureLoginResponse(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ILog.d(TAG, error.toString());
                loginResponseCallback.onFailureLoginResponse(NetworkErrorHelper.getErrorStatus(error).getErrorMessage());
            }
        });
        VolleySingleTon.getInstance(context).addToRequestQueue(loginRequest);
        loginRequest.setRetryPolicy(new DefaultRetryPolicy(15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    protected Map<String, Object> getParams(Context context) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(EMAILID, emailId);
        map.put(PASSWORD, password);
        map.put(ApiUtils.ACCESS_TOKEN, "Ramukaka");
        map.put(ApiUtils.METHOD, METHOD_NAME);
        return map;
    }

    public interface LoginResponseCallback {
        void onSuccessLoginResponse(String response);

        void onFailureLoginResponse(String response);
    }
}
