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

public class PaymentFailureTask extends ApiUtils {

    protected static final String TAG = PaymentFailureTask.class.getSimpleName();

    Context context;

    String orderId;

    public static String METHOD_NAME = "payment_failure_by_order_id";

    public static String ORDER_ID = "order_id";

    public PaymentFailureTask(Context context, String orderId) {
        this.context = context;
        this.orderId = orderId;
    }

    public void sendPaymentFailure(final PaymentFailureTask.PaymentFailureResponseCallback paymentFailureResponseCallback) {
        JsonObjectRequest orderRequest = new JsonObjectRequest(Request.Method.POST, BASE_APP_URL, new JSONObject(getParams(context)), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                ILog.d(TAG, response.toString());
                if (isValidResponse(response.toString()))
                    paymentFailureResponseCallback.onSuccessPaymentFailureResponse(response.toString());
                else
                    paymentFailureResponseCallback.onFailurePaymentFailureResponse(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ILog.d(TAG, error.toString());
                paymentFailureResponseCallback.onFailurePaymentFailureResponse(NetworkErrorHelper.getErrorStatus(error).getErrorMessage());
            }
        });
        VolleySingleTon.getInstance(context).addToRequestQueue(orderRequest);
    }

    protected Map<String, Object> getParams(Context context) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(ApiUtils.ACCESS_TOKEN, "Ramukaka");
        map.put("user_id", PreferenceUtils.getUserid(context));
        map.put(ORDER_ID, orderId);
        map.put(ApiUtils.METHOD, METHOD_NAME);
        return map;
    }

    public interface PaymentFailureResponseCallback {
        void onSuccessPaymentFailureResponse(String response);

        void onFailurePaymentFailureResponse(String response);
    }

}
