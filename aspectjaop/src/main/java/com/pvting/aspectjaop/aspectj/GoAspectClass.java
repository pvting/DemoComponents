package com.pvting.aspectjaop.aspectj;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class GoAspectClass {
    String TAG = "GoAspectClass";

    @Pointcut("execution(* com.pvting.aspectjaop.aspectj.TestAspectC.print(..))")
    public void method() {}

    @Pointcut("execution(* android.app.Activity.onCreate(..))")
    public void method2() {}

    @Around("method()")
    public void methodAroundTest(ProceedingJoinPoint joinPoint){
        Log.d(TAG, "before: 1");
        try {
            joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        Log.d(TAG, "after: 1");
    }

    @Around("method2()")
    public void methodAroundTest2(ProceedingJoinPoint joinPoint){
        long start = System.currentTimeMillis();
        try {
            joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        Log.d(TAG, "oncreate time:"+(end- start));
    }
//@Around("method() && @annotation(singleClick)")
//public void aroundJoinPoint(ProceedingJoinPoint joinPoint, SingleClick singleClick) throws Throwable {
//    Log.e("aaaaa", "发生调用了22");
//}

    @Before("method()")
    public void before(){
        Log.d(TAG, "before: 2");
    }

    @After("method()")
    public void after(){
        Log.d(TAG, "after: 2");
    }
//
//    @Around("around()")
//    public void around(){
//        Log.d(TAG, "around: ");
//    }
}
