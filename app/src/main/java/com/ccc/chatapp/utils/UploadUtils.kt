package com.ccc.chatapp.utils

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import io.reactivex.Observable
import io.reactivex.Single

object UploadUtils {
    fun getUrlAvatar(uri: Uri): Single<String> {
        val fireStorageRef = FirebaseStorage.getInstance()
            .reference.child("${Constant.Path.BASE_PATH_STORAGE}${uri.lastPathSegment}")
        return Single.create<String> { emitter ->
            fireStorageRef
                .putFile(uri).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        fireStorageRef.downloadUrl.addOnSuccessListener {
                            emitter.onSuccess(it.toString())
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
