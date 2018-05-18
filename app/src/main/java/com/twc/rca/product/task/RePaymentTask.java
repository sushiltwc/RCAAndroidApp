package com.twc.rca.product.task;

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

public class RePaymentTask extends ApiUtils {

    protected static final String TAG = RePaymentTask.class.getSimpleName();

    Context context;

    String orderId;

    public static String METHOD_NAME = "repayment_by_order_id";

    public static String ORDER_ID = "order_id";

    public RePaymentTask(Context context, String orderId) {
        this.context = context;
        this.orderId = orderId;
    }

    public void doRePayment(final RePaymentTask.RePaymentResponseCallback rePaymentResponseCallback) {
        JsonObjectRequest orderRequest = new JsonObjectRequest(Request.Method.POST, BASE_APP_URL, new JSONObject(getParams(context)), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                ILog.d(TAG, response.toString());
                if (isValidResponse(response.toString()))
                    rePaymentResponseCallback.onSuccessRePaymentResponse(response.toString());
                else
                    rePaymentResponseCallback.onFailureRePaymentResponse(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ILog.d(TAG, error.toString());
                rePaymentResponseCallback.onFailureRePaymentResponse(NetworkErrorHelper.getErrorStatus(error).getErrorMessage());
            }
        });
        VolleySingleTon.getInstance(context).addToRequestQueue(orderRequest);
    }

    protected Map<String, Object> getParams(Context context) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(ACCESS_TOKEN, PreferenceUtils.getAccessToken(context));
        map.put(USER_ID, PreferenceUtils.getUserid(context));
        map.put(ORDER_ID, orderId);
        map.put(METHOD, METHOD_NAME);
        return map;
    }

    public interface RePaymentResponseCallback {
        void onSuccessRePaymentResponse(String response);

        void onFailureRePaymentResponse(String response);
    }

}