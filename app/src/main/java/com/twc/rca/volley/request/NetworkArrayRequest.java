package com.twc.rca.volley.request;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Request to fetch JSONArray
 * Created by Sushil
 */
public class NetworkArrayRequest extends BaseRequest<JSONArray> {

    /**
     * Array request constructor
     *
     * @param method                  GET, POST, PUT , DELETE
     * @param url                     api url
     * @param headers                 </>Map<String, String> headers = new LinkedHashMap<>() </>params to form body of the POST,PUT,DELETE request , null in GET
     * @param successListener         success listener for request
     * @param errorListener           error listener for request
     * @param enableCustomRequestBody flag to distinguish form encoded params v/s raw body of request
     */

    public NetworkArrayRequest(int method, String url, Map<String, String> headers,
                               Response.Listener<JSONArray> successListener,
                               Response.ErrorListener errorListener, boolean enableCustomRequestBody) {
        super(method, url, headers, successListener, errorListener, enableCustomRequestBody);
    }

    public NetworkArrayRequest(int method, String url, Map<String, String> headers,
                               Response.Listener<JSONArray> successListener,
                               Response.ErrorListener errorListener) {
        super(method, url, headers, successListener, errorListener, false);
    }


    public NetworkArrayRequest(String url, Map<String, String> headers,
                               Response.Listener<JSONArray> successListener,
                               Response.ErrorListener errorListener) {
        super(Request.Method.GET, url, headers, successListener, errorListener, false);
    }

    @Override
    protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
        try {

            String json = baseNetworkResponseParsing(response);
            return Response.success(new JSONArray(json), HttpHeaderParser.parseCacheHeaders(response));

        } catch (JSONException e) {
            return Response.error(new ParseError(e));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }
}
