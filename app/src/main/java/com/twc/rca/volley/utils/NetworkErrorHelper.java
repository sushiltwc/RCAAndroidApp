package com.twc.rca.volley.utils;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.twc.rca.utils.ApiUtils;
import com.twc.rca.utils.ILog;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by Sushil
 */
public class NetworkErrorHelper extends VolleyConstant {

    private static final String TAG = NetworkErrorHelper.class.getSimpleName();

    private static String getErrorMessage(VolleyError error) {
        if (VolleyUtils.isTimeoutError(error)) {
            return MSG_TIME_OUT_ERROR;
        } else if (VolleyUtils.isConnectionProblem(error)) {
            return MSG_POOR_CONNECTIVITY;
        } else if (VolleyUtils.isNetworkProblem(error)) {
            return MSG_NETWORK_ERROR;
        } else if (VolleyUtils.isServerProblem(error)) {
            return handleServerError(error);
        } else if (VolleyUtils.isParseProblem(error)) {
            return MSG_PARSE_ERROR;
        }
        return MSG_TECHNICAL_ERROR;
    }

    public static NetworkErrorStatus getErrorStatus(VolleyError error) {

        NetworkErrorStatus networkErrorStatus = new NetworkErrorStatus();

        if (error != null) {
            networkErrorStatus.setErrorMessage(NetworkErrorHelper.getErrorMessage(error));
        } else {
            networkErrorStatus.setErrorMessage(MSG_UNKNOWN);
        }

        if (error.networkResponse != null) {
            networkErrorStatus.setErrorCode(error.networkResponse.statusCode);
        }

        ILog.d(TAG, "|statusCode|" + networkErrorStatus.getErrorCode() + "\n|msg|" + networkErrorStatus.getErrorMessage());
        return networkErrorStatus;
    }


    private static String handleServerError(VolleyError error) {

        NetworkResponse response = error.networkResponse;

        if (response != null) {
            switch (response.statusCode) {
                case 409:
                    break;
                case 404:
                    break;
                case 422:
                    break;
                case 401:
                    return MSG_UNAUTHORISED_ACCESS;
                case 503:
                    return MSG_SERVER_DOWN_ERROR;
                default:
                    return MSG_SERVER_ERROR;
            }
        }

        return MSG_TECHNICAL_ERROR;
    }

    public static String parseVolleyError(VolleyError error) {
        String message = null;
        try {
            String responseBody = new String(error.networkResponse.data, "utf-8");
            JSONObject data = new JSONObject(responseBody);
            JSONArray errors = data.getJSONArray(ApiUtils.CONTENT);
            JSONObject jsonMessage = errors.getJSONObject(0);
            message = jsonMessage.getString(ApiUtils.MESSAGE);
            return message;
        } catch (JSONException e) {
        } catch (UnsupportedEncodingException errorr) {
        }
        return message;
    }
}
