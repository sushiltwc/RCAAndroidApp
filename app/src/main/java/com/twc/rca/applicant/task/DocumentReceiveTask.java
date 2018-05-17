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

public class DocumentReceiveTask extends ApiUtils {


    protected static final String TAG = DocumentReceiveTask.class.getSimpleName();

    Context context;

    public static String METHOD_NAME = "get_documents_by_applicant_id_and_user_id";

    String applicantId;

    public DocumentReceiveTask(Context context, String applicantId) {
        this.context = context;
        this.applicantId = applicantId;
    }

    public void documentReceive(final DocumentReceiveTask.DocumentReceiveResposeCallback documentReceiveResposeCallback) {
        JsonObjectRequest documentReceiveRequest = new JsonObjectRequest(Request.Method.POST, BASE_APP_URL, new JSONObject(getParams(context)), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                ILog.d(TAG, response.toString());
                if (isValidResponse(response.toString()))
                    documentReceiveResposeCallback.onSuccessDocumentReceeiveResponse(response.toString());
                else
                    documentReceiveResposeCallback.onFailureDocumentReceiveResponse(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ILog.d(TAG, error.toString());
                documentReceiveResposeCallback.onFailureDocumentReceiveResponse(NetworkErrorHelper.getErrorStatus(error).getErrorMessage());
            }
        });
        VolleySingleTon.getInstance(context).addToRequestQueue(documentReceiveRequest);
    }

    protected Map<String, Object> getParams(Context context) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(ApiUtils.ACCESS_TOKEN, PreferenceUtils.getAccessToken(context));
        map.put(USER_ID, PreferenceUtils.getUserid(context));
        map.put("applicant_id", applicantId);
        map.put(ApiUtils.METHOD, METHOD_NAME);
        return map;
    }

    public interface DocumentReceiveResposeCallback {
        void onSuccessDocumentReceeiveResponse(String response);

        void onFailureDocumentReceiveResponse(String response);
    }
}
