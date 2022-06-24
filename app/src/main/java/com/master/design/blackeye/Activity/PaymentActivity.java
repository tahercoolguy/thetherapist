package com.master.design.blackeye.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.master.design.blackeye.Controller.ContextWrapper;
import com.master.design.blackeye.Helper.User;
import com.master.design.blackeye.R;
import java.util.Locale;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int REQUEST_STATUS_MY_ORDER = 111;
    TextView txt_title;
    Button btn_done;
    ProgressBar progressBar;
    WebView webView;
    String bodyPayLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        mapping();
        initWebView();
    }

    private void initWebView() {
        if (getIntent() != null && getIntent().getExtras() != null) {
//            Transaction transaction = getIntent().getParcelableExtra("transaction");
            String url = getIntent().getStringExtra("PaymentUrl");
//            String url = "https://google.com";
            String invoiceid = getIntent().getStringExtra("invoiceid");

            bodyPayLoad = "anything=1";
//                    "total=" + transaction.getTotal()
//                    + "&merchnat_track_id=" + transaction.getMerchnat_track_id()
//                    + "&orders_id=" + transaction.getOrders_id()
//                    + "&user_email=" + transaction.getUser_email()
//                    + "&reference_no=" + transaction.getReference_no()
//                    + "&order_item_count=" + transaction.getOrder_item_count();
            loadWebView(url, bodyPayLoad);
        }
    }


    private void mapping() {
//        txt_title = findViewById(R.id.txt_title);
        webView = findViewById(R.id.webView);
        progressBar = findViewById(R.id.progressBar);
        btn_done = findViewById(R.id.btn_done);
        btn_done.setOnClickListener(this);
//        setTitle(R.string.payment);
    }


    @Override
    public void setTitle(int title) {
        super.setTitle("");
        txt_title.setText(title);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_done:
                if (status) {
                    Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
                    intent.putExtra("request_code", REQUEST_STATUS_MY_ORDER);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    PaymentActivity.this.finish();
                } else {
                    onBackPressed();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ContextWrapper.wrap(newBase, new Locale(new User(newBase).getLanguageCode())));
    }


    @SuppressLint("SetJavaScriptEnabled")
    private void loadWebView(String url, String postData) {
        Log.e("Request", url);
//        Log.e("Parameter", postData);

        progressBar.setVisibility(View.VISIBLE);
        webView.setVisibility(View.GONE);
        setWebViewSettings(webView);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.setWebViewClient(new MyWebViewClient());
//        webView.postUrl(url, postData.getBytes());
//        webView.postUrl(url, EncodingUtils.getBytes(postData, "BASE64"));
        webView.loadUrl(url);
    }

    boolean status = false;

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.e("MyWebViewClient", url);
            if (url.contains("invoice.php?m")) {
                btn_done.setVisibility(View.VISIBLE);
                status = true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            webView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            super.onPageFinished(view, url);
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    public static void setWebViewSettings(WebView webView) {
        webView.setBackgroundColor(Color.TRANSPARENT);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.setLayerType(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT ? View.LAYER_TYPE_HARDWARE : View.LAYER_TYPE_SOFTWARE, null);
    }


}
