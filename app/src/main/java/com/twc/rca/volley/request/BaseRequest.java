package com.twc.rca.volley.request;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.twc.rca.utils.ILog;
import com.twc.rca.volley.utils.VolleyUtils;


import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by Sushil
 */
public abstract class BaseRequest<T> extends Request<T> {

    protected static final String TAG = BaseRequest.class.getSimpleName();

    private final Map<String, String> headers;

    private final Response.Listener<T> mSuccessListener;

    protected static final String PROTOCOL_CHARSET = "UTF-8";

    private static final String PROTOCOL_CONTENT_TYPE = String.format("application/json; charset=%s", PROTOCOL_CHARSET);

    private final boolean mEnabledCustomRequestBody;

    public BaseRequest(int method, String url, Map<String, String> headers, Response.Listener<T> mSuccessListener, Response.ErrorListener errorListener, boolean mEnabledCustomRequestBody) {
        super(method, url, errorListener);
        this.headers = headers;
        this.mSuccessListener = mSuccessListener;
        this.mEnabledCustomRequestBody = mEnabledCustomRequestBody;
    }

    @Override
    protected void deliverResponse(T response) {
        if (mSuccessListener != null)
            mSuccessListener.onResponse(response);
    }

    protected String baseNetworkResponseParsing(NetworkResponse response) throws UnsupportedEncodingException {
        String dataRes = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        ILog.d(TAG, "|url| " + getUrl() + "\n|code| " + response.statusCode + "\n|result|" + dataRes);
        return dataRes;
    }


    /**
     * Return the body of the request based on "mEnabledCustomRequestBody" (mEnabledCustomRequesBody)
     *
     * @return
     * @throws AuthFailureError
     */
    public byte[] getBody() throws AuthFailureError {

        if (headers != null && headers.size() > 0) {
            return VolleyUtils.getRequestBody(headers, mEnabledCustomRequestBody, getParamsEncoding());
        } else {

            Map<String, String> params = getParams();

            return (params != null && params.size() > 0) ?
                    VolleyUtils.getRequestBody(params, mEnabledCustomRequestBody, getParamsEncoding()) :
                    null;

        }

    }
}
