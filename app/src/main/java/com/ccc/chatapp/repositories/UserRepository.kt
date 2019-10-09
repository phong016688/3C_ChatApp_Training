package com.ccc.chatapp.repositories

import com.ccc.chatapp.data.model.User
import com.ccc.chatapp.data.source.local.sharedprf.SharedPrefsApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Observable
import io.reactivex.Single

interface UserRepository {
    fun login(username: String, password: String): Single<Any>
    fun logout()
    fun getCurrentUser(): User?
    fun signIn(user: User, password: String): Single<Any>
    fun isUserLogged(): Boolean
    fun getListFriend(): Observable<User>
    fun setUser(): Single<Any>
}

class UserRepositoryImpl(sharedPrefsApi: SharedPrefsApi) : UserRepository {

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var mFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var mUser: User? = null

    override fun getListFriend(): Observable<User> {
        return Observable.create<DocumentSnapshot> { emiter ->
            mFirestore.collection("user")
                .get()
                .addOnSuccessListener { snapshort ->
                    for (i in snapshort.documents) {
                        mUser?.listFriendId?.forEach {
                            if (i.id.trim() == it.trim())
                                emiter.onNext(i)
                        }
                    }
                }.addOnFailureListener {
                    emiter.tryOnError(it)
                }
        }
            .map {
                it.toObject(User::class.java)
            }
    }

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
                            emitter.onSuccess(Unit)
                        }
                    }
                }.addOnFailureListener {
                    emitter.tryOnError(it)
                }
        }
    }

    override fun signIn(user: User, password: String): Single<Any> {
        mUser = user
        return Single.create { emitter ->
            mAuth.createUserWithEmailAndPassword(user.userName, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val currentUser = mAuth.currentUser ?: return@addOnCompleteListener
                        mFirestore.collection("user").document(currentUser.uid).set(user)
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

    override fun setUser(): Single<Any> {
        return Single.create { emiter ->
            val currenUser = mAuth.currentUser ?: return@create
            mFirestore.collection("user")
                .document(currenUser.uid)
                .get()
                .addOnSuccessListener {
                    mUser = it.toObject(User::class.java)
                    emiter.onSuccess(Unit)
                }
                .addOnFailureListener {
                    emiter.tryOnError(it)
                }
        }
    }

    override fun getCurrentUser(): User? {
        return mUser
    }
}
