package com.pvting.aspectjaop.aspectj;

import android.util.Log;

public class TestAspectC {
    String TAG = "TestAspectC";
    public void print(String str){
        Log.d(TAG, "print: "+str);
    }
    public void print2(String str){
        Log.d(TAG, "print2: "+str);
    }
}
