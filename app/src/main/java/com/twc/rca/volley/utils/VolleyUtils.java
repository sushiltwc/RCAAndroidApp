package com.twc.rca.volley.utils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import org.json.simple.JSONValue;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by Sushil
 */
public class VolleyUtils {

    public static boolean isNetworkProblem(VolleyError error) {
        return error instanceof NetworkError;
    }

    public static boolean isConnectionProblem(VolleyError error) {
        return error instanceof NoConnectionError;
    }

    public static boolean isServerProblem(VolleyError error) {
        return (error instanceof ServerError || error instanceof AuthFailureError);
    }

    public static boolean isTimeoutError(VolleyError error) {
        return error instanceof TimeoutError;
    }

    public static boolean isParseProblem(VolleyError error) {
        return error instanceof ParseError;
    }

    /**
     * Convert HashMap to byte[] with default encoding UTF-8 for raw body type
     *
     * @param params
     * @param paramsEncoding
     * @return
     */
    public static byte[] encodeRawBodyHashMapToByte(Map<String, String> params, String paramsEncoding) {
        try {
            StringWriter stringWriter = new StringWriter();

            try {
                JSONValue.writeJSONString(params, stringWriter);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return stringWriter.toString().getBytes(paramsEncoding);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, e);
        }
    }

    /**
     * Convert HashMap to byte[] with default encoding and body type form-www-url encoded
     *
     * @param params
     * @param paramsEncoding
     * @return
     */
    public static byte[] encodeParameters(Map<String, String> params, String paramsEncoding) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
                encodedParams.append('&');
            }
            return encodedParams.toString().getBytes(paramsEncoding);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, e);
        }
    }

    public static byte[] getRequestBody(Map<String, String> headers, boolean mEnabledCustomRequestBody, String paramsEncoding) {
        if (!mEnabledCustomRequestBody)
            return VolleyUtils.encodeParameters(headers, paramsEncoding);
        else
            return VolleyUtils.encodeRawBodyHashMapToByte(headers, paramsEncoding);

    }
}

