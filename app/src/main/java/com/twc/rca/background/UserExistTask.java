package com.twc.rca.background;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.twc.rca.utils.ApiUtils;
import com.twc.rca.utils.ILog;
import com.twc.rca.utils.MailUtils;
import com.twc.rca.volley.utils.VolleySingleTon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Sushil on 08-03-2018.
 */

public class UserExistTask extends ApiUtils {

    protected static final String TAG = UserExistTask.class.getSimpleName();

    Context context;

    public static String METHOD_NAME="user_exist";

    ArrayList<String> email;

    /**
     * Method to check user exist in system or not
     */

    public UserExistTask(Context context, ArrayList<String> email) {
        this.context = context;
        this.email = email;
    }

    public void isUserExist(final Context context, final UserExistResponseCallback userExistResponseCallback) throws JSONException {

        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray(MailUtils.getGoogleAccountMail(context));
        try {
            json.put(EMAIL_ID, jsonArray);
            json.put(METHOD,METHOD_NAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, BASE_APP_URL, json, new Response.Listener<JSONObject>() {
        @Override
            public void onResponse(JSONObject response) {
                ILog.d(TAG, response.toString());
                if (isValidResponse(response.toString()))
                    userExistResponseCallback.onSuccessUserExistResponse(response.toString());
                else
                    userExistResponseCallback.onFailureUserExistResponse(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ILog.d(TAG, error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new LinkedHashMap<>();
                map.put(ApiUtils.CONTENT_TYPE, "application/json");
                return map;
            }

        };
        VolleySingleTon.getInstance(context).addToRequestQueue(postRequest);
        postRequest.setRetryPolicy(new DefaultRetryPolicy(25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ILog.d(TAG,json.toString());
    }

    public interface UserExistResponseCallback {
        void onSuccessUserExistResponse(String response);

        void onFailureUserExistResponse(String response);
    }
}



