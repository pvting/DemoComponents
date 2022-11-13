#include <jni.h>
#include <string>
#include <android/log.h>

#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,TAG,__VA_ARGS__);
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
    //1.传过来的方法参数多了两个，工具集env和该方法所在的对象
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

    //2.int类型数组的操作
    int intLen = env->GetArrayLength(int_array);
    jint* _intP = env->GetIntArrayElements(int_array,NULL);
    for (int i = 0; i < intLen; ++i) {
        //获取到数组中所有的值
        LOGD("C++ _intP value:%d\n",*(_intP+i))
        //修改数据的值，java中会变化吗？,可以的，但需要下一句刷新
        *(_intP+i) = *(_intP+i) + 100;
    }
    //JNI_OK代表先使用env把修改刷新到java，然后释放
    //JNI_COMMIT 使用env把修改刷新到java
    //JNI_ABORT 释放
    env->ReleaseIntArrayElements(int_array,_intP,JNI_OK);

    //3.String类型数据的操作
    int intLen2 = env->GetArrayLength(str_array);
    for (int i = 0; i < intLen2; i++) {
        jstring _jstr = (jstring)env->GetObjectArrayElement(str_array,i);
        const char * _str = env->GetStringUTFChars(_jstr, NULL);
        //获取到数组中所有的值
        LOGD("C++_str value:%s\n",_str)
        //设置数组中的值
        jstring newValue = env->NewStringUTF("newvalue");
        env->SetObjectArrayElement(str_array,i,newValue);

        env->ReleaseStringUTFChars(_jstr,_str);
    }


}
extern "C"
JNIEXPORT void JNICALL
Java_com_pvting_cppdemo_MainActivity_testObject(JNIEnv *env, jobject thiz, jobject stu,jstring jstr) {
    //4.java给C++传一个对象，读取值，修改值，调用方法
    const char * _str = env->GetStringUTFChars(jstr,NULL);
    jclass stuclass = env->GetObjectClass(stu);//步骤一：直接通过对象获取class
    jmethodID toString = env->GetMethodID(stuclass,"toString","()Ljava/lang/String;");//步骤二：获取method
    jstring results = (jstring) env->CallObjectMethod(stu, toString); //步骤三：调用方法
    const char * result = env->GetStringUTFChars(results,NULL);
    LOGD("####%s",result)
    env->ReleaseStringUTFChars(results,result);

    jmethodID setNameMethod = env->GetMethodID(stuclass,"setName", "(Ljava/lang/String;)V");
    jstring names = env->NewStringUTF(_str);
    env->CallVoidMethod(stu,setNameMethod,names);

    env->DeleteLocalRef(stuclass);
}

jclass stu = nullptr;
extern "C"
JNIEXPORT void JNICALL
Java_com_pvting_cppdemo_MainActivity_createObject(JNIEnv *env, jobject thiz) {
    //创建一个JAVA对象，并调用方法
    jclass stuClass = env->FindClass("com/pvting/cppdemo/Stu");
    jobject person = env->AllocObject(stuClass);

    jmethodID setName = env->GetMethodID(stuClass, "setName", "(Ljava/lang/String;)V");
    jstring name1 = env->NewStringUTF("李四"); env->CallVoidMethod(person, setName, name1);
    //注意释放DeleteLocalRef & ReleaseStringUTFChars

    //创建一个全局变量，jni会出现弹栈回收的情况，长周期变量需要设置成全局引用
    if(!stu){
        jclass tempStu=env->FindClass("com/pvting/cppdemo/Stu");
        stu = (jclass)env->NewGlobalRef((jobject)tempStu);//提升为全局引用
        env->DeleteLocalRef(tempStu);
    }
    jmethodID dogInit = env->GetMethodID(stu, "<init>", "()V");
    jobject dog = env->NewObject(stu, dogInit);
    jmethodID dogInit1 = env->GetMethodID(stu, "<init>", "(I)V");
    jobject dog1 = env->NewObject(stu, dogInit1, 666);
    jmethodID dogInit2 = env->GetMethodID(stu, "<init>", "(II)V");
    jobject dog2 = env->NewObject(stu, dogInit2, 777, 888888);
}
extern "C"
JNIEXPORT void JNICALL
Java_com_pvting_cppdemo_MainActivity_reflectStaticMethod(JNIEnv *env, jobject thiz) {
    jclass jclass1 = env->FindClass("com/pvting/cppdemo/MainActivity");
    jmethodID jmethodId = env->GetStaticMethodID(jclass1,"doStatic","(Ljava/lang/String;)V");
    jstring info = env->NewStringUTF("fff");
    env->CallStaticVoidMethod(jclass1,jmethodId,info);
    env->DeleteLocalRef(jclass1);
    env->DeleteLocalRef(info);
}