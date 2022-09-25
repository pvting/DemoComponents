package com.pvting.customcontrols;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Choreographer;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ToggleButton tb = findViewById(R.id.tb);

        tb.setBackImg(R.mipmap.slide_button_back);
        tb.setFrontImg(R.mipmap.slide_button_img);
        tb.setState(false);

    }

}