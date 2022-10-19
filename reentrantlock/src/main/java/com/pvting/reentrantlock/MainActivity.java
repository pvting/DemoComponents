package com.pvting.reentrantlock;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for (int i = 0; i < 10; i++) {
            BeanUtil.addClient(""+i,new Client());
        }
        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        SystemClock.sleep(1000);
                        BeanUtil.removeClient("6");
                    }
                }.start();

                BeanUtil.send0002Message();
            }
        });
    }
}