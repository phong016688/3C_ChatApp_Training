package com.ccc.chatapp.screens.chat.chatfragment

import com.ccc.chatapp.data.model.Message
import com.ccc.chatapp.screens.BasePresenter

interface ChatFragmentView {
    fun updateListMessage(message: Message)
    fun getListMessage()
}

interface ChatFragmentPresenter : BasePresenter {
    fun getListMessage()
}
