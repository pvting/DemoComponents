package com.pvting.threadpool.lib;

import android.util.Log;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public class RejectHandler implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        Log.d("JAVA", "任务队列已满 任务被拒绝了"+r.toString());
    }
}
