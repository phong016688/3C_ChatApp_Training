package com.ccc.chatapp.repositories

import com.ccc.chatapp.data.model.Message
import com.ccc.chatapp.data.model.User
import com.ccc.chatapp.data.source.local.sharedprf.SharedPrefsApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.FieldValue
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
    fun setToUser(user: User)
    fun listenerListMessage(): Observable<Message>
    fun sendMessage(message: Message): Single<Any>
    fun removeListener()
    fun addFriend(username: String): Single<Any>
}

class UserRepositoryImpl(sharedPrefsApi: SharedPrefsApi) : UserRepository {

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var mFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var mUser: User? = null
    private var mToUser: User? = null
    private var mRegistrationListener: ListenerRegistration? = null

    override fun getListFriend(): Observable<User> = Observable.create<User> { emiter ->
        mFirestore.collection("user")
            .get()
            .addOnSuccessListener { snapshot ->
                for (document in snapshot.documents) {
                    mUser?.listFriendId?.forEach {
                        if (document.id.trim() == it.trim()) {
                            document.toObject(User::class.java)?.let { user ->
                                user.id = document.id.trim()
                                emiter.onNext(user)
                            }
                        }
                    }
                }
            }.addOnFailureListener {
                emiter.tryOnError(it)
            }
    }

    override fun isUserLogged(): Boolean = mAuth.currentUser != null

    override fun login(username: String, password: String): Single<Any> = Single.create { emitter ->
        mAuth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val currentUser = mAuth.currentUser ?: return@addOnCompleteListener
                    val dbRef = mFirestore.collection("user")
                        .document(currentUser.uid)
                    dbRef.get().addOnSuccessListener { snapshot ->
                        mUser = snapshot.toObject(User::class.java)
                        mUser?.id = currentUser.uid
                        emitter.onSuccess(Unit)
                    }
                }
            }.addOnFailureListener {
                emitter.tryOnError(it)
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
                                    mUser?.id = currentUser.uid
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

    override fun setUser(): Single<Any> = Single.create { emiter ->
        val currenUser = mAuth.currentUser ?: return@create
        mFirestore.collection("user")
            .document(currenUser.uid)
            .get()
            .addOnSuccessListener {
                mUser = it.toObject(User::class.java)
                mUser?.id = currenUser.uid
                emiter.onSuccess(Unit)
            }
            .addOnFailureListener {
                emiter.tryOnError(it)
            }
    }

    override fun getCurrentUser(): User? = mUser

    override fun setToUser(user: User) {
        mToUser = user
    }

    override fun listenerListMessage(): Observable<Message> =
        Observable.create<Message> { emitter ->
            mRegistrationListener = mFirestore.collection("user")
                .document("${mUser?.id?.trim()}")
                .collection("message")
                .document("${mToUser?.id?.trim()}")
                .collection("mess")
                .orderBy("timeSend", Query.Direction.ASCENDING)
                .addSnapshotListener { querySnapshot, exception ->
                    if (exception != null) {
                        emitter.tryOnError(exception)
                        return@addSnapshotListener
                    } else {
                        querySnapshot?.documentChanges?.forEach { documentChanges ->
                            if (documentChanges.type == DocumentChange.Type.ADDED) {
                                emitter.onNext(documentChanges.document.toObject(Message::class.java))
                            }
                        }
                    }
                }
        }

    override fun sendMessage(message: Message): Single<Any> = Single.create<Any> { emiter ->
        mFirestore.collection("user")
            .document("${mUser?.id?.trim()}")
            .collection("message")
            .document("${mToUser?.id?.trim()}")
            .collection("mess")
            .add(message)
            .addOnFailureListener {
                emiter.tryOnError(it)
            }
            .addOnSuccessListener {
                emiter.onSuccess(Unit)
            }
    }.flatMap {
        message.isMySend = false
        Single.create<Any> { emiter ->
            mFirestore.collection("user")
                .document("${mToUser?.id?.trim()}")
                .collection("message")
                .document("${mUser?.id?.trim()}")
                .collection("mess")
                .add(message)
                .addOnFailureListener {
                    emiter.tryOnError(it)
                }
                .addOnSuccessListener {
                    emiter.onSuccess(Unit)
                }
        }
    }

    override fun removeListener() {
        mRegistrationListener?.remove()
    }

    override fun addFriend(username: String): Single<Any> = Single.create<String> { emiter ->
        mFirestore.collection("user")
            .whereEqualTo("userName", username)
            .get()
            .addOnSuccessListener { querySnapshot ->
                querySnapshot.documents.forEach { document ->
                    if (mUser?.listFriendId?.contains(document.id.trim()) == false && document.id != mUser?.id) {
                        mUser?.listFriendId?.add(document.id)
                        emiter.onSuccess(document.id)
                    }
                }
            }
            .addOnFailureListener {
                emiter.tryOnError(it)
            }
    }
        .flatMap { idFriend ->
            Single.create<String> { emiter ->
                mFirestore.collection("user")
                    .document("${mUser?.id?.trim()}")
                    .update("listFriendId", FieldValue.arrayUnion(idFriend))
                    .addOnSuccessListener {
                        emiter.onSuccess(idFriend)
                    }
                    .addOnFailureListener {
                        emiter.tryOnError(it)
                    }
            }
        }
        .flatMap { idFriend ->
            Single.create<Any> { emiter ->
                mFirestore.collection("user")
                    .document(idFriend)
                    .update("listFriendId", FieldValue.arrayUnion(mUser?.id))
                    .addOnSuccessListener {
                        emiter.onSuccess(Unit)
                    }
                    .addOnFailureListener {
                        emiter.tryOnError(it)
                    }
            }
        }
}
