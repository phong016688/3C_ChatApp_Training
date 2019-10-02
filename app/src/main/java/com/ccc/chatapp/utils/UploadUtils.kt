package com.ccc.chatapp.utils

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import io.reactivex.Observable

object UploadUtils {
    fun getUrlAvatar(uri: Uri): Observable<String> {
        val fireStorageRef = FirebaseStorage.getInstance()
            .reference.child("${Constant.Path.BASE_PATH_STORAGE}${uri.lastPathSegment}")
        return Observable.create<String> { emitter ->
            fireStorageRef
                .putFile(uri).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        fireStorageRef.downloadUrl.addOnSuccessListener {
                            emitter.onNext(it.toString())
                        }.addOnFailureListener {
                            emitter.tryOnError(it)
                        }
                    }
                }
                .addOnFailureListener {
                    emitter.tryOnError(it)
                }
        }
    }
}
