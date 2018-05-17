package com.twc.rca.applicant.task;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.twc.rca.utils.ApiUtils;
import com.twc.rca.utils.ILog;
import com.twc.rca.utils.PreferenceUtils;
import com.twc.rca.volley.utils.NetworkErrorHelper;
import com.twc.rca.volley.utils.VolleySingleTon;

import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Sushil on 04-05-2018.
 */

public class ApplicantTask extends ApiUtils {

    protected static final String TAG = ApplicantTask.class.getSimpleName();

    Context context;

    public static String METHOD_NAME = "get_applicant_by_user_id";

    public ApplicantTask(Context context) {
        this.context = context;
    }

    public void getApplicantList(final ApplicantTask.ApplicantListResposeCallback applicantListResposeCallback) {
        JsonObjectRequest applicantRequest = new JsonObjectRequest(Request.Method.POST, BASE_APP_URL, new JSONObject(getParams(context)), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                ILog.d(TAG, response.toString());
                if (isValidResponse(response.toString()))
                    applicantListResposeCallback.onSuccessApplicantListResponse(response.toString());
                else
                    applicantListResposeCallback.onFailureApplicantListResponse(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ILog.d(TAG, error.toString());
                applicantListResposeCallback.onFailureApplicantListResponse(NetworkErrorHelper.getErrorStatus(error).getErrorMessage());
            }
        });
        VolleySingleTon.getInstance(context).addToRequestQueue(applicantRequest);
    }

    protected Map<String, Object> getParams(Context context) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(ApiUtils.ACCESS_TOKEN, PreferenceUtils.getAccessToken(context));
        map.put(USER_ID, PreferenceUtils.getUserid(context));
        map.put(ApiUtils.METHOD, METHOD_NAME);
        return map;
    }

    public interface ApplicantListResposeCallback {
        void onSuccessApplicantListResponse(String response);

        void onFailureApplicantListResponse(String response);
    }
}
