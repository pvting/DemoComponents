package com.pvting.webview;

import android.webkit.JavascriptInterface;

public class AndroidtoJs extends Object{
    // 定义JS需要调用的方法，必须使用注解@JavascriptInterface
    @JavascriptInterface
    public void hello(String msg){
        System.out.println("Android："+msg);
    }
}