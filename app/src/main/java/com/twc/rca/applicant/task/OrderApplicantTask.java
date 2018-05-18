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
 * Created by Sushil on 15-05-2018.
 */

public class OrderApplicantTask extends ApiUtils {

    protected static final String TAG = OrderApplicantTask.class.getSimpleName();

    Context context;

    String orderId;

    public static String METHOD_NAME = "get_applicant_by_user_id_order_id";

    public static String ORDER_ID="order_id";

    public OrderApplicantTask(Context context, String orderId) {
        this.context = context;
        this.orderId = orderId;
    }

    public void getOrderApplicantList(final OrderApplicantTask.OrderApplicantListResposeCallback orderApplicantListResposeCallback) {
        JsonObjectRequest orderApplicantRequest = new JsonObjectRequest(Request.Method.POST, BASE_APP_URL, new JSONObject(getParams(context)), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                ILog.d(TAG, response.toString());
                if (isValidResponse(response.toString()))
                    orderApplicantListResposeCallback.onSuccessOrderApplicantListResponse(response.toString());
                else
                    orderApplicantListResposeCallback.onFailureOrderApplicantListResponse(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ILog.d(TAG, error.toString());
                orderApplicantListResposeCallback.onFailureOrderApplicantListResponse(NetworkErrorHelper.getErrorStatus(error).getErrorMessage());
            }
        });
        VolleySingleTon.getInstance(context).addToRequestQueue(orderApplicantRequest);
    }

    protected Map<String, Object> getParams(Context context) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(ACCESS_TOKEN, PreferenceUtils.getAccessToken(context));
        map.put(USER_ID, PreferenceUtils.getUserid(context));
        map.put(ORDER_ID, orderId);
        map.put(METHOD, METHOD_NAME);
        return map;
    }

    public interface OrderApplicantListResposeCallback {
        void onSuccessOrderApplicantListResponse(String response);

        void onFailureOrderApplicantListResponse(String response);
    }
}

