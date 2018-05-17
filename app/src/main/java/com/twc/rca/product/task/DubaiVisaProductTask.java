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
 * Created by Sushil on 13-03-2018.
 */

public class DubaiVisaProductTask extends ApiUtils {

    protected static final String TAG = DubaiVisaProductTask.class.getSimpleName();

    Context context;

    public static String METHOD_NAME = "get_dubai_visa_product";

    public DubaiVisaProductTask(Context context) {
        this.context = context;
    }

    public void getDubaiVisaProduct(final DubaiVisaProductResponseCallback dubaiVisaProductResponseCallback) {
        JsonObjectRequest productListRequest = new JsonObjectRequest(Request.Method.POST, BASE_APP_URL, new JSONObject(getParams(context)), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                ILog.d(TAG, response.toString());
                if (isValidResponse(response.toString()))
                    dubaiVisaProductResponseCallback.onSuccessDubaiVisaProductResponse(response.toString());
                else
                    dubaiVisaProductResponseCallback.onFailureDubaiVisaProductResponse(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ILog.d(TAG, error.toString());
                dubaiVisaProductResponseCallback.onFailureDubaiVisaProductResponse(NetworkErrorHelper.getErrorStatus(error).getErrorMessage());
            }
        });
        VolleySingleTon.getInstance(context).addToRequestQueue(productListRequest);
        productListRequest.setRetryPolicy(new DefaultRetryPolicy(25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    protected Map<String, Object> getParams(Context context) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(ACCESS_TOKEN, PreferenceUtils.getAccessToken(context));
        map.put(USER_ID,PreferenceUtils.getUserid(context));
        map.put(METHOD, METHOD_NAME);
        return map;
    }

    public interface DubaiVisaProductResponseCallback {
        void onSuccessDubaiVisaProductResponse(String response);

        void onFailureDubaiVisaProductResponse(String response);
    }

}
