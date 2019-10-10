package com.ccc.chatapp.extension

import java.text.SimpleDateFormat
import java.util.Date

fun Long.toStringDate(): String {
    val format = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
    return format.format(Date(this))
}
