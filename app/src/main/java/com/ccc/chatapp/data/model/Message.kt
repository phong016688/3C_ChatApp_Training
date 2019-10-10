package com.ccc.chatapp.data.model

import com.google.firebase.firestore.PropertyName

data class Message(
    @PropertyName("text") var text: String = "",
    @PropertyName("timeSend") var timeSend: String = "",
    @PropertyName("isMySend") var isMySend: Boolean = false
)
