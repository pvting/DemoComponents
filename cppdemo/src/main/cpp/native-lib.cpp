#include <jni.h>
#include <string>
#include <android/log.h>

#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,TAG,__VA_ARGS__)
#define TAG "peiweiwei"

extern "C" JNIEXPORT jstring JNICALL
Java_com_pvting_cppdemo_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    char *bug=NULL;
    bug[0]=0;

    std::string hello = "Hello from C++3";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT void JNICALL
Java_com_pvting_cppdemo_MainActivity_testAnyArgs(JNIEnv *env, jobject thiz, jboolean b,jint i, jdouble d,
                                                 jfloat f, jlong l, jstring str,
                                                 jintArray int_array, jobjectArray str_array) {
    //传过来的方法参数多了两个，工具集env和该方法所在的对象
    bool cb = b;
    int ci = i;
    double cd = d;
    float cf = f;
    long cl = l;
    LOGD("-------bool:%d\n",cb);
    LOGD("-------int:%d\n",ci);
    LOGD("-------double:%lf\n",cd);
    LOGD("-------float:%f\n",cf);
    LOGD("-------long:%ld\n",cl);

    const char * _str = env->GetStringUTFChars(str,NULL);
    LOGD("-------_str:%s\n",_str);
    env->ReleaseStringUTFChars(str,_str);//注意回收
}