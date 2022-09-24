package com.pvting.threadpool.lib;

public enum ThreadPoolType {
    FIXED,//固定数量
    SINGLE,//单线程池
    CACHE,//缓存线程池
    SCHEDULE//周期执行任务线程池
}
