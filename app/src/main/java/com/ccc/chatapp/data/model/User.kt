package com.ccc.chatapp.data.model

import com.google.firebase.firestore.PropertyName

data class User(
    @PropertyName("userName") var userName: String = "",
    @PropertyName("fullName") var fullName: String = "",
    @PropertyName("phone") var phone: String = "",
    @PropertyName("avatar") var avatar: String = "",
    @PropertyName("listFriendId") var listFriendId: ArrayList<String>? = null,
    var id: String = ""
)
