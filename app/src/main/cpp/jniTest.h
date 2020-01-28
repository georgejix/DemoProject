#ifndef JNITEST.H_SRC
#define JNITEST.H_SRC

#include <jni.h>

JNIEXPORT jint JNICALL Java_com_jni_JniTest_test
  (JNIEnv *, jclass);

#endif