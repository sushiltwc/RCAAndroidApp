package com.twc.rca.product.task;

import android.content.Context;

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
 * Created by Sushil on 14-05-2018.
 */

public class PaymentResponseTask extends ApiUtils {

    protected static final String TAG = PaymentResponseTask.class.getSimpleName();

    Context context;

    public static String METHOD_NAME = "get_order_payment_status";

    public static String ORDER_ID = "order_id", USER_ID = "user_id";

    String orderId;

    public PaymentResponseTask(Context context, String orderId) {
        this.context = context;
        this.orderId = orderId;
    }

    public void getOrderPaymentStatus(final PaymentResponseTask.PaymentStatusResponseCallback paymentStatusResponseCallback) {
        JsonObjectRequest orderRequest = new JsonObjectRequest(Request.Method.POST, BASE_APP_URL, new JSONObject(getParams(context)), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                ILog.d(TAG, response.toString());
                if (isValidResponse(response.toString()))
                    paymentStatusResponseCallback.onSuccessPaymentStatusResponse(response.toString());
                else
                    paymentStatusResponseCallback.onFailurePaymentStatusResponse(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ILog.d(TAG, error.toString());
                paymentStatusResponseCallback.onFailurePaymentStatusResponse(NetworkErrorHelper.getErrorStatus(error).getErrorMessage());
            }
        });
        VolleySingleTon.getInstance(context).addToRequestQueue(orderRequest);
        orderRequest.setRetryPolicy(new DefaultRetryPolicy(25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    protected Map<String, Object> getParams(Context context) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(ACCESS_TOKEN, PreferenceUtils.getAccessToken(context));
        map.put(USER_ID, PreferenceUtils.getUserid(context));
        map.put(ORDER_ID, orderId);
        map.put(ApiUtils.METHOD, METHOD_NAME);
        return map;
    }

    public interface PaymentStatusResponseCallback {
        void onSuccessPaymentStatusResponse(String response);

        void onFailurePaymentStatusResponse(String response);
    }
}
