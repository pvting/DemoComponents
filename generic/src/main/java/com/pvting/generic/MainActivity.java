package com.pvting.generic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.lang.reflect.Type;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextGenericClassSon t = new TextGenericClassSon();
        Type[] types =  TextGenericClassSon.class.getGenericInterfaces();
        for (int i = 0; i < types.length; i++) {
            System.out.println("=====11111:"+types[i]);
        }

        Type types2 =  TextGenericClassSon.class.getGenericSuperclass();
        System.out.println("=====22222:"+types2);
    }
}