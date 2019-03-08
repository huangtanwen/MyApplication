package com.htw.main;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

public class UseHtml implements View.OnClickListener {
    private Context mContext = null;
    private View mHtmlLayout = null;
    private WebView mWebView = null;
    private Button mRedButton = null;
    private Button mColorButton = null;

    public UseHtml(View view, Context context) {
        mContext = context;
        mHtmlLayout = view;
        initView();
    }

    private void initView() {
        mWebView = (WebView) mHtmlLayout.findViewById(R.id.webWview);
        mRedButton = (Button) mHtmlLayout.findViewById(R.id.red);
        mColorButton = (Button) mHtmlLayout.findViewById(R.id.color);
        mRedButton.setOnClickListener(this);
        mColorButton.setOnClickListener(this);
        initWebView();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.red:  //调用JS中的无参数方法
                mWebView.loadUrl("javascript:setRed()");
                break;
            case R.id.color://调用JS中的有参数方法
                mWebView.loadUrl("javascript:setColor('#00f','这是android 原生调用JS代码的触发事件')");
                break;
        }
    }


    @SuppressLint("JavascriptInterface")
    private void initWebView() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        ButtonClick buttonClick = new ButtonClick();
        mWebView.addJavascriptInterface(buttonClick, buttonClick.toString());
        mWebView.loadUrl("file:///android_asset/testHybrid.html"); //加载assets文件中的H5页面
        //mWebView.loadUrl("http://www.baidu.com");
    }

    /**
     * H5页面按钮点击触发事件
     */
    class ButtonClick {

        //这是 button.click0() 的触发事件
        //H5调用方法：javascript:button.click0()
        @JavascriptInterface
        public void click0() {
            show("title", "");
        }

        //这是 button.click0() 的触发事件，可以传递待参数
        //H5调用方法：javascript:button.click0('参数1','参数2')
        @JavascriptInterface
        public void click0(String data1, String data2) {
            show(data1, data2);
        }


        @JavascriptInterface  //必须添加，这样才可以标志这个类的名称是 button
        public String toString() {
            return "button";
        }

        private void show(String title, String data) {
            new AlertDialog.Builder(mContext)
                    .setTitle(title)
                    .setMessage(data)
                    .setPositiveButton("确定", null)
                    .create().show();
        }
    }
}
