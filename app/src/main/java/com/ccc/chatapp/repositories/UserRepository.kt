package com.ccc.chatapp.repositories

import com.ccc.chatapp.data.source.local.sharedprf.SharedPrefsApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.reactivex.Single

interface UserRepository {
    fun login(username: String, password: String): Single<Any>
    fun logout()
    fun getCurrentUser(): FirebaseUser?
    fun signIn(username: String, password: String): Single<Any>
    fun isUserLogged(): Boolean
}

class UserRepositoryImpl(sharedPrefsApi: SharedPrefsApi) : UserRepository {
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun isUserLogged(): Boolean {
        return mAuth.currentUser != null
    }

    override fun login(username: String, password: String): Single<Any> {
        return Single.create { emitter ->
            mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        emitter.onSuccess(Unit)
                    }
                }.addOnFailureListener {
                    emitter.tryOnError(it)
                }
        }
    }

    override fun signIn(username: String, password: String): Single<Any> {
        return Single.create { emitter ->
            mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        emitter.onSuccess(Unit)
                    }
                }.addOnFailureListener {
                    emitter.tryOnError(it)
                }
        }
    }

    override fun logout() {
        if (mAuth.currentUser != null) {
            mAuth.signOut()
        }
    }

    override fun getCurrentUser(): FirebaseUser? {
        return mAuth.currentUser
    }
}
