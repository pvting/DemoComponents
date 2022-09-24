package com.pvting.threadpool;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.pvting.threadpool.lib.ThreadPoolFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Callable<String> r= new Callable() {
            @Override
            public Object call() {
                SystemClock.sleep(2000);
                String a="1234567890";
                System.out.println(a);
                return a;
            }
        };
        Future<String> future = ThreadPoolFactory.execute(r);
        ThreadPoolFactory.executeOnUIThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "hello", Toast.LENGTH_SHORT).show();
            }
        });
        ThreadPoolFactory.execute(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                String a="0987654321";
                System.out.println(a);
            }
        })
    }
}