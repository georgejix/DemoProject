#include "jniTest.h"
#include <android/log.h>

#define LOGI(format, ...)  __android_log_print(ANDROID_LOG_INFO,  "clog", format, ##__VA_ARGS__)

JNIEXPORT jint JNICALL Java_com_jni_JniTest_test
  (JNIEnv *env, jclass cls){
    LOGI("%s", "jnitest!");
    return 1;
  }