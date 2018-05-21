package com.twc.rca.product.activities;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.twc.rca.R;
import com.twc.rca.activities.BaseActivity;
import com.twc.rca.activities.DashboardActivity;
import com.twc.rca.product.task.PaymentFailureTask;
import com.twc.rca.utils.ILog;
import com.twc.rca.volley.utils.VolleySingleTon;

import java.util.HashMap;

/**
 * Created by Sushil on 07-03-2018.
 */

public class PaymentActivity extends BaseActivity {

    public static final String TAG = PaymentActivity.class.getSimpleName();

    TextView tv_actionbar_title;

    ImageButton img_button_back;

    WebView mWebView;

    private ProgressBar progressBar;

    private SwipeRefreshLayout swipe;

    boolean mLoadingFinished = true;

    boolean mRedirect = false;

    public static String URL;

    String orderId;

    HashMap<String, String> hashMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            View viewActionBar = getLayoutInflater().inflate(R.layout.layout_applicant_actionbar, null);
            ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                    ActionBar.LayoutParams.MATCH_PARENT,
                    ActionBar.LayoutParams.WRAP_CONTENT,
                    Gravity.CENTER);
            tv_actionbar_title = (TextView) viewActionBar.findViewById(R.id.tv_applicant_actionbar_title);
            img_button_back = (ImageButton) viewActionBar.findViewById(R.id.img_btn_back_arrow);
            img_button_back.setVisibility(View.VISIBLE);

            tv_actionbar_title.setText(getString(R.string.securely_pay_visa));
            actionBar.setCustomView(viewActionBar, params);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            Toolbar toolbar = (Toolbar) actionBar.getCustomView().getParent();
            toolbar.setContentInsetsAbsolute(0, 0);
            toolbar.getContentInsetEnd();
            toolbar.setPadding(0, 0, 0, 0);

            img_button_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showCancellationPrompt();
                }
            });
        }

        URL = getIntent().getStringExtra(OrderDetailActivity.PAYMENT_URL).replace(" ", "");
        orderId = getIntent().getStringExtra(OrderDetailActivity.ORDER_ID);
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
        return false;
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
                showProgressDialog(getResources().getString(R.string.please_wait));
                new PaymentFailureTask(getApplicationContext(), orderId).sendPaymentFailure(paymentFailureResponseCallback);
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
        if (url.contains("success")) {
            intent = new Intent(this, PaymentReceiptActivity.class);
            intent.putExtra(OrderDetailActivity.ORDER_ID, getIntent().getStringExtra(OrderDetailActivity.ORDER_ID));
        } else if (url.contains("failure")) {
            intent = new Intent(this, PaymentFailureActivity.class);
            intent.putExtra(OrderDetailActivity.ORDER_ID, getIntent().getStringExtra(OrderDetailActivity.ORDER_ID));
        }
        startActivity(intent);
        finish();
    }

    PaymentFailureTask.PaymentFailureResponseCallback paymentFailureResponseCallback = new PaymentFailureTask.PaymentFailureResponseCallback() {
        @Override
        public void onSuccessPaymentFailureResponse(String response) {
            dismissProgressDialog();
            Intent i = new Intent(PaymentActivity.this, DashboardActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }

        @Override
        public void onFailurePaymentFailureResponse(String response) {
            dismissProgressDialog();
        }
    };
}
