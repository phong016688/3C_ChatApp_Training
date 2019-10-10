package com.ccc.chatapp.screens.chat.chatfragment

import com.ccc.chatapp.data.model.Message
import com.ccc.chatapp.screens.BasePresenter

interface ChatFragmentView {
    fun updateListMessage(message: Message)
    fun listenerListMessage()
    fun clearListMessage()
}

interface ChatFragmentPresenter : BasePresenter {
    fun listenerListMessage()
    fun onResume()
    fun onPause()
    fun sendMessage(message: Message)
}
