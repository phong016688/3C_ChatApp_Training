package com.ccc.chatapp.data.model

import com.google.firebase.firestore.PropertyName

data class User(
    @PropertyName("userName") var username: String,
    @PropertyName("fullName") var fullName: String,
    @PropertyName("phone") var phone: String,
    @PropertyName("avatar") var avatar: String
)
