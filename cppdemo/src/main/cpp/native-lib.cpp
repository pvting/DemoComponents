#include <jni.h>
#include <string>
extern "C" JNIEXPORT jstring JNICALL

Java_com_pvting_cppdemo_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    char *bug=NULL;
    bug[0]=0;

    std::string hello = "Hello from C++3";
    return env->NewStringUTF(hello.c_str());
}