#include "jniTest.h"
#include <android/log.h>

#define LOGI(format, ...)  __android_log_print(ANDROID_LOG_INFO,  "clog", format, ##__VA_ARGS__)

JNIEXPORT jint JNICALL Java_com_jni_JniTest_test
  (JNIEnv *env, jclass cls){
   LOGI("%s", "jnitest!");
   jclass clazz = (*env)->GetObjectClass(env, cls);
       jmethodID mID = (*env)->GetMethodID(env, clazz, "myCallback", "(I)V");
       (*env)->CallVoidMethod(env, cls, mID, 10);
   return 0;
  }

  /*JNIEXPORT void JNICALL Java_xxx_nativeDownload(JNIEnv *env, jobject thiz) {
      //直接用GetObjectClass找到Class, 也就是Sdk.class.
      jcalss jSdkClass =(*env)->GetObjectClass(env,thiz);
      if (jSdkClass == 0) {
          LOG("Unable to find class");
          return;
      }
      //找到需要调用的方法ID
      jmethodID javaCallback = (*env)->GetMethodID(env, jSdkClass,
                                                   "onProgressCallBack", "(JJ)I");
      //进行回调，ret是java层的返回值（这个有些场景很好用）
      jint ret = (*env)->CallIntMethod(env, thiz, javaCallback,1,1);
      return ;
  }
  或者是：
  JNIEXPORT void JNICALL Java_xxx_nativeDownload(JNIEnv *env, jobject thiz) {
      //直接用findClass找到Class, 也就是Sdk.class.
      jcalss jSdkClass =(*env)->FindClass(env,"your/package/name/Sdk");
      if (jSdkClass == 0) {
          LOG("Unable to find class");
          return;
      }
      //找到需要调用的方法ID
      jmethodID javaCallback = (*env)->GetMethodID(env, jSdkClass,
                                                   "onProgressCallBack", "(JJ)I");
     //这时候要回调还没有jobject，那就new 一个
      jmethodID sdkInit = (*env)->GetMethodID(env, jSdkClass,"<init>","()V");
      jobject jSdkObject = (*env)->NewObject(env,jSdkClass,sdkInit);
      //进行回调，ret是java层的返回值（这个有些场景很好用）
      jint ret = (*env)->CallIntMethod(env, jSdkObject, javaCallback,1,1);
      return ;
  }*/