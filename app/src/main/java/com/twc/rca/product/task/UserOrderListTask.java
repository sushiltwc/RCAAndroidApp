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
 * Created by Sushil on 14-05-2018.
 */

public class UserOrderListTask extends ApiUtils {

    protected static final String TAG = UserOrderListTask.class.getSimpleName();

    Context context;

    public static String METHOD_NAME = "get_orders_by_user_id";

    public UserOrderListTask(Context context) {
        this.context = context;
    }

    public void getUserOrders(final UserOrderListTask.UserOrderResponseCallback userOrderResponseCallback) {
        JsonObjectRequest orderRequest = new JsonObjectRequest(Request.Method.POST, BASE_APP_URL, new JSONObject(getParams(context)), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                ILog.d(TAG, response.toString());
                if (isValidResponse(response.toString()))
                    userOrderResponseCallback.onSuccessUserOrderListResponse(response.toString());
                else
                    userOrderResponseCallback.onFailureUserOrderListResponse(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ILog.d(TAG, error.toString());
                userOrderResponseCallback.onFailureUserOrderListResponse(NetworkErrorHelper.getErrorStatus(error).getErrorMessage());
            }
        });
        VolleySingleTon.getInstance(context).addToRequestQueue(orderRequest);
    }

    protected Map<String, Object> getParams(Context context) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(ApiUtils.ACCESS_TOKEN, "Ramukaka");
        map.put("user_id", PreferenceUtils.getUserid(context));
        map.put(ApiUtils.METHOD, METHOD_NAME);
        return map;
    }

    public interface UserOrderResponseCallback {
        void onSuccessUserOrderListResponse(String response);

        void onFailureUserOrderListResponse(String response);
    }
}
