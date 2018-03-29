package com.twc.rca.volley.request;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Request to fetch String
 * Created by Sushil
 */
public class NetworkStringRequest extends BaseRequest<String> {

    /**
     * String request constructor
     *
     * @param method                  GET, POST, PUT , DELETE
     * @param url                     api url
     * @param headers                 </>Map<String, String> headers = new LinkedHashMap<>() </>params to form body of the POST,PUT,DELETE request , null in GET
     * @param successListener         success listener for request
     * @param errorListener           error listener for request
     * @param enableCustomRequestBody flag to distinguish form encoded params v/s raw body of request
     */

    public NetworkStringRequest(int method, String url, Map<String, String> headers, Response.Listener<String> successListener, Response.ErrorListener errorListener, boolean enableCustomRequestBody) {
        super(method, url, headers, successListener, errorListener, enableCustomRequestBody);
    }


    public NetworkStringRequest(int method, String url, Map<String, String> headers, Response.Listener<String> successListener, Response.ErrorListener errorListener) {
        super(method, url, headers, successListener, errorListener, false);
    }


    public NetworkStringRequest(String url, Map<String, String> headers, Response.Listener<String> successListener, Response.ErrorListener errorListener) {
        super(Request.Method.GET, url, headers, successListener, errorListener, false);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = baseNetworkResponseParsing(response);
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }
}
