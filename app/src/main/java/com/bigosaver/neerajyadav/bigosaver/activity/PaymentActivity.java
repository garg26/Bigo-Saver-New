package com.bigosaver.neerajyadav.bigosaver.activity;

import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bigosavers.R;
import com.bigosaver.neerajyadav.bigosaver.model.membership.PayuParams;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import simplifii.framework.activity.BaseActivity;
import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.utility.AppConstants;

public class PaymentActivity extends BaseActivity {
    private PayuParams payuParams;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        payuParams = (PayuParams) getIntent().getSerializableExtra(AppConstants.BUNDLE_KEYS.KEY_SERIALIZABLE_OBJECT);
        initToolBar(getString(R.string.title_payment));
        initWebView();
    }

    private void initWebView() {
        webView = (WebView) findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.addJavascriptInterface(new PayUJavaScriptInterface(), "PayUMoney");
//        webView.setWebChromeClient(new WebChromeClient() {
//        });
        webView.setWebViewClient(new MyBrowser());
        loadDataInWebView();
    }


    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);

            return true;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            hideProgressBar();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (payuParams.getSurl().equalsIgnoreCase(url)) {
                setResult(RESULT_OK);
                finish();
            } else if (payuParams.getFurl().equalsIgnoreCase(url)) {
                setResult(RESULT_FIRST_USER);
                finish();
            }
        }
    }

    private void getPaymentString() {
        HttpParamObject obj = payuParams.getHttpParam();
        executeTask(AppConstants.TASKCODES.PAYMENT_PAYU, obj);
    }

    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response, taskCode, params);
        switch (taskCode) {
            case AppConstants.TASKCODES.PAYMENT_PAYU:
                if (response != null) {
                    String dataToLoad = (String) response;
                }
                break;
        }
    }

    private void loadDataInWebView() {
//        webView.loadData(data, "text/html", "UTF-8");
//        url_s.append("https://test.payu.in/_payment");
        webView.postUrl(payuParams.URL, getPostString().getBytes(Charset.forName("UTF-8")));
        showProgressDialog();

//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//            }
//
//            @SuppressWarnings("unused")
//            public void onReceivedSslError(WebView view, SslErrorHandler handler) {
//                Log.e("Error", "Exception caught!");
//                handler.cancel();
//            }
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//
//        });
    }
    /*
    protected  void writeStatus(String str){
		txtview.setText(str);
	}*/

    private final class PayUJavaScriptInterface {
        PayUJavaScriptInterface() {
        }

        @JavascriptInterface
        public void success(long id, final String paymentId) {
            runOnUiThread(new Runnable() {
                public void run() {

                    showToast("Successful");
                    //String str="Status is txn is success "+" payment id is "+paymentId;
                    // new MainActivity().writeStatus(str);

                }
            });
        }

        @JavascriptInterface
        public void failure(long id, final String paymentId) {
            runOnUiThread(new Runnable() {
                public void run() {

                    showToast("Failure");
                }
            });
        }

    }


    private String getPostString() {
        String key = payuParams.getKey();
        String salt = payuParams.getSalt();
        String txnid = payuParams.getTxnId();
        String amount = payuParams.getAmount();
        String firstname = payuParams.getFirstname();
        String email = payuParams.getEmail();
        String productInfo = payuParams.getProductInfo();

        StringBuilder post = new StringBuilder();
        post.append("key=");
        post.append(key);
        post.append("&");
        post.append("txnid=");
        post.append(txnid);
        post.append("&");
        post.append("amount=");
        post.append(amount);
        post.append("&");
        post.append("productinfo=");
        post.append(productInfo);
        post.append("&");
        post.append("firstname=");
        post.append(firstname);
        post.append("&");
        post.append("email=");
        post.append(email);
        post.append("&");
        post.append("phone=");
        post.append(payuParams.getPhone());
        post.append("&");
        post.append("surl=");
        post.append(payuParams.getSurl());
        //https://www.payumoney.com/mobileapp/payumoney/success.php
        //https://www.payumoney.com/mobileapp/payumoney/failure.php
        post.append("&");
        post.append("furl=");
        post.append(payuParams.getFurl());
        post.append("&");

        StringBuilder checkSumStr = new StringBuilder();
        /* =sha512(key|txnid|amount|productinfo|firstname|email|udf1|udf2|udf3|udf4|udf5||||||salt) */
        MessageDigest digest = null;
        String hash;
        try {
            digest = MessageDigest.getInstance("SHA-512");// MessageDigest.getInstance("SHA-256");

            checkSumStr.append(key);
            checkSumStr.append("|");
            checkSumStr.append(txnid);
            checkSumStr.append("|");
            checkSumStr.append(amount);
            checkSumStr.append("|");
            checkSumStr.append(productInfo);
            checkSumStr.append("|");
            checkSumStr.append(firstname);
            checkSumStr.append("|");
            checkSumStr.append(email);
            checkSumStr.append("|||||||||||");
            checkSumStr.append(salt);

            digest.update(checkSumStr.toString().getBytes());

            hash = bytesToHexString(digest.digest());
            post.append("hash=");
            post.append(hash);
            post.append("&");
            Log.i(TAG, "SHA result is " + hash);
        } catch (NoSuchAlgorithmException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        post.append("service_provider=");
        post.append("payu_paisa");
        return post.toString();
    }


    private static String bytesToHexString(byte[] bytes) {
        // http://stackoverflow.com/questions/332079
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');

            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
