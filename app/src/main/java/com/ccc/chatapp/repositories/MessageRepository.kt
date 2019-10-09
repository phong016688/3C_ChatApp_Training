package com.ccc.chatapp.repositories

import com.ccc.chatapp.data.model.User
import com.ccc.chatapp.data.source.local.sharedprf.SharedPrefsApi
import com.google.firebase.firestore.FirebaseFirestore

interface MessageRepository {
    fun getListMessage(fromUser: User, toUser: User)
}

class MessageRepositoryImpl(sharedPrefsApi: SharedPrefsApi) : MessageRepository {
    private var mFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var mListMessage: ArrayList<String> =  ArrayList()

    override fun getListMessage(fromUser: User, toUser: User) {
        
    }
}