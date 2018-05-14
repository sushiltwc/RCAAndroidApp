package com.twc.rca.product.activities;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.twc.rca.R;
import com.twc.rca.activities.BaseActivity;
import com.twc.rca.utils.ILog;
import com.twc.rca.volley.utils.VolleySingleTon;

import java.util.HashMap;

/**
 * Created by TWC on 07-03-2018.
 */

public class PaymentActivity extends BaseActivity {

    public static final String TAG = PaymentActivity.class.getSimpleName();

    WebView mWebView;

    private ProgressBar progressBar;

    private SwipeRefreshLayout swipe;

    boolean mLoadingFinished = true;

    boolean mRedirect = false;

    public static String URL;
    HashMap<String, String> hashMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        URL = getIntent().getStringExtra(OrderDetailActivity.PAYMENT_URL).replace(" ", "");
        initView();
        startWebView();
    }

    void initView() {
        mWebView = (WebView) findViewById(R.id.payment_web_view);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mWebView.loadUrl(URL);
            }
        });
    }

    void startWebView() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new PaymentWebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient());
        ILog.d("URL", URL);
        mWebView.loadUrl(URL);
    }

    private void showProgress() {
        if (progressBar != null && !(progressBar.getVisibility() == View.VISIBLE)) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void hideProgress() {
        if (progressBar != null && (progressBar.getVisibility() == View.VISIBLE)) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    protected boolean isHomeAsUpEnabled() {
        return true;
    }

    @Override
    public void onBackPressed() {
        showCancellationPrompt();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                showCancellationPrompt();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void showCancellationPrompt() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setMessage(R.string.
                payment_cancel_confirm).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setResult(RESULT_CANCELED);
                finish();
            }
        }).setNegativeButton(R.string.no, null);
        builder.create().show();
    }

    @Override
    protected void onDestroy() {
        VolleySingleTon.getInstance(this).cancelPendingRequests(TAG);
        super.onDestroy();
    }

    class PaymentWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            URL = url;
            mLoadingFinished = false;
            showProgress();
            mRedirect = false;
            ILog.d(TAG, "onPageStarted " + url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            ILog.d(TAG, "shouldOverrideUrlLoading URL " + url);
            URL = url;
            view.loadUrl(url);
            if (!mLoadingFinished)
                mRedirect = true;

            mLoadingFinished = false;
            return true;
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }


        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            ILog.d(TAG, "onPageFinished " + url);
            URL = url;
            swipe.setRefreshing(false);
            if (!mRedirect)
                mLoadingFinished = true;

            if (mLoadingFinished && !mRedirect && (url.contains("success") || url.contains("failure"))) {
                hideProgress();
                getPaymentReceipt(url);
            }
        }

        @SuppressWarnings("deprecation")
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            ILog.d(TAG, "onReceivedError " + description);
        }

        @TargetApi(android.os.Build.VERSION_CODES.M)
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            ILog.d(TAG, "onReceivedError " + error.getDescription());
        }
    }

    void getPaymentReceipt(String url) {
        Intent intent = null;
        if (url.contains("success"))
            intent = new Intent(this, PaymentReceiptActivity.class);
        else if (url.contains("failure"))
            intent = new Intent(this, PaymentFailureActivity.class);
        startActivity(intent);
      /*  try {
            Uri uri = Uri.parse(URLDecoder.decode(url, "UTF-8"));
            if (uri != null) {

                if (url.indexOf("save") != -1) {
                    Intent intent = new Intent(this, PaymentStatusActivity.class);
                    intent.putExtra("quoteDetails", quoteDetail);
                    intent.putExtra("leadId",leadId);
                    intent.putExtra("orderId", orderId);
                    startActivity(intent);
                    finish();
                }
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
    }

}
