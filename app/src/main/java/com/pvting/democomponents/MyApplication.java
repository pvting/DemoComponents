package com.pvting.democomponents;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;

public class MyApplication extends Application {
    String TAG="MyApplication-";
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                Log.d(TAG, "onActivityCreated: "+activity.getLocalClassName());
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {
                Log.d(TAG, "onActivityStarted: "+activity.getLocalClassName());
            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                Log.d(TAG, "onActivityResumed: "+activity.getLocalClassName());
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {
                Log.d(TAG, "onActivityPaused: "+activity.getLocalClassName());
            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {
                Log.d(TAG, "onActivityStopped: "+activity.getLocalClassName());
            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
                Log.d(TAG, "onActivitySaveInstanceState: "+activity.getLocalClassName());
            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                Log.d(TAG, "onActivityDestroyed: "+activity.getLocalClassName());
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Log.d(TAG, "attachBaseContext: ");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "onTerminate: ");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d(TAG, "onLowMemory: ");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.d(TAG, "onTrimMemory: ");
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "onConfigurationChanged: ");
    }

}
