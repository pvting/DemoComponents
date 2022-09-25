package com.pvting.optimizelayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Choreographer;

public class MainActivity extends AppCompatActivity {
    String TAG="optimizelayout";
    private long mLastFrameTime;
    private long mFrameCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
            @Override
            public void doFrame(long frameTimeNanos) {
                if (mLastFrameTime == 0) {
                    mLastFrameTime = frameTimeNanos;
                }
                float diff = (frameTimeNanos - mLastFrameTime) / 1000000.0f;//得到毫秒，正常是 16.66 ms
                if (diff > 500) {
                    double fps = (((double) (mFrameCount * 1000L)) / diff);
                    mFrameCount = 0;
                    mLastFrameTime = 0;
                    Log.d("doFrame", "doFrame: " + fps);
                } else {
                    ++mFrameCount;
                }
                Choreographer.getInstance().postFrameCallback(this);
            }
        });
    }
}