package com.ccc.chatapp.repositories

import com.ccc.chatapp.data.model.User
import com.ccc.chatapp.data.source.local.sharedprf.SharedPrefsApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Single

interface UserRepository {
    fun login(username: String, password: String): Single<Any>
    fun logout()
    fun getCurrentUser(): User?
    fun signIn(user: User, password: String): Single<Any>
    fun isUserLogged(): Boolean
}

class UserRepositoryImpl(sharedPrefsApi: SharedPrefsApi) : UserRepository {
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var mFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var mUser: User? = null

    override fun isUserLogged(): Boolean {
        return mAuth.currentUser != null
    }

    override fun login(username: String, password: String): Single<Any> {
        return Single.create { emitter ->
            mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val currentUser = mAuth.currentUser ?: return@addOnCompleteListener
                        val dbRef = mFirestore.collection("user")
                            .document(currentUser.uid)
                        dbRef.get().addOnSuccessListener { snapshot ->
                            mUser = snapshot.toObject(User::class.java)
                        }
                        emitter.onSuccess(Unit)
                    }
                }.addOnFailureListener {
                    emitter.tryOnError(it)
                }
        }
    }

    override fun signIn(user: User, password: String): Single<Any> {
        mUser = user
        return Single.create { emitter ->
            mAuth.createUserWithEmailAndPassword(user.username, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val currentUser = mAuth.currentUser ?: return@addOnCompleteListener
                        val itemUser = hashMapOf<Any, Any>(
                            currentUser.uid to user
                        )
                        mFirestore.collection("user").add(itemUser)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    emitter.onSuccess(Unit)
                                }
                            }
                            .addOnFailureListener {
                                emitter.tryOnError(it)
                            }
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
            mUser = null
        }
    }

    override fun getCurrentUser(): User? {
        return mUser
    }
}
