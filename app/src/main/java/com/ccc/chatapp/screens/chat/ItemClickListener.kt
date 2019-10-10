package com.ccc.chatapp.screens.chat

import android.view.View
import com.ccc.chatapp.data.model.User

interface ItemClickListener {
    fun onClick(view: View?, user: User)
}
