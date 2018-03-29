package com.twc.rca.volley.utils;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Sushil
 */
public class VolleySingleTon {

    private static Context mContext;

    private static VolleySingleTon mInstance;

    private RequestQueue mRequestQueue;

    private ImageLoader mImageLoader;

    private VolleySingleTon(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();
    }

    /**
     * Create singleton instance of volley request.
     *
     * @param context Application context
     * @return singleton instance of volley
     */
    public static synchronized VolleySingleTon getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleySingleTon(context.getApplicationContext());
        }
        return mInstance;
    }

    // imageLoader to load image using 'NetworkImageView' from volley toolbox
    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null)
            mImageLoader = new ImageLoader(mRequestQueue, new LruBitmapCache());

        return mImageLoader;
    }

    // add request to queue using tag
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? VolleyConstant.DEFAULT_TAG : tag);
        getRequestQueue().add(req);
    }

    // add request to queue using default tag
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(VolleyConstant.DEFAULT_TAG);
        getRequestQueue().add(req);
    }

    // cancel the pending request
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null)
            mRequestQueue.cancelAll(tag);
    }


    //get the request queue.
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null)
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());

        return mRequestQueue;
    }
}
