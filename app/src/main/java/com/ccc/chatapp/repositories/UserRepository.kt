package com.ccc.chatapp.repositories

import com.ccc.chatapp.data.model.User
import com.ccc.chatapp.data.source.local.sharedprf.SharedPrefsApi
import io.reactivex.Single

interface UserRepository {
    fun login(username: String, password: String): Single<User>
}

class UserRepositoryImpl(sharedPrefsApi: SharedPrefsApi) : UserRepository {
    override fun login(username: String, password: String): Single<User> {
        return Single.create { emitter ->
            //TODO Call sign in api from firebase
        }
    }
}
