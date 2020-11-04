#include <jni.h>
#include <string>
#include "MovieController.hpp"

extern "C" JNIEXPORT jobject JNICALL
Java_com_smedic_hr_MainActivity_getMovies(JNIEnv* env, jobject) {

    jclass arrayListClass = static_cast<jclass>(env->NewGlobalRef(env->FindClass("java/util/ArrayList")));
    jmethodID arrayListInit = env->GetMethodID(java_util_ArrayList, "<init>", "(I)V");
    jmethodID arrayListSize = env->GetMethodID(java_util_ArrayList, "size", "()I");
    jmethodID arrayListGet = env->GetMethodID(java_util_ArrayList, "get", "(I)Ljava/lang/Object;");
    jmethodID arrayListAdd = env->GetMethodID(java_util_ArrayList, "add", "(Ljava/lang/Object;)Z");

    movies::MovieController movieController = movies::MovieController();
    std::vector<movies::Movie *> results = movieController.getMovies();

    jobject result = env->NewObject(arrayListClass, arrayListInit, results.size());

    for (int i = 0; i < results.size(); i++) {
        movies::Movie *movie = results[i];
        jclass movieClass = env->FindClass("com/smedic/myapplication/model/Movie");
        jobject movieObject = env->AllocObject(movieClass);

        jfieldID nameField = env->GetFieldID(movieClass, "name", "Ljava/lang/String;");
        jfieldID lastUpdatedField = env->GetFieldID(movieClass, "lastUpdated", "I");

        env->SetObjectField(movieObject, nameField, env->NewStringUTF(movie->name.c_str()));
        env->SetIntField(movieObject, posterUrlField, env->NewStringUTF(movie->posterUrl.c_str()));
    }

    return result;
}
