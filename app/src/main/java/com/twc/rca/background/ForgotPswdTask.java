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

public class ForgotPswdTask extends ApiUtils {

    protected static final String TAG = ForgotPswdTask.class.getSimpleName();

    Context context;

    public static String METHOD_NAME = "forgot_password";

    public static String EMAILID = "emailId";

    String emailId;

    public ForgotPswdTask(Context context, String emailId) {
        this.context = context;
        this.emailId = emailId;
    }

    public void userForgotPswd(final ForgotPswdResponseCallback forgotPswdResponseCallback) {

        JsonObjectRequest forgotPswdRequest = new JsonObjectRequest(Request.Method.POST, BASE_APP_URL, new JSONObject(getParams(context)), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                // ILog.d(TAG, response.toString());
                if (isValidResponse(response.toString()))
                    forgotPswdResponseCallback.onSuccessForgotPswdResponse(response.toString());
                else
                    forgotPswdResponseCallback.onFailureForgotPswdResponse(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ILog.d(TAG, error.toString());
                forgotPswdResponseCallback.onFailureForgotPswdResponse(NetworkErrorHelper.getErrorStatus(error).getErrorMessage());
            }
        });
        VolleySingleTon.getInstance(context).addToRequestQueue(forgotPswdRequest);
        forgotPswdRequest.setRetryPolicy(new DefaultRetryPolicy(25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    protected Map<String, Object> getParams(Context context) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(EMAILID, emailId);
        map.put(ApiUtils.ACCESS_TOKEN, "Ramukaka");
        map.put(ApiUtils.METHOD, METHOD_NAME);
        return map;
    }

    public interface ForgotPswdResponseCallback {
        void onSuccessForgotPswdResponse(String response);

        void onFailureForgotPswdResponse(String response);
    }
}
