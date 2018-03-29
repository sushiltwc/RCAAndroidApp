package com.twc.rca.volley.request;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Request to fetch JSONObject
 * Created by Sushil
 */
public class NetworkJSONRequest extends BaseRequest<JSONObject> {


    /**
     * JSON request constructor
     *
     * @param method                  GET, POST, PUT , DELETE
     * @param url                     api url
     * @param headers                 </>Map<String, String> headers = new LinkedHashMap<>() </>params to form body of the POST,PUT,DELETE request , null in GET
     * @param successListener         success listener for request
     * @param errorListener           error listener for request
     * @param enableCustomRequestBody flag to distinguish form encoded params v/s raw body of request
     */

    public NetworkJSONRequest(int method, String url, Map<String, String> headers, Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener, boolean enableCustomRequestBody) {
        super(method, url, headers, successListener, errorListener, enableCustomRequestBody);
    }

    public NetworkJSONRequest(int method, String url, Map<String, String> headers, Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener) {
        super(method, url, headers, successListener, errorListener, false);
    }

    public NetworkJSONRequest(String url, Map<String, String> headers, Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener) {
        super(Request.Method.GET, url, headers, successListener, errorListener, false);
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = baseNetworkResponseParsing(response);
            return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }
}
