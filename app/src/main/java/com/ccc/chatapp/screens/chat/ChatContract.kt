package com.ccc.chatapp.screens.chat

import com.ccc.chatapp.screens.BasePresenter

interface ChatView {
    fun logout()
}

interface ChatPresenter : BasePresenter {
    fun logout()
}
