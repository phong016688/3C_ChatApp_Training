package com.ccc.chatapp.screens.chat

import com.ccc.chatapp.screens.BasePresenter

interface ChatView{
    fun logoutSuccess()
    fun logout()
}

interface ChatPresenter : BasePresenter{
    fun logout()
}
