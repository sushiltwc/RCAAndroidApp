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
 * Created by Sushil on 17-04-2018.
 */

public class DocumentListTask extends ApiUtils {

    protected static final String TAG = DocumentListTask.class.getSimpleName();

    Context context;

    String applicant_type;

    public static String METHOD_NAME = "get_require_documents_by_applicant_type";

    public DocumentListTask(Context context, String applicant_type) {
        this.context = context;
        this.applicant_type = applicant_type;
    }

    public void getDocList(final DocumentListTask.DocumentListResposeCallback documentListResposeCallback) {
        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, BASE_APP_URL, new JSONObject(getParams(context)), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                ILog.d(TAG, response.toString());
                if (isValidResponse(response.toString()))
                    documentListResposeCallback.onSuccessDocumentListResponse(response.toString());
                else
                    documentListResposeCallback.onSuccessDocumentListResponse(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ILog.d(TAG, error.toString());
                documentListResposeCallback.onFailureDocumentListResponse(NetworkErrorHelper.getErrorStatus(error).getErrorMessage());
            }
        });
        VolleySingleTon.getInstance(context).addToRequestQueue(loginRequest);
    }

    protected Map<String, Object> getParams(Context context) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(ACCESS_TOKEN, PreferenceUtils.getAccessToken(context));
        map.put(USER_ID, PreferenceUtils.getUserid(context));
        map.put(APPLICANT_TYPE, "'" + applicant_type + "'");
        map.put(METHOD, METHOD_NAME);
        return map;
    }

    public interface DocumentListResposeCallback {
        void onSuccessDocumentListResponse(String response);

        void onFailureDocumentListResponse(String response);
    }
}
