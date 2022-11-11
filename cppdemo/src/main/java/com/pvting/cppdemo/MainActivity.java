package com.pvting.cppdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = findViewById(R.id.tv);
//        tv.setText(stringFromJNI());

        testAnyArgs(true,99,11.11,12.12f,9999,"你好",null,null);
    }


    public native String stringFromJNI();

    static {
        System.loadLibrary("cppdemo");
    }

    //java的各种类型传递到C中
    public native void testAnyArgs(boolean b,int i,double d,float f,long l,String str,int[] intArray,String[] strArray);
}