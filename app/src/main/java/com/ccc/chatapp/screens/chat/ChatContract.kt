package com.ccc.chatapp.screens.chat

import com.ccc.chatapp.screens.BasePresenter

interface ChatView {
    fun logout()
    fun moveFragment(position: Int)
}

interface ChatPresenter : BasePresenter {
    fun logout()
    fun toFragmentFriend()
    fun toFragmentMessage()
}
