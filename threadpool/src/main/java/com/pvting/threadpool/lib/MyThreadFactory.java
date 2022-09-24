package com.pvting.threadpool.lib;

import android.util.Log;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class MyThreadFactory implements ThreadFactory {

    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;

    MyThreadFactory(String poolType) {
        namePrefix = "pool-" + poolType + "-thread-";
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, namePrefix + threadNumber.getAndIncrement());
        t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(){
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                //TODO:做一个订阅者，向外部通知线程池中的状态
                Log.d("CoreThreadFactory", t.getName() + " thrown an exception : ", e);
            }
        });
        return t;
    }
}
