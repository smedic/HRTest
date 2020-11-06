#include <jni.h>
#include <string>
#include <android/log.h>
#include "MovieController.hpp"

extern "C" JNIEXPORT jobject JNICALL
Java_com_smedic_hr_viewmodel_MoviesViewModel_getMovies(JNIEnv* env, jobject) {

    jclass arrayListClass = static_cast<jclass>(env->NewGlobalRef(env->FindClass("java/util/ArrayList")));
    jmethodID arrayListInit = env->GetMethodID(arrayListClass, "<init>", "(I)V");
    jmethodID arrayListAdd = env->GetMethodID(arrayListClass, "add", "(Ljava/lang/Object;)Z");

    movies::MovieController movieController = movies::MovieController();
    std::vector<movies::Movie *> results = movieController.getMovies();

    jobject result = env->NewObject(arrayListClass, arrayListInit, static_cast<int>(results.size()));

    for (auto movie : results) {
        jclass movieClass = env->FindClass("com/smedic/hr/model/Movie");
        jobject movieObject = env->AllocObject(movieClass);

        //set movie fields
        jfieldID idField = env->GetFieldID(movieClass, "id", "I");
        jfieldID nameField = env->GetFieldID(movieClass, "name", "Ljava/lang/String;");
        jfieldID lastUpdatedField = env->GetFieldID(movieClass, "lastUpdated", "I");
        jfieldID scoreField = env->GetFieldID(movieClass, "score", "F");
        jfieldID descriptionField = env->GetFieldID(movieClass, "description", "Ljava/lang/String;");
        jfieldID posterUrlField = env->GetFieldID(movieClass, "posterUrl", "Ljava/lang/String;");
        jfieldID trailerYouTubeIdField = env->GetFieldID(movieClass, "trailerYouTubeId", "Ljava/lang/String;");
        jfieldID actorsField = env->GetFieldID(movieClass, "actors", "Ljava/util/List;");

        env->SetObjectField(movieObject, nameField, env->NewStringUTF(movie->name.c_str()));
        env->SetIntField(movieObject, lastUpdatedField, movie->lastUpdated);
        env->SetFloatField(movieObject, scoreField, movie->score);
        env->SetObjectField(movieObject, descriptionField, env->NewStringUTF(movie->description.c_str()));
        env->SetObjectField(movieObject, posterUrlField, env->NewStringUTF(movie->posterUrl.c_str()));
        env->SetObjectField(movieObject, trailerYouTubeIdField, env->NewStringUTF(movie->trailerYouTubeId.c_str()));
        env->SetIntField(movieObject, idField, movie->id);

        //set list of actors
        jobject actors = env->NewObject(arrayListClass, arrayListInit, static_cast<int>(movie->actors.size()));
        for (const auto& actor : movie->actors) {
            jclass actorClass = env->FindClass("com/smedic/hr/model/Actor");
            jobject actorObject = env->AllocObject(actorClass);

            jfieldID actorNameField = env->GetFieldID(actorClass, "name", "Ljava/lang/String;");
            jfieldID ageField = env->GetFieldID(actorClass, "age", "I");
            jfieldID imageUrl = env->GetFieldID(actorClass, "imageUrl", "Ljava/lang/String;");
            jfieldID biographyUrl = env->GetFieldID(actorClass, "biographyUrl", "Ljava/lang/String;");

            env->SetObjectField(actorObject, actorNameField, env->NewStringUTF(actor.name.c_str()));
            env->SetIntField(actorObject, ageField, actor.age);
            env->SetObjectField(actorObject, imageUrl, env->NewStringUTF(actor.imageUrl.c_str()));
            env->SetObjectField(actorObject, biographyUrl, env->NewStringUTF(actor.biographyUrl.c_str()));

            env->CallBooleanMethod(actors, arrayListAdd, actorObject);
            env->DeleteLocalRef(actorObject);
        }
        env->SetObjectField(movieObject, actorsField, actors);

        env->CallBooleanMethod(result, arrayListAdd, movieObject);
        env->DeleteLocalRef(movieObject);
    }

    return result;
}