#include <jni.h>
#include <string>
#include <android/log.h>
#include "MovieController.hpp"

extern "C" JNIEXPORT jobject JNICALL
Java_com_smedic_hr_viewmodel_MoviesViewModel_getMovies(JNIEnv* env, jobject) {

    jclass arrayListClass = static_cast<jclass>(env->NewGlobalRef(env->FindClass("java/util/ArrayList")));
    jmethodID arrayListInit = env->GetMethodID(arrayListClass, "<init>", "(I)V");
    jmethodID arrayListSize = env->GetMethodID(arrayListClass, "size", "()I");
    jmethodID arrayListGet = env->GetMethodID(arrayListClass, "get", "(I)Ljava/lang/Object;");
    jmethodID arrayListAdd = env->GetMethodID(arrayListClass, "add", "(Ljava/lang/Object;)Z");

    movies::MovieController movieController = movies::MovieController();
    std::vector<movies::Movie *> results = movieController.getMovies();

    jobject result = env->NewObject(arrayListClass, arrayListInit, static_cast<int>(results.size()));

    for (auto movie : results) {
        jclass movieClass = env->FindClass("com/smedic/hr/model/Movie");
        jobject movieObject = env->AllocObject(movieClass);

        jfieldID nameField = env->GetFieldID(movieClass, "name", "Ljava/lang/String;");
        jfieldID lastUpdatedField = env->GetFieldID(movieClass, "lastUpdated", "I");

        env->SetObjectField(movieObject, nameField, env->NewStringUTF(movie->name.c_str()));
        env->SetIntField(movieObject, lastUpdatedField, movie->lastUpdated);

        env->CallBooleanMethod(result, arrayListAdd, movieObject);
        env->DeleteLocalRef(movieObject);
    }

    return result;
}

extern "C" JNIEXPORT jobject JNICALL
Java_com_smedic_hr_viewmodel_MoviesViewModel_getMovieDetails(JNIEnv* env, jobject, jstring name) {

    movies::MovieController movieController = movies::MovieController();

    const char *charArray = env->GetStringUTFChars(name, nullptr) ;
    movies::MovieDetail* movieDetail = movieController.getMovieDetail(charArray);

    //set movie details
    jclass movieDetailClass = env->FindClass("com/smedic/hr/model/MovieDetail");
    jobject movieDetailObject = env->AllocObject(movieDetailClass);

    //extract from here?
    auto arrayListClass = static_cast<jclass>(env->NewGlobalRef(env->FindClass("java/util/ArrayList")));
    jmethodID arrayListAdd = env->GetMethodID(arrayListClass, "add", "(Ljava/lang/Object;)Z");

    //set list of actors
    jmethodID arrayListInit = env->GetMethodID(arrayListClass, "<init>", "(I)V");
    jobject actors = env->NewObject(arrayListClass, arrayListInit, static_cast<int>(movieDetail->actors.size()));
    for (const auto& actor : movieDetail->actors) {
        jclass actorClass = env->FindClass("com/smedic/hr/model/Actor");
        jobject actorObject = env->AllocObject(actorClass);

        jfieldID nameField = env->GetFieldID(actorClass, "name", "Ljava/lang/String;");
        jfieldID ageField = env->GetFieldID(actorClass, "age", "I");
        jfieldID imageUrl = env->GetFieldID(actorClass, "imageUrl", "Ljava/lang/String;");

        env->SetObjectField(actorObject, nameField, env->NewStringUTF(actor.name.c_str()));
        env->SetIntField(actorObject, ageField, actor.age);
        env->SetObjectField(actorObject, imageUrl, env->NewStringUTF(actor.imageUrl.c_str()));

        env->CallBooleanMethod(actors, arrayListAdd, actorObject);
        env->DeleteLocalRef(actorObject);
    }

    jfieldID nameField = env->GetFieldID(movieDetailClass, "name", "Ljava/lang/String;");
    jfieldID scoreField = env->GetFieldID(movieDetailClass, "score", "F");
    jfieldID descriptionField = env->GetFieldID(movieDetailClass, "description", "Ljava/lang/String;");
    jfieldID actorsField = env->GetFieldID(movieDetailClass, "actors", "Ljava/util/List;");

    env->SetObjectField(movieDetailObject, nameField, env->NewStringUTF(movieDetail->name.c_str()));
    env->SetFloatField(movieDetailObject, scoreField, movieDetail->score);
    env->SetObjectField(movieDetailObject, descriptionField, env->NewStringUTF(movieDetail->description.c_str()));
    env->SetObjectField(movieDetailObject, actorsField, actors);

    //should clear GetStringUTFChars?
    return movieDetailObject;
}
