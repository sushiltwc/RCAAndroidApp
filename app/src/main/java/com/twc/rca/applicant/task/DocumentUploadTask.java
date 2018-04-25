package com.twc.rca.applicant.task;

import android.content.Context;
import android.view.View;

import com.android.volley.DefaultRetryPolicy;
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

public class DocumentUploadTask extends ApiUtils {

    protected static final String TAG = DocumentUploadTask.class.getSimpleName();

    Context context;

    public static String METHOD_NAME = "upload_documents", DOC_TYPE = "docType", FILE_NAME = "filename", BASE64IMAGEDATA = "base64imagedata";

    String applicantId, docType, fileName, fileFormat, base64ImageData;

    public DocumentUploadTask(Context context, String applicantId, String docType, String fileName, String fileFormat, String base64ImageData) {
        this.context = context;
        this.applicantId = applicantId;
        this.docType = docType;
        this.fileName = fileName;
        this.fileFormat = fileFormat;
        this.base64ImageData = base64ImageData;
    }

    public void documentUpload(final DocumentUploadTask.DocumentUploadResposeCallback documentUploadResposeCallback, final View view, final int position) {
        JsonObjectRequest documentUploadRequest = new JsonObjectRequest(Request.Method.POST, BASE_APP_URL, new JSONObject(getParams(context)), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                ILog.d(TAG, response.toString());
                if (isValidResponse(response.toString()))
                    documentUploadResposeCallback.onSuccessDocumentUploadResponse(response.toString(), view, position, base64ImageData);
                else
                    documentUploadResposeCallback.onFailureDocumentUploadResponse(response.toString(), view, position);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ILog.d(TAG, error.toString());
                documentUploadResposeCallback.onFailureDocumentUploadResponse(NetworkErrorHelper.getErrorStatus(error).getErrorMessage(), view, position);
            }
        });
        VolleySingleTon.getInstance(context).addToRequestQueue(documentUploadRequest);
        documentUploadRequest.setRetryPolicy(new DefaultRetryPolicy(25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    protected Map<String, Object> getParams(Context context) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(ApiUtils.ACCESS_TOKEN, "Ramukaka");
        map.put(ApiUtils.USER_ID, PreferenceUtils.getUserid(context));
        map.put(ApiUtils.APPLICANT_ID, "29");
        map.put(DOC_TYPE, docType);
        map.put(FILE_NAME, fileName);
        map.put(BASE64IMAGEDATA, "data:image/" + fileFormat + ";base64," + base64ImageData);
        map.put(ApiUtils.METHOD, METHOD_NAME);
        return map;
    }

    public interface DocumentUploadResposeCallback {
        void onSuccessDocumentUploadResponse(String response, View view, int position, String base64ImageData);

        void onFailureDocumentUploadResponse(String response, View view, int position);
    }
}
