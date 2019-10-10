package com.ccc.chatapp.data.model

import com.google.firebase.Timestamp

data class Message(var message: String = "", var time: Timestamp = Timestamp.now(), var isSend: Boolean = true)
