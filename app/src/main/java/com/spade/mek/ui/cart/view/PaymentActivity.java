package com.spade.mek.ui.cart.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.spade.mek.R;

/**
 * Created by Ayman Abouzeid on 8/10/17.
 */

public class PaymentActivity extends AppCompatActivity {
    public static final int PAYMENT_SUCCESS = 1;
    public static final int PAYMENT_FAIL = 2;
    public static final String EXTRA_PAYMENT_STATUS = "EXTRA_PAYMENT_STATUS";
    public static final String EXTRA_URL = "EXTRA_URL";
    private static final String STATUS_PARAM = "vpc_TxnResponseCode";
    private String paymentUrl;
    private int paymentStatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle(getString(R.string.your_cart));

        paymentUrl = getIntent().getStringExtra(EXTRA_URL);
        init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init() {
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        WebView webView = (WebView) findViewById(R.id.payment_web_view);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {

            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                webView.loadUrl(url);
                progressBar.setVisibility(View.VISIBLE);
                return shouldOverrideUrlLoading(url);
            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest request) {
                Uri uri = request.getUrl();
                webView.loadUrl(uri.toString());
                progressBar.setVisibility(View.VISIBLE);

                return shouldOverrideUrlLoading(uri.toString());
            }

            private boolean shouldOverrideUrlLoading(final String url) {
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.i("WebView", url);
                Uri uri = Uri.parse(url);
                progressBar.setVisibility(View.GONE);

                try {
                    String value = uri.getQueryParameter(STATUS_PARAM);
                    Log.i("WebView", value);
                    paymentStatus = Integer.parseInt(value);
                    finishPayment();
                } catch (Exception ignored) {
                }
            }
        });

        webView.loadUrl(paymentUrl);
    }

    private void finishPayment() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_PAYMENT_STATUS, paymentStatus);
        setResult(RESULT_OK, intent);
        finish();
    }

    public static Intent getLaunchIntent(Context mContext) {
        return new Intent(mContext, PaymentActivity.class);
    }
}
