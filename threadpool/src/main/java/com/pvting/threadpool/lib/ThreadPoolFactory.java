package com.pvting.threadpool.lib;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolFactory {

    private static final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private static final int KEEP_ALIVE_TIME = 20;
    private static final TimeUnit TIMEUNIT = TimeUnit.SECONDS;
    private static final int TASK_SIZE = 127;

    private ThreadPoolFactory() {
    }

    public static <R> Future<R> execute(Callable<R> callable) {
        return execute(callable, ThreadPoolType.CACHE, 0);
    }

    public static void execute(Runnable runnable) {
        execute(runnable, ThreadPoolType.CACHE, 0);
    }

    /**
     * 周期执行任务
     *
     * @param initialDelay 初始化时间
     * @param period       间隔时间
     * @param unit         时间单位
     */
    public static Future scheduledExecute(Runnable runnable, long initialDelay, long period, TimeUnit unit) {
        return getScheduledPool().scheduleAtFixedRate(runnable, initialDelay, period, unit);
    }

    /**
     * 执行任务
     *
     * @param type             所需线程池的类型
     * @param delayMillisecond 延迟的毫秒数
     * @param <R>              执行后结果
     */
    public static <R> Future<R> execute(final Callable<R> callable, final ThreadPoolType type, int delayMillisecond) {
        final FutureTask<R> mFuture = new FutureTask<R>(callable);

        if (delayMillisecond <= 0) {
            getThreadPool(type).execute(mFuture);
        } else {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getThreadPool(type).execute(mFuture);
                }
            }, delayMillisecond);
        }
        return mFuture;
    }

    /**
     * 执行任务
     *
     * @param type             所需线程池的类型
     * @param delayMillisecond 延迟的毫秒数
     */
    public static void execute(final Runnable runnable, final ThreadPoolType type, int delayMillisecond) {
        if (delayMillisecond <= 0) {
            getThreadPool(type).execute(runnable);
        } else {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getThreadPool(type).execute(runnable);
                }
            }, delayMillisecond);
        }
    }

    public static void executeOnUIThread(Runnable runnable) {
        mHandler.post(runnable);
    }

    public static void shutdown(ThreadPoolType type) {
        switch (type) {
            case SINGLE:
                shutdownThreadPool(mSingleThreadPool);
                break;
            case CACHE:
                shutdownThreadPool(mCacheThreadPool);
                break;
            case SCHEDULE:
                shutdownThreadPool(mScheduledExecutorService);
                break;
            default:
                shutdownThreadPool(mFixedThreadPool);
        }
    }

    private static void shutdownThreadPool(ThreadPoolExecutor executor) {
        if (executor != null && !executor.isShutdown()) {
            executor.shutdown();
        }
    }

    private static final Handler mHandler = new Handler(Looper.getMainLooper());

    private static ThreadPoolExecutor mFixedThreadPool = null;
    private static ThreadPoolExecutor mSingleThreadPool = null;
    private static ThreadPoolExecutor mCacheThreadPool = null;
    private static ScheduledThreadPoolExecutor mScheduledExecutorService = null;

    /**
     * 获取 周期执行任务
     * 注意ScheduledThreadPool 非核心线程数为最大整数，存在线程数开启过多资源耗尽的风险
     */
    private static ScheduledThreadPoolExecutor getScheduledPool() {
        return (ScheduledThreadPoolExecutor) getThreadPool(ThreadPoolType.SCHEDULE);
    }

    private static ThreadPoolExecutor getThreadPool(ThreadPoolType type) {
        switch (type) {
            case SINGLE:
                if (isThreadPoolEmpty(mSingleThreadPool)) {
                    mSingleThreadPool = SingleThreadPoolHolder.sSingleThreadPool;
                }
                return mSingleThreadPool;
            case SCHEDULE:
                if (isThreadPoolEmpty(mScheduledExecutorService)) {
                    mScheduledExecutorService = ScheduledThreadPoolHolder.sScheduledThreadPool;
                }
                return mScheduledExecutorService;
            case FIXED:
                if (isThreadPoolEmpty(mFixedThreadPool)) {
                    mFixedThreadPool = FixedThreadPoolHolder.sFixedThreadPool;
                }
                return mFixedThreadPool;
            default:
                if (isThreadPoolEmpty(mCacheThreadPool)) {
                    mCacheThreadPool = CacheThreadPoolHolder.sCacheThreadPool;
                }
                return mCacheThreadPool;
        }
    }

    private static boolean isThreadPoolEmpty(ThreadPoolExecutor executor) {
        return executor == null || executor.isShutdown() || executor.isTerminated();
    }

    private static class FixedThreadPoolHolder {
        private static final BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>(TASK_SIZE);
        static ThreadPoolExecutor sFixedThreadPool = new ThreadPoolExecutor(CORE_POOL_SIZE * 2 + 1, CORE_POOL_SIZE * 2 + 1,
            KEEP_ALIVE_TIME, TIMEUNIT, blockingQueue, new MyThreadFactory("fixed"),
            new ThreadPoolExecutor.DiscardPolicy());
    }

    private static class SingleThreadPoolHolder {
        private static final BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>(TASK_SIZE);
        static ThreadPoolExecutor sSingleThreadPool = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
            blockingQueue, new MyThreadFactory("single"),
            new ThreadPoolExecutor.AbortPolicy());
    }

    private static class ScheduledThreadPoolHolder {
        static ScheduledThreadPoolExecutor sScheduledThreadPool = new ScheduledThreadPoolExecutor(
            CORE_POOL_SIZE * 2 + 1,
            new MyThreadFactory("schedule"), new RejectHandler());
    }

    private static class CacheThreadPoolHolder {
        private static final BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>(TASK_SIZE);
        static ThreadPoolExecutor sCacheThreadPool = new ThreadPoolExecutor(0, CORE_POOL_SIZE * 2 + 1, 60L,
            TimeUnit.SECONDS, blockingQueue, new MyThreadFactory("cache"),
            new ThreadPoolExecutor.DiscardPolicy());
    }
}
