package com.pvting.threadpool;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pvting.threadpool.lib.ThreadPoolFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity {
    String TAG="MyMainActivity";
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

        try {
            Log.d(TAG, "onCreate: "+future.get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
                Log.d(TAG, "run: "+a);
            }
        });
    }
}