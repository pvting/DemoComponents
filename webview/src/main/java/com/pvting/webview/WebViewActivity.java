package com.pvting.webview;

import static android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;


public class WebViewActivity extends AppCompatActivity {

    private final static String URL = "url";
    private final static String TAG = "WebViewActivity";


    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        mWebView = findViewById(R.id.web_webview);

        String url = getIntent().getStringExtra(URL);
        if (!TextUtils.isEmpty(url)) {
            loadH5(url);
        }
    }

    public static void start(Context context, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(URL, url);
        context.startActivity(intent);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void loadH5(String url) {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setUseWideViewPort(true);
        if (Build.VERSION.SDK_INT >= 21) {
            this.mWebView.getSettings().setMixedContentMode(MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d(TAG, "shouldOverrideUrlLoading: "+url);
                if(!TextUtils.isEmpty(url) && url.endsWith("closingUrl")){
                    mWebView.loadUrl("https://www.baidu.com");
                    return true;
                }else {
                    return false;
                }
            }

            public void onPageFinished(WebView view, String url) {
                Log.d(TAG, "onPageFinished:url: "+url);
                if(!TextUtils.isEmpty(url) && url.endsWith("home#/getVip")){
                    mWebView.clearHistory();
                }else if(!TextUtils.isEmpty(url) && url.endsWith("proType=home#/toHome")){
                    WebViewActivity.this.finish();
                }
            }
        });
        mWebView.loadUrl(url);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
            } else {
                finish();
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}